package com.aptoide.uploader;

import io.reactivex.Observable;

public interface AccountView extends View {

  Observable<CredentialsViewModel> getLoginEvent();

  public static class CredentialsViewModel {

    private final String username;
    private final String password;

    public CredentialsViewModel(String username, String password) {
      this.username = username;
      this.password = password;
    }

    public String getUsername() {
      return username;
    }

    public String getPassword() {
      return password;
    }
  }
}