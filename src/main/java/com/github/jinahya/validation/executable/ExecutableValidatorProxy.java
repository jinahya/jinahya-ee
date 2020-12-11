package com.github.jinahya.validation.executable;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.executable.ExecutableValidator;

import java.lang.reflect.Proxy;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public final class ExecutableValidatorProxy {

    public static Object newProxyInstance(final ClassLoader loader, final Class<?>[] interfaces, final Object object,
                                          final ExecutableValidator validator, final Class<?>[] groups) {
        requireNonNull(validator, "validator is null");
        return Proxy.newProxyInstance(loader, interfaces, (proxy, method, args) -> {
            {
                final Set<ConstraintViolation<Object>> violations
                        = validator.validateParameters(object, method, args, groups);
                if (!violations.isEmpty()) {
                    throw new ConstraintViolationException(violations);
                }
            }
            final Object returnValue = method.invoke(object, args);
            {
                final Set<ConstraintViolation<Object>> violations
                        = validator.validateReturnValue(object, method, returnValue, groups);
                if (!violations.isEmpty()) {
                    throw new ConstraintViolationException(violations);
                }
            }
            return returnValue;
        });
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T newProxyInstance(final ClassLoader loader, final Class<T> interface_, final T object,
                                         final ExecutableValidator validator, final Class<?>[] groups) {
        return (T) newProxyInstance(loader, new Class<?>[] {interface_}, object, validator, groups);
    }

    private ExecutableValidatorProxy() {
        throw new AssertionError("instantiation is not allowed");
    }
}
