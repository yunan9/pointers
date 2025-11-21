package io.github.yunan9.pointer.store;

import static java.util.Objects.requireNonNull;

import io.github.yunan9.pointer.Pointer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

final class PointerStoreImpl implements PointerStore {

  private final Collection<@NotNull Pointer<?>> pointers;

  PointerStoreImpl(final @UnmodifiableView @NotNull Collection<@NotNull Pointer<?>> pointers) {
    this.pointers = pointers.isEmpty() ? new ArrayList<>(1) : new ArrayList<>(pointers);
  }

  @Override
  public @UnmodifiableView @NotNull Collection<@NotNull Pointer<?>> getPointers() {
    return Collections.unmodifiableCollection(this.pointers);
  }

  @Override
  public void registerPointer(final @NotNull Pointer<?> pointer) {
    this.pointers.add(requireNonNull(pointer));
  }

  @Override
  public void removePointer(final @NotNull String key, final @NotNull Class<?> type) {
    final var pointer = this.getPointer(key, type);
    if (pointer == null) {
      return;
    }

    this.pointers.remove(pointer);
  }

  @Override
  public @Nullable <T> Pointer<T> getPointer(
      final @NotNull String key, final @NotNull Class<T> type) {
    requireNonNull(key);
    requireNonNull(type);

    return (Pointer<T>)
        this.pointers.stream()
            .filter(pointer -> pointer.getKey().equals(key) && pointer.getType() == type)
            .findFirst()
            .orElse(null);
  }
}
