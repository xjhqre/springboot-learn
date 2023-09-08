package com.xjhqre.config;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * <p>
 * JacksonConfiguration
 * <p>
 *
 * @author xjhqre
 * @since 9月 08, 2023
 */
@Configuration
public class JacksonConfiguration {
    public static String pattern = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter optionalDateTimePattern =
        (new DateTimeFormatterBuilder()).appendValue(ChronoField.YEAR, 4)
            .appendPattern("[-][/]MM[-][/]dd['T'][ ]HH[:]mm[:][ss][,SSS][.SSS]").toFormatter();;
    static final ZoneOffset zoneOffset = OffsetDateTime.now(ZoneId.systemDefault()).getOffset();

    public JacksonConfiguration() {}

    public static Jackson2ObjectMapperBuilder createJackson2ObjectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        return customizeBuilder(builder).serializerByType(LocalDateTime.class,
            new LocalDateTimeSerializer(String.class));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return (builder) -> {
            customizeBuilder(builder).serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(String.class));
        };
    }

    private static Jackson2ObjectMapperBuilder customizeBuilder(Jackson2ObjectMapperBuilder builder) {
        builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer())
            // 将日期时间序列化为时间戳（以毫秒表示），而不是默认的日期时间字符串格式，如果已经设置了serializerByType则会失效
            .featuresToEnable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            // 允许将单个值解析为数组。这意味着如果 JSON 中的某个属性期望是数组类型，但实际上只有一个值，那么它也会被解析为数组。
            .featuresToEnable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            // 解开包装在单个值数组中的值。这意味着如果 JSON 中的某个属性被包装在一个只有一个元素的数组中，那么它会被解包成单个值。
            .featuresToEnable(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS);
        return builder;
    }

    public static final class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        public LocalDateTimeDeserializer() {}

        public LocalDateTime deserialize(JsonParser p, DeserializationContext context) throws IOException {
            // 时间戳格式
            if (StringUtils.isNumeric(p.getValueAsString())) {
                long timestamp = Long.parseLong(p.getValueAsString());

                // 带毫秒
                if (p.getValueAsString().length() == 13) {
                    return Instant.ofEpochMilli(timestamp).atZone(JacksonConfiguration.zoneOffset).toLocalDateTime();
                }
                // 不带毫秒
                else {
                    return Instant.ofEpochSecond(timestamp).atZone(JacksonConfiguration.zoneOffset).toLocalDateTime();
                }
            } else {
                // 2023-09-08 16:12:25 或 2023-09-08T16:12:25 或 2023-09-08 16:12:25.456 格式
                String dateTimeString = p.getValueAsString();
                if (StringUtils.isNotEmpty(dateTimeString)) {
                    return LocalDateTime.parse(dateTimeString, optionalDateTimePattern);
                } else {
                    return null;
                }
            }
        }
    }

    public static final class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        private Class<?> targetClass;

        public LocalDateTimeSerializer() {}

        public LocalDateTimeSerializer(Class<?> targetClass) {
            this.targetClass = targetClass;
        }

        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
            if (this.targetClass == String.class) {
                gen.writeString(DateTimeFormatter.ofPattern(JacksonConfiguration.pattern).format(value));
            } else {
                gen.writeNumber(value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }

        }
    }
}
