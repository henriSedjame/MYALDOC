package org.myaldoc.authorizationserver.configuration.controllers;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@RestController
public class AuthRestController {

  @GetMapping("/user")
  public Principal user(Principal principal) {
    return principal;
  }

  @GetMapping("/exit")
  public void exit(HttpServletRequest request, HttpServletResponse response) {
    new SecurityContextLogoutHandler().logout(request, null, null);
    try {
      response.sendRedirect(request.getHeader("referer"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
