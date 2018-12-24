package org.myaldoc.gestionpersonnesservice.api;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 24/12/2018
 * @Class purposes : .......
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public Mono<String> getTestMessage(OAuth2Authentication auth) {
        return Mono.just("Hello " + auth.getName());
    }
}
