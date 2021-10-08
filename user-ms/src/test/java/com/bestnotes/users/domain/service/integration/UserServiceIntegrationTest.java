package com.bestnotes.users.domain.service.integration;

import com.bestnotes.users.domain.entity.User;
import com.bestnotes.users.domain.repository.UserRepository;
import com.bestnotes.users.domain.service.UserService;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class UserServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @Test
  public void testGet() {
    final User user = new User();
    user.setEmail("test@bestnotes.com");
    user.setPassword("veryStrongPassword");
    userRepository.save(user);

    Mono<User> userMono = userService.get(user.getId());
    User fetchedUser = userMono.block();
    Assertions.assertNotNull(fetchedUser);
    Assertions.assertEquals(user.getEmail(), fetchedUser.getEmail());
    Assertions.assertEquals(user.getPassword(), fetchedUser.getPassword());

  }

  @Test
  public void testGetAll() {
    final User user = new User();
    user.setEmail("test@bestnotes.com");
    user.setPassword("veryStrongPassword");
    userRepository.save(user);

    Mono<List<User>> usersMono = userService.getAllUsers()
        .collectList();
    List<User> fetchedUsers = usersMono.block();
    Assertions.assertNotNull(fetchedUsers);
    Assertions.assertEquals(1, fetchedUsers.size());
    Assertions.assertEquals(user.getEmail(), fetchedUsers.get(0).getEmail());
    Assertions.assertEquals(user.getPassword(), fetchedUsers.get(0).getPassword());

  }

}
