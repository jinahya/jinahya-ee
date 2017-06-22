/*
 * Copyright 2011 Jin Kwon &lt;jinahya_at_gmail.com&gt;.
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
package com.github.jinahya.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * Buffered implementation of ServletOutputStream.
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class BufferedServletOutputStream extends ServletOutputStream {

    /**
     * Creates a new instance.
     */
    public BufferedServletOutputStream() {
        super();
        outputStream = new ByteArrayOutputStream();
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(final WriteListener writeListener) {
        if (writeListener == null) {
            throw new NullPointerException("null writeListener");
        }
        if (this.writeListener != null) {
            throw new IllegalStateException("writeListener alread set");
        }
        this.writeListener = writeListener;
        try {
            this.writeListener.onWritePossible();
        } catch (final IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @Override
    public void write(final int b) throws IOException {
        outputStream.write(b);
    }

    /**
     * Resets the underlying {@code ByteArrayOutputStream}.
     */
    public final void reset() {
        outputStream.reset();
    }

    /**
     * Returns buffered bytes.
     *
     * @return buffered bytes
     */
    public final byte[] bytes() {
        return outputStream.toByteArray();
    }

    private final ByteArrayOutputStream outputStream;

    private WriteListener writeListener;
}
