package com.github.jinahya.persistence;

import com.github.jinahya.util.JavaAwtTestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_ProfileRGB;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import static com.github.jinahya.persistence._PersistableColorTests.newRandomizedColor;
import static com.github.jinahya.persistence._PersistableColorTests.newRandomizedComponents;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
abstract class _PersistableColorTest<T extends _PersistableColor> extends _AbstractPersistableTest<T> {

    _PersistableColorTest(final Class<T> persistableClass) {
        super(persistableClass);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    T newRandomizedEntityInstance() {
        final T instance = super.newRandomizedEntityInstance();
        instance.setRed(newRandomizedColor());
        instance.setGreen(newRandomizedColor());
        instance.setBlue(newRandomizedColor());
        instance.setAlpha(newRandomizedColor());
        return instance;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @DisplayName("fromComponents")
    @Nested
    class FromComponentsTest {

        @DisplayName("fromComponents(initializer,components)")
        @Test
        void __() {
            final float[] components = newRandomizedComponents();
            final var instance = _PersistableColor.fromComponents(entityInitializer, components);
            assertThat(instance)
                    .isNotNull()
                    .extracting(_PersistableColor::getComponents)
                    .isEqualTo(components);
        }

        @DisplayName("fromComponents(components)")
        @Test
        void __redefined() {
            final float[] components = newRandomizedComponents();
            final T instance;
            try {
                final var method = entityClass.getMethod("fromComponents", String.class);
                try {
                    instance = entityClass.cast(method.invoke(null, components));
                } catch (final ReflectiveOperationException roe) {
                    throw new RuntimeException(roe);
                }
            } catch (final NoSuchMethodException nsme) {
                return;
            }
            assertThat(instance)
                    .isNotNull()
                    .extracting(_PersistableColor::getComponents)
                    .isEqualTo(components);
        }
    }

    @DisplayName("fromCssRgbHexadecimalNotation")
    @Nested
    class FromCssRgbHexadecimalNotationTest {

        @DisplayName("fromCssRgbHexadecimalNotation(initializer,cssRgbHexadecimalNotation)")
        @Test
        void __() {
            final var cssRgbHexadecimalNotation = _PersistableColorTests.randomCssRgbHexadecimalNotation();
            final var instance = _PersistableColor.fromCssRgbHexadecimalNotation(
                    entityInitializer, cssRgbHexadecimalNotation);
            assertThat(instance).isNotNull().satisfies(i -> {
                assertThat(Set.of(i.toCssRgbHexadecimalNotation3(),
                                  i.toCssRgbHexadecimalNotation4(),
                                  i.toCssRgbHexadecimalNotation6(),
                                  i.toCssRgbHexadecimalNotation8()))
                        .contains(cssRgbHexadecimalNotation);
            });
        }

        @DisplayName("fromCssRgbHexadecimalNotation(cssRgbHexadecimalNotation)")
        @Test
        void __redefined() {
            final var cssRgbHexadecimalNotation = _PersistableColorTests.randomCssRgbHexadecimalNotation();
            final T instance;
            try {
                final var method = entityClass.getMethod("fromCssRgbHexadecimalNotation", String.class);
                try {
                    instance = entityClass.cast(method.invoke(null, cssRgbHexadecimalNotation));
                } catch (final ReflectiveOperationException roe) {
                    throw new RuntimeException(roe);
                }
            } catch (final NoSuchMethodException nsme) {
                return;
            }
            assertThat(instance).isNotNull().satisfies(i -> {
                assertThat(Set.of(i.toCssRgbHexadecimalNotation3(),
                                  i.toCssRgbHexadecimalNotation4(),
                                  i.toCssRgbHexadecimalNotation6(),
                                  i.toCssRgbHexadecimalNotation8()))
                        .contains(cssRgbHexadecimalNotation);
            });
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void toCssRgbHexadecimalNotation3__() {
        final var instance = newRandomizedEntityInstance();
        final var result = instance.toCssRgbHexadecimalNotation3();
        assertThat(result)
                .hasSize(3)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION3)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION);
    }

    @Test
    void toCssRgbHexadecimalNotation4__() {
        final var instance = newRandomizedEntityInstance();
        final var result = instance.toCssRgbHexadecimalNotation4();
        assertThat(result)
                .hasSize(4)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION4)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION);
    }

    @Test
    void toCssRgbHexadecimalNotation6__() {
        final var instance = newRandomizedEntityInstance();
        final var result = instance.toCssRgbHexadecimalNotation6();
        assertThat(result)
                .hasSize(6)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION6)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION);
        assertThat(_PersistableColor.fromCssRgbHexadecimalNotation(entityInitializer, result))
                .isNotNull()
                .satisfies(a -> {
                    assertThat(a.getRed()).isEqualTo(instance.getRed());
                    assertThat(a.getGreen()).isEqualTo(instance.getGreen());
                    assertThat(a.getBlue()).isEqualTo(instance.getBlue());
                });
    }

    @Test
    void toCssRgbHexadecimalNotation8__() {
        final var instance = newRandomizedEntityInstance();
        final var result = instance.toCssRgbHexadecimalNotation8();
        assertThat(result)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION8)
                .matches(_PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION);
        assertThat(_PersistableColor.fromCssRgbHexadecimalNotation(entityInitializer, result))
                .isEqualTo(instance);
    }

    @DisplayName("_PersistableColorUtils")
    @Nested
    class _PersistableColorUtilsTest {

        @DisplayName("toCIEXYZ(persistableColor,rgbProfile)[F")
        @Test
        void toCIEXYZ__() throws IOException {
            final T instance = newRandomizedEntityInstance();
            final var rgbProfiles = JavaAwtTestUtils.getIccProfiles()
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

        @DisplayName("toCMYK(persistableColor,rgbProfile,cmykProfile)")
        @Test
        void toCMYK__WithRgbProfile() throws IOException {
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
                    log.debug("cmyk: {}", Arrays.toString(cmyk));
                    assertThat(cmyk).isNotNull().hasSize(4).satisfies(f -> {
                        assertThat(f[0]).isBetween(.0f, 1.0f);
                        assertThat(f[1]).isBetween(.0f, 1.0f);
                        assertThat(f[2]).isBetween(.0f, 1.0f);
                        assertThat(f[3]).isBetween(.0f, 1.0f);
                    });
                }
            }
        }

        @DisplayName("toCMYK(persistableColor,null,cmykProfile)")
        @Test
        void toCMYK__WithoutRgbProfile() throws IOException {
            final T instance = newRandomizedEntityInstance();
            final var cmykProfiles = JavaAwtTestUtils.getIccProfiles()
                    .stream().filter(p -> p.getColorSpaceType() == ColorSpace.TYPE_CMYK)
                    .map(ICC_ColorSpace::new)
                    .toList();
            for (final var cmykProfile : cmykProfiles) {
                log.debug("cmykProfile: {}", cmykProfile);
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
