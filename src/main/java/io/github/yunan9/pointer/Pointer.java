package io.github.yunan9.pointer;

import io.github.yunan9.commons.key.Keyable;
import io.github.yunan9.commons.value.Valuable;
import io.github.yunan9.pointer.key.PointerKey;
import java.util.function.Supplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A lazily-evaluated, keyed value.
 *
 * <p>A {@code Pointer} associates a {@link PointerKey} with a value of type {@code T}.
 * Implementations are free to compute the value eagerly or lazily, but callers should treat {@link
 * #getValue()} as the canonical access point.
 *
 * @param <T> the value type of this pointer
 */
public sealed interface Pointer<T> extends Keyable<@NotNull PointerKey<T>>, Valuable<T>
    permits PointerImpl {

  /**
   * Creates a new {@link Pointer} backed by the given key and value supplier.
   *
   * <p>The supplied {@link Supplier} may be used to compute the value lazily. The exact evaluation
   * strategy (eager, memoized, re-computed, etc.) is implementation-specific.
   *
   * @param pointerKey the key associated with the pointer
   * @param valueSupplier the supplier used to compute the pointer's value
   * @param <O> the value type of the pointer
   * @return a new {@link Pointer} instance
   */
  @Contract(value = "_, _ -> new", pure = true)
  static <O> @NotNull Pointer<O> newPointer(
      final @NotNull PointerKey<O> pointerKey, final @NotNull Supplier<O> valueSupplier) {
    return new PointerImpl<>(pointerKey, valueSupplier);
  }

  /**
   * Returns the key associated with this pointer.
   *
   * @return the pointer key
   */
  @Override
  @NotNull
  PointerKey<T> getKey();

  /**
   * Returns the value associated with this pointer.
   *
   * <p>The value may be computed lazily depending on the implementation.
   *
   * @return the pointer's value
   */
  @Override
  T getValue();
}
