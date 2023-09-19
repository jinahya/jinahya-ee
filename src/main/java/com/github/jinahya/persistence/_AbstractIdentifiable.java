package com.github.jinahya.persistence;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public abstract class _AbstractIdentifiable<ID extends Serializable>
        extends _AbstractPersistable
        implements _Identifiable<ID> {

    @Serial
    private static final long serialVersionUID = 648968227122467522L;

    protected _AbstractIdentifiable() {
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
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof _AbstractIdentifiable<?> that)) {
            return false;
        }
        return Objects.equals(getId(), that.getId());
    }
}
