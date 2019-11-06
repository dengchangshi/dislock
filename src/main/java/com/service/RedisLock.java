package com.service;

import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import redis.clients.jedis.ScanParams;


import static org.springframework.data.redis.connection.jedis.JedisConverters.NX;
//import redis.clients.jedis.set.SetParams;

/**
 * @Author: dengcs
 * @Date: 2019/4/25 18:18
 * 注释 Redis实现布式锁 可靠，并发量大，可能造成死锁
 */

@Service
public class RedisLock implements Lock {
  /*
    @Resource
    private JedisPool jedisPool;
    private Jedis jedis = jedisPool.getResource();
    */
    @Override
    public void lock() {
        return;
    }

    //lockKey 键值，uniqueValue 唯一值，seconds 失效时间
    public boolean lock(Jedis jedis,String lockKey, String uniqueValue, int seconds){
        //jedis = jedisPoll.getResource();
        /*
        *   nxxx的值只能取NX或者XX，如果取NX，则只有当key不存在是才进行set，如果取XX，则只有当key已经存在时才进行set
            expx的值只能取EX或者PX，代表数据过期时间的单位，EX代表秒，PX代表毫秒。
        * */
        String result = jedis.set(lockKey,uniqueValue,"nx","px",500);
        if ("OK".equals(result)) {//加锁成功！
            return true;
        }
        return false;
    }

    public boolean unlock(Jedis jedis, String lockKey, String uniqueValue){
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
