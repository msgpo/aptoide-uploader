package com.aptoide.uploader.apps.view;

import com.aptoide.uploader.apps.UploadDraft;
import com.aptoide.uploader.apps.UploadManager;
import com.aptoide.uploader.view.Presenter;
import com.aptoide.uploader.view.View;
import io.reactivex.Completable;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;

public class NotificationPresenter implements Presenter {
  private final NotificationView view;
  private final UploadManager uploadManager;

  public NotificationPresenter(NotificationView view, UploadManager uploadManager) {
    this.view = view;
    this.uploadManager = uploadManager;
  }

  @Override public void present() {
    checkUploads();
  }

  private void checkUploads() {
    view.getLifecycleEvent()
        .filter(event -> event.equals(View.LifecycleEvent.CREATE))
        .flatMap(__ -> uploadManager.getDrafts())
        .flatMapIterable(drafts -> drafts)
        .flatMap(draft -> {
          if (draft.getStatus()
              .equals(UploadDraft.Status.PROGRESS)) {
            return updateProgress(draft);
          } else {
            return Observable.just(draft);
          }
        })
        .concatMap(i -> Observable.just(i)
            .delay(200, TimeUnit.MILLISECONDS))
        .flatMapCompletable(draft -> showNotification(draft))
        .subscribe();
  }

  private Completable showNotification(UploadDraft draft) {
    String appName = draft.getInstalledApp()
        .getName();
    String packageName = draft.getInstalledApp()
        .getPackageName();
    String md5 = draft.getMd5();

    switch (draft.getStatus()) {
      case PENDING:
        view.showPendingUploadNotification(appName, packageName);
        break;
      case NOT_EXISTENT:
        break;
      case NO_META_DATA:
        view.showNoMetaDataNotification(appName, packageName, md5);
        break;
      case PROGRESS:
        break;
      case COMPLETED:
        view.showCompletedUploadNotification(appName, packageName);
        return uploadManager.removeUploadFromPersistence(draft);
      case DUPLICATE:
        view.showDuplicateUploadNotification(appName, packageName);
        return uploadManager.removeUploadFromPersistence(draft);
      case INFECTED:
        view.showUploadInfectionNotificaton(appName, packageName);
        return uploadManager.removeUploadFromPersistence(draft);
      case PUBLISHER_ONLY:
        view.showPublisherOnlyNotification(appName, packageName);
        return uploadManager.removeUploadFromPersistence(draft);
      case INVALID_SIGNATURE:
        view.showInvalidSignatureNotification(appName, packageName);
        return uploadManager.removeUploadFromPersistence(draft);
      case META_DATA_ADDED:
        break;
      case RETRY:
        break;
      case INTELLECTUAL_RIGHTS:
        view.showIntellectualRightsNotification(appName, packageName);
        return uploadManager.removeUploadFromPersistence(draft);
      case APP_BUNDLE:
        view.showAppBundleNotification(appName, packageName);
        return uploadManager.removeUploadFromPersistence(draft);
      case UPLOAD_FAILED_RETRY:
        view.showFailedUploadWithRetryNotification(appName, packageName);
        return uploadManager.removeUploadFromPersistence(draft);
      case UPLOAD_FAILED:
        view.showFailedUploadNotification(appName, packageName);
        return uploadManager.removeUploadFromPersistence(draft);
      case UNKNOWN_ERROR_RETRY:
        view.showUnknownErrorRetryNotification(appName, packageName);
        return uploadManager.removeUploadFromPersistence(draft);
      case CLIENT_ERROR:
      case UNKNOWN_ERROR:
      default:
        view.showUnknownErrorNotification(appName, packageName);
        return uploadManager.removeUploadFromPersistence(draft);
    }
    return Completable.complete();
  }

  private Observable<UploadDraft> updateProgress(UploadDraft draft) {
    return uploadManager.getProgress(draft.getInstalledApp()
        .getPackageName())
        .sample(500, TimeUnit.MILLISECONDS)
        .doOnNext(uploadProgress -> view.updateUploadProgress(draft.getInstalledApp()
            .getName(), draft.getInstalledApp()
            .getPackageName(), uploadProgress.getProgress()))
        .map(__ -> draft)
        .doOnError(__ -> view.showUnknownErrorRetryNotification(draft.getInstalledApp()
            .getName(), draft.getInstalledApp()
            .getPackageName()));
  }
}

