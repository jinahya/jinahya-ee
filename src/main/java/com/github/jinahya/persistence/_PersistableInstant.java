package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

@MappedSuperclass
@SuppressWarnings({
        "java:S101" // _Persistable...
})
public abstract class _PersistableInstant implements _Persistable {

    @Serial
    private static final long serialVersionUID = -6521255355577442373L;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_EPOCH_SECOND = "epoch_second";

    // https://www.petefreitag.com/tools/sql_reserved_words_checker/?word=nano
    public static final String COLUMN_NAME_NANO = "nano";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance of {@link T}, supplied by specified supplier, whose fields initialized with values from
     * specified temporal accessor.
     *
     * @param initializer the supplier for a new instance of {@link T}.
     * @param instant     the temporal accessor
     * @param <T>         instance type parameter
     * @return a new instance.
     * @throws NullPointerException when a {@code null} reference supplied by the {@code initializer}.
     */
    public static <T extends _PersistableInstant> T from(final Supplier<? extends T> initializer,
                                                         final Instant instant) {
        Objects.requireNonNull(initializer, "initializer is null");
        Objects.requireNonNull(instant, "instant is null");
        final T instance = Objects.requireNonNull(initializer.get(), "null supplied from " + initializer);
        instance.setEpochSecond(instance.getEpochSecond());
        instance.setNano(instant.getNano());
        return instance;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    protected _PersistableInstant() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + '{'
               + "epochSecond=" + epochSecond
               + ",nano=" + nano
               + '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof _PersistableInstant)) {
            return false;
        }
        final _PersistableInstant that = (_PersistableInstant) obj;
        return Objects.equals(epochSecond, that.epochSecond)
               && Objects.equals(nano, that.nano);
    }

    @Override
    public int hashCode() {
        return Objects.hash(epochSecond, nano);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public OffsetDateTime toOffsetDateTime(final ZoneId zone) {
        Objects.requireNonNull(zone, "zone is null");
        return toZonedDateTime(zone).toOffsetDateTime();
    }

    public ZonedDateTime toZonedDateTime(final ZoneId zone) {
        Objects.requireNonNull(zone, "zone is null");
        return toTemporalAccessor(v -> v.atZone(zone));
    }

    public <T extends TemporalAccessor> T toTemporalAccessor(final Function<? super Instant, ? extends T> mapper) {
        return Objects.requireNonNull(mapper, "mapper is null").apply(toInstant());
    }

    public Instant toInstant() {
        final Long _epochSecond = getEpochSecond();
        if (_epochSecond == null) {
            throw new IllegalStateException("epochSecond is null");
        }
        final Integer _nano = getNano();
        if (_nano == null) {
            throw new IllegalStateException("nano is null");
        }
        return Instant.ofEpochSecond(_epochSecond, _nano);
    }

    // ----------------------------------------------------------------------------------------------------- epochSecond

    public Long getEpochSecond() {
        return epochSecond;
    }

    public void setEpochSecond(final Long epochSecond) {
        this.epochSecond = epochSecond;
    }

    // ------------------------------------------------------------------------------------------------------------ nano

    /**
     * Returns current value of {@link _PersistableInstant_#nano} attribute.
     *
     * @return current value of the {@link _PersistableInstant_#nano} attribute.
     */
    public Integer getNano() {
        return nano;
    }

    /**
     * Replaces current value of {@link _PersistableInstant_#nano} attribute with specified value.
     *
     * @param nano a new value for the {@link _PersistableInstant_#nano} attribute.
     */
    public void setNano(final Integer nano) {
        this.nano = nano;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_EPOCH_SECOND, nullable = false, insertable = true, updatable = true)
    private Long epochSecond;

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_NANO, nullable = false, insertable = true, updatable = true)
    private Integer nano;
}
