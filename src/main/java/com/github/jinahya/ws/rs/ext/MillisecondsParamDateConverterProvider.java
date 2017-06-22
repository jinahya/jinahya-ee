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
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;

/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public abstract class MillisecondsParamDateConverterProvider
        implements MillisecondsParamConverterProvider<MillisecondsParamDateConverter> {

    @Override
    public <S> MillisecondsParamDateConverter getMillisecondsConverter(
            final Class<S> rawType, final Type genericType,
            final Annotation[] annotations) {
        if (!Date.class.isAssignableFrom(rawType)) {
            return null;
        }
        for (final Annotation annotation : annotations) {
            if (MillisecondsParam.class.isInstance(annotation)) {
                return new MillisecondsParamDateConverter();
            }
        }
        return null;
    }
}
