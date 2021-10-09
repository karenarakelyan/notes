package com.bestnotes.users.presentation.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.bestnotes.users.domain.entity.User;
import com.bestnotes.users.domain.service.UserService;
import com.bestnotes.users.error.ErrorResponse;
import com.bestnotes.users.presentation.dto.UserResponse;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import javax.persistence.EntityNotFoundException;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UsersRouterTest extends BaseApiTest {

  @MockBean
  private UserService userService;

  @Test
  public void testGet() {
    final UUID id = UUID.randomUUID();
    final String email = "test@bestnotes.com";

    final User user = buildUserObject(id, email);

    when(userService.get(id)).thenReturn(Mono.just(user));

    final UserResponse result = webTestClient.get().
        uri("/users/{userId}", id)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(UserResponse.class)
        .returnResult().getResponseBody();

    assertNotNull(result);
    assertEquals(id, result.getId());
    assertEquals(email, result.getEmail());
    assertNotNull(result.getCreatedOn());
    assertNotNull(result.getUpdatedOn());
  }


  @Test
  public void testGetAll() {
    final UUID id = UUID.randomUUID();
    final String email = "test@bestnotes.com";

    final User user = buildUserObject(id, email);

    when(userService.getAllUsers()).thenReturn(Flux.fromStream(Stream.of(user)));

    final List<UserResponse> result = webTestClient.get().
        uri("/users/")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(UserResponse.class)
        .returnResult().getResponseBody();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(id, result.get(0).getId());
    assertEquals(email, result.get(0).getEmail());
    assertNotNull(result.get(0).getCreatedOn());
    assertNotNull(result.get(0).getUpdatedOn());
  }

  @Test
  public void testIllegalArgumentExceptionScenario() {
    final UUID id = UUID.randomUUID();
    final String errorMessage = "You provided something bad";

    when(userService.get(id)).thenReturn(Mono.error(new IllegalArgumentException(errorMessage)));

    final ErrorResponse result = webTestClient.get().
        uri("/users/{userId}", id)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody(ErrorResponse.class)
        .returnResult().getResponseBody();

    assertNotNull(result);
    assertEquals(errorMessage, result.getMessage());
  }

  @Test
  public void testEntityNotFoundExceptionScenario() {
    final UUID id = UUID.randomUUID();
    final String errorMessage = "You provided something bad";

    when(userService.get(id)).thenReturn(Mono.error(new EntityNotFoundException(errorMessage)));

    final ErrorResponse result = webTestClient.get().
        uri("/users/{userId}", id)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isNotFound()
        .expectBody(ErrorResponse.class)
        .returnResult().getResponseBody();

    assertNotNull(result);
    assertEquals(errorMessage, result.getMessage());
  }

  @Test
  public void testUnexpectedExceptionScenario() {
    final UUID id = UUID.randomUUID();
    final String errorMessage = "You provided something bad";

    when(userService.get(id)).thenReturn(Mono.error(new Exception(errorMessage)));

    final ErrorResponse result = webTestClient.get().
        uri("/users/{userId}", id)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().is5xxServerError()
        .expectBody(ErrorResponse.class)
        .returnResult().getResponseBody();

    assertNotNull(result);
    assertEquals(errorMessage, result.getMessage());
  }


  private User buildUserObject(UUID id, String email) {
    final User user = new User();
    user.setEmail(email);
    try {
      Field idField = user.getClass().getDeclaredField("id");
      idField.setAccessible(true);
      idField.set(user, id);
      Field createdOnField = user.getClass().getDeclaredField("createdOn");
      createdOnField.setAccessible(true);
      createdOnField.set(user, LocalDateTime.now());
      Field updatedOnField = user.getClass().getDeclaredField("updatedOn");
      updatedOnField.setAccessible(true);
      updatedOnField.set(user, LocalDateTime.now());
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
    return user;

  }

}
