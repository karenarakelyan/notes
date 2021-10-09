package com.bestnotes.api.gateway.client;

import com.bestnotes.api.gateway.dto.CreateNoteCommand;
import com.bestnotes.api.gateway.dto.NoteResponse;
import com.bestnotes.api.gateway.dto.UpdateNoteCommand;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class NoteServiceClient {

  private final WebClient webClient;

  @Value("${ms.notes.path}")
  private String notesMsPath;

  @Value("${ms.notes.port}")
  private Integer notesMsPort;

  public NoteServiceClient(final WebClient webClient) {
    this.webClient = webClient;
  }

  public Mono<NoteResponse> create(final UUID userId, final CreateNoteCommand command) {
    return webClient.post()
        .uri(uriBuilder ->
            uriBuilder
                .scheme("http")
                .host(notesMsPath)
                .port(notesMsPort)
                .path("/users/{userId}/notes")
                .build(userId))
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(command))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(NoteResponse.class);
  }

  public Flux<NoteResponse> getAll(final UUID userId) {
    return webClient.get()
        .uri(uriBuilder ->
            uriBuilder
                .scheme("http")
                .host(notesMsPath)
                .port(notesMsPort)
                .path("/users/{userId}/notes")
                .build(userId))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToFlux(NoteResponse.class);
  }

  public Mono<NoteResponse> get(final UUID id, final UUID userId) {
    return webClient.get()
        .uri(uriBuilder ->
            uriBuilder
                .scheme("http")
                .host(notesMsPath)
                .port(notesMsPort)
                .path("/users/{userId}/notes/{id}")
                .build(userId, id))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(NoteResponse.class);
  }

  public Mono<NoteResponse> update(final UUID id, final UUID userId, final UpdateNoteCommand command) {
    return webClient.put()
        .uri(uriBuilder ->
            uriBuilder
                .scheme("http")
                .host(notesMsPath)
                .port(notesMsPort)
                .path("/users/{userId}/notes/{id}")
                .build(userId, id))
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(command))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(NoteResponse.class);
  }

  public Mono<ResponseEntity<Void>> delete(final UUID id, final UUID userId) {
    return webClient.delete()
        .uri(uriBuilder ->
            uriBuilder
                .scheme("http")
                .host(notesMsPath)
                .port(notesMsPort)
                .path("/users/{userId}/notes/{id}")
                .build(userId, id))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .toBodilessEntity();
  }

}
