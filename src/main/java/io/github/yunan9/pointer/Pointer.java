package io.github.yunan9.pointer;

import io.github.yunan9.commons.key.Keyable;
import io.github.yunan9.commons.type.Typeable;
import io.github.yunan9.commons.value.Valuable;
import java.util.function.Supplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public sealed interface Pointer<T>
    extends Keyable<@NotNull String>, Typeable<@NotNull Class<T>>, Valuable<T> permits PointerImpl {

  @Contract(value = "_, _, _ -> new", pure = true)
  static <O> @NotNull Pointer<O> newPointer(
      final @NotNull String key,
      final @NotNull Class<O> type,
      final @NotNull Supplier<O> valueSupplier) {
    return new PointerImpl<>(key, type, valueSupplier);
  }

  @Override
  @NotNull
  String getKey();

  @Override
  @NotNull
  Class<T> getType();

  @Override
  T getValue();
}
