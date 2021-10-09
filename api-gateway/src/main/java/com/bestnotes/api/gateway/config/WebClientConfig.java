package com.bestnotes.api.gateway.config;

import com.bestnotes.api.gateway.error.ErrorResponse;
import com.bestnotes.api.gateway.error.exception.WebClientCallingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

  @Bean
  public WebClient webClient() {
    return WebClient.builder().filter(handleResponse()).build();
  }

  private ExchangeFilterFunction handleResponse() {
    return ExchangeFilterFunction.ofResponseProcessor(response -> {
      if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
        return response.bodyToMono(String.class)
            .flatMap(x -> {
              final ObjectMapper objectMapper = new ObjectMapper();
              try {
                final ErrorResponse errorResponse = objectMapper.readValue(x, ErrorResponse.class);
                return Mono.error(
                    new WebClientCallingException(errorResponse.getMessage(), response.rawStatusCode()));
              } catch (JsonProcessingException e) {
                return Mono.error(
                    new WebClientCallingException(x, response.rawStatusCode()));
              }
            });
      }
      return Mono.just(response);
    });
  }

}
