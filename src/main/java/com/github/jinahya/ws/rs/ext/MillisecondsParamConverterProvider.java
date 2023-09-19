/*
 * Copyright 2015 Jin Kwon &lt;jinahya_at_gmail.com&gt;.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jinahya.ws.rs.ext;

import com.github.jinahya.ws.rs.MillisecondsParam;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @param <T> converter type parameter
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public interface MillisecondsParamConverterProvider<T extends MillisecondsParamConverter<?>>
        extends ParamConverterProvider {

    @Override
    @SuppressWarnings("unchecked")
    default <S> ParamConverter<S> getConverter(final Class<S> rawType,
                                               final Type genericType,
                                               final Annotation[] annotations) {
        for (final Annotation annotation : annotations) {
            if (MillisecondsParam.class.isInstance(annotation)) {
                return (ParamConverter<S>) getMillisecondsConverter(
                        rawType, genericType, annotations);
            }
        }
        return null;
    }

    /**
     * Provides a {@link ParamConverter} that can provide from/to string conversion for an instance of particular Java
     * type.
     *
     * @param <S>
     * @param rawType
     * @param genericType
     * @param annotations
     * @return the converter, otherwise {@code null}.
     */
    <S> T getMillisecondsConverter(Class<S> rawType, Type genericType,
                                   Annotation[] annotations);
}
