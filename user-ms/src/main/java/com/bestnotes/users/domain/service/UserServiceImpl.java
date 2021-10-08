package com.bestnotes.users.domain.service;

import com.bestnotes.users.domain.entity.User;
import com.bestnotes.users.domain.repository.UserRepository;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  private final UserRepository userRepository;

  public UserServiceImpl(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Mono<User> get(final UUID id) {
    Assert.notNull(id, "Id must not be null");
    logger.debug("Fetching user with id {}", id);
    return Mono.just(userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Not found user with id '%s'", id))))
        .doOnNext(note -> logger.debug("Successfully fetched user with id {}", id))
        .doOnError(
            e -> logger.error(String.format("Error occurred when fetching user with id %s: Error message: %s", id, e)));
  }

  @Override
  public Flux<User> getAllUsers() {
    return Flux.fromStream(userRepository.findAll().stream());
  }

}
