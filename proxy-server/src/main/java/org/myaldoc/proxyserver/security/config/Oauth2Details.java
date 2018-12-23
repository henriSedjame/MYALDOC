package org.myaldoc.proxyserver.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 23/12/2018
 * @Class purposes : .......
 */
@Component
@ConfigurationProperties("security.oauth2.client")
@Getter
@Setter
public class Oauth2Details {
    private String clientId;
    private String clientSecret;
    private String accessTokenUri;
}
