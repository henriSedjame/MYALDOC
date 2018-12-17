package org.myaldoc.authorizationserver.connection.resources.mvc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.myaldoc.authorizationserver.connection.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectionControllerTest {

  public static final String USER_CREATE = "/user/create";
  public static final String USER_DELETE = "/user/delete";
  public static final String USER_ACTIVATE = "/user/activate";
  public static final String USERNAME = "guest";
  public static final String PASSWORD = "Hiphop!87";
  public static final String USER_ID = "14jkkltest";
  public static final String EMAIL = "guest@gmail.com";
  User user;
  @Autowired
  private ConnectionController controller;
  private WebTestClient webTestClient;

  @Before
  public void setUp() throws Exception {
    webTestClient = WebTestClient
            .bindToController(controller)
            .build();

    user = User.builder()
            .id(USER_ID)
            .username(USERNAME)
            .password(PASSWORD)
            .email(EMAIL)
            .build();
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void createUser() {

    webTestClient
            .post()
            .uri(USER_CREATE)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(user), User.class)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody(User.class)
            .consumeWith(
                    result -> {
                      User u = result.getResponseBody();
                      assertNotNull(u);
                      assertEquals(USERNAME, u.getUsername());
                      assertEquals(EMAIL, u.getEmail());
                      assertFalse(u.isEnabled());
                    }
            );
  }

  @Test
  public void activateUser() {
  }

  @Test
  public void deleteUser() {
  }

}