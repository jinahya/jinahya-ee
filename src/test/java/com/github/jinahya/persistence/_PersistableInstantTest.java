package com.github.jinahya.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

abstract class _PersistableInstantTest<T extends _PersistableInstant>
        extends _AbstractPersistableTest<T> {

    static Stream<TemporalAccessor> temporalStream() {
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
            return _PersistableInstantTest.temporalStream();
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
}
