package org.myaldoc.authorizationserver.configuration;

import lombok.extern.slf4j.Slf4j;
import org.myaldoc.authorizationserver.configuration.handlers.ConnexionFailureHandler;
import org.myaldoc.authorizationserver.configuration.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableResourceServer
@Order(1)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  CustomUserDetailsService service;
  @Autowired
  BCryptPasswordEncoder passwordEncoder;
  @Autowired
  ConnexionFailureHandler failureHandler;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
            .userDetailsService(service)
            .passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
            .csrf().disable()
            .requestMatchers()
            .antMatchers("/login", "/oauth/authorize", "/exit")
            .and()
            .authorizeRequests()
            .antMatchers("/login").permitAll()
            .anyRequest().authenticated()
            .and().formLogin()
            .loginPage("/login").permitAll()
            .failureHandler(this.failureHandler)

            .and().logout().logoutUrl("/logout")
            .permitAll();
  }

}
