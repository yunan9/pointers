package io.github.yunan9.pointer.store;

import io.github.yunan9.pointer.Pointer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collection;
import java.util.List;

final class PointerStoreImpl implements PointerStore {

  @Override
  public @UnmodifiableView @NotNull Collection<@NotNull Pointer<?>> getPointers() {
    return List.of();
  }

  @Override
  public void registerPointer(final @NotNull Pointer<?> pointer) {}

  @Override
  public @Nullable <T> Pointer<T> getPointer(
      final @NotNull String key, final @NotNull Class<T> type) {
    return null;
  }
}
