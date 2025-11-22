# `pointers`
<p align="left">
  <img alt="Java Version" src="https://img.shields.io/badge/Java-21+-ea2845?style=for-the-badge&logo=openjdk">
  <img alt="License" src="https://img.shields.io/github/license/yunan9/pointers?style=for-the-badge">
  <img alt="Build" src="https://img.shields.io/github/actions/workflow/status/yunan9/pointers/publish.yml?style=for-the-badge&logo=github">
  <img alt="Maven Central" src="https://img.shields.io/maven-central/v/io.github.yunan9/pointers?style=for-the-badge&logo=apache-maven">
  <a href="https://javadoc.io/doc/io.github.yunan9/pointers">
    <img alt="Javadoc" src="https://javadoc.io/badge2/io.github.yunan9/pointers/javadoc.svg?style=for-the-badge">
  </a>
</p>

A tiny, type-safe library for storing and retrieving lazily-evaluated values using **typed keys**.
Designed for registries, plugin systems, dynamic configuration layers, and dependency graphs.

---

## Features

* **Typed keys**: `PointerKey<T>`
* **Lazy or eager values**: via `Supplier<T>`
* **Thread-safe or non-thread-safe** stores
* **Zero reflection**, zero overhead
* Clean, sealed, null-annotated API

---

## Installation

```kotlin
dependencies {
    implementation("io.github.yunan9:pointer:<version>")
}
```

```xml
<dependency>
  <groupId>io.github.yunan9</groupId>
  <artifactId>pointer</artifactId>
  <version>VERSION_HERE</version>
</dependency>
```

---

## Quick Example

```java
import io.github.yunan9.pointer.Pointer;
import io.github.yunan9.pointer.key.PointerKey;
import io.github.yunan9.pointer.store.PointerStore;

PointerKey<String> NAME = PointerKey.newPointerKey("name", String.class);
PointerKey<Integer> LEVEL = PointerKey.newPointerKey("level", int.class);

PointerStore store = PointerStore.newConcurrentPointerStore();

store.registerPointer(NAME, () -> "Steve");
store.registerPointer(LEVEL, () -> computeLevel());

// Retrieve
String name = store.getPointer(NAME).getValue();   // "Steve"
int level = store.getPointer(LEVEL).getValue();    // computed int
```

---

## API Overview

### Create a typed key

```java
PointerKey<Boolean> enabled =
    PointerKey.newPointerKey("enabled", boolean.class);
```

### Register a pointer (lazy)

```java
store.registerPointer(enabled, () -> checkDatabaseFlag());
```

### Register a pointer (manual)

```java
Pointer<Boolean> pointer = Pointer.newPointer(enabled, () -> true);
store.registerPointer(pointer);
```

### Remove

```java
store.removePointer(enabled);
```

### List pointers

```java
store.getPointers().forEach(pointer -> {
    System.out.println(pointer.getKey().getKey());
});
```

---

## Thread Safety

| Store                         | Backing             | Thread-safe |
| ----------------------------- | ------------------- | ----------- |
| `newPointerStore()`           | `HashMap`           | ❌ No        |
| `newConcurrentPointerStore()` | `ConcurrentHashMap` | ✅ Yes       |

---

## License

MIT. See `LICENSE`.

---
