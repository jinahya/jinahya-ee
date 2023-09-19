package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * .
 *
 * @see <a href="https://www.w3.org/TR/css-color-3/#rgba-color">.2.2. RGBA color values</a> (CSS Color Module Level 3)
 * @see <a href="https://www.w3.org/TR/css-color-4/#hex-color">.2. The RGB Hexadecimal Notations: #RRGGBB</a> (CSS Color
 * Module Level 4)
 */
@MappedSuperclass
@SuppressWarnings({
        "java:S101" // _Persistable...
})
public abstract class _PersistableColor implements _Persistable {

    // -----------------------------------------------------------------------------------------------------------------

    public static final int RGBA_COMPONENT_MIN = 0;

    public static final int RGBA_COMPONENT_MAX = 255;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_RED = "red";

    public static final String COLUMN_NAME_GREEN = "green";

    public static final String COLUMN_NAME_BLUE = "blue";

    public static final String COLUMN_NAME_ALPHA = "alpha";

    // -----------------------------------------------------------------------------------------------------------------
    private static final String PATTERN_HEX_CHAR = "[0-9a-f]";

    private static final String REGEXP_CSS_HEXADECIMAL_NOTATION3 = PATTERN_HEX_CHAR + "{3}";

    static final Pattern PATTERN_CSS_HEXADECIMAL_NOTATION3 = Pattern.compile(REGEXP_CSS_HEXADECIMAL_NOTATION3);

    private static final String REGEXP_CSS_HEXADECIMAL_NOTATION4 = PATTERN_HEX_CHAR + "{4}";

    static final Pattern PATTERN_CSS_HEXADECIMAL_NOTATION4 = Pattern.compile(REGEXP_CSS_HEXADECIMAL_NOTATION4);

    private static final String REGEXP_CSS_HEXADECIMAL_NOTATION6 = PATTERN_HEX_CHAR + "{6}";

    static final Pattern PATTERN_CSS_HEXADECIMAL_NOTATION6 = Pattern.compile(REGEXP_CSS_HEXADECIMAL_NOTATION6);

    private static final String REGEXP_CSS_HEXADECIMAL_NOTATION8 = PATTERN_HEX_CHAR + "{8}";

    static final Pattern PATTERN_CSS_HEXADECIMAL_NOTATION8 = Pattern.compile(REGEXP_CSS_HEXADECIMAL_NOTATION8);

    // https://stackoverflow.com/q/47633735/330457
    private static final String REGEXP_CSS_HEXADECIMAL_NOTATION =
            REGEXP_CSS_HEXADECIMAL_NOTATION3 + '|' +
            REGEXP_CSS_HEXADECIMAL_NOTATION4 + '|' +
            REGEXP_CSS_HEXADECIMAL_NOTATION6 + '|' +
            REGEXP_CSS_HEXADECIMAL_NOTATION8;

    static final Pattern PATTERN_CSS_HEXADECIMAL_NOTATION = Pattern.compile(REGEXP_CSS_HEXADECIMAL_NOTATION);

    // -----------------------------------------------------------------------------------------------------------------
    public static <T extends _PersistableColor> T fromCssRgbHexadecimalNotation(
            final Supplier<? extends T> initializer, final CharSequence cssRgbHexadecimalNotation) {
        Objects.requireNonNull(initializer, "initializer is null");
        Objects.requireNonNull(cssRgbHexadecimalNotation, "cssRgbHexadecimalNotation is null");
        if (!PATTERN_CSS_HEXADECIMAL_NOTATION.matcher(cssRgbHexadecimalNotation).matches()) {
            throw new IllegalArgumentException("invalid CSS RGB Hexadecimal Notation: " + cssRgbHexadecimalNotation);
        }
        final T instance = Objects.requireNonNull(initializer.get(), "null supplied from " + initializer);
        final var nibbles = String.valueOf(cssRgbHexadecimalNotation).chars().map(c -> Character.digit(c, 16)).boxed().collect(Collectors.toList());
        if (nibbles.size() == 3 || nibbles.size() == 4) {
            final var r = nibbles.remove(0);
            instance.setRed((r << 4) | r);
            final var g = nibbles.remove(0);
            instance.setGreen((g << 4) | g);
            final var b = nibbles.remove(0);
            instance.setBlue((b << 4) | b);
            if (nibbles.isEmpty()) {
                instance.setAlpha(0);
            } else {
                final var a = nibbles.remove(0);
                instance.setAlpha((a << 4) | a);
            }
            assert nibbles.isEmpty();
            return instance;
        }
        assert nibbles.size() == 6 || nibbles.size() == 8;
        instance.setRed((nibbles.remove(0) << 4) | nibbles.remove(0));
        instance.setGreen((nibbles.remove(0) << 4) | nibbles.remove(0));
        instance.setBlue((nibbles.remove(0) << 4) | nibbles.remove(0));
        if (nibbles.isEmpty()) {
            instance.setAlpha(0);
        } else {
            instance.setAlpha((nibbles.remove(0) << 4) | nibbles.remove(0));
        }
        assert nibbles.isEmpty();
        return instance;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    protected _PersistableColor() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return super.toString() + '{'
               + "red=" + red
               + ",green=" + green
               + ",blue=" + blue
               + ",alpha=" + alpha
               + '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof _PersistableColor that)) {
            return false;
        }
        return Objects.equals(red, that.red)
               && Objects.equals(green, that.green)
               && Objects.equals(blue, that.blue)
               && Objects.equals(alpha, that.alpha)
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                red,
                green,
                blue,
                alpha
        );
    }

    // -----------------------------------------------------------------------------------------------------------------

    public <R> R apply(
            final IntFunction<
                    ? extends IntFunction<
                            ? extends IntFunction<
                                    ? extends IntFunction<
                                            ? extends R>>>> function) {
        Objects.requireNonNull(function, "function is null");
        return function
                .apply(getRed())
                .apply(getGreen())
                .apply(getBlue())
                .apply(getAlpha())
                ;
    }

    public <R> R applyHigh(
            final IntFunction<
                    ? extends IntFunction<
                            ? extends IntFunction<
                                    ? extends IntFunction<
                                            ? extends R>>>> function) {
        Objects.requireNonNull(function, "function is null");
        return apply(
                r -> g -> b -> a ->
                        function.apply(r >> 4)
                                .apply(g >> 4)
                                .apply(b >> 4)
                                .apply(a >> 4)
        );
    }

    @SuppressWarnings({"unchecked"})
    public <A extends Appendable> A toCssRgbHexadecimalNotation3(final A appendable) {
        Objects.requireNonNull(appendable, "appendable is null");
        return (A) applyHigh(
                r -> g -> b -> a -> new Formatter(appendable).format("%1$x%2$x%3$x", r, g, b).out()
        );
    }

    @SuppressWarnings({"unchecked"})
    public <A extends Appendable> A toCssRgbHexadecimalNotation6(final A appendable) {
        Objects.requireNonNull(appendable, "appendable is null");
        return (A) apply(
                r -> g -> b -> a -> new Formatter(appendable).format("%1$02x%2$02x%3$02x", r, g, b).out()
        );
    }

    @SuppressWarnings({"unchecked"})
    public <A extends Appendable> A toCssRgbHexadecimalNotation4(final A appendable) {
        Objects.requireNonNull(appendable, "appendable is null");
        return (A) applyHigh(
                r -> g -> b -> a -> new Formatter(appendable).format("%1$x%2$x%3$x%4$x", r, g, b, a).out()
        );
    }

    @SuppressWarnings({"unchecked"})
    public <A extends Appendable> A toCssRgbHexadecimalNotation8(final A appendable) {
        Objects.requireNonNull(appendable, "appendable is null");
        return (A) apply(
                r -> g -> b -> a -> new Formatter(appendable).format("%1$02x%2$02x%3$02x%4$02x", r, g, b, a).out()
        );
    }

    public String toCssRgbHexadecimalNotation3() {
        return toCssRgbHexadecimalNotation3(new StringBuilder()).toString();
    }

    public String toCssRgbHexadecimalNotation6() {
        return toCssRgbHexadecimalNotation6(new StringBuilder()).toString();
    }

    public String toCssRgbHexadecimalNotation4() {
        return toCssRgbHexadecimalNotation4(new StringBuilder()).toString();
    }

    public String toCssRgbHexadecimalNotation8() {
        return toCssRgbHexadecimalNotation8(new StringBuilder()).toString();
    }

    // ------------------------------------------------------------------------------------------------------------- red
    public Integer getRed() {
        return red;
    }

    public void setRed(final Integer red) {
        this.red = red;
    }

    // ----------------------------------------------------------------------------------------------------------- green
    public Integer getGreen() {
        return green;
    }

    public void setGreen(final Integer green) {
        this.green = green;
    }

    // ------------------------------------------------------------------------------------------------------------ blue
    public Integer getBlue() {
        return blue;
    }

    public void setBlue(final Integer blue) {
        this.blue = blue;
    }

    // ----------------------------------------------------------------------------------------------------------- alpha
    public Integer getAlpha() {
        return alpha;
    }

    public void setAlpha(final Integer alpha) {
        this.alpha = alpha;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Max(RGBA_COMPONENT_MAX)
    @Min(RGBA_COMPONENT_MIN)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_RED, nullable = false, insertable = true, updatable = true)
    private Integer red;

    @Max(RGBA_COMPONENT_MAX)
    @Min(RGBA_COMPONENT_MIN)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_GREEN, nullable = false, insertable = true, updatable = true)
    private Integer green;

    @Max(RGBA_COMPONENT_MAX)
    @Min(RGBA_COMPONENT_MIN)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_BLUE, nullable = false, insertable = true, updatable = true)
    private Integer blue;

    @Max(RGBA_COMPONENT_MAX)
    @Min(RGBA_COMPONENT_MIN)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_ALPHA, nullable = false, insertable = true, updatable = true)
    private Integer alpha;
}
