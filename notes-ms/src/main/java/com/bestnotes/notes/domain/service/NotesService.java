package com.bestnotes.notes.domain.service;

import com.bestnotes.notes.domain.entity.Note;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NotesService {

  Mono<Note> create(String title, String content, UUID userId);

  Mono<Note> get(UUID id, UUID userId);

  Flux<Note> getAll(UUID userId);

  Mono<Note> update(UUID id, UUID userId, String title, String content);

  Mono<Void> delete(UUID id, UUID userId);

}
