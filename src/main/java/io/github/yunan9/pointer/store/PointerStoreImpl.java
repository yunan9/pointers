package io.github.yunan9.pointer.store;

import static java.util.Objects.requireNonNull;

import io.github.yunan9.pointer.Pointer;
import io.github.yunan9.pointer.key.PointerKey;
import java.util.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

final class PointerStoreImpl implements PointerStore {

  private final Map<@NotNull PointerKey<?>, @NotNull Pointer<?>> pointers;

  PointerStoreImpl(final @NotNull Map<@NotNull PointerKey<?>, @NotNull Pointer<?>> pointers) {
    this.pointers = pointers;
  }

  @Override
  public @UnmodifiableView @NotNull Collection<@NotNull Pointer<?>> getPointers() {
    return Collections.unmodifiableCollection(this.pointers.values());
  }

  @Override
  public void registerPointer(final @NotNull Pointer<?> pointer) {
    requireNonNull(pointer);

    this.pointers.put(pointer.key(), pointer);
  }

  @Override
  public void removePointer(final @NotNull PointerKey<?> pointerKey) {
    this.pointers.remove(requireNonNull(pointerKey));
  }

  @Override
  public @Nullable <T> Pointer<T> pointer(final @NotNull PointerKey<T> pointerKey) {
    return (Pointer<T>) this.pointers.get(requireNonNull(pointerKey));
  }
}
