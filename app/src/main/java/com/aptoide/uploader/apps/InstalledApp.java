package com.aptoide.uploader.apps;

public class InstalledApp {

  private final String icon;
  private final String name;
  private final boolean isSystem;
  private final String packageName;
  private final String apkPath;
  private final long installedDate;

  public InstalledApp(String icon, String name, boolean isSystem, String packageName,
      String apkPath, long installedDate) {
    this.icon = icon;
    this.name = name;
    this.isSystem = isSystem;
    this.packageName = packageName;
    this.apkPath = apkPath;
    this.installedDate = installedDate;
  }

  public String getIcon() {
    return icon;
  }

  public String getName() {
    return name;
  }

  public boolean isSystem() {
    return isSystem;
  }

  public String getPackageName() {
    return packageName;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    InstalledApp that = (InstalledApp) o;

    return packageName.equals(that.packageName);
  }

  @Override public int hashCode() {
    return packageName.hashCode();
  }

  public String getApkPath() {
    return apkPath;
  }

  public long getInstalledDate() {
    return installedDate;
  }
}
