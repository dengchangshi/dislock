package com.dislock.dislocklock;

import com.dislock.dislocklock.dto.UseRunnable;
import com.service.JdkLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DislocklockApplicationTests {

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
}
