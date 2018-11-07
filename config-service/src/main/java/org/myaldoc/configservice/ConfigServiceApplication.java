package org.myaldoc.configservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableConfigServer
@SpringBootApplication
public class ConfigServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ConfigServiceApplication.class, args);
  }

  /**
   * Déclaration d'un bean RestTemplate
   * pour le test unitaire de chargement
   * de la configuration depuis le repo git
   *
   * @return
   */
  @Bean
  RestTemplate template() {
    return new RestTemplate();
  }
}
