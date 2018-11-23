package org.myaldoc.proxyserver.microservices.services.impl;

import org.myaldoc.proxyserver.microservices.model.MicroService;
import org.myaldoc.proxyserver.microservices.repository.MicroServicesRepository;
import org.myaldoc.proxyserver.microservices.services.MicroServicesService;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MicroServicesServiceImpl implements MicroServicesService {

  private DiscoveryClient discoveryClient;
  private MicroServicesRepository microServicesRepository;

  public MicroServicesServiceImpl(DiscoveryClient discoveryClient, MicroServicesRepository microServicesRepository) {
    this.discoveryClient = discoveryClient;
    this.microServicesRepository = microServicesRepository;
  }

  @Override
  public void saveAllRegisteredServices() {

    microServicesRepository.deleteAll();

    discoveryClient.getServices().forEach(serviceName -> {
      discoveryClient.getInstances(serviceName).forEach(instance -> {
        final MicroService microService = extractMicroServiceFromServiceInstance(instance);
        microService.setWeight(0L);
        microServicesRepository.save(microService);
      });
    });
  }

  @Override
  public MicroService retrieveMicroService(String name) {
    MicroService microService = null;
    List<MicroService> microServices = null;
    final int nbRegsiteredService = this.discoveryClient.getInstances(name).size();

    do {
      microServices = microServicesRepository.findByName(name);
      if (microServices.size() != nbRegsiteredService) this.saveAllRegisteredServices();
    } while (microServices.size() != nbRegsiteredService);

    final List<Long> weights = microServices
            .stream()
            .map(ms -> ms.getWeight())
            .collect(Collectors.toList());

    final Long min = Collections.min(weights);

    if (Objects.nonNull(min)) {
      microService = microServices.stream()
              .filter(ms -> ms.getWeight() == min)
              .findFirst().orElseThrow();
    } else {
      microService = microServices.get(0);
    }
    microService.setWeight(microService.getWeight() + 1);

    return this.microServicesRepository.save(microService);
  }

  private MicroService extractMicroServiceFromServiceInstance(ServiceInstance serviceInstance) {
    return MicroService.builder()
            .name(serviceInstance.getServiceId().toLowerCase())
            .scheme(serviceInstance.getScheme())
            .host(serviceInstance.getHost())
            .port(serviceInstance.getPort())
            .build();
  }
}
