package by.itransition.chikanoff.utils;

import by.itransition.chikanoff.payloads.message.SimpleMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class MessageProducer {
    @Autowired
    private RabbitTemplate template;

    public void pushMessage(SimpleMessage msg) {
        template.convertAndSend("jcg-exchange", "routingkey1", msg);
        log.info("Message sent: " + msg);
    }
}
