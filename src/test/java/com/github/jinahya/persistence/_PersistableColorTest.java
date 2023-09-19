package com.github.jinahya.persistence;

import org.junit.jupiter.api.Test;

import static com.github.jinahya.persistence._PersistableColorTests.newRandomizedRgbComponent;
import static org.assertj.core.api.Assertions.assertThat;

abstract class _PersistableColorTest<T extends _PersistableColor> extends _PersistableTest<T> {

    _PersistableColorTest(final Class<T> persistableClass) {
        super(persistableClass);
    }

    @Test
    void __toCssRgbHexadecimalNotation3() {
        final var instance = newRandomizedPersistableInstance();
        final var cssRgbHexadecimalNotation3 = instance.toCssRgbHexadecimalNotation3();
        assertThat(cssRgbHexadecimalNotation3)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION3)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION);
    }

    @Test
    void __toCssRgbHexadecimalNotation4() {
        final var instance = newRandomizedPersistableInstance();
        final var cssRgbHexadecimalNotation4 = instance.toCssRgbHexadecimalNotation4();
        assertThat(cssRgbHexadecimalNotation4)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION4)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION);
    }

    @Test
    void __toCssRgbHexadecimalNotation6() {
        final var instance = newRandomizedPersistableInstance();
        final var cssRgbHexadecimalNotation6 = instance.toCssRgbHexadecimalNotation6();
        assertThat(cssRgbHexadecimalNotation6)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION6)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION);
        final var actual = _PersistableColor.fromCssRgbHexadecimalNotation(initializerOfNew, cssRgbHexadecimalNotation6);
        assertThat(actual.getRed()).isEqualTo(instance.getRed());
        assertThat(actual.getGreen()).isEqualTo(instance.getGreen());
        assertThat(actual.getBlue()).isEqualTo(instance.getBlue());
    }

    @Test
    void __toCssRgbHexadecimalNotation8() {
        final var instance = newRandomizedPersistableInstance();
        final var cssRgbHexadecimalNotation8 = instance.toCssRgbHexadecimalNotation8();
        assertThat(cssRgbHexadecimalNotation8)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION8)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION);
        final var actual = _PersistableColor.fromCssRgbHexadecimalNotation(initializerOfNew, cssRgbHexadecimalNotation8);
        assertThat(actual).isEqualTo(instance);
    }

    @Override
    T newRandomizedPersistableInstance() {
        final T instance = super.newRandomizedPersistableInstance();
        instance.setRed(newRandomizedRgbComponent());
        instance.setGreen(newRandomizedRgbComponent());
        instance.setBlue(newRandomizedRgbComponent());
        instance.setAlpha(newRandomizedRgbComponent());
        return instance;
    }
}
