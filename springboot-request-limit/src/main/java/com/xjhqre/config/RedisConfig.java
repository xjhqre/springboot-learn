package com.xjhqre.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>
 * redis配置
 * <p>
 *
 * @author xjhqre
 * @since 9月 12, 2023
 */
@Configuration
public class RedisConfig {

    /**
     * redis template.
     *
     * @param factory
     *            factory
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public DefaultRedisScript<Long> limitScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(this.limitScriptText());
        redisScript.setResultType(Long.class);
        return redisScript;
    }

    /**
     * 限流脚本
     * 
     */
    private String limitScriptText() {
        /*
        local key = KEYS[1]
        local count = tonumber(ARGV[1])  // 限定请求次数
        local time = tonumber(ARGV[2])   // 限定时间
        local current = redis.call('get', key);  // 当前请求次数
        if current and tonumber(current) > count then // 请求次数超出限制
            return tonumber(current);
        end
        current = redis.call('incr', key)  // 当前请求次数+1
        if tonumber(current) == 1 then // 如果当前请求次数为1，表示是第一次请求
            redis.call('expire', key, time) // 使用Redis的EXPIRE命令设置键key的过期时间为time秒
        end
        return tonumber(current);
         */
        return "local key = KEYS[1]\n" + "local count = tonumber(ARGV[1])\n" + "local time = tonumber(ARGV[2])\n"
            + "local current = redis.call('get', key);\n" + "if current and tonumber(current) > count then\n"
            + "    return tonumber(current);\n" + "end\n" + "current = redis.call('incr', key)\n"
            + "if tonumber(current) == 1 then\n" + "    redis.call('expire', key, time)\n" + "end\n"
            + "return tonumber(current);";
    }
}
