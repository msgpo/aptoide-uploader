package com.aptoide.uploader.account.view;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import com.aptoide.uploader.R;
import com.aptoide.uploader.apps.view.MyStoreFragment;

public class LoginNavigator {

  private final Context context;
  private final FragmentManager fragmentManager;

  public LoginNavigator(Context context, FragmentManager fragmentManager) {
    this.context = context;
    this.fragmentManager = fragmentManager;
  }

  public void navigateToMyAppsView() {
    fragmentManager.beginTransaction()
        .replace(R.id.activity_main_container, MyStoreFragment.newInstance())
        .commit();
  }

  public void navigateToCreateStoreView() {
    fragmentManager.beginTransaction()
        .replace(R.id.activity_main_container, CreateStoreFragment.newInstance())
        .commit();
  }

  public void navigateToCreateAccountView() {
    fragmentManager.beginTransaction()
        .replace(R.id.activity_main_container, CreateAccountFragment.newInstance())
        .commit();
  }
}
