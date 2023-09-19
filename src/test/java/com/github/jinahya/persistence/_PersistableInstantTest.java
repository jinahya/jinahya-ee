package com.github.jinahya.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.function.Supplier;
import java.util.stream.Stream;

abstract class _PersistableInstantTest<T extends _PersistableInstant> extends _PersistableTest<T> {

    _PersistableInstantTest(final Class<T> persistableClass) {
        super(persistableClass);
        this.persistableInitializer = () -> {
            try {
                return persistableClass.getConstructor().newInstance();
            } catch (ReflectiveOperationException roe) {
                throw new RuntimeException(roe);
            }
        };
    }

    @DisplayName("from(initializer,instant)")
    @Nested
    class FromTest {

        private static Stream<TemporalAccessor> temporalAccessorStream() {
            return Stream.of(
                    Instant.now(),
                    ZonedDateTime.now(),
                    OffsetDateTime.now()
            );
        }

        @MethodSource({"temporalAccessorStream"})
        @ParameterizedTest
        void __(final TemporalAccessor temporalAccessor) {
            _PersistableInstant.from(persistableInitializer, Instant.from(temporalAccessor));
        }
    }

    final Supplier<? extends T> persistableInitializer;
}
