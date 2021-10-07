package com.bestnotes.notes.presentation.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.bestnotes.notes.domain.entity.Note;
import com.bestnotes.notes.domain.service.NotesService;
import com.bestnotes.notes.presentation.dto.command.CreateNoteCommand;
import com.bestnotes.notes.presentation.dto.command.UpdateNoteCommand;
import com.bestnotes.notes.presentation.dto.response.NoteResponse;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class NotesRouterTest extends BaseApiTest {

  @MockBean
  private NotesService notesService;

  @Test
  public void testCreate() {
    final UUID userId = UUID.randomUUID();
    final String title = "Test title";
    final String content = "Test content";

    final CreateNoteCommand command = new CreateNoteCommand();
    command.setNote(content);
    command.setTitle(title);

    final Note note = buildNoteObject(UUID.randomUUID(), userId, title, content);

    when(notesService.create(title, content, userId)).thenReturn(Mono.just(note));

    final NoteResponse result = webTestClient.post().
        uri("/users/{userId}/notes", userId)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(command))
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isCreated()
        .expectBody(NoteResponse.class)
        .returnResult().getResponseBody();

    assertNotNull(result);
    assertEquals(title, result.getTitle());
    assertEquals(content, result.getNote());
    assertEquals(userId, result.getUserId());
    assertNotNull(result.getId());
    assertNotNull(result.getCreatedOn());
    assertNotNull(result.getUpdatedOn());
  }

  @Test
  public void testUpdate() {
    final UUID id = UUID.randomUUID();
    final UUID userId = UUID.randomUUID();
    final String title = "Test title";
    final String content = "Test content";

    final UpdateNoteCommand command = new UpdateNoteCommand();
    command.setNote(content);
    command.setTitle(title);

    final Note note = buildNoteObject(id, userId, title, content);

    when(notesService.update(id, userId, title, content)).thenReturn(Mono.just(note));

    final NoteResponse result = webTestClient.put().
        uri("/users/{userId}/notes/{id}", userId, id)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(command))
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(NoteResponse.class)
        .returnResult().getResponseBody();

    assertNotNull(result);
    assertEquals(id, result.getId());
    assertEquals(userId, result.getUserId());
    assertEquals(title, result.getTitle());
    assertEquals(content, result.getNote());
    assertNotNull(result.getCreatedOn());
    assertNotNull(result.getUpdatedOn());
  }

  @Test
  public void testGet() {
    final UUID id = UUID.randomUUID();
    final UUID userId = UUID.randomUUID();
    final String title = "Test title";
    final String content = "Test content";

    final UpdateNoteCommand command = new UpdateNoteCommand();
    command.setNote(content);
    command.setTitle(title);

    final Note note = buildNoteObject(id, userId, title, content);

    when(notesService.get(id, userId)).thenReturn(Mono.just(note));

    final NoteResponse result = webTestClient.get().
        uri("/users/{userId}/notes/{id}", userId, id)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(NoteResponse.class)
        .returnResult().getResponseBody();

    assertNotNull(result);
    assertEquals(id, result.getId());
    assertEquals(userId, result.getUserId());
    assertEquals(title, result.getTitle());
    assertEquals(content, result.getNote());
    assertNotNull(result.getCreatedOn());
    assertNotNull(result.getUpdatedOn());
  }


  @Test
  public void testGetAllByUserId() {
    final UUID id = UUID.randomUUID();
    final UUID userId = UUID.randomUUID();
    final String title = "Test title";
    final String content = "Test content";

    final UpdateNoteCommand command = new UpdateNoteCommand();
    command.setNote(content);
    command.setTitle(title);

    final Note note = buildNoteObject(id, userId, title, content);

    when(notesService.getAll(userId)).thenReturn(Flux.fromStream(Stream.of(note)));

    final List<NoteResponse> result = webTestClient.get().
        uri("/users/{userId}/notes", userId, id)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(NoteResponse.class)
        .returnResult().getResponseBody();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(id, result.get(0).getId());
    assertEquals(userId, result.get(0).getUserId());
    assertEquals(title, result.get(0).getTitle());
    assertEquals(content, result.get(0).getNote());
    assertNotNull(result.get(0).getCreatedOn());
    assertNotNull(result.get(0).getUpdatedOn());
  }

  @Test
  public void testDelete() {
    final UUID id = UUID.randomUUID();
    final UUID userId = UUID.randomUUID();
    final String title = "Test title";
    final String content = "Test content";

    final UpdateNoteCommand command = new UpdateNoteCommand();
    command.setNote(content);
    command.setTitle(title);

    when(notesService.delete(id, userId)).thenReturn(Mono.empty());

    webTestClient.delete().
        uri("/users/{userId}/notes/{id}", userId, id)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk();
  }

  private Note buildNoteObject(UUID id, UUID userId, String title, String content) {
    final Note note = new Note();
    note.setTitle(title);
    note.setContent(content);
    note.setUserId(userId);
    try {
      Field idField = note.getClass().getDeclaredField("id");
      idField.setAccessible(true);
      idField.set(note, id);
      Field createdOnField = note.getClass().getDeclaredField("createdOn");
      createdOnField.setAccessible(true);
      createdOnField.set(note, LocalDateTime.now());
      Field updatedOnField = note.getClass().getDeclaredField("updatedOn");
      updatedOnField.setAccessible(true);
      updatedOnField.set(note, LocalDateTime.now());
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
    return note;

  }

}
