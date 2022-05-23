package com.jpmc.salesreport.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {

    private String opType;

    private long opPrice;

    private String opProdName;

    private int position;

}
