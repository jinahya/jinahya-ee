package com.github.jinahya.persistence;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class _PersistableColorStaticTest {

    @Nested
    class PatternTest {

        private static Stream<String> cssRgbHexadecimalNotationStream() {
            return _PersistableColorTests.cssRgbHexadecimalNotationStream();
        }

        @MethodSource({"cssRgbHexadecimalNotationStream"})
        @ParameterizedTest
        void __(final String cssRgbHexadecimalNotation) {
            assertThat(_PersistableColorTests.matcherCssHexadecimalNotation(cssRgbHexadecimalNotation)).matches();
        }
    }
}
