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

import javax.persistence.AttributeConverter;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public abstract class AttributeConveterTest<T extends AttributeConverter<X, Y>, X, Y> {

    public AttributeConveterTest(final Class<T> converterClass) {
        super();
        this.converterClass = converterClass;
    }

    protected T converterInstance() {
        try {
            return converterClass.newInstance();
        } catch (final ReflectiveOperationException roe) {
            fail("failed to instantiate " + converterClass, roe);
            throw new RuntimeException(roe);
        }
    }

    protected void withEntityAttribute(final X entityAttribute) {
        final T converterInstance = converterInstance();
        final Y databaseColumn
                = converterInstance.convertToDatabaseColumn(entityAttribute);
        assertEquals(converterInstance.convertToEntityAttribute(databaseColumn),
                     entityAttribute);
    }

    protected void withDatabaseColumn(final Y databaseColumn) {
        final T converterInstance = converterInstance();
        final X entityAttribute
                = converterInstance.convertToEntityAttribute(databaseColumn);
        assertEquals(converterInstance.convertToDatabaseColumn(entityAttribute),
                     databaseColumn);
    }

    protected final Class<T> converterClass;
}
