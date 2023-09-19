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

    private static final String PATTERN_HEXADECIMAL_NOTATION3 = PATTERN_HEX_CHAR + "{3}";

    private static final String PATTERN_HEXADECIMAL_NOTATION4 = PATTERN_HEX_CHAR + "{4}";

    private static final String PATTERN_HEXADECIMAL_NOTATION6 = PATTERN_HEX_CHAR + "{6}";

    private static final String PATTERN_HEXADECIMAL_NOTATION8 = PATTERN_HEX_CHAR + "{8}";

    private static final String PATTERN_HEXADECIMAL_NOTATION =
            '(' + PATTERN_HEX_CHAR + "{3}|(?1){4}|(?1){6}|(?1){8}|(?1){4})";

    // -----------------------------------------------------------------------------------------------------------------
    public static <T extends _PersistableColor> T fromHexadecimalNotation3(final Supplier<? extends T> instanceSupplier,
                                                                           final String hexadecimalNotation) {
        Objects.requireNonNull(instanceSupplier, "instanceSupplier is null");
        Objects.requireNonNull(hexadecimalNotation, "hexadecimalNotation is null");
        final var nibbles = new ArrayList<Integer>(8);
        try (var scanner = new Scanner(hexadecimalNotation.toLowerCase())) {
            while (scanner.hasNext()) {
                nibbles.add(Integer.parseInt(scanner.next(PATTERN_HEX_CHAR), 16));
            }
        }
        if (nibbles.size() == 8) {
        }
        if (nibbles.size() == 6) {
        }
        if (nibbles.size() == 4) {
        }
        if (nibbles.size() == 3) {
        }
        return null;
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
                        function.apply(r >> Short.SIZE)
                                .apply(g >> Short.SIZE)
                                .apply(b >> Short.SIZE)
                                .apply(a >> Short.SIZE)
        );
    }

    @SuppressWarnings({"unchecked"})
    public <A extends Appendable> A toHexadecimalNotation3(final A appendable) {
        Objects.requireNonNull(appendable, "appendable is null");
        return (A) applyHigh(
                r -> g -> b -> a -> new Formatter(appendable).format("%1$x%2$x%3$x", r, g, b).out()
        );
    }

    @SuppressWarnings({"unchecked"})
    public <A extends Appendable> A toHexadecimalNotation6(final A appendable) {
        Objects.requireNonNull(appendable, "appendable is null");
        return (A) apply(
                r -> g -> b -> a -> new Formatter(appendable).format("%1$02x%2$02x%3$02x", r, g, b).out()
        );
    }

    @SuppressWarnings({"unchecked"})
    public <A extends Appendable> A toHexadecimalNotation4(final A appendable) {
        Objects.requireNonNull(appendable, "appendable is null");
        return (A) applyHigh(
                r -> g -> b -> a -> new Formatter(appendable).format("1$%x%2$x%x%3$x%4$x", r, g, b, a).out()
        );
    }

    @SuppressWarnings({"unchecked"})
    public <A extends Appendable> A toHexadecimalNotation8(final A appendable) {
        Objects.requireNonNull(appendable, "appendable is null");
        return (A) apply(
                r -> g -> b -> a -> new Formatter(appendable).format("%1$02x%2$02x%3$02x%4$02x", r, g, b, a).out()
        );
    }

    public String toHexadecimalNotation3() {
        return toHexadecimalNotation3(new StringBuilder()).toString();
    }

    public String toHexadecimalNotation6() {
        return toHexadecimalNotation6(new StringBuilder()).toString();
    }

    public String toHexadecimalNotation4() {
        return toHexadecimalNotation4(new StringBuilder()).toString();
    }

    public String toHexadecimalNotation8() {
        return toHexadecimalNotation8(new StringBuilder()).toString();
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
