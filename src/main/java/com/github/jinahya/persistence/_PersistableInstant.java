package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@MappedSuperclass
@SuppressWarnings({
        "java:S101" // _Persistable...
})
public abstract class _PersistableInstant extends _AbstractPersistable {

    @Serial
    private static final long serialVersionUID = -6521255355577442373L;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The name of the table column to which the {@link _PersistableInstant_#epochSecond} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_EPOCH_SECOND = "epoch_second";

    /**
     * The name of the table column to which the {@link _PersistableInstant_#nanoAdjustment} attribute maps. The value
     * is {@value}.
     */
    public static final String COLUMN_NAME_NANO_ADJUSTMENT = "nano_adjustment";

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
        instance.setEpochSecond(instant.getEpochSecond());
        instance.setNanoAdjustment(instant.getNano());
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
               + ",nanoAdjustment=" + nanoAdjustment
               + '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof _PersistableInstant that)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        ;
        return Objects.equals(epochSecond, that.epochSecond)
               && Objects.equals(nanoAdjustment, that.nanoAdjustment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                epochSecond,
                nanoAdjustment
        );
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

    /**
     * Returns an instance of {@link Instant} represents current state of this entity.
     *
     * @return an instance of {@link Instant} represents current state of this entity.
     * @see Instant#ofEpochSecond(long, long)
     */
    public Instant toInstant() {
        return Instant.ofEpochSecond(
                Optional.ofNullable(getEpochSecond())
                        .orElseThrow(() -> new IllegalStateException("epochSecond is null")),
                Optional.ofNullable(getNanoAdjustment())
                        .orElseThrow(() -> new IllegalStateException("epochSecond is null"))
        );
    }

    /**
     * Replaces attributes with an {@link Instant} obtained from specified temporal accessor.
     *
     * @param temporalAccessor the temporal accessor from which an {@link Instant} is obtained.
     * @throws DateTimeException if unable to convert {@code temporalAccessor} to an {@link Instant}.
     * @see Instant#from(TemporalAccessor)
     * @see #fromInstant(Instant)
     */
    public void fromTemporalAccessor(final TemporalAccessor temporalAccessor) {
        fromInstant(
                Optional.ofNullable(temporalAccessor)
                        .map(Instant::from)
                        .orElse(null)
        );
    }

    /**
     * Replaces attributes with values from specified instant.
     *
     * @param instant the instant from which values are get.
     * @see Instant#getEpochSecond()
     * @see Instant#getNano()
     */
    public void fromInstant(final Instant instant) {
        setEpochSecond(
                Optional.ofNullable(instant)
                        .map(Instant::getEpochSecond)
                        .orElse(null)
        );
        setNanoAdjustment(
                Optional.ofNullable(instant)
                        .map(Instant::getNano)
                        .orElse(null)
        );
    }

    // ----------------------------------------------------------------------------------------------------- epochSecond

    /**
     * Returns current value of {@link _PersistableInstant_#epochSecond} attribute.
     *
     * @return current value of the {@link _PersistableInstant_#epochSecond} attribute.
     */
    public Long getEpochSecond() {
        return epochSecond;
    }

    /**
     * Replaces current value of {@link _PersistableInstant_#nanoAdjustment} attribute with specified value.
     *
     * @param epochSecond new value for the {@link _PersistableInstant_#nanoAdjustment} attribute.
     */
    public void setEpochSecond(final Long epochSecond) {
        this.epochSecond = epochSecond;
    }

    /**
     * Replaces current value of {@link _PersistableInstant_#epochSecond} attribute with {@link ChronoUnit#SECONDS}
     * field value of specified temporalAmount.
     *
     * @param temporalAmount the temporal amount whose {@link ChronoUnit#SECONDS} field is set to
     *                       {@link _PersistableInstant_#epochSecond} attribute.
     * @throws DateTimeException                if a value for the {@link ChronoUnit#SECONDS} cannot be obtained
     * @throws UnsupportedTemporalTypeException if the {@link ChronoUnit#SECONDS} is not supported
     * @see TemporalAmount#get(TemporalUnit)
     * @see ChronoUnit#SECONDS
     */
    public void setEpochSecondFrom(final TemporalAmount temporalAmount) {
        setEpochSecond(
                Optional.ofNullable(temporalAmount)
                        .map(v -> v.get(ChronoUnit.SECONDS))
                        .orElse(null)
        );
    }

    // ------------------------------------------------------------------------------------------------------------ nano

    /**
     * Returns current value of {@link _PersistableInstant_#nanoAdjustment} attribute.
     *
     * @return current value of the {@link _PersistableInstant_#nanoAdjustment} attribute.
     */
    public Integer getNanoAdjustment() {
        return nanoAdjustment;
    }

    /**
     * Replaces current value of {@link _PersistableInstant_#nanoAdjustment} attribute with specified value.
     *
     * @param nanoAdjustment a new value for the {@link _PersistableInstant_#nanoAdjustment} attribute.
     */
    public void setNanoAdjustment(final Integer nanoAdjustment) {
        this.nanoAdjustment = nanoAdjustment;
    }

    /**
     * Replaces current value of {@link _PersistableInstant_#nanoAdjustment} attribute with {@link ChronoUnit#NANOS}
     * field value of specified temporalAmount.
     *
     * @param temporalAmount the temporal amount whose {@link ChronoUnit#NANOS} field is set to
     *                       {@link _PersistableInstant_#nanoAdjustment} attribute.
     * @throws DateTimeException                if a value for the {@link ChronoUnit#NANOS} cannot be obtained
     * @throws UnsupportedTemporalTypeException if the {@link ChronoUnit#NANOS} is not supported
     * @see TemporalAmount#get(TemporalUnit)
     * @see ChronoUnit#SECONDS
     */
    public void setNanoAdjustmentFrom(final TemporalAmount temporalAmount) {
        setNanoAdjustment(
                Optional.ofNullable(temporalAmount)
                        .map(v -> v.get(ChronoUnit.NANOS))
                        .map(Math::toIntExact)
                        .orElse(null)
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_EPOCH_SECOND, nullable = false, insertable = true, updatable = true)
    private Long epochSecond;

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_NANO_ADJUSTMENT, nullable = false, insertable = true, updatable = true)
    private Integer nanoAdjustment = 0;
}
