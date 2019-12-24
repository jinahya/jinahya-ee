/*
 * Copyright 2016 Jin Kwon &lt;jinahya_at_gmail.com&gt;.
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
package com.github.jinahya.ws.core;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

/**
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class StatusTypes {

    public static StatusType newInstance(final int statusCode,
                                         final String reasonPhrase) {

        return new AbstractStatusType(statusCode, reasonPhrase) {
        };
    }

    public static StatusType newInstance(final Status status,
                                         final String reasonPhrase) {

        if (status == null) {
            throw new NullPointerException("null status");
        }

        return newInstance(status.getStatusCode(), reasonPhrase);
    }

    protected StatusTypes() {

        super();
    }
}
