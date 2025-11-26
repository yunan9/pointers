package io.github.yunan9.pointer.key;

import org.jetbrains.annotations.NotNull;

record PointerKeyImpl<T>(String reference, Class<T> type) implements PointerKey<T> {

  @Override
  public boolean equals(final @NotNull Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof PointerKeyImpl<?>(String reference1, Class<?> type1))) {
      return false;
    }

    return this.reference.equals(reference1) && this.type.equals(type1);
  }
}
