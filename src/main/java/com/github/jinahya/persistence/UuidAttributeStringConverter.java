package com.github.jinahya.persistence;

import static java.util.Optional.ofNullable;
import java.util.UUID;
import javax.persistence.AttributeConverter;

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
