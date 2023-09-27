package com.github.jinahya.persistence;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.stream.Stream;

final class _PersistableColorTests {

    static Stream<String> cssRgbHexadecimalNotationStream() {
        return Stream.of(
                "00ff00",
                "0000ffcc",
                "123",
                "00fc"
        );
    }

    static Matcher matcherCssHexadecimalNotation(final CharSequence cssHexadecimalNotation) {
        return _PersistableColor.PATTERN_CSS_HEXADECIMAL_NOTATION.matcher(cssHexadecimalNotation);
    }

    static float[] newRandomizedComponents() {
        final int[] colors = newRandomizedColors();
        final float[] components = new float[colors.length];
        for (int i = 0; i < components.length; i++) {
            components[i] = _PersistableColor.toComponent(colors[i]);
        }
        return components;
    }

    static int[] newRandomizedColors() {
        final int[] colors = new int[_PersistableColor.NUMBER_OF_COLORS];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = newRandomizedColor();
        }
        return colors;
    }

    static float newRandomizedComponent() {
        return _PersistableColor.toComponent(newRandomizedColor());
    }

    static int newRandomizedColor() {
        return ThreadLocalRandom.current().nextInt(_PersistableColor.MAX_COLOR + 1);
    }

    static boolean matchesCssHexadecimalNotation(final CharSequence cssHexadecimalNotation) {
        return matcherCssHexadecimalNotation(cssHexadecimalNotation).matches();
    }

    static int randomOctet() {
        return ThreadLocalRandom.current().nextInt(256);
    }

    static int randomNibble() {
        return randomOctet() >> 4;
    }

    static String randomCssRgbHexadecimalNotation3() {
        return String.format("%1$x%2$x%3$x", randomNibble(), randomNibble(), randomNibble());
    }

    static String randomCssRgbHexadecimalNotation4() {
        return String.format("%1$s%2$x", randomCssRgbHexadecimalNotation3(), randomNibble());
    }

    static String randomCssRgbHexadecimalNotation6() {
        return String.format("%1$02x%2$02x%3$02x", randomOctet(), randomOctet(), randomOctet());
    }

    static String randomCssRgbHexadecimalNotation8() {
        return String.format("%1$s%2$02x", randomCssRgbHexadecimalNotation6(), randomOctet());
    }

    static String randomCssRgbHexadecimalNotation() {
        return switch (ThreadLocalRandom.current().nextInt(4)) {
            case 0 -> randomCssRgbHexadecimalNotation3();
            case 1 -> randomCssRgbHexadecimalNotation4();
            case 2 -> randomCssRgbHexadecimalNotation6();
            default -> randomCssRgbHexadecimalNotation8();
        };
    }
}
