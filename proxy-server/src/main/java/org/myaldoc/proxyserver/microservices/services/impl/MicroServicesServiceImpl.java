package org.myaldoc.proxyserver.microservices.services.impl;

import org.myaldoc.proxyserver.microservices.model.MicroService;
import org.myaldoc.proxyserver.microservices.repository.MicroServicesRepository;
import org.myaldoc.proxyserver.microservices.services.MicroServicesService;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        microServicesRepository.save(MicroService.builder()
                .name(serviceName)
                .host(instance.getHost())
                .port(instance.getPort())
                .build()
        );
      });
    });
  }

  @Override
  public MicroService retrieveMicroService(String name, int port) {

    Optional<ServiceInstance> serviceInstance = discoveryClient.getInstances(name).stream()
            .filter(instance -> instance.getPort() == port).findFirst();

    if (serviceInstance.isPresent())
      return MicroService.builder()
              .name(serviceInstance.get().getServiceId())
              .host(serviceInstance.get().getHost())
              .port(serviceInstance.get().getPort())
              .build();

    return this.microServicesRepository.findByNameAndPort(name, port);
  }
}
