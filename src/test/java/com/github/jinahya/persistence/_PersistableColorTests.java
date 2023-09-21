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

    static int newRandomizedRgbComponent() {
        return ThreadLocalRandom.current().nextInt(_PersistableColor.MAX_COMPONENT + 1);
    }

    static boolean matchesCssHexadecimalNotation(final CharSequence cssHexadecimalNotation) {
        return matcherCssHexadecimalNotation(cssHexadecimalNotation).matches();
    }
}
