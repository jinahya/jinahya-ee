package com.github.jinahya.persistence;

import java.util.Objects;
import java.util.function.Supplier;

abstract class _PersistableTest<T extends _Persistable> {

    _PersistableTest(final Class<T> persistableClass) {
        super();
        this.persistableClass = Objects.requireNonNull(persistableClass, "persistableClass is null");
        initializerOfNew = this::newPersistableInstance;
        initializerOfNewRandomized = this::newRandomizedPersistableInstance;
    }

    T newRandomizedPersistableInstance() {
        return newPersistableInstance();
    }

    T newPersistableInstance() {
        try {
            return persistableClass.getConstructor().newInstance();
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException(roe);
        }
    }

    final Class<T> persistableClass;

    final Supplier<T> initializerOfNew;

    final Supplier<T> initializerOfNewRandomized;
}
