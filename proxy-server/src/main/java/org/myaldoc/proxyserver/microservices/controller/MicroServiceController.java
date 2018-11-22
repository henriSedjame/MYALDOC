package org.myaldoc.proxyserver.microservices.controller;

import org.myaldoc.proxyserver.microservices.model.MicroService;
import org.myaldoc.proxyserver.microservices.services.MicroServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registry")
public class MicroServiceController {

  @Autowired
  private MicroServicesService microServicesService;

  @GetMapping("/info")
  public ResponseEntity<MicroService> getMicroService(@RequestParam(name = "name") String name, @RequestParam(name = "port") String port) {
    return new ResponseEntity<>(this.microServicesService.retrieveMicroService(name, Integer.parseInt(port)), HttpStatus.ACCEPTED);
  }
}
