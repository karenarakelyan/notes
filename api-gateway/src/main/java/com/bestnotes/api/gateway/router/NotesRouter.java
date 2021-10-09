package com.bestnotes.api.gateway.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.bestnotes.api.gateway.handler.NotesHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class NotesRouter {

  @Bean
  public RouterFunction<ServerResponse> routeNotes(final NotesHandler notesHandler) {
    return RouterFunctions
        .route(POST("/notes").and(accept(MediaType.APPLICATION_JSON)), notesHandler::create)
        .andRoute(GET("/notes").and(accept(MediaType.APPLICATION_JSON)), notesHandler::getAll)
        .andRoute(GET("/notes/{id}").and(accept(MediaType.APPLICATION_JSON)), notesHandler::get)
        .andRoute(PUT("/notes/{id}").and(accept(MediaType.APPLICATION_JSON)), notesHandler::update)
        .andRoute(DELETE("/notes/{id}").and(accept(MediaType.APPLICATION_JSON)), notesHandler::delete);
  }

}
