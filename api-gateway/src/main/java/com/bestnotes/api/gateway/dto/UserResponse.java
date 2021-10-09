package com.bestnotes.api.gateway.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserResponse {

  private UUID id;
  private String email;
  private LocalDateTime createdOn;
  private LocalDateTime updatedOn;

  public UUID getId() {
    return id;
  }

  public void setId(final UUID id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(final LocalDateTime createdOn) {
    this.createdOn = createdOn;
  }

  public LocalDateTime getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(final LocalDateTime updatedOn) {
    this.updatedOn = updatedOn;
  }
}
