package io.github.yunan9.pointer;

import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

final class PointerImpl<T> implements Pointer<T> {

  private final String key;

  private final Class<T> type;

  private final Supplier<T> valueSupplier;

  PointerImpl(
      final @NotNull String key,
      final @NotNull Class<T> type,
      final @NotNull Supplier<T> valueSupplier) {
    this.key = key;

    this.type = type;

    this.valueSupplier = valueSupplier;
  }

  @Override
  public @NotNull String getKey() {
    return this.key;
  }

  @Override
  public @NotNull Class<T> getType() {
    return this.type;
  }

  @Override
  public T getValue() {
    return this.valueSupplier.get();
  }
}
