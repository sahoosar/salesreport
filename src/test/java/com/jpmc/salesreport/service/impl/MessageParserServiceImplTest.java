package com.jpmc.salesreport.service.impl;

import com.jpmc.salesreport.TestConfig;
import com.jpmc.salesreport.model.Product;
import com.jpmc.salesreport.util.ReadAndParseMessages;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageParserServiceImplTest extends TestConfig {


    @Test
    void parseAndProcessMessages1Test()
    {
        MessageParserServiceImpl messageParserService=new MessageParserServiceImpl();
        messageParserService.readAndParseMessages=new ReadAndParseMessages();
        Product product=messageParserService.parseAndProcessMessages("apple at 45p");
        assertEquals("apple",product.getProductName());
    }
    @Test
    void parseAndProcessMessages2Test()
    {
        MessageParserServiceImpl messageParserService=new MessageParserServiceImpl();
        messageParserService.readAndParseMessages=new ReadAndParseMessages();
        Product product=messageParserService.parseAndProcessMessages("20 sales of banana at 25p each");
        assertEquals("banana",product.getProductName());
    }
    @Test
    void parseAndProcessMessagesNullTest()
    {
        MessageParserServiceImpl messageParserService=new MessageParserServiceImpl();
        messageParserService.readAndParseMessages=new ReadAndParseMessages();
        Product product=messageParserService.parseAndProcessMessages("hello 20 sales of banana at 25p each");
        assertEquals(null,product);
    }
}