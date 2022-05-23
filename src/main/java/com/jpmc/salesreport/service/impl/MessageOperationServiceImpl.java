package com.jpmc.salesreport.service.impl;

import com.jpmc.salesreport.model.Operation;
import com.jpmc.salesreport.service.MessageOperationService;
import com.jpmc.salesreport.util.PatternsDefinition;
import com.jpmc.salesreport.util.ReadAndParseMessages;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageOperationServiceImpl implements MessageOperationService {

    @Resource
    ReadAndParseMessages readAndParseMessages;

    @Override
    public Operation parseOperationalMessages(String messageLine) {
        Operation operation = null;
        if (messageLine.matches(PatternsDefinition.MSG_PATTERN_3)) {
            operation = readAndParseMessages.deriveProductAndOperationDetails(PatternsDefinition.MSG_PATTERN_3, messageLine);
        }
        return operation;
    }


}

