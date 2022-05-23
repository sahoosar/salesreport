package com.jpmc.salesreport.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SalesMessageProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessageToDestination(final String message) {
        jmsTemplate.convertAndSend(message);
    }
}
