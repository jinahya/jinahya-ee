package com.github.jinahya.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

abstract class _PersistableTest<P extends _Persistable> {

    _PersistableTest(final Class<P> entityClass) {
        super();
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass is null");
        entityInitializer = this::newEntityInstance;
        randomizedEntityInitializer = this::newRandomizedEntityInstance;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @DisplayName("newEntityInstance().toString() should not blank")
    @Test
    void toString_NotBlank_NewEntityInstance() {
        final var string = newEntityInstance().toString();
        assertThat(string).isNotBlank();
    }

    @DisplayName("newRandomizedEntityInstance().toString() should not blank")
    @Test
    void toString_NotBlank_NewRandomizedEntityInstance() {
        final var string = newRandomizedEntityInstance().toString();
        assertThat(string).isNotBlank();
    }

    // -----------------------------------------------------------------------------------------------------------------
    P newRandomizedEntityInstance() {
        return newEntityInstance();
    }

    final P newEntityInstance() {
        try {
            final var constructor = entityClass.getDeclaredConstructor();
            if (!constructor.canAccess(null)) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance();
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException(roe);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    final Class<P> entityClass;

    final Supplier<P> entityInitializer;

    final Supplier<P> randomizedEntityInitializer;
}
