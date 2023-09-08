package com.xjhqre.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.xjhqre.config.JacksonConfiguration;

/**
 * <p>
 * JacksonUtils
 * <p>
 *
 * @author xjhqre
 * @since 9月 08, 2023
 */
public class JacksonUtils {

    private static final ObjectMapper objectMapper;
    private static final ObjectMapper objectMapper_notNull;
    private static final ObjectMapper objectMapper_wrapRoot;

    static {
        objectMapper = JacksonConfiguration.createJackson2ObjectMapperBuilder()
            // FAIL_ON_UNKNOWN_PROPERTIES：反序列化过程中遇到未知属性时不会抛出异常。
            // 参考：特殊数据格式处理-JSON框架Jackson精解第2篇
            .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            // JacksonAnnotationIntrospector：用于处理注解相关的操作
            /*
            具体来说，JacksonAnnotationIntrospector可以实现以下功能：
            
            序列化和反序列化的属性过滤：通过在Java类的属性上添加@JsonIgnore注解，可以指示Jackson在序列化和反序列化时忽略该属性。
            
            定制属性名称：通过在Java类的属性上添加@JsonProperty注解，可以指定属性在序列化和反序列化时使用的名称。
            
            定制属性顺序：通过在Java类上添加@JsonPropertyOrder注解，可以指定属性在序列化时的输出顺序。
            
            定制类型序列化和反序列化：通过在Java类或属性上添加@JsonSerialize和@JsonDeserialize注解，可以指定自定义的序列化器和反序列化器。
            
            处理多态类型：通过在父类或接口上添加@JsonTypeInfo注解，可以指定多态类型的处理方式，如使用属性或类型标识进行识别。
            
            处理日期和时间格式：通过在Java类的属性上添加@JsonFormat注解，可以指定日期和时间的格式化方式。
            
            处理枚举类型：通过在枚举类上添加@JsonEnumDefaultValue注解，可以指定默认的枚举值。
             */
            .annotationIntrospector(new JacksonAnnotationIntrospector()).build();

        // 禁用了通过可注入的值来满足对象构造或属性设置的需求
        // 参考：@JacksonInject与@JsonAlias注解-JSON框架Jackson精解第4篇
        objectMapper.setInjectableValues(new InjectableValues() {
            public Object findInjectableValue(Object valueId, DeserializationContext ctxt, BeanProperty forProperty,
                Object beanInstance) {
                return null;
            }
        });
        objectMapper_wrapRoot = JacksonConfiguration.createJackson2ObjectMapperBuilder()
            .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .annotationIntrospector(new JacksonAnnotationIntrospector()).build();
        objectMapper_wrapRoot.enable(SerializationFeature.WRAP_ROOT_VALUE);
        objectMapper_wrapRoot.setInjectableValues(new InjectableValues() {
            public Object findInjectableValue(Object valueId, DeserializationContext ctxt, BeanProperty forProperty,
                Object beanInstance) {
                return null;
            }
        });
        objectMapper_notNull = JacksonConfiguration.createJackson2ObjectMapperBuilder()
            .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .serializationInclusion(Include.NON_NULL).annotationIntrospector(new JacksonAnnotationIntrospector())
            .build();
        objectMapper_notNull.setInjectableValues(objectMapper.getInjectableValues());
    }

    private JacksonUtils() {}

    public static JsonNode readTree(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        } else {
            try {
                return objectMapper.readTree(json);
            } catch (Exception var1) {
                return objectMapper.valueToTree(json);
            }
        }
    }

    public static String obj2Json(Object obj) {
        if (obj == null) {
            return null;
        } else {
            try {
                return objectMapper.writeValueAsString(obj);
            } catch (JsonProcessingException var2) {
                throw new RuntimeException(var2);
            }
        }
    }

    public static String obj2JsonWrapRoot(Object obj) {
        if (obj == null) {
            return null;
        } else {
            try {
                return objectMapper_wrapRoot.writeValueAsString(obj);
            } catch (JsonProcessingException var2) {
                throw new RuntimeException(var2);
            }
        }
    }

    public static String obj2JsonIgnoreNull(Object obj) {
        if (obj == null) {
            return null;
        } else {
            try {
                return objectMapper_notNull.writeValueAsString(obj);
            } catch (JsonProcessingException var2) {
                throw new RuntimeException(var2);
            }
        }
    }

    public static <T> T json2Pojo(String jsonString, Class<T> clazz) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        } else {
            try {
                return objectMapper.readValue(jsonString, clazz);
            } catch (IOException var3) {
                throw new RuntimeException(var3);
            }
        }
    }

    public static <T> T json2Pojo(String jsonString, TypeReference<T> typeReference) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        } else {
            try {
                return objectMapper.readValue(jsonString, typeReference);
            } catch (IOException var3) {
                throw new RuntimeException(var3);
            }
        }
    }

    public static Map<String, Object> json2Map(String jsonString) {
        return json2Map(jsonString, Object.class);
    }

    public static <T> Map<String, T> json2Map(String jsonString, Class<T> valueClass) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        } else {
            try {
                JavaType javaType =
                    objectMapper.getTypeFactory().constructMapType(LinkedHashMap.class, String.class, valueClass);
                return objectMapper.readValue(jsonString, javaType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static List<Map<String, Object>> json2MapList(String jsonArrayStr) {
        return json2MapList(jsonArrayStr, Object.class);
    }

    public static <T> List<Map<String, T>> json2MapList(String jsonArrayStr, Class<T> valueClass) {
        JavaType mapType =
            objectMapper.getTypeFactory().constructMapType(LinkedHashMap.class, String.class, valueClass);
        return json2List(jsonArrayStr, mapType);
    }

    public static <T> List<T> json2List(String jsonArrayStr, Class<T> clazz) {
        if (StringUtils.isBlank(jsonArrayStr)) {
            return null;
        } else {
            JavaType entityType = objectMapper.getTypeFactory().constructSimpleType(clazz, null);
            return json2List(jsonArrayStr, entityType);
        }
    }

    private static <T> List<T> json2List(String jsonArrayStr, JavaType entityType) {
        if (StringUtils.isBlank(jsonArrayStr)) {
            return null;
        } else {
            try {
                JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, entityType);
                return objectMapper.readValue(jsonArrayStr, javaType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
