package org.myaldoc.authorizationserver.configuration.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class MyaldocClientDetails {

  public static final String SEPARATOR = ",";
  public static final int DEFAULT_TOKEN_VALIDITY_SECONDS = 3600;

  @Value("${clientId}")
  private String clientId;
  @Value("${clientSecret}")
  private String clientSecret;
  @Value("${grantTypes}")
  private String grantTypes;
  @Value("${scopes}")
  private String scopes;
  @Value("${redirectUris}")
  private String redirectUris;
  @Value("${tokenValiditySeconds}")
  private String tokenValiditySeconds;
  @Value("${resourceIds}")
  private String resourceIds;

  public Integer getAccesTokenValiditySeconds() {
    return StringUtils.isEmpty(this.getTokenValiditySeconds()) ? DEFAULT_TOKEN_VALIDITY_SECONDS : Integer.parseInt(this.getTokenValiditySeconds());
  }
}
