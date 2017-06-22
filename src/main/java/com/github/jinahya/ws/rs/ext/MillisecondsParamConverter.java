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

import java.util.Optional;
import static java.util.Optional.ofNullable;
import javax.ws.rs.ext.ParamConverter;

/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 * @param <T>
 */
public interface MillisecondsParamConverter<T> extends ParamConverter<T> {

    default Optional<Long> millisecondsFromString(final String value) {

        return ofNullable(value).map(Long::parseLong);
    }

    default Optional<String> millisecondsToString(final Long value) {

        return ofNullable(value).map(Object::toString);
    }

}
