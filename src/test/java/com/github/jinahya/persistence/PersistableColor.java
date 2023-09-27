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
import java.util.Objects;

@Entity
@Table(name = PersistableColor.TABLE_NAME)
@Setter
@Getter
public class PersistableColor
        extends _PersistableColor
        implements _Identifiable<Long> {

    @Serial
    private static final long serialVersionUID = -6554154866180078547L;

    // https://www.petefreitag.com/tools/sql_reserved_words_checker/?word=color
    public static final String TABLE_NAME = "color";

    // -----------------------------------------------------------------------------------------------------------------
    public static PersistableColor fromComponents(final float[] components) {
        return fromComponents(PersistableColor::new, components);
    }

    public static PersistableColor fromCssRgbHexadecimalNotation(final CharSequence cssRgbHexadecimalNotation) {
        return fromCssRgbHexadecimalNotation(PersistableColor::new, cssRgbHexadecimalNotation);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    protected PersistableColor() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return _Identifiable._toString(super.toString(), this);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PersistableColor that)) {
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

    @Override
    public Long getId_() {
        return id;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = _PersistenceConstants.COLUMN_NAME_ID, nullable = false, insertable = false, updatable = false)
    private Long id;
}
