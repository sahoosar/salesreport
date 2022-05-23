package com.jpmc.salesreport.service;

import com.jpmc.salesreport.model.SalesOperation;

@FunctionalInterface
public interface SalesService {
    public void calculateSalesPrice(SalesOperation salesOperation);
}
