package com.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import redis.clients.jedis.ScanParams;
import redis.clients.jedis.set.SetParams;

/**
 * @Author: dengcs
 * @Date: 2019/4/25 18:18
 * 注释 Redis实现布式锁 可靠，并发量大，可能造成死锁
 */

@Service
public class RedisLock implements Lock {
    @Resource
    private JedisPool jedisPoll;
    private Jedis jedis = jedisPoll.getResource();
    @Override
    public void lock() {
        return;
    }

    //lockKey 键值，uniqueValue 唯一值，seconds 失效时间
    public boolean lock(String lockKey, String uniqueValue, int seconds){
        //jedis = jedisPoll.getResource();
        SetParams params = new SetParams();
        params.nx().ex(seconds);
        String result = jedis.set(lockKey, uniqueValue, params);
        if ("OK".equals(result)) {
            return true;
        }
        return false;
    }

    public boolean unlock(String lockKey, String uniqueValue){
        //使用lua语言，保障原子操作，通过uniqueValue 删除自己设置的key，避免删除别人的
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] " +
                "then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script,
                Collections.singletonList(lockKey),
                Collections.singletonList(uniqueValue));
        if (result.equals(1)) {
            return true;
        }
        return false;
    }
    @Override
    public void lockInterruptibly() throws InterruptedException {
        return;
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        return;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
