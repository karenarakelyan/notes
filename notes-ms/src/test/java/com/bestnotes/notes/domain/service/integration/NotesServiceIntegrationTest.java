package com.bestnotes.notes.domain.service.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.bestnotes.notes.domain.entity.Note;
import com.bestnotes.notes.domain.service.NotesService;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class NotesServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private NotesService notesService;

  @Test
  public void testCreate() {
    final UUID userId = UUID.randomUUID();
    final String title = "Test title";
    final String content = "Test content";
    Mono<Note> noteMono = notesService.create(title, content, userId);
    Note note = noteMono.block();
    Assertions.assertNotNull(note);
    assertEquals(title, note.getTitle());
    assertEquals(content, note.getContent());
  }

  @Test
  public void testUpdate() {
    final UUID userId = UUID.randomUUID();
    final String title = "Test title";
    final String content = "Test content";
    Mono<Note> noteMono = notesService.create(title, content, userId);
    Note note = noteMono.block();
    Assertions.assertNotNull(note);
    assertEquals(title, note.getTitle());
    assertEquals(content, note.getContent());

    final String newTitle = "Test updated title";
    final String newContent = "Test updated content";
    Mono<Note> updatedNoteMono = notesService.update(note.getId(), userId, newTitle, newContent);
    Note updatedNote = updatedNoteMono.block();
    Assertions.assertNotNull(updatedNote);
    assertEquals(newTitle, updatedNote.getTitle());
    assertEquals(newContent, updatedNote.getContent());

  }

  @Test
  public void testGet() {
    final UUID userId = UUID.randomUUID();
    final String title = "Test title";
    final String content = "Test content";
    Mono<Note> noteMono = notesService.create(title, content, userId);
    Note note = noteMono.block();
    assertNotNull(note);
    assertEquals(title, note.getTitle());
    assertEquals(content, note.getContent());

    Mono<Note> fetchedNoteMono = notesService.get(note.getId(), userId);
    Note fetchedNote = fetchedNoteMono.block();
    assertNotNull(fetchedNote);
    assertEquals(title, fetchedNote.getTitle());
    assertEquals(content, fetchedNote.getContent());

  }

  @Test
  public void testGetAll() {
    final UUID userId1 = UUID.randomUUID();
    final UUID userId2 = UUID.randomUUID();
    final String title = "Test title";
    final String content = "Test content";
    notesService.create(title, content, userId1).subscribe();
    notesService.create(title, content, userId1).subscribe();
    notesService.create(title, content, userId2).subscribe();

    Flux<Note> fetchedNotesMono = notesService.getAll(userId1);
    List<Note> fetchedNotes = fetchedNotesMono.collectList().block();
    assertNotNull(fetchedNotes);
    assertEquals(2, fetchedNotes.size());

    fetchedNotesMono = notesService.getAll(userId2);
    fetchedNotes = fetchedNotesMono.collectList().block();
    assertNotNull(fetchedNotes);
    assertEquals(1, fetchedNotes.size());
  }


  @Test
  public void testDelete() {
    final UUID userId = UUID.randomUUID();
    final String title = "Test title";
    final String content = "Test content";
    Mono<Note> noteMono = notesService.create(title, content, userId);
    Note note = noteMono.block();
    assertNotNull(note);
    assertEquals(title, note.getTitle());
    assertEquals(content, note.getContent());

    notesService.delete(note.getId(), userId).subscribe();

    Mono<Note> fetchedNoteMono = Mono.just(note.getId()).flatMap(id -> notesService.get(id, userId));
    StepVerifier.create(fetchedNoteMono)
        .expectError(EntityNotFoundException.class)
        .verify();
  }

}
