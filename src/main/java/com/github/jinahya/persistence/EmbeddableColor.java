package com.github.jinahya.persistence;

import jakarta.persistence.Embeddable;

import java.io.Serial;

@Embeddable
public class EmbeddableColor extends _PersistableColor {

    @Serial
    private static final long serialVersionUID = -6850092420567493544L;

    // -----------------------------------------------------------------------------------------------------------------

    public static EmbeddableColor fromComponents(final float[] components) {
        return fromComponents(EmbeddableColor::new, components);
    }

    public static EmbeddableColor fromCssRgbHexadecimalNotation(final CharSequence cssRgbHexadecimalNotation) {
        return fromCssRgbHexadecimalNotation(EmbeddableColor::new, cssRgbHexadecimalNotation);
    }
}
