package com.bestnotes.filegeneration.domain.service;

import com.bestnotes.filegeneration.domain.entity.Note;
import com.bestnotes.filegeneration.domain.entity.ProcessingType;
import com.bestnotes.filegeneration.domain.repository.NoteRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NoteServiceImpl implements NoteService {

  private final NoteRepository noteRepository;

  public NoteServiceImpl(final NoteRepository noteRepository) {
    this.noteRepository = noteRepository;
  }

  @Override
  @Transactional
  public Mono<Note> create(final UUID id, final UUID userId) {
    Assert.notNull(id, "Id must not be null");
    Assert.notNull(userId, "User id must not be null");
    final Note note = new Note();
    note.setId(id);
    note.setUserId(userId);
    note.setProcessingType(ProcessingType.NOT_PROCESSED);
    return Mono.just(noteRepository.save(note));
  }

  @Override
  public Mono<Note> update(final UUID id, final UUID userId) {
    Assert.notNull(id, "Id must not be null");
    Assert.notNull(userId, "User id must not be null");
    final Optional<Note> noteOptional = noteRepository.getNoteByIdAndUserId(id, userId);
    final Note note = noteOptional.orElseThrow(() -> new EntityNotFoundException("Error"));
    note.setProcessingType(ProcessingType.TO_REPROCESS);
    return Mono.just(noteRepository.save(note));
  }

  @Override
  public Mono<Note> makeProcessed(final UUID id, final UUID userId) {
    Assert.notNull(id, "Id must not be null");
    Assert.notNull(userId, "User id must not be null");
    final Optional<Note> noteOptional = noteRepository.getNoteByIdAndUserId(id, userId);
    final Note note = noteOptional.orElseThrow(() -> new EntityNotFoundException("Error"));
    note.setProcessingType(ProcessingType.PROCESSED);
    return Mono.just(noteRepository.save(note));
  }

  @Override
  public Flux<Note> getCandidatesForProcessing() {
    List<Note> notesToProcess = noteRepository
        .getNoteByProcessingTypeIn(Arrays.asList(ProcessingType.NOT_PROCESSED, ProcessingType.TO_REPROCESS));
    return Flux.fromStream(notesToProcess.stream());
  }
}
