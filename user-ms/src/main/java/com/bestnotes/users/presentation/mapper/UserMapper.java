package com.bestnotes.users.presentation.mapper;

import com.bestnotes.users.domain.entity.User;
import com.bestnotes.users.presentation.dto.UserResponse;

public class UserMapper {

  public static UserResponse mapToUserResponse(final User user) {
    return UserResponse.UserResponseBuilder.anUserResponse()
        .withCreatedOn(user.getCreatedOn())
        .withEmail(user.getEmail())
        .withId(user.getId())
        .withUpdatedOn(user.getUpdatedOn())
        .build();

  }

}
