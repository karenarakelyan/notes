package com.bestnotes.users.presentation.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserResponse {

  private final UUID id;
  private final String email;
  private final LocalDateTime createdOn;
  private final LocalDateTime updatedOn;

  public UserResponse(final UUID id, final String email, final LocalDateTime createdOn, final LocalDateTime updatedOn) {
    this.id = id;
    this.email = email;
    this.createdOn = createdOn;
    this.updatedOn = updatedOn;
  }

  public UUID getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }

  public LocalDateTime getUpdatedOn() {
    return updatedOn;
  }

  public static final class UserResponseBuilder {

    private UUID id;
    private String email;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    private UserResponseBuilder() {
    }

    public static UserResponseBuilder anUserResponse() {
      return new UserResponseBuilder();
    }

    public UserResponseBuilder withId(UUID id) {
      this.id = id;
      return this;
    }

    public UserResponseBuilder withEmail(String email) {
      this.email = email;
      return this;
    }

    public UserResponseBuilder withCreatedOn(LocalDateTime createdOn) {
      this.createdOn = createdOn;
      return this;
    }

    public UserResponseBuilder withUpdatedOn(LocalDateTime updatedOn) {
      this.updatedOn = updatedOn;
      return this;
    }

    public UserResponse build() {
      return new UserResponse(this.id, this.email, this.createdOn, this.updatedOn);
    }
  }
}
