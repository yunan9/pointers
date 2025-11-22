package io.github.yunan9.pointer;

import static io.github.yunan9.pointer.Pointer.newPointer;
import static io.github.yunan9.pointer.key.PointerKey.newPointerKey;

import io.github.yunan9.pointer.key.PointerKey;
import io.github.yunan9.pointer.store.PointerStore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Benchmarks for {@link PointerStore} operations.
 *
 * <p>Benchmarks include:
 *
 * <ul>
 *   <li>Hit lookups
 *   <li>Miss lookups
 *   <li>Mixed lookups
 *   <li>Pointer registration via key + supplier
 *   <li>Pointer registration via pointer instance
 * </ul>
 *
 * <p>Both HashMap and ConcurrentHashMap store variants are compared.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 8, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Fork(2)
public class PointerStoreBenchmark {

  /** Store implementation under test. */
  public enum StoreType {
    HASH_MAP {

      @Override
      @NotNull
      PointerStore create() {
        return PointerStore.newPointerStore();
      }
    },

    CONCURRENT_HASH_MAP {

      @Override
      @NotNull
      PointerStore create() {
        return PointerStore.newConcurrentPointerStore();
      }
    };

    abstract @NotNull PointerStore create();
  }

  // ---------------------------------------------------------------------------
  // Read (lookup) benchmarks
  // ---------------------------------------------------------------------------

  /** Shared state for read-heavy benchmarks. */
  @State(Scope.Benchmark)
  public static class ReadState {

    @Param({"HASH_MAP", "CONCURRENT_HASH_MAP"})
    public StoreType storeType;

    @Param({"16", "64", "256", "1024"})
    public int pointerCount;

    @Param({"90"})
    public int hitRatio;

    public PointerStore store;

    public PointerKey<String>[] existingKeys, missingKeys;

    @SuppressWarnings("unchecked")
    @Setup
    public void setUp() {
      this.store = this.storeType.create();

      this.existingKeys = new PointerKey[this.pointerCount];
      this.missingKeys = new PointerKey[this.pointerCount];

      // Register existing pointers
      for (var i = 0; i < this.pointerCount; i++) {
        final var pointerKey = newPointerKey("key-" + i, String.class);
        this.existingKeys[i] = pointerKey;

        final var pointerName = "value-" + i;
        this.store.registerPointer(newPointer(pointerKey, () -> pointerName));
      }

      // Prepare missing keys
      for (var i = 0; i < this.pointerCount; i++) {
        this.missingKeys[i] = newPointerKey("missing-" + i, String.class);
      }
    }
  }

  // ---------------------------------------------------------------------------
  // Write (registration) benchmarks
  // ---------------------------------------------------------------------------

  /** Shared state for pointer registration benchmarks. */
  @State(Scope.Benchmark)
  public static class WriteState {

    @Param({"HASH_MAP", "CONCURRENT_HASH_MAP"})
    public StoreType storeType;

    public PointerStore store;

    public int counter;

    @Setup
    public void setUp() {
      this.store = this.storeType.create();
      this.counter = 0;
    }

    @NotNull
    PointerKey<String> nextKey() {
      return newPointerKey("dynamic-" + ++this.counter, String.class);
    }
  }

  // ---------------------------------------------------------------------------
  // Benchmark methods
  // ---------------------------------------------------------------------------

  /** Lookup with guaranteed hits. */
  @Benchmark
  public void getPointer_hit(final @NotNull ReadState state, final @NotNull Blackhole blackhole) {
    final var random = ThreadLocalRandom.current();
    blackhole.consume(
        state.store.getPointer(state.existingKeys[random.nextInt(state.pointerCount)]));
  }

  /** Lookup with guaranteed misses. */
  @Benchmark
  public void getPointer_miss(final @NotNull ReadState state, final @NotNull Blackhole blackhole) {
    final var random = ThreadLocalRandom.current();
    blackhole.consume(
        state.store.getPointer(state.missingKeys[random.nextInt(state.pointerCount)]));
  }

  /** Lookup with mixed hit/miss ratio. */
  @Benchmark
  public void getPointer_mixed(final @NotNull ReadState state, final @NotNull Blackhole blackhole) {
    final var random = ThreadLocalRandom.current();
    final var hit = random.nextInt(100) < state.hitRatio;

    blackhole.consume(
        state.store.getPointer(
            hit
                ? state.existingKeys[random.nextInt(state.pointerCount)]
                : state.missingKeys[random.nextInt(state.pointerCount)]));
  }

  /** Register a pointer via key + supplier. */
  @Benchmark
  public void registerPointer_withKeyAndSupplier(final @NotNull WriteState state) {
    final var key = state.nextKey();
    state.store.registerPointer(key, () -> "dynamic-" + key.getKey());
  }

  /** Register a pointer via an explicitly created instance. */
  @Benchmark
  public void registerPointer_withPointerInstance(final @NotNull WriteState state) {
    final var key = state.nextKey();
    final var pointer = newPointer(key, () -> "dynamic-" + key.getKey());

    state.store.registerPointer(pointer);
  }
}
