package com.bestnotes.api.gateway.handler;

import com.bestnotes.api.gateway.client.FirebaseAuthenticatorWebClient;
import com.bestnotes.api.gateway.dto.firebase.FirebaseTokenGenerationRequest;
import com.bestnotes.api.gateway.dto.firebase.FirebaseTokenGenerationResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Component
public class LoginHandler {

  private final FirebaseAuthenticatorWebClient firebaseAuthenticatorWebClient;

  public LoginHandler(
      final FirebaseAuthenticatorWebClient firebaseAuthenticatorWebClient) {
    this.firebaseAuthenticatorWebClient = firebaseAuthenticatorWebClient;
  }

  @NonNull
  public Mono<ServerResponse> login(@NonNull final ServerRequest serverRequest) {
    return ServerResponse.ok().body(serverRequest.bodyToMono(FirebaseTokenGenerationRequest.class)
        .flatMap(firebaseAuthenticatorWebClient::create), FirebaseTokenGenerationResponse.class);
  }

}
