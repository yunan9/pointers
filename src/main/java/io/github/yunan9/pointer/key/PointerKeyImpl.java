package io.github.yunan9.pointer.key;

import org.jetbrains.annotations.NotNull;

final class PointerKeyImpl<T> implements PointerKey<T> {

  private final String reference;

  private final Class<T> type;

  PointerKeyImpl(final @NotNull String reference, final @NotNull Class<T> type) {
    this.reference = reference;

    this.type = type;
  }

  @Override
  public @NotNull String getReference() {
    return this.reference;
  }

  @Override
  public @NotNull Class<T> getType() {
    return this.type;
  }

  @Override
  public boolean equals(final @NotNull Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof final PointerKeyImpl<?> other)) {
      return false;
    }

    return this.reference.equals(other.reference) && this.type.equals(other.type);
  }

  @Override
  public int hashCode() {
    return 31 * this.reference.hashCode() + this.type.hashCode();
  }
}
