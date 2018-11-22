package org.myaldoc.proxyserver.microservices.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class MicroService {
  @Id
  private String id;
  private String name;
  private int port;
  private String host;

}
