package by.itransition.chikanoff.services;

import by.itransition.chikanoff.payloads.message.SimpleMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Log4j2
@Service
public class RabbitMQListener {
    @RabbitListener(queues = {"queue1"})
    public void onMessage(SimpleMessage message) {
        log.info("Hello " + message.getName());
    }
}
