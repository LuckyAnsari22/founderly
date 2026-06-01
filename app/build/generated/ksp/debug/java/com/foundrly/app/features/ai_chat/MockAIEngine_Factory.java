package com.foundrly.app.features.ai_chat;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class MockAIEngine_Factory implements Factory<MockAIEngine> {
  @Override
  public MockAIEngine get() {
    return newInstance();
  }

  public static MockAIEngine_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static MockAIEngine newInstance() {
    return new MockAIEngine();
  }

  private static final class InstanceHolder {
    private static final MockAIEngine_Factory INSTANCE = new MockAIEngine_Factory();
  }
}
