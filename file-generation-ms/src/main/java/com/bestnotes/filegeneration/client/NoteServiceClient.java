package com.bestnotes.filegeneration.client;

import com.bestnotes.filegeneration.client.dto.NoteResponse;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
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

}
