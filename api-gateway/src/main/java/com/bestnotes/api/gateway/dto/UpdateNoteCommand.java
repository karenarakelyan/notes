package com.bestnotes.api.gateway.dto;

public class UpdateNoteCommand {

  private String title;
  private String note;

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
}
