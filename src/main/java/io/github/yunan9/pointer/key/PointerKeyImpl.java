package io.github.yunan9.pointer.key;

import org.jetbrains.annotations.NotNull;

final class PointerKeyImpl<T> implements PointerKey<T> {

  private final String key;

  private final Class<T> type;

  PointerKeyImpl(final @NotNull String key, final @NotNull Class<T> type) {
    this.key = key;

    this.type = type;
  }

  @Override
  public @NotNull String getKey() {
    return this.key;
  }

  @Override
  public @NotNull Class<T> getType() {
    return this.type;
  }
}
