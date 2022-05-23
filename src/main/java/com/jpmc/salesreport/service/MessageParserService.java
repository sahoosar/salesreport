package com.jpmc.salesreport.service;

import com.jpmc.salesreport.model.Product;
import com.jpmc.salesreport.exception.ParseAndProcessMessageException;

@FunctionalInterface
public interface MessageParserService {
    public Product parseAndProcessMessages(String messageLine) throws ParseAndProcessMessageException;
}
