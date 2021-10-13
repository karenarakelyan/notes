package com.bestnotes.filegeneration.error.exception;

public class WebClientCallingException extends RuntimeException {

  private final int statusCode;

  public WebClientCallingException(final String message, final int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }

}
