package com.github.jinahya.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

abstract class _PersistableTest<P extends _Persistable> {

    _PersistableTest(final Class<P> entityClass) {
        super();
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass is null");
        entityInitializer = this::newEntityInstance;
        randomizedEntityInitializer = this::newRandomizedEntityInstance;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Nested
    class EqualsTest {

        @Test
        void __NewEntityInstance() {
            assertThat(newEntityInstance()).isEqualTo(newEntityInstance());
        }
    }

    @Nested
    class HashCodeTest {

        @Test
        void __NewEntityInstance() {
            final var instance = newEntityInstance();
            assertThatCode(instance::hashCode).doesNotThrowAnyException();
        }

        @Test
        void __NewRandomizedEntityInstance() {
            final var instance = newRandomizedEntityInstance();
            assertThatCode(instance::hashCode).doesNotThrowAnyException();
        }
    }

    @Nested
    class ToStringTest {

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
    }

    // -----------------------------------------------------------------------------------------------------------------
    private void properties__(final P bean)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Objects.requireNonNull(bean, "bean is null");
        final var info = Introspector.getBeanInfo(entityClass);
        final var descriptors = info.getPropertyDescriptors();
        if (descriptors == null) {
            return;
        }
        for (var descriptor : descriptors) {
            final var reader = descriptor.getReadMethod();
            if (reader == null) {
                continue;
            }
            if (!_Persistable.class.isAssignableFrom(reader.getDeclaringClass())) {
                continue;
            }
            if (!reader.canAccess(bean)) {
                reader.setAccessible(true);
            }
            // https://github.com/assertj/assertj/issues/1652
            final var value = reader.invoke(bean);
            final var writer = descriptor.getWriteMethod();
            if (writer == null) {
                continue;
            }
            if (!writer.canAccess(bean)) {
                writer.setAccessible(true);
            }
            assertThatCode(() -> writer.invoke(bean, value)).doesNotThrowAnyException();
        }
    }

    @DisplayName("newEntityInstance/set...(get...())")
    @Test
    void properties__newEntityInstance()
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        properties__(newEntityInstance());
    }

    @DisplayName("newRandomizedEntityInstance/set...(get...())")
    @Test
    void properties__newRandomizedEntityInstance()
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        properties__(newRandomizedEntityInstance());
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns a new randomized instance of {@link #entityClass}.
     *
     * @return a new randomized instance of {@link #entityClass}.
     * @implNote Default implementation invokes {@link #newEntityInstance()} method, and returns the result.
     */
    P newRandomizedEntityInstance() {
        return newEntityInstance();
    }

    /**
     * Returns a new instance of {@link #entityClass}.
     *
     * @return a new instance of {@link #entityClass}.
     */
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
