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
 * A status type for {@code 412 Precondition Failed} which is a member of {@code Client Error 4xx}.
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 * @see <a href="http://tools.ietf.org/html/rfc2616#section-10.4.13">10.4.13 412
 * Precondition Failed (RFC 2616 Hypertext Transfer Protocol -- HTTP/1.1)</a>
 */
public class PreconditionFailed412 extends AbstractStatusType {

    /**
     * Creates a new instance with given reason phrase.
     *
     * @param reasonPhrase the reason phrase
     */
    public PreconditionFailed412(final String reasonPhrase) {

        super(Status.PRECONDITION_FAILED, reasonPhrase);
    }
}
