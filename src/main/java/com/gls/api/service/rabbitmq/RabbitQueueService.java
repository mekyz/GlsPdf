package com.gls.api.service.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gls.util.Loggers;

@Service
public class RabbitQueueService {
	
	@Autowired
    private RabbitTemplate rabbitTemplate;
	public void insert(String queueName, Object msg) {
        rabbitTemplate.convertAndSend(queueName, msg);
        Loggers.WEB_ERROR_LOGGER.info("RabbitQueueService convertAndSend >> " + queueName + " >> " + msg);
    }
}
