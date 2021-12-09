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
import org.springframework.amqp.core.Message;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

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
    public void listenerGotMessageTest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleMessage message = new SimpleMessage();
        message.setName("world");
        Message msg = new Message(mapper.writeValueAsString(message).getBytes(StandardCharsets.UTF_8));
        listener.onMessage(msg);
        assertThat(memoryAppender.search("Hello world", Level.INFO).size()).isEqualTo(1);
    }
}
