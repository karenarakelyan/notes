package com.bestnotes.api.gateway.client;

import com.bestnotes.api.gateway.dto.firebase.FirebaseTokenGenerationRequest;
import com.bestnotes.api.gateway.dto.firebase.FirebaseTokenGenerationResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class FirebaseAuthenticatorWebClient {

  private final WebClient webClient;

  public FirebaseAuthenticatorWebClient(final WebClient webClient) {
    this.webClient = webClient;
  }

  public Mono<FirebaseTokenGenerationResponse> create(final FirebaseTokenGenerationRequest body) {
    body.setReturnSecureToken(true);
    return webClient.post()
        .uri(uriBuilder ->
            uriBuilder
                .scheme("https")
                .host("identitytoolkit.googleapis.com")
                .path("v1/accounts:signInWithPassword")
                .queryParam("key", "AIzaSyBXuOQRZJjbtZowb7uq6yC61mqlAF8s53k")
                .build())
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(body))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(FirebaseTokenGenerationResponse.class);
  }

}
