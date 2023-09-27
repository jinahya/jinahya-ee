package com.github.jinahya.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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

    @DisplayName("toComponent(color)")
    @Nested
    class ToComponentTest {

        @DisplayName("(MIN_COLOR)MIN_COMPONENT")
        @Test
        void _MIN_MIN() {
            final int color = _PersistableColor.MIN_COLOR;
            final float component = _PersistableColor.toComponent(color);
            assertThat(component).isEqualTo(_PersistableColor.MIN_COMPONENT);
        }

        @DisplayName("(MAX_COLOR)MAX_COMPONENT")
        @Test
        void _MAX_MAX() {
            final int color = _PersistableColor.MAX_COLOR;
            final float component = _PersistableColor.toComponent(color);
            assertThat(component).isEqualTo(_PersistableColor.MAX_COMPONENT);
        }

        @DisplayName("(random)[MIN_COMPONENT..MAX_COMPONENT]")
        @Test
        void __toComponent() {
            final int color = _PersistableColorTests.newRandomizedColor();
            final float component = _PersistableColor.toComponent(color);
            assertThat(component).isBetween(_PersistableColor.MIN_COMPONENT, _PersistableColor.MAX_COMPONENT);
        }
    }

    @DisplayName("toColor(color)")
    @Nested
    class ToColorTest {

        @DisplayName("(MIN_COLOR)MIN_COMPONENT")
        @Test
        void _MIN_MIN() {
            final float component = _PersistableColor.MIN_COMPONENT;
            final int color = _PersistableColor.toColor(component);
            assertThat(color).isEqualTo(_PersistableColor.MIN_COLOR);
        }

        @DisplayName("(MAX_COLOR)MAX_COMPONENT")
        @Test
        void _MAX_MAX() {
            final float component = _PersistableColor.MAX_COMPONENT;
            final int color = _PersistableColor.toColor(component);
            assertThat(color).isEqualTo(_PersistableColor.MAX_COLOR);
        }

        @DisplayName("(random)[MIN_COLOR..MAX_COLOR]")
        @Test
        void __toColor() {
            final float component = _PersistableColorTests.newRandomizedComponent();
            final int color = _PersistableColor.toColor(component);
            assertThat(color).isBetween(_PersistableColor.MIN_COLOR, _PersistableColor.MAX_COLOR);
        }
    }
}
