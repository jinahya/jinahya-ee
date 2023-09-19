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

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A {@link javax.servlet.Filter} implementation which dispatches suffixed path to a typed path.
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class SuffixToTypeDispatcher extends HttpFilter {

    /**
     * A regular expression for file suffix.
     */
    public static final String SUFFIX_EXPRESSION
            = "file\\.suffix\\.([^\\.]+)";

    /**
     * A precompiled pattern of {@link #SUFFIX_EXPRESSION}.
     */
    protected static final Pattern SUFFIX_PATTERN
            = Pattern.compile(SUFFIX_EXPRESSION);

    /**
     * A regular expression for media type.
     */
    public static final String TYPE_EXPRESSION = "media/type/(.+)";

    /**
     * A precompiled pattern of {@link #TYPE_EXPRESSION}.
     */
    protected static final Pattern TYPE_PATTERN
            = Pattern.compile(TYPE_EXPRESSION);

    private static final Pattern NAME_PATTERN
            = Pattern.compile("([^\\.]+)\\.([^\\.]+)");

    @Override
    public void init(final FilterConfig config) throws ServletException {

        super.init(config);

        contextPath = filterConfig().getServletContext().getContextPath();
        contextPathLength = contextPath.length();

        for (final Enumeration<String> e = config.getInitParameterNames();
             e.hasMoreElements(); ) {
            final String name = e.nextElement();
            final Matcher suffixMatcher = SUFFIX_PATTERN.matcher(name);
            if (!suffixMatcher.matches()) {
                continue;
            }
            final String value = config.getInitParameter(name);
            final Matcher typeMatcher = TYPE_PATTERN.matcher(value);
            if (!typeMatcher.matches()) {
                continue;
            }
            final String suffix = suffixMatcher.group(1);
            final String type = typeMatcher.group(1);
            if (map == null) {
                map = new HashMap<>();
            }
            map.put(suffix, type);
        }
    }

    @Override
    protected void doFilter(final HttpServletRequest request,
                            final HttpServletResponse response,
                            final FilterChain chain)
            throws IOException, ServletException {

        if (map == null) {
            chain.doFilter(request, response);
            return;
        }

        final String requestUri = request.getRequestURI();
        if (requestUri == null) {
            chain.doFilter(request, response);
            return;
        }

        final String resourcePath = requestUri.substring(contextPathLength);

        final int lastSlashIndex = resourcePath.lastIndexOf('/');
        if (lastSlashIndex == -1) {
            chain.doFilter(request, response);
            return;
        }

        final String fileName = resourcePath.substring(lastSlashIndex + 1);
        final Matcher fileNameMatcher = NAME_PATTERN.matcher(fileName);
        if (!fileNameMatcher.matches()) {
            chain.doFilter(request, response);
            return;
        }

        final String fileSuffix = fileNameMatcher.group(2);
        final String mediaType = map.get(fileSuffix);
        if (mediaType == null) {
            chain.doFilter(request, response);
            return;
        }

        final String path = resourcePath.substring(
                0, resourcePath.length() - fileSuffix.length() - 1);

        final ServletRequest wrapper
                = HeadersRequestWrapper.newPrecedingInstance(
                request, "Accept", mediaType);
        final RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(wrapper, response);

        return;
    }

    private transient String contextPath;

    private transient int contextPathLength;

    private Map<String, String> map = null;
}
