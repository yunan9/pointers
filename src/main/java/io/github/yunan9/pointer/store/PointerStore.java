package io.github.yunan9.pointer.store;

import static io.github.yunan9.pointer.Pointer.newPointer;

import io.github.yunan9.pointer.Pointer;
import io.github.yunan9.pointer.key.PointerKey;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import org.jetbrains.annotations.*;

/**
 * Central registry for {@link Pointer pointers}.
 *
 * <p>A {@code PointerStore} is responsible for:
 *
 * <ul>
 *   <li>Holding a set of {@link Pointer} instances keyed by {@link PointerKey},
 *   <li>Registering new pointers,
 *   <li>Looking up pointers by their key,
 *   <li>Removing pointers from the store.
 * </ul>
 *
 * <p>Implementations are provided via the static factory methods: {@link #newPointerStore()},
 * {@link #newConcurrentPointerStore()}, and {@link #newPointerStore(Map)}.
 */
public sealed interface PointerStore permits PointerStoreImpl {

  /** Default initial capacity used when creating backing maps for new stores. */
  int INITIAL_POINTER_STORE_CAPACITY = 64;

  /** Default load factor used when creating backing maps for new stores. */
  float POINTER_STORE_LOAD_FACTOR = .75f;

  /**
   * Creates a new {@link PointerStore} backed by the provided map.
   *
   * <p>The caller is responsible for ensuring that the map is not modified externally in a way that
   * would violate the store's invariants.
   *
   * @param pointers the backing map containing pointers keyed by {@link PointerKey}
   * @return a new {@link PointerStore} using the given map as backing storage
   */
  @Contract("_ -> new")
  static @NotNull PointerStore newPointerStore(
      final @NotNull Map<@NotNull PointerKey<?>, @NotNull Pointer<?>> pointers) {
    return new PointerStoreImpl(pointers);
  }

  /**
   * Creates a new, non-concurrent {@link PointerStore}.
   *
   * <p>The resulting store is backed by a {@link HashMap} and is therefore <strong>not</strong>
   * thread-safe.
   *
   * @return a new {@link PointerStore} backed by a {@link HashMap}
   */
  @Contract(" -> new")
  static @NotNull PointerStore newPointerStore() {
    return newPointerStore(
        new HashMap<>(INITIAL_POINTER_STORE_CAPACITY, POINTER_STORE_LOAD_FACTOR));
  }

  /**
   * Creates a new, concurrent {@link PointerStore}.
   *
   * <p>The resulting store is backed by a {@link ConcurrentHashMap} and is safe to use from
   * multiple threads concurrently, subject to the semantics of {@link ConcurrentHashMap}.
   *
   * @return a new {@link PointerStore} backed by a {@link ConcurrentHashMap}
   */
  @Contract(" -> new")
  static @NotNull PointerStore newConcurrentPointerStore() {
    return newPointerStore(
        new ConcurrentHashMap<>(INITIAL_POINTER_STORE_CAPACITY, POINTER_STORE_LOAD_FACTOR));
  }

  /**
   * Returns an unmodifiable view of all pointers currently registered in this store.
   *
   * @return an unmodifiable collection of registered pointers
   */
  @UnmodifiableView
  @NotNull
  Collection<@NotNull Pointer<?>> getPointers();

  /**
   * Registers a new {@link Pointer} created from the given key and value supplier.
   *
   * <p>This is a convenience method that delegates to {@link #registerPointer(Pointer)}.
   *
   * @param pointerKey the key identifying the pointer
   * @param valueSupplier the supplier used to compute the pointer's value
   * @param <T> the value type of the pointer
   */
  @ApiStatus.NonExtendable
  default <T> void registerPointer(
      final @NotNull PointerKey<T> pointerKey, final @NotNull Supplier<T> valueSupplier) {
    this.registerPointer(newPointer(pointerKey, valueSupplier));
  }

  /**
   * Registers the given {@link Pointer} in this store.
   *
   * <p>If a pointer with the same key is already present, the semantics of replacement, if any, are
   * implementation-specific.
   *
   * @param pointer the pointer to register
   */
  void registerPointer(final @NotNull Pointer<?> pointer);

  /**
   * Removes the pointer associated with the given key from this store, if present.
   *
   * @param pointerKey the key of the pointer to remove
   */
  void removePointer(final @NotNull PointerKey<?> pointerKey);

  /**
   * Retrieves the {@link Pointer} associated with the given key.
   *
   * @param pointerKey the key used to look up the pointer
   * @param <T> the value type associated with the pointer key
   * @return the pointer associated with {@code pointerKey}, or {@code null} if none exists
   */
  <T> @Nullable Pointer<T> getPointer(final @NotNull PointerKey<T> pointerKey);

  /**
   * Simple abstraction for types that hold a {@link PointerStore}.
   *
   * <p>This is typically used to expose pointer-store access on larger components.
   */
  @FunctionalInterface
  interface Holder {

    /**
     * Returns the {@link PointerStore} associated with this holder.
     *
     * @return the pointer store
     */
    @NotNull
    PointerStore getPointerStore();
  }
}
