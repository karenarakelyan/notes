package com.bestnotes.filegeneration.error;

public class ErrorResponse {

  private String message;

  public ErrorResponse(final String message) {
    this.message = message;
  }

  public ErrorResponse() {

  }

  public String getMessage() {
    return message;
  }

}
