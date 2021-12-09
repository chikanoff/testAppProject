package by.itransition.chikanoff.service;

import by.itransition.chikanoff.payloads.message.SimpleMessage;
import by.itransition.chikanoff.utils.AfterListenerAspect;
import by.itransition.chikanoff.utils.MemoryAppender;
import by.itransition.chikanoff.utils.MessageProducer;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@EnableAspectJAutoProxy
@SpringBootTest
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
    public void sendMessageToListener() throws JsonProcessingException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleMessage message = new SimpleMessage();
        message.setName("world");
        producer.pushMessage(mapper.writeValueAsString(message));
        aspect.getLatch().await();
        assertThat(memoryAppender.search("Hello world").size()).isEqualTo(1);
    }
}
