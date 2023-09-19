package com.github.jinahya.persistence;

abstract class _AbstractPersistableTest<P extends _AbstractPersistable> extends _PersistableTest<P> {

    _AbstractPersistableTest(final Class<P> entityClass) {
        super(entityClass);
    }
}
