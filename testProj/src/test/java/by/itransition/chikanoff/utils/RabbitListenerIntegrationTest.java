package by.itransition.chikanoff.utils;

import by.itransition.chikanoff.payloads.message.SimpleMessage;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@EnableRabbit
@EnableAspectJAutoProxy
@SpringBootTest
@Import(RabbitConfig.class)
@ActiveProfiles("test")
public class RabbitListenerIntegrationTest {
    @Autowired
    private AfterListenerAspect aspect;
    @Autowired
    private MessageProducer producer;
    private MemoryAppender memoryAppender;

    @BeforeEach
    public void setup() {
        aspect.resetLatch();
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.DEBUG);
        logger.addAppender(memoryAppender);
        memoryAppender.start();
    }

    @Test
    public void sendMessageToListener() throws InterruptedException {
        SimpleMessage message = new SimpleMessage();
        message.setName("name");
        producer.pushMessage(message);
        aspect.getLatch().await();
        assertThat(memoryAppender.search("Hello name").size()).isEqualTo(1);
    }
}
