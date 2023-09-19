package com.github.jinahya.persistence;

import java.util.Objects;
import java.util.function.Supplier;

abstract class _PersistableTest<P extends _Persistable> {

    _PersistableTest(final Class<P> entityClass) {
        super();
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass is null");
        entityInitializer = this::newEntityInstance;
        randomizedEntityInitializer = this::newRandomizedEntityInstance;
    }

    P newRandomizedEntityInstance() {
        return newEntityInstance();
    }

    P newEntityInstance() {
        try {
            final var constructor = entityClass.getDeclaredConstructor();
            if (!constructor.canAccess(null)) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance();
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException(roe);
        }
    }

    final Class<P> entityClass;

    final Supplier<P> entityInitializer;

    final Supplier<P> randomizedEntityInitializer;
}
