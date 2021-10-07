package com.bestnotes.notes.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class NoteResponse {

  private final UUID id;
  private final UUID userId;
  private final String title;
  private final String note;
  private final LocalDateTime createdOn;
  private final LocalDateTime updatedOn;

  private NoteResponse(final UUID id, final UUID userId, final String title, final String note, final LocalDateTime createdOn,
      final LocalDateTime updatedOn) {
    this.id = id;
    this.userId = userId;
    this.title = title;
    this.note = note;
    this.createdOn = createdOn;
    this.updatedOn = updatedOn;
  }

  public UUID getId() {
    return id;
  }

  public UUID getUserId() {
    return userId;
  }

  public String getTitle() {
    return title;
  }

  public String getNote() {
    return note;
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }

  public LocalDateTime getUpdatedOn() {
    return updatedOn;
  }

  public static final class NoteResponseBuilder {

    public UUID id;
    public UUID userId;
    public String title;
    public String note;
    public LocalDateTime createdOn;
    public LocalDateTime updatedOn;

    private NoteResponseBuilder() {
    }

    public static NoteResponseBuilder aNoteResponse() {
      return new NoteResponseBuilder();
    }

    public NoteResponseBuilder withId(UUID id) {
      this.id = id;
      return this;
    }

    public NoteResponseBuilder withUserId(UUID userId) {
      this.userId = userId;
      return this;
    }

    public NoteResponseBuilder withTitle(String title) {
      this.title = title;
      return this;
    }

    public NoteResponseBuilder withNote(String note) {
      this.note = note;
      return this;
    }

    public NoteResponseBuilder withCreatedOn(LocalDateTime createdOn) {
      this.createdOn = createdOn;
      return this;
    }

    public NoteResponseBuilder withUpdatedOn(LocalDateTime updatedOn) {
      this.updatedOn = updatedOn;
      return this;
    }

    public NoteResponse build() {
      return new NoteResponse(id, userId, title, note, createdOn, updatedOn);
    }
  }
}
