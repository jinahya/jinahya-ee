package com.github.jinahya.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;

import java.awt.color.ICC_Profile;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * .
 *
 * @see <a href="https://mrserge.lv/2023/how-to-install-icc-profiles-on-pc-and-mac-and-where-to-find-them/">How to
 * install ICC profiles on PC and MAC and where to find them</a>
 */
@Slf4j
public final class JavaAwtTestUtils {

    private static List<ICC_Profile> getIccProfilesMac() throws IOException {
        final List<Path> files = new ArrayList<>();
        final var paths = new Path[] {
                Paths.get("/Library/ColorSync/Profiles"),
                Optional.ofNullable(System.getProperty("user.home"))
                        .map(v -> Paths.get(v).resolve(Paths.get("Library", "ColorSync", "Profiles")))
                        .orElse(null)
        };
        for (final var path : paths) {
            if (path == null || !Files.isDirectory(path)) {
                continue;
            }
            Files.walkFileTree(path, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs)
                        throws IOException {
                    if (Files.isRegularFile(file) && file.toFile().getName().endsWith(".icc")) {
                        files.add(file);
                    }
                    return super.visitFile(file, attrs);
                }
            });
        }
        return files.stream()
                .map(f -> {
                    try (var stream = new FileInputStream(f.toFile())) {
                        return ICC_Profile.getInstance(stream);
                    } catch (final IOException ioe) {
                        throw new UncheckedIOException(ioe);
                    }
                })
                .toList();
    }

    public static List<ICC_Profile> getIccProfiles() throws IOException {
        final var osName = SystemUtils.OS_NAME;
        if (osName != null && osName.toLowerCase().contains("mac")) {
            return getIccProfilesMac();
        }
        return Collections.emptyList();
    }

    private JavaAwtTestUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
