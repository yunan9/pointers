package io.github.yunan9.pointer;

import io.github.yunan9.commons.key.Keyable;
import io.github.yunan9.commons.value.Valuable;
import io.github.yunan9.pointer.key.PointerKey;
import java.util.function.Supplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public sealed interface Pointer<T> extends Keyable<@NotNull PointerKey<T>>, Valuable<T>
    permits PointerImpl {

  @Contract(value = "_, _ -> new", pure = true)
  static <O> @NotNull Pointer<O> newPointer(
      final @NotNull PointerKey<O> pointerKey, final @NotNull Supplier<O> valueSupplier) {
    return new PointerImpl<>(pointerKey, valueSupplier);
  }

  @Override
  @NotNull
  PointerKey<T> getKey();

  @Override
  T getValue();
}
