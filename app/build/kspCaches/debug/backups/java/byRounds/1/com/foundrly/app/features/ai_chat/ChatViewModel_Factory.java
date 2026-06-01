package com.foundrly.app.features.ai_chat;

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
public final class ChatViewModel_Factory implements Factory<ChatViewModel> {
  private final Provider<MockAIEngine> mockAIEngineProvider;

  public ChatViewModel_Factory(Provider<MockAIEngine> mockAIEngineProvider) {
    this.mockAIEngineProvider = mockAIEngineProvider;
  }

  @Override
  public ChatViewModel get() {
    return newInstance(mockAIEngineProvider.get());
  }

  public static ChatViewModel_Factory create(Provider<MockAIEngine> mockAIEngineProvider) {
    return new ChatViewModel_Factory(mockAIEngineProvider);
  }

  public static ChatViewModel newInstance(MockAIEngine mockAIEngine) {
    return new ChatViewModel(mockAIEngine);
  }
}
