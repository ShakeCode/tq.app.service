package com.org.dream.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.org.dream.constant.RedisConstant;
import com.org.dream.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The type Redis cache config.
 */
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheConfig.class);

    private static final Pattern HMS_PATTERN = Pattern.compile("^\\d+(?i)[hms]$", Pattern.CASE_INSENSITIVE);

    private static final Pattern DAY_PATTERN = Pattern.compile("^\\d+(?i)[d]$", Pattern.CASE_INSENSITIVE);

    @Value("${cache.ttl.grid:30m}")
    private String gridCacheTtl;

    @Value("${cache.ttl.uptown:20d}")
    private String uptownCacheTtl;

    /**
     * Redis template redis template.
     * @param factory the factory
     * @return the redis template
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // key采用String的序列化方式
        template.setKeySerializer(getStringRedisSerializer());
        // value序列化方式采用jackson
        template.setValueSerializer(getJackson2JsonRedisSerializer());
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(getStringRedisSerializer());
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(getJackson2JsonRedisSerializer());
        return template;
    }

    private Jackson2JsonRedisSerializer<Object> getJackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);
        // 日期序列化处理
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.setTimeZone(TimeZone.getTimeZone("UTC"));
        om.registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .registerModule(new ParameterNamesModule());
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

    /**
     * Redis cache manager cache manager.
     * @param factory the factory
     * @return the cache manager
     */
    @Primary
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 配置序列化（解决乱码的问题）,全局默认过期时间
                .entryTtl(Duration.ofMinutes(30))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(getStringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(getJackson2JsonRedisSerializer()))
                .disableCachingNullValues()
                // 缓存目录前缀-应用名称
                .prefixCacheNameWith(RedisConstant.APP_NAME_PREFIX_KEY);
        Map<String, RedisCacheConfiguration> cacheManagerMap = new HashMap<>(2);
        cacheManagerMap.put(RedisConstant.GRID_CACHE_KEY, config.entryTtl(getDuration(gridCacheTtl)));
        // 最終存入格式: tq-app:uptown
        cacheManagerMap.put(RedisConstant.UPTOWN_CACHE_KEY, config.entryTtl(getDuration(uptownCacheTtl)));
        return RedisCacheManager.builder(factory)
                .cacheWriter(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
                // 缓存名称初始化
                .initialCacheNames(cacheManagerMap.keySet())
                // 缓存配置map
                .withInitialCacheConfigurations(cacheManagerMap)
                .cacheDefaults(config)
                .build();
    }

    private RedisSerializer<String> getStringRedisSerializer() {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        return redisSerializer;
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                LOGGER.error("key:{} 缓存获取异常：{}", key, e.getMessage(), e);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                LOGGER.error("key:{}缓存添加异常：{}", key, e.getMessage(), e);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
                LOGGER.error("key:{}缓存删除异常：{}", key, e.getMessage(), e);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                LOGGER.info("缓存清理异常", e);
            }
        };
        return cacheErrorHandler;
    }

    /**
     * Wisely key generator key generator.
     * @return the key generator
     */
    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append("." + method.getName());
            if (params == null || params.length == 0 || params[0] == null) {
                return null;
            }
            String join = String.join("&", Arrays.stream(params).map(Object::toString).collect(Collectors.toList()));
            String format = String.format("%s{%s}", sb.toString(), join);
            LOGGER.info("缓存key：" + format);
            return format;
        };
    }

    public static void main(String[] args) {
        String config = "5d";
        System.out.println("5H".matches(HMS_PATTERN.pattern()));
        System.out.println("5h".matches(HMS_PATTERN.pattern()));
        System.out.println("5m".matches(HMS_PATTERN.pattern()));
        System.out.println("5M".matches(HMS_PATTERN.pattern()));
        System.out.println("5s".matches(HMS_PATTERN.pattern()));
        System.out.println("5S".matches(HMS_PATTERN.pattern()));
        System.out.println("===================================");
        System.out.println("5d".matches(DAY_PATTERN.pattern()));
        System.out.println("5D".matches(DAY_PATTERN.pattern()));

        System.out.println("===================================");
        System.out.println(getDuration("5S").getSeconds());
        System.out.println(getDuration("5m").getSeconds());
        System.out.println(getDuration("5M").getSeconds());
        System.out.println(getDuration("1d").getSeconds());
    }

    public static Duration getDuration(String config) {
        LOGGER.info("parse duration: {}", config);
        if (config.matches(HMS_PATTERN.pattern())) {
            return Duration.parse("PT".concat(config));
        } else if (config.matches(DAY_PATTERN.pattern())) {
            return Duration.parse("P".concat(config));
        } else {
            throw new ServiceException("un-support ttl config");
        }
    }

}
