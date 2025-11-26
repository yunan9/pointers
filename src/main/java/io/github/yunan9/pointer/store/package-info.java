/**
 * Storage and management facilities for pointers.
 *
 * <p>This package provides the {@link io.github.yunan9.pointer.store.PointerStore PointerStore}
 * abstraction, which maintains a registry of {@link io.github.yunan9.pointer.Pointer Pointer}
 * instances mapped by {@link io.github.yunan9.pointer.key.PointerKey PointerKey}.
 *
 * <p>Factory methods such as {@link io.github.yunan9.pointer.store.PointerStore#newPointerStore()},
 * {@link io.github.yunan9.pointer.store.PointerStore#newConcurrentPointerStore()}, and {@link
 * io.github.yunan9.pointer.store.PointerStore#newPointerStore(java.util.Map)} allow callers to
 * select a backing implementation suitable for their concurrency needs.
 */
package io.github.yunan9.pointer.store;
