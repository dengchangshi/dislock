package com.dislock.dislocklock.dto;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author: dengcs
 * @Date: 2019/4/25 17:54
 */
public class UseRunnable implements Runnable {
    private static int tickCnt = 100;

    //@Resource(name = "JdkLock")
    private Lock lock = new Lock() {
        @Override
        public void lock() {

        }

        @Override
        public void lockInterruptibly() throws InterruptedException {

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

        }

        @Override
        public Condition newCondition() {
            return null;
        }
    };

    @Override
    public void run() {
        while (tickCnt>0){
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"售出："+tickCnt--);

            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                lock.unlock();
            }
        }

        try {
            Thread.sleep((int)Math.random() * 80); //稍作休息
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
