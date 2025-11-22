package io.github.yunan9.pointer.store;

import io.github.yunan9.pointer.Pointer;
import io.github.yunan9.pointer.key.PointerKey;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import org.jetbrains.annotations.*;

public sealed interface PointerStore permits PointerStoreImpl {

  int INITIAL_POINTER_STORE_CAPACITY = 64;

  float POINTER_STORE_LOAD_FACTOR = .75f;

  @Contract("_ -> new")
  static @NotNull PointerStore newPointerStore(
      final @NotNull Map<@NotNull PointerKey<?>, @NotNull Pointer<?>> pointers) {
    return new PointerStoreImpl(pointers);
  }

  @Contract(" -> new")
  static @NotNull PointerStore newPointerStore() {
    return newPointerStore(
        new HashMap<>(INITIAL_POINTER_STORE_CAPACITY, POINTER_STORE_LOAD_FACTOR));
  }

  @Contract(" -> new")
  static @NotNull PointerStore newConcurrentPointerStore() {
    return newPointerStore(
        new ConcurrentHashMap<>(INITIAL_POINTER_STORE_CAPACITY, POINTER_STORE_LOAD_FACTOR));
  }

  @UnmodifiableView
  @NotNull
  Collection<@NotNull Pointer<?>> getPointers();

  @ApiStatus.NonExtendable
  default <T> void registerPointer(
      final @NotNull PointerKey<T> pointerKey, final @NotNull Supplier<T> valueSupplier) {
    this.registerPointer(Pointer.newPointer(pointerKey, valueSupplier));
  }

  void registerPointer(final @NotNull Pointer<?> pointer);

  void removePointer(final @NotNull PointerKey<?> pointerKey);

  <T> @Nullable Pointer<T> getPointer(final @NotNull PointerKey<T> pointerKey);

  @FunctionalInterface
  interface Holder {

    @NotNull
    PointerStore getPointerStore();
  }
}
