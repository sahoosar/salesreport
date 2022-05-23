package com.jpmc.salesreport.util;

import com.jpmc.salesreport.model.Operation;
import com.jpmc.salesreport.model.Product;
import com.jpmc.salesreport.exception.ResourcePathException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.EnumUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class ReadAndParseMessages {

    @Value("${file.path}")
    private String filePathLocation;

    public List<String> readFile() throws  ResourcePathException{


        try{
            Path filePath = Paths.get(ReadAndParseMessages.class.getResource(filePathLocation).getPath());
            return Files.lines(filePath).collect(Collectors.toList());
        } catch (Exception ioe) {
            log.error("Exception raised due to {}", ioe);
            throw new ResourcePathException("Resource file path, check file name in properties file");
        }


    }

    public Product deriveProductNamePriceAndQuantity(String pattern, String line) {

        Product product = new Product();

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(line);
        List<ProductNames> productNameList = Stream.of(ProductNames.values()).collect(Collectors.toList());

        if (m.find()) {
            if(EnumUtils.isValidEnum(ProductNames.class,m.group(GroupDefinition.PRODUCT_NAME)))
            product.setProductName(EnumUtils.getEnum(ProductNames.class,m.group(GroupDefinition.PRODUCT_NAME)).label);
            product.setProductPrice(Long.valueOf(m.group(GroupDefinition.PRODUCT_PRICE)));
            product.setProductQuantity(m.groupCount() > 2 ? Integer.valueOf(m.group(GroupDefinition.PRODUCT_SALES_QUANTITY)) : 1);
        }

        return product;
    }

    public Operation deriveProductAndOperationDetails(String pattern, String line) {

        Operation operation = new Operation();

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(line);

        if (m.find()) {
            if(EnumUtils.isValidEnum(ProductNames.class,m.group(GroupDefinition.PRODUCT_NAME)))
            operation.setOpProdName(EnumUtils.getEnum(ProductNames.class,m.group(GroupDefinition.PRODUCT_NAME)).label);
            operation.setOpPrice(Long.valueOf(m.group(GroupDefinition.PRODUCT_OPERATION_PRICE)));
            operation.setOpType(m.group(GroupDefinition.PRODUCT_OPERATION));

        }
        return operation;
    }
}