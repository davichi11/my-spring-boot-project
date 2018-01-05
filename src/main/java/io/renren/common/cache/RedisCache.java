package io.renren.common.cache;

import io.renren.common.utils.ApplicationContextHolder;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.common.cache
 * @Description TODO(描述)
 * @create 2017/7/28-07:45
 */
@SuppressWarnings("unchecked")
public class RedisCache implements Cache {

//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final String id;
    private RedisTemplate<String, Object> redisTemplate;

    private final static long EXPIRE_TIME_IN_MINUTES = 30;
    private final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    public RedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an id");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * 向redis缓存中放数据
     *
     * @param key
     * @param value
     */
    @Override
    public void putObject(Object key, Object value) {
        String strKey = "";
        if (!(key instanceof String)) {
            strKey = Objects.toString(key);
        }
        ValueOperations operations = getRedisTemplate().opsForValue();
        operations.set(strKey, value, EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES);
    }

    /**
     * 从redis缓存中获取数据
     *
     * @param key
     * @return
     */
    @Override
    public Object getObject(Object key) {
        String strKey = "";
        if (!(key instanceof String)) {
            strKey = Objects.toString(key);
        }
        return getRedisTemplate().opsForValue().get(strKey);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object removeObject(Object key) {
        try {
            getRedisTemplate().delete(key);
            return true;
        } catch (Exception e) {
            logger.error("删除异常", e);
        }
        return false;
    }

    @Override
    public void clear() {
        getRedisTemplate().execute((RedisCallback) redisConnection -> {
            redisConnection.flushDb();
            return null;
        });
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    private RedisTemplate getRedisTemplate() {
        if (redisTemplate == null) {
            redisTemplate = (RedisTemplate) ApplicationContextHolder.getBean("redisTemplate");
        }
        return redisTemplate;
    }

}

