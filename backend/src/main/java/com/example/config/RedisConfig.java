package com.example.config;

import java.time.Duration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching // Enables Spring's annotation-driven caching (@Cacheable, @CachePut, @CacheEvict)
public class RedisConfig {

    /**
     * Configures a RedisTemplate bean to interact with Redis directly.
     * 
     * @param connectionFactory the Lettuce connection factory used to connect to Redis
     * @return RedisTemplate<String, Object> configured with key and value serializers
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Use String for Redis keys
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        // Serialize Java objects as JSON for Redis values
        GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();

        // Set serializers for keys and values
        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);

        template.afterPropertiesSet(); // Finalize the template configuration
        return template;
    }

    /**
     * Configures a RedisCacheManager for Spring's annotation-based caching.
     * 
     * @param connectionFactory the Lettuce connection factory used to connect to Redis
     * @return RedisCacheManager with default TTL and serializers configured
     */
    @Bean
    public RedisCacheManager cacheManager(LettuceConnectionFactory connectionFactory) {
        // Use String for cache keys
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        // Serialize cache values as JSON
        GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();

        // Configure cache settings
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(5)) // Cache entries expire after 5 minutes
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer));

        // Build and return the cache manager with the default configuration
        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(config)
            .build();
    }
}
