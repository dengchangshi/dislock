package com.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author: dengcs
 * @Date: 2019/4/25 18:18
 * 注释 Mysql实现布式锁, 可靠，并发量500左右，可能造成死锁
 */

@Service
public class MysqlLock implements Lock {
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
