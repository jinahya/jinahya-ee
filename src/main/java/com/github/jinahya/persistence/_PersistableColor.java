package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * .
 *
 * @see <a href="https://www.w3.org/TR/css-color-3/#rgba-color">4.2.2. RGBA color values</a> (www.w3.org / CSS Color
 * Module Level 3)
 * @see <a href="https://www.w3.org/TR/css-color-4/#hex-color">5.2. The RGB Hexadecimal Notations: '#RRGGBB'</a>
 * (www.w3.org / CSS Color Module Level 4)
 */
@MappedSuperclass
@SuppressWarnings({
        "java:S101" // _Persistable...
})
public abstract class _PersistableColor extends _AbstractPersistable {

    @Serial
    private static final long serialVersionUID = -5630953064592505401L;

    // -----------------------------------------------------------------------------------------------------------------

    public static final int MIN_COMPONENT = 0;

    public static final int MAX_COMPONENT = 255;

    private static final float MIN_COMPONENT_FLOAT = MIN_COMPONENT;

    private static final float MAX_COMPONENT_FLOAT = 1.0f;

    private static final String DECIMAL_MIN_COMPONENT = "0.0";

    private static final String DECIMAL_MAX_COMPONENT = "1.0";

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

    /**
     * Returns an instance, supplied by specified supplier, whose color components set with values parsed from specified
     * hexadecimal notation.
     *
     * @param initializer               the supplier for the instance.
     * @param cssRgbHexadecimalNotation the hexadecimal notation to parse.
     * @param <T>                       color type parameter
     * @return an instance.
     * @see <a href="https://www.w3.org/TR/css-color-4/#hex-color">5.2. The RGB Hexadecimal Notations: '#RRGGBB'</a>
     * (CSS Color Module Level 4)
     */
    public static <T extends _PersistableColor> T fromCssRgbHexadecimalNotation(
            final Supplier<? extends T> initializer, final CharSequence cssRgbHexadecimalNotation) {
        Objects.requireNonNull(initializer, "initializer is null");
        Objects.requireNonNull(cssRgbHexadecimalNotation, "cssRgbHexadecimalNotation is null");
        if (!PATTERN_CSS_HEXADECIMAL_NOTATION.matcher(cssRgbHexadecimalNotation).matches()) {
            throw new IllegalArgumentException("invalid CSS RGB Hexadecimal Notation: " + cssRgbHexadecimalNotation);
        }
        final T instance = Objects.requireNonNull(initializer.get(), "null supplied from " + initializer);
        final var nibbles = cssRgbHexadecimalNotation.chars()
                .map(c -> Character.digit(c, 16))
                .boxed()
                .collect(Collectors.toList());
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof _PersistableColor that)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        return Objects.equals(red, that.red)
               && Objects.equals(green, that.green)
               && Objects.equals(blue, that.blue)
               && Objects.equals(alpha, that.alpha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                red,
                green,
                blue,
                alpha
        );
    }

// -----------------------------------------------------------------------------------------------------------------

    protected <R> R apply(
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

    protected <R> R applyHigh(
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

    /**
     * Returns a {@code 3}-long hexadecimal string representation of color components.
     *
     * @return a string representation of {@code rgb}.
     * @see #toCssRgbHexadecimalNotation4()
     * @see <a href="https://www.w3.org/TR/css-color-4/#hex-color">5.2. The RGB Hexadecimal Notations: '#RRGGBB'</a>
     * (CSS Color Module Level 4)
     */
    public String toCssRgbHexadecimalNotation3() {
        return applyHigh(r -> g -> b -> a -> String.format("%1$x%2$x%3$x", r, g, b));
    }

    /**
     * Returns a {@code 4}-long hexadecimal string representation of color components.
     *
     * @return a string representation of {@code rgba}.
     * @see #toCssRgbHexadecimalNotation3()
     * @see <a href="https://www.w3.org/TR/css-color-4/#hex-color">5.2. The RGB Hexadecimal Notations: '#RRGGBB'</a>
     * (CSS Color Module Level 4)
     */
    public String toCssRgbHexadecimalNotation4() {
        return applyHigh(r -> g -> b -> a -> String.format("%1$x%2$x%3$x%4$x", r, g, b, a));
    }

    /**
     * Returns a {@code 6}-long hexadecimal string representation of color components.
     *
     * @return a string representation of {@code rrggbb}.
     * @see #toCssRgbHexadecimalNotation8()
     * @see <a href="https://www.w3.org/TR/css-color-4/#hex-color">5.2. The RGB Hexadecimal Notations: '#RRGGBB'</a>
     * (CSS Color Module Level 4)
     */
    public String toCssRgbHexadecimalNotation6() {
        return apply(r -> g -> b -> a -> String.format("%1$02x%2$02x%3$02x", r, g, b));
    }

    /**
     * Returns a {@code 8}-long hexadecimal string representation of color components.
     *
     * @return a string representation of {@code rrggbbaa}.
     * @see #toCssRgbHexadecimalNotation6()
     * @see <a href="https://www.w3.org/TR/css-color-4/#hex-color">5.2. The RGB Hexadecimal Notations: '#RRGGBB'</a>
     * (CSS Color Module Level 4)
     */
    public String toCssRgbHexadecimalNotation8() {
        return apply(r -> g -> b -> a -> String.format("%1$02x%2$02x%3$02x%4$02x", r, g, b, a));
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Sets four color components on specified array starting at specified offset.
     *
     * @param offset     the index of the array to which the first component is set.
     * @param components the array on which color components are set; may be {@code null} or should be longer than
     *                   {@code 4}.
     * @return given {@code components} or a newly created array.
     */
    @Transient
    public float[] getComponents(final int offset, float[] components) {
        if (offset < 0) {
            throw new IllegalArgumentException("offset(" + offset + ") is negative");
        }
        if (components != null && (components.length < (offset + 4))) {
            throw new IllegalArgumentException(
                    "components.length(" + components.length + ") < offset(" + offset + ") + 4");
        }
        if (components == null) {
            components = new float[4];
        }
        assert components.length >= offset + 4;
        final var divisor = (float) _PersistableColor.MAX_COMPONENT;
        components[0] = ((float) getRed()) / divisor;
        components[1] = ((float) getGreen()) / divisor;
        components[2] = ((float) getBlue()) / divisor;
        components[3] = ((float) getAlpha()) / divisor;
        return components;
    }

    /**
     * Sets four color components on specified array starting at specified offset.
     *
     * @param components the array on which color components are set; may be {@code null} or should be longer than
     *                   {@code 4}.
     * @return given {@code components} or a newly created array.
     */
    @Transient
    public float[] getComponents(float[] components) {
        return getComponents(0, components);
    }

    // ------------------------------------------------------------------------------------------------------------- red

    /**
     * Returns current value of {@link _PersistableColor_#red} attribute.
     *
     * @return current value of the {@link _PersistableColor_#red} attribute.
     */
    public Integer getRed() {
        return red;
    }

    /**
     * Replaces current value of {@link _PersistableColor_#red} attribute with specified value.
     *
     * @param red new value of the {@link _PersistableColor_#red} attribute.
     */
    public void setRed(final Integer red) {
        this.red = red;
    }

    /**
     * Returns current value of {@link _PersistableColor_#red} attribute as {@code float} value.
     *
     * @return current value of the {@link _PersistableColor_#red} attribute as {@code float} value; between
     * {@value #MIN_COMPONENT_FLOAT} and {@value #MAX_COMPONENT_FLOAT}, both inclusive.
     */
    @DecimalMax(DECIMAL_MAX_COMPONENT)
    @DecimalMin(DECIMAL_MIN_COMPONENT)
    @Transient
    public Float getRedComponent() {
        return Optional.ofNullable(getRed()).map(v -> v / MAX_COMPONENT_FLOAT).orElse(null);
    }

    public void setRedComponent(final Float redComponent) {
        setRed(Optional.ofNullable(redComponent).map(v -> (int) (v * MAX_COMPONENT)).orElse(null));
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
    @Max(MAX_COMPONENT)
    @Min(MIN_COMPONENT)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_RED, nullable = false, insertable = true, updatable = true)
    private Integer red;

    @Max(MAX_COMPONENT)
    @Min(MIN_COMPONENT)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_GREEN, nullable = false, insertable = true, updatable = true)
    private Integer green;

    @Max(MAX_COMPONENT)
    @Min(MIN_COMPONENT)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_BLUE, nullable = false, insertable = true, updatable = true)
    private Integer blue;

    @Max(MAX_COMPONENT)
    @Min(MIN_COMPONENT)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_ALPHA, nullable = false, insertable = true, updatable = true)
    private Integer alpha;
}
