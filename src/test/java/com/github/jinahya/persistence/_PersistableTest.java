package com.github.jinahya.persistence;

import java.util.Objects;

abstract class _PersistableTest<T extends _Persistable> {

    _PersistableTest(final Class<T> persistableClass) {
        super();
        this.persistableClass = Objects.requireNonNull(persistableClass, "persistableClass is null");
    }

    final Class<T> persistableClass;
}
