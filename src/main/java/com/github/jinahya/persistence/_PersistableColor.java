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

    public static final int MIN_COLOR = 0;

    public static final int MAX_COLOR = 255;

    static final float MIN_COMPONENT = 0.0f;

    static final float MAX_COMPONENT = 1.0f;

    private static final String DECIMAL_MIN_COMPONENT = "0.0";

    private static final String DECIMAL_MAX_COMPONENT = "1.0";

    static final int NUMBER_OF_COLORS = 4;

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
    private static int requireValidColor(final int color) {
        if (color < MIN_COLOR) {
            throw new IllegalArgumentException("color(" + color + ") < " + MIN_COLOR);
        }
        if (color > MAX_COLOR) {
            throw new IllegalArgumentException("color(" + color + ") > " + MAX_COLOR);
        }
        return color;
    }

    static float requireValidComponent(final float component) {
        if (component < MIN_COMPONENT) {
            throw new IllegalArgumentException("component(" + component + ") < " + MIN_COMPONENT);
        }
        if (component > MAX_COMPONENT) {
            throw new IllegalArgumentException("component(" + component + ") > " + MAX_COMPONENT);
        }
        return component;
    }

    static float toComponent(final int color) {
        return requireValidColor(color) / (float) MAX_COLOR;
    }

    static int toColor(final float component) {
        return (int) (requireValidComponent(component) * MAX_COLOR);
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static <T extends _PersistableColor> T fromComponents(
            final Supplier<? extends T> initializer, final float[] components) {
        Objects.requireNonNull(initializer, "initializer is null");
        Objects.requireNonNull(components, "components is null");
        if (components.length != NUMBER_OF_COLORS) {
            throw new IllegalArgumentException("components.length(" + components.length + " != " + NUMBER_OF_COLORS);
        }
        final T instance = Objects.requireNonNull(initializer.get(), "null supplied from " + initializer);
        int index = -1;
        instance.setRedComponent(components[++index]);
        instance.setGreenComponent(components[++index]);
        instance.setBlueComponent(components[++index]);
        instance.setAlphaComponent(components[++index]);
        return instance;
    }

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
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof _PersistableColor that)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        return Objects.equals(red, that.red) &&
               Objects.equals(green, that.green) &&
               Objects.equals(blue, that.blue) &&
               Objects.equals(alpha, that.alpha);
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
    @Transient
    public int[] getColors() {
        final int[] colors = new int[NUMBER_OF_COLORS];
        int index = -1;
        colors[++index] = getRed();
        colors[++index] = getGreen();
        colors[++index] = getBlue();
        colors[++index] = getAlpha();
        return colors;
    }

    @Transient
    public float[] getComponents() {
        final int[] colors = getColors();
        final float[] components = new float[colors.length];
        for (int i = 0; i < components.length; i++) {
            components[i] = toComponent(colors[i]);
        }
        return components;
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
     * Returns current value of {@link _PersistableColor_#red} attribute as a {@code float} value.
     *
     * @return current value of the {@link _PersistableColor_#red} attribute; between {@value #MIN_COMPONENT} and
     * {@value #MAX_COMPONENT}, both inclusive.
     */
    @DecimalMax(DECIMAL_MAX_COMPONENT)
    @DecimalMin(DECIMAL_MIN_COMPONENT)
    @Transient
    public Float getRedComponent() {
        return Optional.ofNullable(getRed()).map(_PersistableColor::toComponent).orElse(null);
    }

    /**
     * Replaces current value of {@link _PersistableColor_#red} attribute with specified value.
     *
     * @param redComponent new value for the {@link _PersistableColor_#red} attribute; between {@value #MIN_COMPONENT}
     *                     and {@value #MAX_COMPONENT}, both inclusive.
     */
    public void setRedComponent(final Float redComponent) {
        if (redComponent != null) {
            requireValidComponent(redComponent);
        }
        setRed(Optional.ofNullable(redComponent).map(_PersistableColor::toColor).orElse(null));
    }

    // ----------------------------------------------------------------------------------------------------------- green
    public Integer getGreen() {
        return green;
    }

    public void setGreen(final Integer green) {
        this.green = green;
    }

    /**
     * Returns current value of {@link _PersistableColor_#green} attribute as a {@code float} value.
     *
     * @return current value of the {@link _PersistableColor_#green} attribute; between {@value #MIN_COMPONENT} and
     * {@value #MAX_COMPONENT}, both inclusive.
     */
    @DecimalMax(DECIMAL_MAX_COMPONENT)
    @DecimalMin(DECIMAL_MIN_COMPONENT)
    @Transient
    public Float getGreenComponent() {
        return Optional.ofNullable(getGreen()).map(_PersistableColor::toComponent).orElse(null);
    }

    /**
     * Replaces current value of {@link _PersistableColor_#green} attribute with specified value.
     *
     * @param greenComponent new value for the {@link _PersistableColor_#green} attribute; between
     *                       {@value #MIN_COMPONENT} and {@value #MAX_COMPONENT}, both inclusive.
     */
    public void setGreenComponent(final Float greenComponent) {
        if (greenComponent != null) {
            requireValidComponent(greenComponent);
        }
        setGreen(Optional.ofNullable(greenComponent).map(_PersistableColor::toColor).orElse(null));
    }

    // ------------------------------------------------------------------------------------------------------------ blue
    public Integer getBlue() {
        return blue;
    }

    public void setBlue(final Integer blue) {
        this.blue = blue;
    }

    /**
     * Returns current value of {@link _PersistableColor_#blue} attribute as a {@code float} value.
     *
     * @return current value of the {@link _PersistableColor_#blue} attribute; between {@value #MIN_COMPONENT} and
     * {@value #MAX_COMPONENT}, both inclusive.
     */
    @DecimalMax(DECIMAL_MAX_COMPONENT)
    @DecimalMin(DECIMAL_MIN_COMPONENT)
    @Transient
    public Float getBlueComponent() {
        return Optional.ofNullable(getBlue()).map(_PersistableColor::toComponent).orElse(null);
    }

    /**
     * Replaces current value of {@link _PersistableColor_#blue} attribute with specified value.
     *
     * @param blueComponent new value for the {@link _PersistableColor_#blue} attribute; between {@value #MIN_COMPONENT}
     *                      and {@value #MAX_COMPONENT}, both inclusive.
     */
    public void setBlueComponent(final Float blueComponent) {
        if (blueComponent != null) {
            requireValidComponent(blueComponent);
        }
        setBlue(Optional.ofNullable(blueComponent).map(_PersistableColor::toColor).orElse(null));
    }

    // ----------------------------------------------------------------------------------------------------------- alpha
    public Integer getAlpha() {
        return alpha;
    }

    public void setAlpha(final Integer alpha) {
        this.alpha = alpha;
    }

    /**
     * Returns current value of {@link _PersistableColor_#alpha} attribute as a {@code float} value.
     *
     * @return current value of the {@link _PersistableColor_#alpha} attribute; between {@value #MIN_COMPONENT} and
     * {@value #MAX_COMPONENT}, both inclusive.
     */
    @DecimalMax(DECIMAL_MAX_COMPONENT)
    @DecimalMin(DECIMAL_MIN_COMPONENT)
    @Transient
    public Float getAlphaComponent() {
        return Optional.ofNullable(getAlpha()).map(_PersistableColor::toComponent).orElse(null);
    }

    /**
     * Replaces current value of {@link _PersistableColor_#alpha} attribute with specified value.
     *
     * @param alphaComponent new value for the {@link _PersistableColor_#alpha} attribute; between
     *                       {@value #MIN_COMPONENT} and {@value #MAX_COMPONENT}, both inclusive.
     */
    public void setAlphaComponent(final Float alphaComponent) {
        if (alphaComponent != null) {
            requireValidComponent(alphaComponent);
        }
        setAlpha(Optional.ofNullable(alphaComponent).map(_PersistableColor::toColor).orElse(null));
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Max(MAX_COLOR)
    @Min(MIN_COLOR)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_RED, nullable = false, insertable = true, updatable = true)
    private Integer red = MIN_COLOR;

    @Max(MAX_COLOR)
    @Min(MIN_COLOR)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_GREEN, nullable = false, insertable = true, updatable = true)
    private Integer green = MIN_COLOR;

    @Max(MAX_COLOR)
    @Min(MIN_COLOR)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_BLUE, nullable = false, insertable = true, updatable = true)
    private Integer blue = MIN_COLOR;

    @Max(MAX_COLOR)
    @Min(MIN_COLOR)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_ALPHA, nullable = false, insertable = true, updatable = true)
    private Integer alpha = MIN_COLOR;
}
