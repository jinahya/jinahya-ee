/*
 * Copyright 2012 Jin Kwon &lt;jinahya_at_gmail.com&gt;.
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

import jakarta.ws.rs.core.Response.Status;

/**
 * A status type for {@code 500 Internal Server Error} which is a member of {@code Server Error 5xx}.
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 * @see <a href="http://tools.ietf.org/html/rfc2616#section-10.5.1">10.5.1 500
 * Internal Server Error (RFC 2616 Hypertext Transfer Protocol -- HTTP/1.1)</a>
 */
public class InternalServerError500 extends AbstractStatusType {

    /**
     * Creates a new instance with given reason phrase.
     *
     * @param reasonPhrase the reason phrase
     */
    public InternalServerError500(final String reasonPhrase) {

        super(Status.INTERNAL_SERVER_ERROR, reasonPhrase);
    }
}
