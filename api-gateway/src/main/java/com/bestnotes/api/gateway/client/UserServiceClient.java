package com.bestnotes.api.gateway.client;

import com.bestnotes.api.gateway.dto.UserResponse;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserServiceClient {

  private final WebClient webClient;

  @Value("${ms.users.path}")
  private String usersMsPath;

  @Value("${ms.users.port}")
  private Integer usersMsPort;
  public UserServiceClient(final WebClient webClient) {
    this.webClient = webClient;
  }

  public Mono<UserResponse> get(final UUID id) {
    return webClient.get()
        .uri(uriBuilder ->
            uriBuilder
                .scheme("http")
                .host(usersMsPath)
                .port(usersMsPort)
                .path("/users/{userId}")
                .build(id))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(UserResponse.class);
  }

}
