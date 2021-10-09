package com.bestnotes.api.gateway.dto.firebase;

public class FirebaseTokenGenerationRequest {

  private String email;
  private String password;
  private Boolean returnSecureToken;

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public Boolean getReturnSecureToken() {
    return returnSecureToken;
  }

  public void setReturnSecureToken(final Boolean returnSecureToken) {
    this.returnSecureToken = returnSecureToken;
  }
}
