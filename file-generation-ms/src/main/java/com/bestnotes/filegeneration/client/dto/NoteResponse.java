package com.bestnotes.filegeneration.client.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class NoteResponse {

  private UUID id;
  private UUID userId;
  private String title;
  private String note;
  private LocalDateTime createdOn;
  private LocalDateTime updatedOn;

  public UUID getId() {
    return id;
  }

  public void setId(final UUID id) {
    this.id = id;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(final UUID userId) {
    this.userId = userId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getNote() {
    return note;
  }

  public void setNote(final String note) {
    this.note = note;
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
