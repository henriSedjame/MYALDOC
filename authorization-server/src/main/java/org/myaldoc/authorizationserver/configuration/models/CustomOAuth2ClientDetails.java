package org.myaldoc.authorizationserver.configuration.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.myaldoc.core.utils.IterableUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
@ConfigurationProperties("client-details")
public class CustomOAuth2ClientDetails {

  public static final String SEPARATOR = ",";
  public static final int DEFAULT_TOKEN_VALIDITY_SECONDS = 3600;

  private String clientId;
  private String clientSecret;
  private String grantTypes;
  private String scopes;
  private String redirectUris;
  private String tokenValiditySeconds;
  private String refreshTokenValiditySecond;
  private String resourceIds;

  public Integer getRefreshTokenValiditySeconds() {
    return StringUtils.isEmpty(this.getRefreshTokenValiditySecond()) ? DEFAULT_TOKEN_VALIDITY_SECONDS : Integer.parseInt(this.getRefreshTokenValiditySecond());
  }
  public Integer getAccesTokenValiditySeconds() {
    return StringUtils.isEmpty(this.getTokenValiditySeconds()) ? DEFAULT_TOKEN_VALIDITY_SECONDS : Integer.parseInt(this.getTokenValiditySeconds());
  }

  public String[] getRegisteredRedirectUris() {
    return IterableUtils.stringtoArray(this.getRedirectUris(), ",");
  }

  public String[] getAuthorizedGrantTypes() {
    return IterableUtils.stringtoArray(this.getGrantTypes(), ",");
  }

  public String[] getRegisteredResourceIds() {
    return IterableUtils.stringtoArray(this.getResourceIds(), ",");
  }



}
