package org.myaldoc.proxyserver.microservices.repository;

import org.myaldoc.proxyserver.microservices.model.MicroService;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MicroServicesRepository extends MongoRepository<MicroService, String> {

  MicroService findByName(String serviceName);

  MicroService findByNameAndPort(String serviceName, int port);
}
