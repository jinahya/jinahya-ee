package com.github.jinahya.persistence;

import jakarta.persistence.Embeddable;

import java.io.Serial;
import java.time.Instant;

@Embeddable
public class EmbeddableInstant extends _PersistableInstant {

    @Serial
    private static final long serialVersionUID = -718528298074009584L;

    // -----------------------------------------------------------------------------------------------------------------

    public static EmbeddableInstant from(final Instant instant) {
        return from(EmbeddableInstant::new, instant);
    }
}
