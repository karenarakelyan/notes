package com.bestnotes.api.gateway.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.bestnotes.api.gateway.handler.LoginHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class LoginRouter {

  @Bean
  public RouterFunction<ServerResponse> routeLogin(final LoginHandler loginHandler) {
    return RouterFunctions
        .route(POST("/login").and(accept(MediaType.APPLICATION_JSON)), loginHandler::login);
  }

}
