package com.bestnotes.users.domain.service;

import com.bestnotes.users.domain.entity.User;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

  Mono<User> get(UUID id);

  Flux<User> getAllUsers();

}
