package by.itransition.chikanoff.utils;

import lombok.Getter;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Aspect
@Component
@Configurable
@Getter
public class AfterListenerAspect {
    private CountDownLatch latch = new CountDownLatch(1);

    @After("execution(* by.itransition.chikanoff.services.RabbitMQListener.onMessage(..))")
    public void doCountDown() {
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }
}
