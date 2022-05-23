package com.jpmc.salesreport.service.impl;

import com.jpmc.salesreport.model.Product;
import com.jpmc.salesreport.service.MessageParserService;
import com.jpmc.salesreport.util.PatternsDefinition;
import com.jpmc.salesreport.util.ReadAndParseMessages;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageParserServiceImpl implements MessageParserService {

    @Resource
    ReadAndParseMessages readAndParseMessages;

    @Override
    public Product parseAndProcessMessages(String messageLine) {

        if (messageLine.matches(PatternsDefinition.MSG_PATTERN_1)) {
            return readAndParseMessages.deriveProductNamePriceAndQuantity(PatternsDefinition.MSG_PATTERN_1, messageLine);
        } else if(messageLine.matches(PatternsDefinition.MSG_PATTERN_2)) {
           return readAndParseMessages.deriveProductNamePriceAndQuantity(PatternsDefinition.MSG_PATTERN_2, messageLine);
        }

        return null;
    }
}
