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


package com.github.jinahya.ws.core.response;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public abstract class AbstractStatusType implements StatusType {


    /**
     * Creates a new instance.
     *
     * @param family the status family
     * @param statusCode the HTTP status code
     * @param reasonPhrase the HTTP reason phrase
     */
    protected AbstractStatusType(final Family family, final int statusCode,
                                 final String reasonPhrase) {

        super();

        this.family = family;
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }


    /**
     * Creates a new instance with given {@code status} and
     * {@code reasonPhrase}.
     *
     * @param status the status to wrap
     * @param reasonPhrase the HTTP reason phrase
     */
    protected AbstractStatusType(final Status status,
                                 final String reasonPhrase) {

        this(status.getFamily(), status.getStatusCode(), reasonPhrase);
    }


    @Override
    public Family getFamily() {

        if (family != null) {
            return family;
        }

        //return Family.familyOf(statusCode); // Java EE 7

        switch (statusCode / 100) {
            case 1:
                return Family.INFORMATIONAL;
            case 2:
                return Family.SUCCESSFUL;
            case 3:
                return Family.REDIRECTION;
            case 4:
                return Family.CLIENT_ERROR;
            case 5:
                return Family.SERVER_ERROR;
            default:
                return Family.OTHER;
        }
    }


    @Override
    public String getReasonPhrase() {

        return reasonPhrase;
    }


    @Override
    public int getStatusCode() {

        return statusCode;
    }


    /**
     * Returns a new response builder for this status type.
     *
     * @return a new response builder to build
     *
     * @see Response#status(StatusType)
     */
    public Response.ResponseBuilder toResponseBuilder() {

        return Response.status(this);
    }


    /**
     * Returns a new response for this status type.
     *
     * @return a new response to respond.
     *
     * @see #toResponseBuilder()
     */
    public Response toResponse() {

        return toResponseBuilder().build();
    }


    /**
     * Returns a new web application exception for this status type.
     *
     * @return a new web application exception to throw
     *
     * @see #toResponse()
     */
    public WebApplicationException toWebApplicationException() {

        return new WebApplicationException(toResponse());
    }


    /**
     * family.
     */
    private final Family family;


    /**
     * status code.
     */
    private final int statusCode;


    /**
     * reason phrase.
     */
    private final String reasonPhrase;


}

