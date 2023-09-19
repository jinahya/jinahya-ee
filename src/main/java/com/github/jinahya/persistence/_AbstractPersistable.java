package com.github.jinahya.persistence;

import java.io.Serial;
import java.util.Objects;

public abstract class _AbstractPersistable implements _Persistable {

    @Serial
    private static final long serialVersionUID = -4195769332044924047L;

    protected _AbstractPersistable() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + '{' +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof _AbstractPersistable)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
