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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectionControllerTest {

  private static final String USER_CREATE = "/user/create";
  private static final String USER_DELETE = "/user/delete";
  private static final String USER_ACTIVATE = "/user/activate";
  private static final String USERNAME = "guest";
  private static final String PASSWORD = "Hiphop!87";
  private static final String USER_ID = "14jkkltest";
  private static final String EMAIL = "guest@gmail.com";
  private User user;

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
            .returnResult(User.class)
            .consumeWith(
                    result -> {
                      Flux<User> u = result.getResponseBody();
                      StepVerifier.create(u)
                              .assertNext(us -> {
                                assertNotNull(u);
                                assertEquals(USERNAME, us.getUsername());
                                assertEquals(EMAIL, us.getEmail());
                                assertFalse(us.isEnabled());
                              }).expectComplete()
                              .verify();
                    }
            );
  }

  @Test
  public void activateUser() {
    webTestClient
            .get()
            .uri(USER_ACTIVATE + "/" + USER_ID)
            .exchange()
            .expectStatus()
            .isOk()
            .returnResult(User.class)
            .consumeWith(result -> {
              Flux<User> body = result.getResponseBody();
              StepVerifier.create(body)
                      .assertNext(u -> {
                        assertNotNull(u);
                        assertTrue(u.isEnabled());
                      });
            });
  }

  @Test
  public void deleteUser() {
    webTestClient
            .delete()
            .uri(USER_DELETE + "/" + USER_ID)
            .exchange()
            .expectStatus()
            .isOk();
  }

}