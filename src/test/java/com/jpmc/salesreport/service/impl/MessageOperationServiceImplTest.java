package com.jpmc.salesreport.service.impl;

import com.jpmc.salesreport.TestConfig;
import com.jpmc.salesreport.util.ReadAndParseMessages;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageOperationServiceImplTest extends TestConfig {


    @Test
    void parseOperationalMessages() {
        MessageOperationServiceImpl messageOperationService=new MessageOperationServiceImpl();
        messageOperationService.readAndParseMessages=new ReadAndParseMessages();
        assertEquals("Add",messageOperationService.parseOperationalMessages("Add 25p apples").getOpType());
    }
}