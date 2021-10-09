package com.bestnotes.filegeneration.domain.service;

import com.bestnotes.filegeneration.domain.entity.Note;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NoteService {

  Mono<Note> create(UUID id, UUID userId);

  Mono<Note> update(UUID id, UUID userId);

  Mono<Note> makeProcessed(UUID id, UUID userId);

  Flux<Note> getCandidatesForProcessing();

}
