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

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A {@code Filter} implementation which handles {@code HttpServletRequest} and {@code HttpServletResponse}.
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public abstract class HttpFilter extends AbstractFilter {

    /**
     * {@inheritDoc} The {@code doFilter(ServletRequest, ServletResponse, FilterChain)} method of {@code HttpFilter}, if
     * {@code request} is an instance of {@link HttpServletRequest} and {@code response} is an instance of {@link
     * HttpServletResponse}, invokes {@link #doFilter(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)} or invokes {@link
     * FilterChain#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse)} on given {@code filter} with
     * specified {@code request} and {@code response}.
     *
     * @param request  {@inheritDoc}
     * @param response {@inheritDoc}
     * @param chain    {@inheritDoc}
     * @throws IOException      {@inheritDoc }
     * @throws ServletException {@inheritDoc }
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
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     * @param chain    the filter chain
     * @throws IOException      if an I/O error occurs.
     * @throws ServletException if a servlet related error occurs.
     */
    protected abstract void doFilter(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain chain)
            throws IOException, ServletException;
}
