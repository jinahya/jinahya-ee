package com.github.jinahya.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = PersistableInstant.TABLE_NAME)
@Setter
@Getter
public class PersistableInstant
        extends _PersistableInstant
        implements _Identifiable<Long> {

    @Serial
    private static final long serialVersionUID = -8182431925309667642L;

    // https://www.petefreitag.com/tools/sql_reserved_words_checker/?word=instant
    public static final String TABLE_NAME = "instant";

    // -----------------------------------------------------------------------------------------------------------------

    public static PersistableInstant from(final Instant instant) {
        return from(PersistableInstant::new, instant);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    protected PersistableInstant() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + '{' +
               "id=" + id +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PersistableInstant that)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        return _Identifiable._idEquals(this, that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                _Identifiable._hashCode(this)
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = _PersistenceConstants.COLUMN_NAME_ID, nullable = false, insertable = false, updatable = false)
    private Long id;
}
