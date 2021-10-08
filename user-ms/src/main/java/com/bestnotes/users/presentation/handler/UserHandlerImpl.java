package com.bestnotes.users.presentation.handler;

import com.bestnotes.users.domain.service.UserService;
import com.bestnotes.users.presentation.dto.UserResponse;
import com.bestnotes.users.presentation.mapper.UserMapper;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Component
public class UserHandlerImpl implements UserHandler {

  private final UserService userService;

  public UserHandlerImpl(final UserService userService) {
    this.userService = userService;
  }

  @NonNull
  @Override
  public Mono<ServerResponse> get(@NonNull final ServerRequest request) {
    return ServerResponse.ok()
        .body(Mono.just(request.pathVariable("id"))
                .map(UUID::fromString)
                .flatMap(userService::get)
                .map(UserMapper::mapToUserResponse),
            UserResponse.class);
  }

  @NonNull
  @Override
  public Mono<ServerResponse> getAll(@NonNull final ServerRequest request) {
    return ServerResponse.ok()
        .body(userService.getAllUsers()
                .map(UserMapper::mapToUserResponse),
            UserResponse.class);
  }
}
