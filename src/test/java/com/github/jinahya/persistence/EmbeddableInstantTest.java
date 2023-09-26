package com.github.jinahya.persistence;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class EmbeddableInstantTest extends _PersistableInstantTest<EmbeddableInstant> {

    EmbeddableInstantTest() {
        super(EmbeddableInstant.class);
    }

    @Nested
    class FromTest {

        private static Stream<TemporalAccessor> temporalStream() {
            return validTemporalStreamForFrom();
        }

        @MethodSource({"temporalStream"})
        @ParameterizedTest
        void __(final TemporalAccessor temporal) {
            final var instant = Instant.from(temporal);
            final var instance = EmbeddableInstant.from(instant);
            assertThat(instance)
                    .isNotNull()
                    .extracting(_PersistableInstant::toInstant)
                    .isEqualTo(instant);
        }
    }
}
