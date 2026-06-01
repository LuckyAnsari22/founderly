package com.foundrly.app.features.dashboard;

import com.foundrly.app.data.repository.TaskRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<TaskRepository> taskRepositoryProvider;

  public DashboardViewModel_Factory(Provider<TaskRepository> taskRepositoryProvider) {
    this.taskRepositoryProvider = taskRepositoryProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(taskRepositoryProvider.get());
  }

  public static DashboardViewModel_Factory create(Provider<TaskRepository> taskRepositoryProvider) {
    return new DashboardViewModel_Factory(taskRepositoryProvider);
  }

  public static DashboardViewModel newInstance(TaskRepository taskRepository) {
    return new DashboardViewModel(taskRepository);
  }
}
