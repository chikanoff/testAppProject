package by.itransition.chikanoff.services;

import by.itransition.chikanoff.payloads.message.SimpleMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Log4j2
@Service
public class RabbitMQListener {

    @RabbitListener(queues = "queue1")
    public void onMessage(Message message) {
        ObjectMapper objMapper = new ObjectMapper();
        try {
            SimpleMessage msg = objMapper.readValue(message.getBody(), SimpleMessage.class);
            log.info("Hello " + msg.getName());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
