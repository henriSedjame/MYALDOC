package org.myaldoc.proxyserver.microservices.services;

import org.myaldoc.proxyserver.microservices.model.MicroService;

public interface MicroServicesService {

  void saveAllRegisteredServices();

  MicroService retrieveMicroService(String name, int port);

}
