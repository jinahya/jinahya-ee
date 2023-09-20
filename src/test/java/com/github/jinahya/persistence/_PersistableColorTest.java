package com.github.jinahya.persistence;

import com.github.jinahya.util.JavaAwtTestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_ProfileRGB;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import static com.github.jinahya.persistence._PersistableColorTests.newRandomizedRgbComponent;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

@Slf4j
abstract class _PersistableColorTest<T extends _PersistableColor> extends _AbstractPersistableTest<T> {

    _PersistableColorTest(final Class<T> persistableClass) {
        super(persistableClass);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    T newRandomizedEntityInstance() {
        final T instance = super.newRandomizedEntityInstance();
        instance.setRed(newRandomizedRgbComponent());
        instance.setGreen(newRandomizedRgbComponent());
        instance.setBlue(newRandomizedRgbComponent());
        instance.setAlpha(newRandomizedRgbComponent());
        return instance;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void __toCssRgbHexadecimalNotation3() {
        final var instance = newRandomizedEntityInstance();
        final var cssRgbHexadecimalNotation3 = instance.toCssRgbHexadecimalNotation3();
        assertThat(cssRgbHexadecimalNotation3)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION3)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION);
    }

    @Test
    void __toCssRgbHexadecimalNotation4() {
        final var instance = newRandomizedEntityInstance();
        final var cssRgbHexadecimalNotation4 = instance.toCssRgbHexadecimalNotation4();
        assertThat(cssRgbHexadecimalNotation4)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION4)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION);
    }

    @Test
    void __toCssRgbHexadecimalNotation6() {
        final var instance = newRandomizedEntityInstance();
        final var cssRgbHexadecimalNotation6 = instance.toCssRgbHexadecimalNotation6();
        assertThat(cssRgbHexadecimalNotation6)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION6)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION);
        final var actual = _PersistableColor.fromCssRgbHexadecimalNotation(
                entityInitializer,
                cssRgbHexadecimalNotation6
        );
        assertThat(actual).isNotNull().satisfies(a -> {
            assertThat(a.getRed()).isEqualTo(instance.getRed());
            assertThat(a.getGreen()).isEqualTo(instance.getGreen());
            assertThat(a.getBlue()).isEqualTo(instance.getBlue());
        });
    }

    @Test
    void __toCssRgbHexadecimalNotation8() {
        final var instance = newRandomizedEntityInstance();
        final var cssRgbHexadecimalNotation8 = instance.toCssRgbHexadecimalNotation8();
        assertThat(cssRgbHexadecimalNotation8)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION8)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION);
        final var actual = _PersistableColor.fromCssRgbHexadecimalNotation(
                entityInitializer,
                cssRgbHexadecimalNotation8
        );
        assertThat(actual).isEqualTo(instance);
    }

    private static void acceptMockedLocalGraphicsEnvironment(
            final boolean headlessInstance,
            final Consumer<? super GraphicsEnvironment> environmentConsumer) {
        final var local = mock(GraphicsEnvironment.class);
        given(local.isHeadlessInstance()).willReturn(headlessInstance);
        try (var mockStatic = mockStatic(GraphicsEnvironment.class)) {
            mockStatic.when(GraphicsEnvironment::getLocalGraphicsEnvironment).thenReturn(local);
            environmentConsumer.accept(GraphicsEnvironment.getLocalGraphicsEnvironment());
        }
    }

    @Nested
    class _PersistableColorUtilsTest {

        @Test
        void toCIEXYZ__() throws IOException {
            final T instance = newRandomizedEntityInstance();
            final List<ICC_ColorSpace> rgbProfiles = JavaAwtTestUtils.getIccProfiles()
                    .stream()
                    .filter(p -> p instanceof ICC_ProfileRGB)
                    .map(ICC_ColorSpace::new)
                    .toList();
            for (final var rgbProfile : rgbProfiles) {
                final var ciexyz = _PersistableColorUtils.toCIEXYZ(instance, rgbProfile);
                assertThat(ciexyz).isNotNull().hasSize(3).satisfies(f -> {
                    assertThat(f[0]).isBetween(.0f, 1.0f);
                    assertThat(f[1]).isBetween(.0f, 1.0f);
                    assertThat(f[2]).isBetween(.0f, 1.0f);
                });
            }
        }

        @Test
        void toCMYK__() throws IOException {
            final T instance = newRandomizedEntityInstance();
            final var rgbProfiles = JavaAwtTestUtils.getIccProfiles()
                    .stream()
                    .filter(p -> p instanceof ICC_ProfileRGB)
                    .map(ICC_ColorSpace::new)
                    .toList();
            final var cmykProfiles = JavaAwtTestUtils.getIccProfiles()
                    .stream().filter(p -> p.getColorSpaceType() == ColorSpace.TYPE_CMYK)
                    .map(ICC_ColorSpace::new)
                    .toList();
            for (final var rgbProfile : rgbProfiles) {
                for (final var cmykProfile : cmykProfiles) {
                    final var cmyk = _PersistableColorUtils.toCMYK(instance, rgbProfile, cmykProfile);
                    assertThat(cmyk).isNotNull().hasSize(4).satisfies(f -> {
                        assertThat(f[0]).isBetween(.0f, 1.0f);
                        assertThat(f[1]).isBetween(.0f, 1.0f);
                        assertThat(f[2]).isBetween(.0f, 1.0f);
                        assertThat(f[3]).isBetween(.0f, 1.0f);
                    });
                }
            }
            for (final var cmykProfile : cmykProfiles) {
                final var cmyk = _PersistableColorUtils.toCMYK(instance, null, cmykProfile);
                assertThat(cmyk).isNotNull().hasSize(4).satisfies(f -> {
                    assertThat(f[0]).isBetween(.0f, 1.0f);
                    assertThat(f[1]).isBetween(.0f, 1.0f);
                    assertThat(f[2]).isBetween(.0f, 1.0f);
                    assertThat(f[3]).isBetween(.0f, 1.0f);
                });
            }
        }
    }
}
