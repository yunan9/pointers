package io.github.yunan9.pointer.key;

import org.jetbrains.annotations.NotNull;

record PointerKeyImpl<T>(String reference, Class<T> type) implements PointerKey<T> {

  PointerKeyImpl(final @NotNull String reference, final @NotNull Class<T> type) {
    this.reference = reference;

    this.type = type;
  }

  @Override
  public @NotNull String reference() {
    return this.reference;
  }

  @Override
  public @NotNull Class<T> type() {
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

}
