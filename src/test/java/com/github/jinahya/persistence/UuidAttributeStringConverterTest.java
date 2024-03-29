/*
 * Copyright 2016 Jin Kwon &lt;onacit at gmail.com&gt;.
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

import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @author Jin Kwon &lt;onacit at gmail.com&gt;
 */
public class UuidAttributeStringConverterTest
        extends AttributeConveterTest<UuidAttributeStringConverter, UUID, String> {

    public UuidAttributeStringConverterTest() {
        super(UuidAttributeStringConverter.class);
    }

    @Test
    public void withEntityAttribute() {
        withEntityAttribute(UUID.randomUUID());
    }

    @Test
    public void withDatabaseColumn() {
        withDatabaseColumn(UUID.randomUUID().toString());
    }
}
