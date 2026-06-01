package com.foundrly.app.features.community;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class FeedViewModel_Factory implements Factory<FeedViewModel> {
  @Override
  public FeedViewModel get() {
    return newInstance();
  }

  public static FeedViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FeedViewModel newInstance() {
    return new FeedViewModel();
  }

  private static final class InstanceHolder {
    private static final FeedViewModel_Factory INSTANCE = new FeedViewModel_Factory();
  }
}
