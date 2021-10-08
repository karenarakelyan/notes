package com.bestnotes.users.domain.repository;

import com.bestnotes.users.domain.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findById(UUID id);

}
