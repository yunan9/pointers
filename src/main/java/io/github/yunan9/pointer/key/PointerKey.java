package io.github.yunan9.pointer.key;

import io.github.yunan9.commons.key.Keyable;
import io.github.yunan9.commons.type.Typeable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public sealed interface PointerKey<T> extends Keyable<@NotNull String>, Typeable<@NotNull Class<T>>
    permits PointerKeyImpl {

  @Contract(value = "_, _ -> new", pure = true)
  static <O> @NotNull PointerKey<O> newPointerKey(
      final @NotNull String key, final @NotNull Class<O> type) {
    return new PointerKeyImpl<>(key, type);
  }

  @Override
  @NotNull
  String getKey();

  @Override
  @NotNull
  Class<T> getType();
}
