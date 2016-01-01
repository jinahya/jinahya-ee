/*
 * Copyright 2013 Jin Kwon &lt;jinahya_at_gmail.com&gt;.
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


package com.github.jinahya.servlet.http;


import com.github.jinahya.servlet.AbstractFilter;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public abstract class HttpFilter extends AbstractFilter {


    /**
     * {@inheritDoc}
     * <p/>
     * Overridden to invoke
     * {@link #doFilter(HttpServletRequest, HttpServletResponse, FilterChain)}
     * when {@code request} is an instance of {@link HttpServletRequest} and
     * {@code response} is an instance of {@link HttpServletResponse}.
     *
     * @param request {@inheritDoc}
     * @param response {@inheritDoc}
     * @param chain {@inheritDoc}
     *
     * @throws IOException {@inheritDoc }
     * @throws ServletException {@inheritDoc }
     *
     * @see #doFilter(HttpServletRequest, HttpServletResponse, FilterChain)
     */
    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain)
        throws IOException, ServletException {

        if (request instanceof HttpServletRequest
            && response instanceof HttpServletResponse) {

            doFilter((HttpServletRequest) request,
                     (HttpServletResponse) response, chain);
            return;
        }

        chain.doFilter(request, response);
    }


    /**
     * Does filtering on given arguments.
     *
     * @param request the HTTP servlet request
     * @param response the HTTP servlet response
     * @param chain the filter chain
     *
     * @throws IOException if an I/O error occurs.
     * @throws ServletException if a servlet related error occurs.
     */
    protected abstract void doFilter(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain chain)
        throws IOException, ServletException;


}

