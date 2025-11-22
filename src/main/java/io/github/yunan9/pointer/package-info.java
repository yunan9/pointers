/**
 * Core pointer abstractions.
 *
 * <p>This package defines the {@link io.github.yunan9.pointer.Pointer Pointer} interface, which
 * represents a typed, keyed value that may be computed lazily via a {@link
 * java.util.function.Supplier Supplier}. Pointers act as the fundamental unit of storage within a
 * {@link io.github.yunan9.pointer.store.PointerStore PointerStore}.
 *
 * <p>Pointer instances are typically created using {@link
 * io.github.yunan9.pointer.Pointer#newPointer(io.github.yunan9.pointer.key.PointerKey,
 * java.util.function.Supplier) Pointer.newPointer(...)}.
 */
package io.github.yunan9.pointer;
