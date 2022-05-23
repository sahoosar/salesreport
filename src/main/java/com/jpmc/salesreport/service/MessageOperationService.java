package com.jpmc.salesreport.service;

import com.jpmc.salesreport.model.Operation;

@FunctionalInterface
public interface MessageOperationService {
    public Operation parseOperationalMessages(String messageLine);
}
