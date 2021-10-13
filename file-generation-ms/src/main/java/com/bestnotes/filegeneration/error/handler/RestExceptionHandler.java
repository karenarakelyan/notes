package com.bestnotes.filegeneration.error.handler;

import com.bestnotes.filegeneration.error.ErrorResponse;
import com.bestnotes.filegeneration.error.exception.WebClientCallingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Component
@Order(-2)
public class RestExceptionHandler implements WebExceptionHandler {

  private final ObjectMapper objectMapper;

  public RestExceptionHandler(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @NonNull
  @Override
  public Mono<Void> handle(@NonNull final ServerWebExchange exchange, @NonNull final Throwable ex) {
    return handleException(exchange, ex);
  }

  private Mono<Void> handleException(final ServerWebExchange exchange, final Throwable exception) {
    final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
    final HttpStatus httpStatus = getHttpStatus(exception);
    try {
      exchange.getResponse().setStatusCode(httpStatus);
      exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
      DefaultDataBuffer db = new DefaultDataBufferFactory().wrap(objectMapper.writeValueAsBytes(errorResponse));
      return exchange.getResponse().writeWith(Mono.just(db));
    } catch (JsonProcessingException e) {
      return Mono.empty();
    }
  }

  private HttpStatus getHttpStatus(final Throwable t) {
    if (t instanceof IllegalArgumentException) {
      return HttpStatus.BAD_REQUEST;
    } else if (t instanceof WebClientCallingException) {
      final WebClientCallingException exp = (WebClientCallingException) t;
      return HttpStatus.valueOf(exp.getStatusCode());
    } else {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
  }

}
