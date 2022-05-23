package com.jpmc.salesreport.producer;

import com.jpmc.salesreport.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import static org.junit.jupiter.api.Assertions.*;

class SalesMessageProducerTest extends TestConfig {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    SalesMessageProducer salesMessageProducer;

    public static final String MEG_LINE_FOR_PRODUCER = "Add 5p apples" ;

    @Test
    public void sendMessageToDestinationTest() {
        assertNotNull(MEG_LINE_FOR_PRODUCER);
        salesMessageProducer.sendMessageToDestination(MEG_LINE_FOR_PRODUCER);
    }

}