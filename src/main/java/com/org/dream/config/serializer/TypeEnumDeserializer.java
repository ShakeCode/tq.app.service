package com.org.dream.config.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.org.dream.enums.BaseEnum;
import com.org.dream.util.EnumUtil;

import java.io.IOException;

public class TypeEnumDeserializer extends StdDeserializer<BaseEnum> implements ContextualDeserializer {
    public TypeEnumDeserializer() {
        super((JavaType) null);
    }
    public TypeEnumDeserializer(JavaType valueType) {
        super(valueType);
    }
    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        return new TypeEnumDeserializer(property.getType());
    }
    @Override
    @SuppressWarnings("all")
    public BaseEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return (BaseEnum) EnumUtil.match((Class) _valueClass, p.getIntValue());
    }
}