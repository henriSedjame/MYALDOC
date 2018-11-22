package org.myaldoc.proxyserver.microservices.repository;

import org.myaldoc.proxyserver.microservices.model.MicroService;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface MicroServicesRepository extends MongoRepository<MicroService, String> {

  List<MicroService> findByName(String serviceName);

  MicroService findByNameAndPort(String serviceName, int port);
}
