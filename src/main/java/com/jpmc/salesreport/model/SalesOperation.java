package com.jpmc.salesreport.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SalesOperation {

     private List<Product> productList = new ArrayList<>();
     private List<Operation> operationList = new ArrayList<>();


}
