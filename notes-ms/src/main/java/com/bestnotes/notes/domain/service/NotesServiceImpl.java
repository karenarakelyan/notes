package com.bestnotes.notes.domain.service;

import com.bestnotes.notes.domain.entity.Note;
import com.bestnotes.notes.domain.repository.NotesRepository;
import com.bestnotes.notes.messaging.publisher.NoteMessagePublisher;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NotesServiceImpl implements NotesService {

  private static final Logger logger = LoggerFactory.getLogger(NotesServiceImpl.class);

  private final NotesRepository notesRepository;

  private final NoteMessagePublisher noteMessagePublisher;

  public NotesServiceImpl(final NotesRepository notesRepository,
      final NoteMessagePublisher noteMessagePublisher) {
    this.notesRepository = notesRepository;
    this.noteMessagePublisher = noteMessagePublisher;
  }

  @Override
  @Transactional
  public Mono<Note> create(final String title, final String content, final UUID userId) {
    Assert.hasText(title, "Title must not be empty");
    Assert.notNull(userId, "User id must not be null");
    logger.debug("Creating note with title {} and content {}", title, content);
    final Note noteObject = createNoteObject(title, content, userId);
    return Mono.just(notesRepository.save(noteObject))
        .map(note -> {
          noteMessagePublisher.publishNoteCreatedMessage(note);
          return note;
        })
        .doOnNext(note -> logger.debug("Successfully created note with title {} and id {}", title, note.getId()))
        .doOnError(e -> logger
            .error(String.format("Error occurred when creating note with title %s: Error message: %s", title, e)));
  }

  @Override
  public Mono<Note> get(final UUID id, final UUID userId) {
    Assert.notNull(id, "Id must not be null");
    Assert.notNull(userId, "User id must not be null");
    logger.debug("Fetching note with id {}", id);
    return Mono.just(notesRepository.findByIdAndUserId(id, userId)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Not found note with id '%s'", id))))
        .doOnNext(note -> logger.debug("Successfully fetched note with id {}", id))
        .doOnError(
            e -> logger.error(String.format("Error occurred when fetching note with id %s: Error message: %s", id, e)));
  }

  @Override
  public Flux<Note> getAll(final UUID userId) {
    Assert.notNull(userId, "User id must not be null");
    return Flux.fromStream(notesRepository.findByUserId(userId).stream());
  }

  @Override
  @Transactional
  public Mono<Note> update(final UUID id, final UUID userId, final String title, final String content) {
    Assert.notNull(id, "Id must not be null");
    Assert.hasText(title, "Title must not be empty");
    Assert.notNull(userId, "User id must not be null");
    logger.debug("Updating note with id {} to title {} and content {}", id, title, content);
    return this.get(id, userId)
        .map(note -> {
          note.setTitle(title);
          note.setContent(content);
          return note;
        })
        .map(notesRepository::save)
        .map(note -> {
          noteMessagePublisher.publishNoteUpdatedMessage(note);
          return note;
        })
        .doOnNext(note -> logger.debug("Successfully updated note with id {}", id))
        .doOnError(
            e -> logger.error(String.format("Error occurred when updating note with id %s: Error message: %s", id, e)));
  }

  @Override
  @Transactional
  public Mono<Void> delete(final UUID id, final UUID userId) {
    Assert.notNull(id, "Id must not be null");
    Assert.notNull(userId, "User id must not be null");
    logger.debug("Deleting note with id {}", id);
    notesRepository.deleteByIdAndUserId(id, userId);
    logger.debug("Successfully deleted note with id {}", id);
    return Mono.empty();
  }

  private Note createNoteObject(final String title, final String content, final UUID userId) {
    final Note note = new Note();
    note.setUserId(userId);
    note.setTitle(title);
    note.setContent(content);
    return note;
  }

}
