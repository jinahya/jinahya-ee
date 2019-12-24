package com.github.jinahya.persistence;

import javax.persistence.AttributeConverter;
import java.util.UUID;

import static java.util.Optional.ofNullable;

public class UuidAttributeStringConverter
        implements AttributeConverter<UUID, String> {

    @Override
    public String convertToDatabaseColumn(final UUID attribute) {
        return ofNullable(attribute).map(Object::toString).orElse(null);
    }

    @Override
    public UUID convertToEntityAttribute(final String dbData) {
        return ofNullable(dbData).map(UUID::fromString).orElse(null);
    }
}
