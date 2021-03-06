package com.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author: dengcs
 * @Date: 2019/4/25 18:18
 * 注释 Zookeeper实现布式锁 可靠，并发量大，解决死锁较好
 */

@Service
public class ZookeeperLock implements Lock {
    @Override
    public void lock() {
        return;
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
