package com.bestnotes.api.gateway.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

public interface NotesHandler {

  @NonNull
  Mono<ServerResponse> create(@NonNull ServerRequest serverRequest);

  @NonNull
  Mono<ServerResponse> getAll(@NonNull ServerRequest serverRequest);

  @NonNull
  Mono<ServerResponse> get(@NonNull ServerRequest serverRequest);

  @NonNull
  Mono<ServerResponse> update(@NonNull ServerRequest serverRequest);

  @NonNull
  Mono<ServerResponse> delete(@NonNull ServerRequest serverRequest);

}
