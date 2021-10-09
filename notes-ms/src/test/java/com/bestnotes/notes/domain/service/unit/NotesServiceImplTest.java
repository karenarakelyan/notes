package com.bestnotes.notes.domain.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.bestnotes.notes.domain.entity.Note;
import com.bestnotes.notes.domain.repository.NotesRepository;
import com.bestnotes.notes.domain.service.NotesServiceImpl;
import com.bestnotes.notes.messaging.publisher.NoteMessagePublisher;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class NotesServiceImplTest {

  @Mock
  private NotesRepository notesRepository;

  @Mock
  private NoteMessagePublisher noteMessagePublisher;

  @InjectMocks
  private NotesServiceImpl notesService;

  @Test
  public void testCreate() {
    final UUID userId = UUID.randomUUID();
    final String title = "Test Title";
    final String content = "Test content";

    when(notesRepository.save(any(Note.class))).thenAnswer((Answer<Note>) invocationOnMock -> {
      final Note arg = invocationOnMock.getArgument(0);
      assertNotNull(arg);
      assertEquals(title, arg.getTitle());
      assertEquals(content, arg.getContent());
      return arg;
    });

    Mono<Note> noteMono = notesService.create(title, content, userId);
    Note note = noteMono.block();
    assertNotNull(note);
    assertEquals(title, note.getTitle());
    assertEquals(content, note.getContent());
  }

  @Test
  public void testCreateWithIllegalArguments() {
    final UUID userId = UUID.randomUUID();
    final String title = "Test title";
    final String content = "Test content";
    try {
      notesService.create(null, content, userId);
      fail();
    } catch (final IllegalArgumentException exp) {
      //Exception
    }
    try {
      notesService.create(title, content, null);
      fail();
    } catch (final IllegalArgumentException exp) {
      //Exception
    }
  }

  @Test
  public void testGet() {
    final UUID id = UUID.randomUUID();
    final UUID userId = UUID.randomUUID();
    final String title = "Test title";
    final String content = "Test content";
    final Note note = new Note();
    note.setTitle(title);
    note.setContent(content);
    when(notesRepository.findByIdAndUserId(id, userId)).thenReturn(Optional.of(note));

    Mono<Note> noteMono = notesService.get(id, userId);
    Note fetchedNote = noteMono.block();
    assertEquals(note, fetchedNote);
  }

  @Test
  public void testGetWithIllegalArguments() {
    try {
      notesService.get(UUID.randomUUID(), null);
      fail();
    } catch (final IllegalArgumentException exp) {
      //Exception
    }
    try {
      notesService.get(null, UUID.randomUUID());
      fail();
    } catch (final IllegalArgumentException exp) {
      //Exception
    }
  }

  @Test
  public void testUpdate() {
    final UUID id = UUID.randomUUID();
    final UUID userId = UUID.randomUUID();
    final String title = "Test Title";
    final String content = "Test content";
    final String updatedTitle = "Updated Title";
    final String updatedContent = "Updated content";
    final Note note = new Note();
    note.setTitle(title);
    note.setContent(content);

    when(notesRepository.findByIdAndUserId(id, userId)).thenReturn(Optional.of(note));
    when(notesRepository.save(any(Note.class))).thenAnswer((Answer<Note>) invocationOnMock -> {
      final Note arg = invocationOnMock.getArgument(0);
      assertNotNull(arg);
      assertEquals(updatedTitle, arg.getTitle());
      assertEquals(updatedContent, arg.getContent());
      return arg;
    });

    Mono<Note> noteMono = notesService.update(id, userId, updatedTitle, updatedContent);
    Note updatedNote = noteMono.block();
    assertNotNull(updatedNote);
    assertEquals(updatedTitle, updatedNote.getTitle());
    assertEquals(updatedContent, updatedNote.getContent());
  }

  @Test
  public void testUpdateWithIllegalArguments() {
    final UUID id = UUID.randomUUID();
    final UUID userId = UUID.randomUUID();
    final String title = "Test Title";
    final String content = "Test content";
    try {
      notesService.update(null, userId, title, content);
      fail();
    } catch (final IllegalArgumentException exp) {
      //Exception
    }
    try {
      notesService.update(id, null, title, content);
      fail();
    } catch (final IllegalArgumentException exp) {
      //Exception
    }
    try {
      notesService.update(id, userId, null, content);
      fail();
    } catch (final IllegalArgumentException exp) {
      //Exception
    }

  }

  @Test
  public void testDelete() {
    final UUID id = UUID.randomUUID();
    final UUID userId = UUID.randomUUID();
    notesService.delete(id, userId).block();//TODO revisit in the end
  }

  @Test
  public void testDeleteWithIllegalArgs() {
    try {
      notesService.delete(null, UUID.randomUUID());
    } catch (final IllegalArgumentException exp) {
      //Exception
    }
    try {
      notesService.delete(UUID.randomUUID(), null);
    } catch (final IllegalArgumentException exp) {
      //Exception
    }
  }


}
