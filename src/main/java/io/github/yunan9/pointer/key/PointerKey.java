package io.github.yunan9.pointer.key;

import io.github.yunan9.commons.reference.Referenceable;
import io.github.yunan9.commons.type.Typeable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Typed key used to identify a {@link io.github.yunan9.pointer.Pointer Pointer} within a {@link
 * io.github.yunan9.pointer.store.PointerStore}.
 *
 * <p>A {@code PointerKey} combines:
 *
 * <ul>
 *   <li>a string identifier ({@link #getReference()}), and
 *   <li>a {@link Class type token} ({@link #getType()}) describing the value type.
 * </ul>
 *
 * <p>The combination of key and type allows implementations to provide type-safe access to
 * associated pointer values.
 *
 * @param <T> the value type associated with this key
 */
public sealed interface PointerKey<T>
    extends Referenceable<@NotNull String>, Typeable<@NotNull Class<T>> permits PointerKeyImpl {

  /**
   * Creates a new {@link PointerKey} for the given key and type.
   *
   * @param reference the string identifier for this key
   * @param type the runtime type token associated with this key
   * @param <O> the value type associated with the key
   * @return a new {@link PointerKey} instance
   */
  @Contract(value = "_, _ -> new", pure = true)
  static <O> @NotNull PointerKey<O> newPointerKey(
      final @NotNull String reference, final @NotNull Class<O> type) {
    return new PointerKeyImpl<>(reference, type);
  }

  /**
   * Returns the string identifier of this key.
   *
   * @return the key identifier
   */
  @Override
  @NotNull
  String getReference();

  /**
   * Returns the runtime type token associated with this key.
   *
   * @return the value type of this key
   */
  @Override
  @NotNull
  Class<T> getType();
}
