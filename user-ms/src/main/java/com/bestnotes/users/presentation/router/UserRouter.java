package com.bestnotes.users.presentation.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.bestnotes.users.presentation.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class UserRouter {

  @Bean
  public RouterFunction<ServerResponse> route(final UserHandler userHandler) {
    return RouterFunctions
        .route(GET("/users/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::get)
        .andRoute(GET("/users/").and(accept(MediaType.APPLICATION_JSON)), userHandler::getAll);
  }

}
