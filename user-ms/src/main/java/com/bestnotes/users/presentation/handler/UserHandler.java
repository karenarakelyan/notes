package com.bestnotes.users.presentation.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

public interface UserHandler {

  @NonNull
  Mono<ServerResponse> get(@NonNull ServerRequest request);

  @NonNull
  Mono<ServerResponse> getAll(@NonNull ServerRequest request);

}
