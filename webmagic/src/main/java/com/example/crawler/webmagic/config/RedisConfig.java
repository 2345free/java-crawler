package com.example.crawler.webmagic.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * spring 集成 redis配置,代码配置方式
 *
 * @author tianyi
 */
@Configuration
@Slf4j
public class RedisConfig {

    @Value("${redis.hosts}")
    private String servers;

    /**
     * redis 官方 cluster api
     *
     * @return
     */
    @Bean
    public JedisCluster jedisCluster() {
        Set<HostAndPort> nodes = new HashSet<>();
        for (String server : servers.split(",")) {
            String[] node = server.split(":");
            nodes.add(new HostAndPort(node[0], Integer.parseInt(node[1])));
        }
        JedisCluster jedisCluster = new JedisCluster(nodes);
        System.err.println(String.format("jedisCluster [%s] has been initialized...", servers));
        return jedisCluster;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxIdle(20);
        jedisPoolConfig.setMinIdle(10);
        jedisPoolConfig.setBlockWhenExhausted(true);
        jedisPoolConfig.setMaxWaitMillis(3000);
        jedisPoolConfig.setTestOnBorrow(false);
        jedisPoolConfig.setTestOnReturn(false);
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(60000);
        jedisPoolConfig.setNumTestsPerEvictionRun(-1);
        return jedisPoolConfig;
    }

    /**
     * require spring-data-redis 1.7+(require spring framework 4.2.8+)
     * spring 提供的redis cluster api
     *
     * @return
     */
    @Bean
    public RedisClusterConfiguration redisClusterConfiguration() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        Set<RedisNode> nodes = new HashSet<>();
        for (String server : servers.split(",")) {
            String[] node = server.split(":");
            nodes.add(new RedisNode(node[0], Integer.parseInt(node[1])));
        }
        redisClusterConfiguration.setClusterNodes(nodes);
        return redisClusterConfiguration;
    }

    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory(redisClusterConfiguration());
        //		String[] hostInfo=servers.split(",")[0].split(":");
        //		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        //		jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        //		jedisConnectionFactory.setHostName(hostInfo[0]);
        //		jedisConnectionFactory.setPort(Integer.parseInt(hostInfo[1]));
        //		jedisConnectionFactory.setShardInfo(new JedisShardInfo("tcp://"+servers.split(",")[0])); // 适用于redis 2.x版本的分片模式
        //		return jedisConnectionFactory;
        //		 Redis Sentinel HA Cluster
        //		RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
        //				.master("mymaster")
        //				.sentinel("127.0.0.1", 6380)
        //				.sentinel("127.0.0.1", 6381)
        //				.sentinel("127.0.0.1", 6382);
        //		return new JedisConnectionFactory(sentinelConfig);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory());
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setValueSerializer(new StringRedisSerializer());
        return stringRedisTemplate;
    }

}
