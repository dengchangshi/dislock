package com.dislock.dislocklock;

import com.dislock.dislocklock.dto.UseRunnable;
import com.service.JdkLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DislocklockApplicationTests {

    private static Integer count = 10000;
    private CountDownLatch countDownLatch = new CountDownLatch(5);
    private static final Object obj = new Object();


    private Lock lock;

    @Test
    public void contextLoads() {

    }

    @Test
    public void testDisLock(){
       Lock lock = new JdkLock();
      /*  Lock lock = new MysqlLock();
        Lock lock = new RedisLock();
        Lock lock = new ZookeeperLock();*/

        UseRunnable runnable = new UseRunnable(lock);
        new Thread(runnable,"售票窗口2").start();
        new Thread(runnable,"售票窗口3").start();
        new Thread(runnable,"售票窗口1").start();
    }

    private  void testCnt(){
        System.out.println("你好，你好！！"+System.currentTimeMillis());
    }

    @Test
    public void testCountdown() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 1; i <= 5; i++) {
            try {

                //executorService.execute(this::testCnt);// 都可以
                executorService.execute(() -> {
                    testCnt();
                });
            } catch (Throwable e) {
                //TODO
            } finally {
                // 很关键, 无论上面程序是否异常必须执行countDown,否则await无法释放
                countDownLatch.countDown();
            }
        }
        // 5个线程countDown()都执行之后才会释放当前线程,程序才能继续往后执行
        countDownLatch.await();
        //关闭线程池
        executorService.shutdown();
        //System.out.println(count);
        //System.out.println(Thread.currentThread().getName() + ":这是最后一个线程!");
    }
}
