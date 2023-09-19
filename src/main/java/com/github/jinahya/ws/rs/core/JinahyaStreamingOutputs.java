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
package com.github.jinahya.ws.rs.core;

import jakarta.ws.rs.core.StreamingOutput;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

/**
 * Utilities for {@link StreamingOutput}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public final class JinahyaStreamingOutputs {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns a streaming output copies specified path to the output.
     *
     * @param path    the path whose content are copied.
     * @param copied  a consumer to be accepted with the number of bytes copied; may be {@code null}.
     * @param delete  a flag for deleting the {@code path} when finished.
     * @param deleted a consumer to be accepted with the result of {@link Files#deleteIfExists(Path) deletion} of the
     *                {@code path}; may be {@code null}.
     * @return an instance of streaming output.
     */
    public static StreamingOutput ofCopying(final Path path, final LongConsumer copied, final boolean delete,
                                            final Consumer<Boolean> deleted) {
        if (path == null) {
            throw new NullPointerException("path is null");
        }
        return o -> {
            final long c = Files.copy(path, o);
            if (copied != null) {
                copied.accept(c);
            }
            if (delete) {
                final boolean d = Files.deleteIfExists(path);
                if (deleted != null) {
                    deleted.accept(d);
                }
            }
        };
    }

    /**
     * Returns a streaming output copies specified path to the output.
     *
     * @param path   the path whose content are copied.
     * @param delete a flag for deleting the {@code path} when finished.
     * @return an instance of streaming output.
     */
    public static StreamingOutput ofCopying(final Path path, final boolean delete) {
        return ofCopying(path, null, delete, null);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    private JinahyaStreamingOutputs() {
        super();
    }
}
