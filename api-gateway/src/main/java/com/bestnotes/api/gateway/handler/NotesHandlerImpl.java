package com.bestnotes.api.gateway.handler;

import com.bestnotes.api.gateway.client.NoteServiceClient;
import com.bestnotes.api.gateway.client.UserServiceClient;
import com.bestnotes.api.gateway.dto.CreateNoteCommand;
import com.bestnotes.api.gateway.dto.NoteResponse;
import com.bestnotes.api.gateway.dto.UpdateNoteCommand;
import com.bestnotes.api.gateway.dto.UserResponse;
import java.util.UUID;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Component
public class NotesHandlerImpl implements NotesHandler {

  private final NoteServiceClient noteServiceClient;

  private final UserServiceClient userServiceClient;

  public NotesHandlerImpl(final NoteServiceClient noteServiceClient, final UserServiceClient userServiceClient,
      final UserServiceClient userServiceClient1) {
    this.noteServiceClient = noteServiceClient;
    this.userServiceClient = userServiceClient1;
  }

  @NonNull
  @Override
  public Mono<ServerResponse> create(@NonNull final ServerRequest serverRequest) {
    final Mono<UUID> userIdMono = getUserId().flatMap(this::validate);
    final Mono<CreateNoteCommand> createNoteCommandMono = serverRequest.bodyToMono(CreateNoteCommand.class);
    final Mono<NoteResponse> noteResponseMono = Mono.zip(userIdMono, createNoteCommandMono)
        .flatMap(tpl2 -> noteServiceClient.create(tpl2.getT1(), tpl2.getT2()));
    return ServerResponse.created(serverRequest.uri()).body(noteResponseMono, NoteResponse.class);
  }

  @NonNull
  @Override
  public Mono<ServerResponse> getAll(@NonNull final ServerRequest serverRequest) {
    final Mono<UUID> userIdMono = getUserId();
    Flux<NoteResponse> notesResponseMono = userIdMono.flatMapMany(noteServiceClient::getAll);
    return ServerResponse.ok().body(notesResponseMono, NoteResponse.class);
  }

  @NonNull
  @Override
  public Mono<ServerResponse> get(@NonNull final ServerRequest serverRequest) {
    final Mono<UUID> userIdMono = getUserId();
    final Mono<UUID> idMono = Mono.just(serverRequest.pathVariable("id")).map(UUID::fromString);
    final Mono<NoteResponse> noteResponseMono = Mono.zip(idMono, userIdMono)
        .flatMap(tpl2 -> noteServiceClient.get(tpl2.getT1(), tpl2.getT2()));
    return ServerResponse.ok().body(noteResponseMono, NoteResponse.class);
  }

  @NonNull
  @Override
  public Mono<ServerResponse> update(@NonNull final ServerRequest serverRequest) {
    final Mono<UUID> userIdMono = getUserId();
    final Mono<UpdateNoteCommand> updateNoteCommandMono = serverRequest.bodyToMono(UpdateNoteCommand.class);
    final Mono<UUID> idMono = Mono.just(serverRequest.pathVariable("id")).map(UUID::fromString);
    final Mono<NoteResponse> noteResponseMono = Mono.zip(idMono, userIdMono, updateNoteCommandMono)
        .flatMap(tpl3 -> noteServiceClient.update(tpl3.getT1(), tpl3.getT2(), tpl3.getT3()));
    return ServerResponse.ok().body(noteResponseMono, NoteResponse.class);

  }

  @NonNull
  @Override
  public Mono<ServerResponse> delete(@NonNull final ServerRequest serverRequest) {
    final Mono<UUID> userIdMono = getUserId();
    final Mono<UUID> idMono = Mono.just(serverRequest.pathVariable("id")).map(UUID::fromString);
    return Mono.zip(idMono, userIdMono)
        .flatMap(tpl2 -> noteServiceClient.delete(tpl2.getT1(), tpl2.getT2()))
        .flatMap(r -> ServerResponse.ok().build());
  }

  private Mono<UUID> getUserId() {
    return ReactiveSecurityContextHolder.getContext()
        .map(c -> ((Jwt) c.getAuthentication().getPrincipal()).getClaimAsString("userId"))
        .map(UUID::fromString);
  }

  private Mono<UUID> validate(final UUID id) {
    return userServiceClient.get(id)
        .filter(userResponse -> userResponse.getId() != null)
        .map(UserResponse::getId);
  }


}
