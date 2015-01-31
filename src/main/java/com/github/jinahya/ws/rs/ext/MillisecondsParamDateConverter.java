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


import java.util.Date;
import static java.util.Optional.ofNullable;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class MillisecondsParamDateConverter
    implements MillisecondsParamConverter<Date> {


    @Override
    public Date fromString(final String value) {

        return millisecondsFromString(value)
            .map(Date::new)
            .orElse(null);
    }


    @Override
    public String toString(final Date value) {

        return millisecondsToString(
            ofNullable(value).
            map(Date::getTime)
            .orElse(null)
        ).orElse(null);
    }


}

