package org.myaldoc.proxyserver.security.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 11/11/2018
 * @Class purposes : .......
 */
@Configuration
@Order(0)
@EnableOAuth2Sso
public class Securityconfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**", "/login").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("http://localhost:9000/auth/exit");
    }

}
