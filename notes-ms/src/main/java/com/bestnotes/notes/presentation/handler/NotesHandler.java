package com.bestnotes.notes.presentation.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

public interface NotesHandler {

  @NonNull
  Mono<ServerResponse> create(@NonNull ServerRequest request);

  @NonNull
  Mono<ServerResponse> get(@NonNull ServerRequest request);

  @NonNull
  Mono<ServerResponse> getAll(@NonNull ServerRequest request);

  @NonNull
  Mono<ServerResponse> update(@NonNull ServerRequest request);

  @NonNull
  Mono<ServerResponse> delete(@NonNull ServerRequest request);

}
