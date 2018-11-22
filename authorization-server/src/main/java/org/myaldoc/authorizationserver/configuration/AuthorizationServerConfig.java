package org.myaldoc.authorizationserver.configuration;

import org.myaldoc.authorizationserver.configuration.model.CustomOAuth2ClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  @Autowired
  private CustomOAuth2ClientDetails clientDetails;

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security.tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

    clients
            .inMemory()
            .withClient(clientDetails.getClientId())
            .secret(passwordEncoder.encode(clientDetails.getClientSecret()))
            .authorizedGrantTypes(clientDetails.getAuthorizedGrantTypes())
            .redirectUris(clientDetails.getRegisteredRedirectUris())
            .scopes(clientDetails.getScopes())
            .accessTokenValiditySeconds(clientDetails.getAccesTokenValiditySeconds())
            .refreshTokenValiditySeconds(clientDetails.getRefreshTokenValiditySeconds())
            .resourceIds(clientDetails.getRegisteredResourceIds())
            .autoApprove(true);
  }

}
