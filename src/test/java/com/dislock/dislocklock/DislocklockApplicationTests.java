package com.dislock.dislocklock;

import com.dislock.dislocklock.dto.UseRunnable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DislocklockApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testDisLock(){
        UseRunnable runnable = new UseRunnable();
        new Thread(runnable,"售票窗口2").start();
        new Thread(runnable,"售票窗口3").start();
        new Thread(runnable,"售票窗口1").start();
    }
}
