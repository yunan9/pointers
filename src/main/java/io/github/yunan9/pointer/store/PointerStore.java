package io.github.yunan9.pointer.store;

import static io.github.yunan9.pointer.Pointer.newPointer;

import io.github.yunan9.pointer.Pointer;
import io.github.yunan9.pointer.key.PointerKey;
import java.util.Collection;
import java.util.function.Supplier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public sealed interface PointerStore permits PointerStoreImpl {

  @UnmodifiableView
  @NotNull
  Collection<@NotNull Pointer<?>> getPointers();

  @ApiStatus.NonExtendable
  default <T> void registerPointer(
      final @NotNull String key,
      final @NotNull Class<T> type,
      final @NotNull Supplier<T> valueSupplier) {
    this.registerPointer(newPointer(key, type, valueSupplier));
  }

  @ApiStatus.NonExtendable
  default <T> void registerPointer(
      final @NotNull PointerKey<T> pointerKey, final @NotNull Supplier<T> valueSupplier) {
    this.registerPointer(pointerKey.getKey(), pointerKey.getType(), valueSupplier);
  }

  void registerPointer(final @NotNull Pointer<?> pointer);

  @ApiStatus.NonExtendable
  default void removePointer(final @NotNull PointerKey<?> pointerKey) {
    this.removePointer(pointerKey.getKey(), pointerKey.getType());
  }

  void removePointer(final @NotNull String key, final @NotNull Class<?> type);

  @ApiStatus.NonExtendable
  default <T> @Nullable Pointer<T> getPointer(final @NotNull PointerKey<T> pointerKey) {
    return this.getPointer(pointerKey.getKey(), pointerKey.getType());
  }

  <T> @Nullable Pointer<T> getPointer(final @NotNull String key, final @NotNull Class<T> type);

  @FunctionalInterface
  interface Holder {

    @NotNull
    PointerStore getPointerStore();
  }
}
