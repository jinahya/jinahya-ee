package com.github.jinahya.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

abstract class _PersistableInstantTest<T extends _PersistableInstant>
        extends _AbstractPersistableTest<T> {

    static Stream<TemporalAccessor> validTemporalStreamForFrom() {
        return Stream.of(
                Instant.now(),
                ZonedDateTime.now(),
                OffsetDateTime.now()
        );
    }

    _PersistableInstantTest(final Class<T> persistableClass) {
        super(persistableClass);
    }

    @DisplayName("from(initializer,instant)")
    @Nested
    class FromTest {

        private static Stream<TemporalAccessor> temporalStream() {
            return validTemporalStreamForFrom();
        }

        @MethodSource({"temporalStream"})
        @ParameterizedTest
        void __(final TemporalAccessor temporal) {
            final var instant = Instant.from(temporal);
            final var instance = _PersistableInstant.from(entityInitializer, instant);
            assertThat(instance)
                    .isNotNull()
                    .extracting(_PersistableInstant::toInstant)
                    .isEqualTo(instant);
        }
    }

    @Nested
    class ToOffsetDateTimeTest {

        @Test
        void __() {
            final var instance = newRandomizedEntityInstance();
            for (final String zoneId : ZoneId.getAvailableZoneIds()) {
                instance.toOffsetDateTime(ZoneId.of(zoneId));
            }
        }
    }

    @Nested
    class FromTemporalAccessorTest {

        private static Stream<TemporalAccessor> temporalAccessorStream() {
            return Stream.of(
                    Instant.now(),
                    ZonedDateTime.now(),
                    OffsetDateTime.now()
            );
        }

        @Test
        void __Null() {
            final var instance = spy(newEntityInstance());
            instance.fromTemporalAccessor(null);
            verify(instance, times(1)).fromInstant(null);
        }

        @MethodSource({"temporalAccessorStream"})
        @ParameterizedTest
        void __NonNull(final TemporalAccessor temporalAccessor) {
            final var instance = spy(newEntityInstance());
            try (var mockStatic = mockStatic(Instant.class)) {
                instance.fromTemporalAccessor(temporalAccessor);
                mockStatic.verify(() -> Instant.from(temporalAccessor), times(1));
                verify(instance, times(1)).fromInstant(Instant.from(temporalAccessor));
            }
        }
    }

    @Nested
    class FromInstantTest {

        @Test
        void __Null() {
            final var instance = spy(newEntityInstance());
            instance.fromTemporalAccessor(null);
            verify(instance, times(1)).fromInstant(null);
        }
    }

    @DisplayName("setEpochSecondFrom(temporalAmount)")
    @Nested
    class SetEpochSecondFromTest {

        private static Stream<TemporalAmount> validTemporalAmountStream() {
            return Stream.of(
                    Duration.ofNanos(1L),
                    Duration.ofNanos(TimeUnit.MICROSECONDS.toNanos(1)),
                    Duration.ofNanos(TimeUnit.MINUTES.toNanos(1)),
                    Duration.ofMillis(1L),
                    Duration.ofSeconds(1L),
                    Duration.ofMinutes(1L),
                    Duration.ofHours(1L),
                    Duration.ofDays(1L)
            );
        }

        private static Stream<TemporalAmount> invalidTemporalAmountStream() {
            return Stream.of(
                    Period.ofDays(1),
                    Period.ofWeeks(1),
                    Period.ofMonths(1),
                    Period.ofYears(1)
            );
        }

        @MethodSource({"validTemporalAmountStream"})
        @ParameterizedTest
        void __Valid(final TemporalAmount validTemporalAmount) {
            assert validTemporalAmount.getUnits().contains(ChronoUnit.SECONDS);
            final var instant = newEntityInstance();
            instant.setEpochSecondFrom(validTemporalAmount);
            assertThat(instant.getEpochSecond())
                    .isEqualTo(validTemporalAmount.get(ChronoUnit.SECONDS));
        }

        @MethodSource({"invalidTemporalAmountStream"})
        @ParameterizedTest
        void _UnsupportedTemporalTypeException_Invalid(final TemporalAmount invalidTemporalAmount) {
            assert !invalidTemporalAmount.getUnits().contains(ChronoUnit.SECONDS);
            final var instant = newEntityInstance();
            assertThatThrownBy(() -> instant.setEpochSecondFrom(invalidTemporalAmount))
                    .isInstanceOf(UnsupportedTemporalTypeException.class);
        }
    }

    @DisplayName("setNanoAdjustmentFrom(temporalAmount)")
    @Nested
    class SetNanoAdjustmentFromTest {

        private static Stream<TemporalAmount> validTemporalAmountStream() {
            return Stream.of(
                    Duration.ofNanos(1L),
                    Duration.ofNanos(TimeUnit.MICROSECONDS.toNanos(1)),
                    Duration.ofNanos(TimeUnit.MINUTES.toNanos(1)),
                    Duration.ofMillis(1L),
                    Duration.ofSeconds(1L),
                    Duration.ofMinutes(1L),
                    Duration.ofHours(1L),
                    Duration.ofDays(1L)
            );
        }

        private static Stream<TemporalAmount> invalidTemporalAmountStream() {
            return Stream.of(
                    Period.ofDays(1),
                    Period.ofWeeks(1),
                    Period.ofMonths(1),
                    Period.ofYears(1)
            );
        }

        @MethodSource({"validTemporalAmountStream"})
        @ParameterizedTest
        void __Valid(final TemporalAmount validTemporalAmount) {
            assert validTemporalAmount.getUnits().contains(ChronoUnit.NANOS);
            final var instant = newEntityInstance();
            instant.setNanoAdjustmentFrom(validTemporalAmount);
            assertThat(instant.getNanoAdjustment())
                    .isEqualTo(Math.toIntExact(validTemporalAmount.get(ChronoUnit.NANOS)));
        }

        @MethodSource({"invalidTemporalAmountStream"})
        @ParameterizedTest
        void _UnsupportedTemporalTypeException_Invalid(final TemporalAmount invalidTemporalAmount) {
            assert !invalidTemporalAmount.getUnits().contains(ChronoUnit.NANOS);
            final var instant = newEntityInstance();
            assertThatThrownBy(() -> instant.setNanoAdjustmentFrom(invalidTemporalAmount))
                    .isInstanceOf(UnsupportedTemporalTypeException.class);
        }
    }

    @Override
    T newRandomizedEntityInstance() {
        final var instance = super.newRandomizedEntityInstance();
        instance.fromInstant(Instant.now());
        return instance;
    }
}
