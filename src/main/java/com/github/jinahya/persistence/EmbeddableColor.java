package com.github.jinahya.persistence;

import jakarta.persistence.Embeddable;

@Embeddable
public class EmbeddableColor extends _PersistableColor {

    // -----------------------------------------------------------------------------------------------------------------

    public static EmbeddableColor from(final CharSequence cssRgbHexadecimalNotation) {
        return fromCssRgbHexadecimalNotation(EmbeddableColor::new, cssRgbHexadecimalNotation);
    }
}
