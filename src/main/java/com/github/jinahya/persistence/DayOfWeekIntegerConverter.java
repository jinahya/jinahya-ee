/*
 * Copyright 2017 Jin Kwon &lt;onacit@gmail.com&gt;.
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
package com.github.jinahya.persistence;

import javax.persistence.Converter;
import java.time.DayOfWeek;

import static java.util.Optional.ofNullable;

/**
 * A converter for converting a {@link DayOfWeek} into an {@code Integer} and vice versa.
 *
 * @author Jin Kwon &lt;onacit@gmail.com&gt;
 */
@Converter
public class DayOfWeekIntegerConverter
        implements EnumAttributeConverter<DayOfWeek, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final DayOfWeek attribute) {
        return ofNullable(attribute).map(DayOfWeek::getValue).orElse(null);
    }

    @Override
    public DayOfWeek convertToEntityAttribute(final Integer dbData) {
        return ofNullable(dbData).map(DayOfWeek::of).orElse(null);
    }
}
