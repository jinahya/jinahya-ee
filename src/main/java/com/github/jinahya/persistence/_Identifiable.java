package com.github.jinahya.persistence;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings({
        "java:S100", // _...
        "java:S119"  // <ID>
})
public interface _Identifiable<ID extends Serializable> extends _Persistable {

    static String _toString(final String superString, final _Identifiable<?> object) {
        Objects.requireNonNull(superString, "superString is null");
        Objects.requireNonNull(object, "object is null");
        return superString + '{' + "id=" + object.getId_() + '}';
    }

    static <T extends _Identifiable<?>> boolean _idEquals(final T object1, final T object2) {
        Objects.requireNonNull(object1, "object1 is null");
        Objects.requireNonNull(object2, "object2 is null");
        return Objects.equals(object1.getId_(), object2.getId_());
    }

    static <T extends _Identifiable<?>> int _hashCode(final T object1) {
        Objects.requireNonNull(object1, "object1 is null");
        return Objects.hashCode(object1.getId_());
    }

    /**
     * Returns a value identifies this object.
     *
     * @return the value identifies this object.
     */
    ID getId_();
}
