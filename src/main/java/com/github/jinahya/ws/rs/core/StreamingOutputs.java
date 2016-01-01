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


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.ws.rs.core.StreamingOutput;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public final class StreamingOutputs {


    public static StreamingOutput of(final Path path) {

        return o -> Files.copy(path, o);
    }


    public static StreamingOutput of(final File file) {

        return StreamingOutputs.of(file.toPath());
    }


    private StreamingOutputs() {

        super();
    }


}

