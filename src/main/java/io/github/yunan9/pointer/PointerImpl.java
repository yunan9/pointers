package io.github.yunan9.pointer;

import io.github.yunan9.pointer.key.PointerKey;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

final class PointerImpl<T> implements Pointer<T> {

  private final PointerKey<T> key;

  private final Supplier<T> valueSupplier;

  PointerImpl(final @NotNull PointerKey<T> key, final @NotNull Supplier<T> valueSupplier) {
    this.key = key;

    this.valueSupplier = valueSupplier;
  }

  @Override
  public @NotNull PointerKey<T> getKey() {
    return this.key;
  }

  @Override
  public T getValue() {
    return this.valueSupplier.get();
  }
}
