package com.foundrly.app.di;

import com.foundrly.app.data.local.AppDatabase;
import com.foundrly.app.data.local.TaskDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class AppModule_ProvideTaskDaoFactory implements Factory<TaskDao> {
  private final Provider<AppDatabase> appDatabaseProvider;

  public AppModule_ProvideTaskDaoFactory(Provider<AppDatabase> appDatabaseProvider) {
    this.appDatabaseProvider = appDatabaseProvider;
  }

  @Override
  public TaskDao get() {
    return provideTaskDao(appDatabaseProvider.get());
  }

  public static AppModule_ProvideTaskDaoFactory create(Provider<AppDatabase> appDatabaseProvider) {
    return new AppModule_ProvideTaskDaoFactory(appDatabaseProvider);
  }

  public static TaskDao provideTaskDao(AppDatabase appDatabase) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideTaskDao(appDatabase));
  }
}
