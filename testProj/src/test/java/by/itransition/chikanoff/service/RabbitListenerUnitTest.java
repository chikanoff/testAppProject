package by.itransition.chikanoff.service;

import by.itransition.chikanoff.payloads.message.SimpleMessage;
import by.itransition.chikanoff.services.RabbitMQListener;
import by.itransition.chikanoff.utils.MemoryAppender;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class RabbitListenerUnitTest {
    @InjectMocks
    private RabbitMQListener listener;
    private MemoryAppender memoryAppender;

    @BeforeEach
    public void setup() {
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.DEBUG);
        logger.addAppender(memoryAppender);
        memoryAppender.start();
    }

    @Test
    public void listenerGotMessageTest() {
        SimpleMessage message = new SimpleMessage();
        message.setName("world");
        listener.onMessage(message);
        assertThat(memoryAppender.search("Hello world", Level.INFO).size()).isEqualTo(1);
    }
}
