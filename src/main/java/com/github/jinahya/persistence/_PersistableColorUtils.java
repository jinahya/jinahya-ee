package com.github.jinahya.persistence;

import jakarta.persistence.MappedSuperclass;

import java.awt.color.ColorSpace;
import java.util.Objects;

/**
 * .
 *
 * @see <a href="https://www.ibm.com/docs/en/i/7.3?topic=concepts-color-spaces-icc-profiles">Color spaces and ICC
 * profiles</a> (ibm.com / IBM i / 7.3)
 * @see <a href="https://www.ibm.com/docs/en/i/7.5?topic=concepts-color-spaces-icc-profiles">Color spaces and ICC
 * profiles</a> (ibm.com / IBM i / 7.5)
 */
@MappedSuperclass
@SuppressWarnings({
        "java:S101" // _Persistable...
})
public final class _PersistableColorUtils {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns an array of {@code CIEXYZ} color components converted from specified persistable color object.
     *
     * @param persistableColor the persistable color object whose {@code RGBA} color components are converted.
     * @param rgbColorSpace    an auxiliary {@code RGB} color space; may be {@code null}.
     * @return an array of converted color components.
     */
    public static float[] toCIEXYZ(final _PersistableColor persistableColor, final ColorSpace rgbColorSpace) {
        Objects.requireNonNull(rgbColorSpace, "rgbColorSpace is null");
        return rgbColorSpace.toCIEXYZ(persistableColor.getComponents());
    }

    /**
     * Returns an array of {@code CMYK} color components converted from specified persistable color object.
     *
     * @param persistableColor the persistable color object whose {@code RGBA} color components are converted.
     * @param rgbColorSpace    an auxiliary {@code RGB} color space; may be {@code null}.
     * @param cmykColorSpace   a target {@code CMYK} color space.
     * @return an array of converted color components.
     */
    public static float[] toCMYK(final _PersistableColor persistableColor, final ColorSpace rgbColorSpace,
                                 final ColorSpace cmykColorSpace) {
        Objects.requireNonNull(cmykColorSpace, "cmykColorSpace is null");
        if (rgbColorSpace == null) {
            return cmykColorSpace.fromRGB(persistableColor.getComponents());
        }
        return cmykColorSpace.fromCIEXYZ(toCIEXYZ(persistableColor, rgbColorSpace));
    }

    // -----------------------------------------------------------------------------------------------------------------

    private _PersistableColorUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
