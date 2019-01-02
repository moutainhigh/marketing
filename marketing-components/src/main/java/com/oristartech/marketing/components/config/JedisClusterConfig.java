package com.oristartech.marketing.components.config;
//package com.tthappy.m.core.config;
//
//import com.tthappy.m.core.config.properties.RedisProperties;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * @author wangweiheng
// * @date 2018-08-17 18:15:11
// */
//@Configuration
//@ConditionalOnProperty(prefix = RedisProperties.REDIS_PREFIX, name = "open", havingValue = "true", matchIfMissing = true)
//public class JedisClusterConfig {
//	@Bean
//	public JedisCluster getJedisCluster(RedisProperties prop) {
//		System.out.println("open====="+prop.getOpen());
//		String[] serverArray = prop.getClusterNodes().split(",");
//		Set<HostAndPort> nodes = new HashSet<>();
//		for (String ipPort : serverArray) {
//			String[] ipPortPair = ipPort.split(":");
//			System.out.println(ipPortPair[0]+"-------"+ipPortPair[1]);
//			nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
//		}
//		// 最大空闲连接数, 默认8个
//		System.out.println(nodes.toString()+"---");
//		String pwd=prop.getPassword();
//		JedisCluster jedisCluster =null;
//		if(StringUtils.isBlank(pwd)){
//			jedisCluster = new JedisCluster(nodes, prop.getCommandTimeout(), prop.getSoTimeout(), prop.getMaxAttempts(),  getJedisPoolConfig(prop));
//		}else{
//			jedisCluster = new JedisCluster(nodes, prop.getCommandTimeout(), prop.getSoTimeout(), prop.getMaxAttempts(), prop.getPassword(), getJedisPoolConfig(prop));
//		}
//		return jedisCluster;
//	}
//
//	@Bean
//	public JedisPoolConfig getJedisPoolConfig(RedisProperties prop){
//
//		JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxWaitMillis(prop.getMaxWaitMillis());
//        config.setMinEvictableIdleTimeMillis(prop.getMinEvictableIdleTimeMillis());
//        config.setMinIdle(0);
//		config.setMaxIdle(100);
//		// 最大连接数, 默认8个
//		config.setMaxTotal(500);
//		//最小空闲连接数, 默认0
//		// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
//		//对拿到的connection进行validateObject校验
//		config.setTestOnBorrow(true);
//		System.out.println("获取完毕啦" );
//        return config;
//	}
//}
