package com.foundrly.app.di;

import com.foundrly.app.data.local.TaskDao;
import com.foundrly.app.data.repository.TaskRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideTaskRepositoryFactory implements Factory<TaskRepository> {
  private final Provider<TaskDao> taskDaoProvider;

  public AppModule_ProvideTaskRepositoryFactory(Provider<TaskDao> taskDaoProvider) {
    this.taskDaoProvider = taskDaoProvider;
  }

  @Override
  public TaskRepository get() {
    return provideTaskRepository(taskDaoProvider.get());
  }

  public static AppModule_ProvideTaskRepositoryFactory create(Provider<TaskDao> taskDaoProvider) {
    return new AppModule_ProvideTaskRepositoryFactory(taskDaoProvider);
  }

  public static TaskRepository provideTaskRepository(TaskDao taskDao) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideTaskRepository(taskDao));
  }
}
