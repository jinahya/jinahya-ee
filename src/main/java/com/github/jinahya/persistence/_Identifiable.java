package com.github.jinahya.persistence;

import java.io.Serializable;
import java.util.Objects;

public interface _Identifiable<ID extends Serializable> extends _Persistable {

    static <T extends _Identifiable<?>> boolean _idEquals(final T object1, final T object2) {
        Objects.requireNonNull(object1, "object1 is null");
        Objects.requireNonNull(object2, "object2 is null");
        return Objects.equals(object1.getId(), object2.getId());
    }

    static <T extends _Identifiable<?>> int _hashCode(final T object1) {
        Objects.requireNonNull(object1, "object1 is null");
        return object1.hashCode();
    }

    ID getId();
}
