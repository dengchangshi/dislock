package com.dislock.dislocklock.dto;

import com.service.JdkLock;


import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author: dengcs
 * @Date: 2019/4/25 17:54
 */
public class UseRunnable implements Runnable {
    private static int tickCnt = 100;

    private Lock lock;

    /*构造函数*/
    public UseRunnable(Lock lock){
        this.lock = lock;
    }

    @Override
    public void run() {
        while (tickCnt>0){
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"售出：    "+tickCnt--);

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
