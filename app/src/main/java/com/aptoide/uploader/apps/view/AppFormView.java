package com.aptoide.uploader.apps.view;

import com.aptoide.uploader.apps.Category;
import com.aptoide.uploader.apps.Metadata;
import com.aptoide.uploader.view.View;
import io.reactivex.Observable;
import java.util.List;

public interface AppFormView extends View {

  Observable<Boolean> backPressedEvent();

  void setAppName();

  void showMandatoryFieldError();

  void showGeneralError();

  void showForm();

  void showCategories(List<Category> categoriesList);

  Metadata getMetadata();

  Observable<Metadata> submitAppEvent();

  void hideKeyboard();

  boolean isValidForm();
}
