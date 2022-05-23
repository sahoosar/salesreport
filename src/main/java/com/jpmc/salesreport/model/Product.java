package com.jpmc.salesreport.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String productName;
    private long productPrice;
    private  Operation operation;
    private Integer productQuantity;
    private Integer productPosition;

}
