package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.LongFunction;
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
        instance.fromInstant(instant);
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

    /**
     * Returns current state of this entity as an instance of {@link OffsetDateTime} combined with specified zone id.
     *
     * @param zone the zone id.
     * @return current state of this entity as an instance of {@link OffsetDateTime}.
     * @see #toZonedDateTime(ZoneId)
     * @see ZonedDateTime#toOffsetDateTime()
     */
    public OffsetDateTime toOffsetDateTime(final ZoneId zone) {
        Objects.requireNonNull(zone, "zone is null");
        return toZonedDateTime(zone).toOffsetDateTime();
    }

    /**
     * Returns current state of this entity as an instance of {@link ZonedDateTime} combined with specified zone id.
     *
     * @param zone the zone id.
     * @return current state of this entity as an instance of {@link ZonedDateTime}.
     * @see #toInstant()
     * @see Instant#atZone(ZoneId)
     */
    public ZonedDateTime toZonedDateTime(final ZoneId zone) {
        Objects.requireNonNull(zone, "zone is null");
        return toTemporal(v -> v.atZone(zone));
    }

    /**
     * Applies an {@link Instant} represents current state of this entity to specified function, and returns the
     * result.
     *
     * @param function the function to be applied.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     * @see #toInstant()
     */
    public <R extends TemporalAccessor> R toTemporal(final Function<? super Instant, ? extends R> function) {
        return Objects.requireNonNull(function, "function is null")
                .apply(toInstant());
    }

    /**
     * Returns an instance of {@link Instant} represents current state of this entity.
     *
     * @return an instance of {@link Instant} represents current state of this entity.
     * @see Instant#ofEpochSecond(long, long)
     */
    public Instant toInstant() {
        return toAppliedWithNonNull(s -> n -> Instant.ofEpochSecond(s, n));
    }

    /**
     * Applies current values of {@link _PersistableInstant_#epochSecond} attribute and
     * {@link _PersistableInstant_#nanoAdjustment} attribute, in a currying manner, to specified function, and returns
     * the result.
     * <p>
     * {@snippet :
     * toAppliedWithNonNull(s -> n -> {
     *     return null;
     * });
     *}
     * {@snippet :
     * toAppliedWithNonNull(s -> {
     *     return n -> {
     *         return null;
     *     };
     * });
     *}
     *
     * @param function the function to be applied.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     * @throws IllegalStateException if either {@link _PersistableInstant_#epochSecond} attribute or
     *                               {@link _PersistableInstant_#nanoAdjustment} attribute is currently {@code null}.
     * @see #toApplied(Function)
     */
    public <R> R toAppliedWithNonNull(final LongFunction<? extends IntFunction<? extends R>> function) {
        Objects.requireNonNull(function, "function is null");
        return toApplied(s -> {
            if (s == null) {
                throw new IllegalStateException("epochSecond is currently null");
            }
            return n -> {
                if (n == null) {
                    throw new IllegalStateException("nanoAdjustment is currently null");
                }
                return function.apply(s).apply(n);
            };
        });
    }

    /**
     * Applies current values of {@link _PersistableInstant_#epochSecond} attribute and
     * {@link _PersistableInstant_#nanoAdjustment} attribute, in a currying manner, to specified function, and returns
     * the result.
     * <p>
     * {@snippet :
     * toApplied(s -> n -> {
     *     // s may be null
     *     // n may be null
     *     return null;
     * });
     *}
     * {@snippet :
     * toApplied(s -> {
     *     // s may be null
     *     return  n -> {
     *         // n may be null
     *         return null;
     *     };
     * });
     *}
     *
     * @param function the function to be applied.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     * @throws IllegalStateException if either {@link _PersistableInstant_#epochSecond} attribute or
     *                               {@link _PersistableInstant_#nanoAdjustment} attribute is currently {@code null}.
     * @see #toAppliedWithNonNull(LongFunction)
     */
    public <R> R toApplied(
            final Function<? super Long, ? extends Function<? super Integer, ? extends R>> function) {
        return Objects.requireNonNull(function, "mapper is null")
                .apply(getEpochSecond())
                .apply(getNanoAdjustment());
    }

    /**
     * Replaces attributes with an {@link Instant} obtained from specified temporal accessor.
     *
     * @param temporal the temporal accessor from which an {@link Instant} is obtained.
     * @throws DateTimeException if unable to convert {@code temporal} to an {@link Instant}.
     * @see Instant#from(TemporalAccessor)
     * @see #fromInstant(Instant)
     */
    public void fromTemporal(final TemporalAccessor temporal) {
        fromInstant(
                Optional.ofNullable(temporal)
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
        fromSupplied(
                () -> Optional.ofNullable(instant)
                        .map(Instant::getEpochSecond)
                        .orElse(null),
                () -> Optional.ofNullable(instant)
                        .map(Instant::getNano)
                        .orElse(null)
        );
    }

    public void fromSupplied(final Supplier<Long> epochSecondSupplier, final Supplier<Integer> nanoAdjustmentSupplier) {
        Objects.requireNonNull(epochSecondSupplier, "epochSecondSupplier is null");
        Objects.requireNonNull(nanoAdjustmentSupplier, "nanoAdjustmentSupplier is null");
        setEpochSecond(epochSecondSupplier.get());
        setNanoAdjustment(nanoAdjustmentSupplier.get());
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
     * Replaces current value of {@link _PersistableInstant_#epochSecond} attribute with specified value.
     *
     * @param epochSecond new value for the {@link _PersistableInstant_#epochSecond} attribute.
     */
    public void setEpochSecond(final Long epochSecond) {
        this.epochSecond = epochSecond;
    }

    // -------------------------------------------------------------------------------------------------- nanoAdjustment

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

    public <R> R getNanoAdjustmentAsApplied(final Function<? super Integer, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return function.apply(getNanoAdjustment());
    }

    @Transient
    public <T extends TemporalAmount> T getNanoAdjustmentAsAmount(final Function<? super Integer, ? extends T> function) {
        return getNanoAdjustmentAsApplied(function);
    }

    @Transient
    public Duration getNanoAdjustmentAsDuration() {
        return getNanoAdjustmentAsAmount(
                v -> Optional.ofNullable(v).map(Duration::ofNanos).orElse(null)
        );
    }

    public void setNanoAdjustmentWithSuppliedAsInt(final IntSupplier supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        setNanoAdjustment(supplier.getAsInt());
    }

    public void setNanoAdjustmentWithSupplied(final Supplier<Integer> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        setNanoAdjustment(supplier.get());
    }

    @Transient
    public void setNanoAdjustmentWithNonosPartOf(final Duration duration) {
        setNanoAdjustmentWithSupplied(
                () -> Optional.ofNullable(duration)
                        .map(Duration::toNanosPart)
                        .orElse(null)
        );
    }

    @Transient
    public void setNanoAdjustmentWithNonosPartOfDurationFrom(final TemporalAmount amount) {
        setNanoAdjustmentWithNonosPartOf(
                Optional.ofNullable(amount)
                        .map(Duration::from)
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
