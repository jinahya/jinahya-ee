package com.github.jinahya.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
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

    static Stream<ZoneId> zoneStream() {
        return ZoneId.getAvailableZoneIds().stream().map(ZoneId::of);
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

    @DisplayName("toOffsetDateTime(zone)")
    @Nested
    class ToOffsetDateTimeTest {

        private static Stream<ZoneId> zoneStream() {
            return _PersistableInstantTest.zoneStream();
        }

        @MethodSource({"zoneStream"})
        @ParameterizedTest
        void __(final ZoneId zone) {
            final var instance = newRandomizedEntityInstance();
            final var result = instance.toOffsetDateTime(zone);
            assertThat(result)
                    .isNotNull()
                    .isEqualTo(OffsetDateTime.from(instance.toInstant().atZone(zone)));
        }
    }

    @DisplayName("toZonedDateTime(zone)")
    @Nested
    class ToZonedDateTimeTest {

        private static Stream<ZoneId> zoneStream() {
            return _PersistableInstantTest.zoneStream();
        }

        @MethodSource({"zoneStream"})
        @ParameterizedTest
        void __(final ZoneId zone) {
            final var instance = newRandomizedEntityInstance();
            final var result = instance.toZonedDateTime(zone);
            assertThat(result)
                    .isNotNull()
                    .isEqualTo(ZonedDateTime.from(instance.toInstant().atZone(zone)));
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
            instance.fromTemporal(null);
            verify(instance, times(1)).fromInstant(null);
        }

        @MethodSource({"temporalAccessorStream"})
        @ParameterizedTest
        void __NonNull(final TemporalAccessor temporalAccessor) {
            final var instance = spy(newEntityInstance());
            try (var mockStatic = mockStatic(Instant.class)) {
                instance.fromTemporal(temporalAccessor);
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
            instance.fromTemporal(null);
            verify(instance, times(1)).fromInstant(null);
        }
    }

    @DisplayName("getNanoAdjustmentAsDuration()Ljava.time.Duration;")
    @Nested
    class GetNanoAdjustmentAsDurationTest {

        @Test
        void _Null_Null() {
            final var instance = spy(newEntityInstance());
            given(instance.getNanoAdjustment()).willReturn(null);
            assertThat(instance.getNanoAdjustmentAsDuration()).isNull();
        }

        @Test
        void __() {
            final var instance = spy(newEntityInstance());
            final var nanos = ThreadLocalRandom.current().nextInt(1000000000);
            given(instance.getNanoAdjustment()).willReturn(nanos);
            assertThat(instance.getNanoAdjustmentAsDuration()).isEqualTo(Duration.ofNanos(nanos));
        }
    }

    @DisplayName("setNanoAdjustmentWithSuppliedAsIntTest(supplier)")
    @Nested
    class SetNanoAdjustmentWithSuppliedAsIntTest {

        @Test
        void __() {
            final var instance = spy(newEntityInstance());
            final var nanos = ThreadLocalRandom.current().nextInt(1000000000);
            instance.setNanoAdjustmentWithSuppliedAsInt(() -> nanos);
            verify(instance, times(1)).setNanoAdjustment(nanos);
        }
    }

    @DisplayName("SetNanoAdjustmentWithNanosPartOfDurationFrom(amount)")
    @Nested
    class SetNanoAdjustmentWithNanosPartOfDurationFromTest {

        @DisplayName("null -> setNanoAdjustment(null)")
        @Test
        void _Null_Null() {
            final var instance = spy(newEntityInstance());
            instance.setNanoAdjustmentWithNonosPartOfDurationFrom(null);
            verify(instance, times(1)).setNanoAdjustmentWithNonosPartOf(null);
        }

        @DisplayName("null -> setNanoAdjustment(period)")
        @Test
        void __PeriodOf1Day() {
            final var instance = spy(newEntityInstance());
            final var amount = Period.ofDays(1);
            assertThatThrownBy(() -> instance.setNanoAdjustmentWithNonosPartOfDurationFrom(amount))
                    .isInstanceOf(UnsupportedTemporalTypeException.class);
        }

        @DisplayName("null -> setNanoAdjustment(duration)")
        @Test
        void __() {
            final var instance = spy(newEntityInstance());
            final var amount = Duration.ofNanos(ThreadLocalRandom.current().nextInt(1000000000));
            instance.setNanoAdjustmentWithNonosPartOfDurationFrom(amount);
            verify(instance, times(1)).setNanoAdjustmentWithNonosPartOf(amount);
        }
    }

    @Override
    T newRandomizedEntityInstance() {
        final var instance = super.newRandomizedEntityInstance();
        instance.fromInstant(Instant.now());
        return instance;
    }
}
