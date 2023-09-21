package com.github.jinahya.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Slf4j
final class JavaAwtTestUtilsTest {

    @Test
    void getIccProfiles() throws IOException {
        JavaAwtTestUtils.getIccProfiles().forEach(p -> {
            log.debug("profile: {}", p);
            log.debug("\tcolorSpaceType: {}", p.getColorSpaceType());
            log.debug("\tPCSType: {}", p.getPCSType());
            log.debug("\tprofileClass: {}", p.getProfileClass());
            log.debug("\tmajorVersion: {}", p.getMajorVersion());
            log.debug("\tminorVersion: {}", p.getMinorVersion());
            log.debug("\tnumComponents: {}", p.getNumComponents());
        });
    }
}
