package com.bestnotes.notes.presentation.handler;

import com.bestnotes.notes.presentation.dto.command.CreateNoteCommand;
import com.bestnotes.notes.presentation.dto.command.UpdateNoteCommand;
import com.bestnotes.notes.presentation.dto.response.NoteResponse;
import com.bestnotes.notes.presentation.mapper.NoteMapper;
import com.bestnotes.notes.domain.service.NotesService;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Component
public class NotesHandlerImpl implements NotesHandler {

  private final NotesService notesService;

  public NotesHandlerImpl(final NotesService notesService) {
    this.notesService = notesService;
  }

  @NonNull
  @Override
  public Mono<ServerResponse> create(@NonNull final ServerRequest request) {
    final Mono<UUID> userIdMono = Mono.just(request.pathVariable("userId")).map(UUID::fromString);
    final Mono<CreateNoteCommand> createNoteCommandMono = request.bodyToMono(CreateNoteCommand.class);
    return ServerResponse.created(request.uri()).body(Mono.zip(userIdMono, createNoteCommandMono)
        .flatMap(tpl2 -> notesService.create(tpl2.getT2().getTitle(), tpl2.getT2().getNote(), tpl2.getT1()))
        .map(NoteMapper::mapToResponse), NoteResponse.class);
  }

  @NonNull
  @Override
  public Mono<ServerResponse> get(@NonNull final ServerRequest request) {
    final Mono<UUID> userIdMono = Mono.just(request.pathVariable("userId")).map(UUID::fromString);
    final Mono<UUID> idMono = Mono.just(request.pathVariable("id")).map(UUID::fromString);
    return ServerResponse.ok().body(Mono.zip(idMono, userIdMono)
        .flatMap(tpl2 -> notesService.get(tpl2.getT1(), tpl2.getT2()))
        .map(NoteMapper::mapToResponse), NoteResponse.class);
  }

  @NonNull
  @Override
  public Mono<ServerResponse> getAll(@NonNull final ServerRequest request) {
    return ServerResponse.ok().body(Mono.just(request.pathVariable("userId"))
        .map(UUID::fromString)
        .flatMapMany(notesService::getAll)
        .map(NoteMapper::mapToResponse)
        .collectList(), NoteResponse.class);
  }

  @NonNull
  @Override
  public Mono<ServerResponse> update(@NonNull final ServerRequest request) {
    Mono<UUID> idMono = Mono.just(request.pathVariable("id")).map(UUID::fromString);
    Mono<UUID> userIdMono = Mono.just(request.pathVariable("userId")).map(UUID::fromString);
    Mono<UpdateNoteCommand> updateNoteCommandMono = request.bodyToMono(UpdateNoteCommand.class);
    return ServerResponse.ok()
        .body(Mono.zip(idMono, userIdMono, updateNoteCommandMono)
                .flatMap(
                    tpl3 -> notesService
                        .update(tpl3.getT1(), tpl3.getT2(), tpl3.getT3().getTitle(), tpl3.getT3().getNote()))
                .map(NoteMapper::mapToResponse),
            NoteResponse.class);
  }

  @NonNull
  @Override
  public Mono<ServerResponse> delete(@NonNull final ServerRequest request) {
    Mono<UUID> idMono = Mono.just(request.pathVariable("id")).map(UUID::fromString);
    Mono<UUID> userIdMono = Mono.just(request.pathVariable("userId")).map(UUID::fromString);
    return Mono.zip(idMono, userIdMono)
        .flatMap(tpl2 -> notesService.delete(tpl2.getT1(), tpl2.getT2()))
        .flatMap(r -> ServerResponse.ok().build());
  }

}
