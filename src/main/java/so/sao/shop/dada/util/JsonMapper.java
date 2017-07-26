package so.sao.shop.dada.util;

import com.fasterxml.jackson.databind.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonMapper {

    private ObjectMapper mapper;

    public JsonMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String toJson(Object object) {
        try {
            ObjectWriter writer = mapper.writerFor(object.getClass());
            return writer.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            ObjectReader reader = mapper.readerFor(clazz);
            return reader.readValue(jsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(JsonNode node, Class<T> clazz) {
        if (node == null) {
            return null;
        }

        try {
            ObjectReader reader = mapper.readerFor(clazz);
            return reader.readValue(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            ObjectReader reader = mapper.readerFor(javaType);
            return reader.readValue(jsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> fromCollectionJson(String jsonString, Class<T> clazz) {
        return fromJson(jsonString, contructListType(clazz));
    }

    public JavaType contructListType(Class<?> elementClass) {
        return contructCollectionType(List.class, elementClass);
    }

    @SuppressWarnings("rawtypes")
    public JavaType contructCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    public JavaType contructMapType(Class<?> keyClass, Class<?> valueClass) {
        return contructMapType(HashMap.class, keyClass, valueClass);
    }

    @SuppressWarnings("rawtypes")
    public JavaType contructMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        return mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    }
}
