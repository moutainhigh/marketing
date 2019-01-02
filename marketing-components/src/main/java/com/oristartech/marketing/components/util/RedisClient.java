package com.oristartech.marketing.components.util;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.*;

public class RedisClient {
    @Autowired
    private JedisPool jedisPool;

    public void set(String key, Object value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param value
     * @param exptime seconds
     * @throws Exception
     */
    public void setWithExpireTime(String key, String value, int exptime) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            jedis.expire(key, exptime);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }


    public String get(String key) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return null;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 批量查询 jedis.keys
     *
     * @param pattern
     * @return
     */
    public List<Object> getList(String pattern) {
        List<Object> list = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Set<String> keys = jedis.keys(pattern);
            Pipeline pip = jedis.pipelined();
            for (String s : keys) {
                pip.get(s);
            }
            List<Object> searchList = pip.syncAndReturnAll();
            if (searchList != null && searchList.size() > 0) {
                list = searchList;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            jedis.close();
        }
        return list;
    }

    /**
     * 批量插入or更新
     *
     * @param map
     */
    public void setList(Map<String, String> map) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pip = jedis.pipelined();
            for (String s : map.keySet()) {
                pip.set(s, map.get(s));
            }
            pip.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    public Long del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            jedis.close();
        }
    }


    /**
     * 向Redis中Set集合添加值:点赞
     *
     * @return
     */
    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            ////logger.error("Jedis sadd 异常 ：" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 移除：取消点赞
     *
     * @param key
     * @param value
     * @return
     */
    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            ////logger.error("Jedis srem 异常：" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 判断key,value是否是集合中值
     *
     * @param key
     * @param value
     * @return
     */
    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            ////logger.error("Jedis sismember 异常：" + e.getMessage());
            return false;
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    ////logger.error("Jedis关闭异常" + e.getMessage());
                }
            }
        }
    }

    /**
     * 获取集合大小
     *
     * @param key
     * @return
     */
    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            ////logger.error("Jedis scard 异常：" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    public Set<String> smembers(String key) {

        Jedis jedis = null;
        Set<String> smembers = new HashSet<String>();
        try {
            jedis = jedisPool.getResource();
            smembers = jedis.smembers(key);
            return smembers;
        } catch (Exception e) {
            ////logger.error("Jedis scard 异常：" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            return smembers;
        }
    }



    public String  setnx(String key, String value,Long expireTime) {
        Jedis jedis = null;
        String result="";
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(key, value, "NX", "EX", expireTime);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return result;
    }

    public String getset(String key, String value) {
        Jedis jedis = null;
        String result = "";
        try {
            jedis = jedisPool.getResource();
            result =  jedis.getSet(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return result;
    }
    public String delKeys(String headKey) {
        Jedis jedis = null;
        String result = "";
        try {
            jedis = jedisPool.getResource();
            Set<String> keySet = jedis.keys(headKey+"*");
            if (keySet != null){
                String[] keys = new String[keySet.size()];
                keys = keySet.toArray(keys);
                jedis.del(keySet.toArray(keys));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return result;
    }
}