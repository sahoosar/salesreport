package com.jpmc.salesreport.service.impl;

import com.jpmc.salesreport.model.Operation;
import com.jpmc.salesreport.model.Product;
import com.jpmc.salesreport.model.SalesOperation;
import com.jpmc.salesreport.service.SalesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SalesServiceImpl implements SalesService {
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[0m";
    @Override
    public void calculateSalesPrice(SalesOperation operations) {

        for(Operation operation:operations.getOperationList()) {
            List<Product> products = operations.getProductList().stream().
                    filter(p ->p.getProductName().equalsIgnoreCase(operation.getOpProdName())
                            && p.getProductPosition() < operation.getPosition()
                    )
                    .collect(Collectors.toList());
            products.forEach(p -> {
                if(null==p.getOperation())
                    p.setOperation(operation);
                else
                    consolidateOperation(p,operation);

            });
        }
        Map<String, List<Product>> productGroup = operations.getProductList().stream().collect(Collectors.groupingBy(Product::getProductName));
        printTabularData(productGroup);
    }

    private void consolidateOperation(Product p, Operation operation) {
        p.setProductPrice(calculateFinal(p.getOperation(),p));
        p.setOperation(operation);
    }

    private long calculateFinal(Operation op,Product product)
    {
        if(null==op)
            return product.getProductPrice();
        if(op.getOpType().equalsIgnoreCase("Add") && product.getProductName().equalsIgnoreCase(op.getOpProdName()))
        {
            return product.getProductPrice()+op.getOpPrice();
        }
        if(op.getOpType().equalsIgnoreCase("Sub") && product.getProductName().equalsIgnoreCase(op.getOpProdName()))
        {
            return product.getProductPrice()-op.getOpPrice();
        }
        if(op.getOpType().equalsIgnoreCase("Mul") && product.getProductName().equalsIgnoreCase(op.getOpProdName()))
        {
            return product.getProductPrice()*op.getOpPrice();
        }
        return product.getProductPrice();
    }
    private void printTabularData(Map<String, List<Product>> productGroup) {
        String leftAlignFormat = "| %-15s | %-8s | %-8s | %-10s | %-10s | %-14s |%n";
        final AtomicLong[] totalSales = {new AtomicLong(),new AtomicLong()};
        System.out.format(ANSI_GREEN+"+-----------------+----------+----------+------------+------------+----------------+%n");
        System.out.format(ANSI_CYAN+"| PRODUCT         | QTY      | PRICE(p) | OPERAND(p) | SALE PRICE |QTY * SALE PRICE|%n");
        System.out.format(ANSI_GREEN+"+-----------------+----------+----------+------------+------------+----------------+%n");

        productGroup.entrySet().stream().forEach(e-> {
            List<Product> productList=e.getValue();
            totalSales[0] = new AtomicLong(); // Corresponding to price
            totalSales[1] = new AtomicLong();//corresponding to quantity
            productList.forEach(product -> {
                totalSales[0].set(totalSales[0].get() + (product.getProductQuantity() * calculateFinal(product.getOperation(), product)));
                totalSales[1].set(totalSales[1].get() +product.getProductQuantity());
                long calculatedPrice=calculateFinal(product.getOperation(), product);
                System.out.format(leftAlignFormat, (product.getProductName()),
                        product.getProductQuantity(),
                        product.getProductPrice(),
                        null!= product.getOperation() ? getOperator(product.getOperation().getOpType()) + product.getOperation().getOpPrice() : 0
                        , calculatedPrice,calculatedPrice*product.getProductQuantity());});
            System.out.format(ANSI_GREEN+"+-----------------+----------+----------+------------+------------+----------------+%n");
            System.out.format(leftAlignFormat,ANSI_PURPLE+"Total Amount   ",totalSales[1],"","","", totalSales[0] );
            System.out.format(ANSI_GREEN+"+-----------------+----------+----------+------------+------------+----------------+%n");
        });
        System.out.println(ANSI_WHITE);
    }

    private String getOperator(String op)
    {
        if(op.equalsIgnoreCase("Add"))
            return "(+)";
        if(op.equalsIgnoreCase("Sub"))
            return "(-)";
        if(op.equalsIgnoreCase("Mul"))
            return "(*)";

        return "";
    }
}
