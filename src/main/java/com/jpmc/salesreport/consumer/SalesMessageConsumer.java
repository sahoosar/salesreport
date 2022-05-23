package com.jpmc.salesreport.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import com.jpmc.salesreport.model.Product;
import com.jpmc.salesreport.model.Operation;
import com.jpmc.salesreport.model.SalesOperation;
import com.jpmc.salesreport.exception.MessageOperationException;
import com.jpmc.salesreport.exception.ParseAndProcessMessageException;
import com.jpmc.salesreport.service.MessageOperationService;
import com.jpmc.salesreport.service.MessageParserService;
import com.jpmc.salesreport.service.SalesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.logging.Level;

@Component
@Slf4j
public class SalesMessageConsumer {

    @Autowired
    MessageParserService messageParserService;
    @Autowired
    MessageOperationService messageOperationService;

    @Autowired
    SalesService salesService;

    @Autowired
    SalesOperation salesOperation;

    private int messageCounter = 1;

    @JmsListener(destination = "MSG_IN_QUEUE")
    public void onMessageArrival(Message message) {
        if(messageCounter>=50)
            log.error(Level.SEVERE+"Message Consumer :: Unable to process Message {} with text {} ",messageCounter,message);
        else if (message instanceof TextMessage) {
            try {
                String msgLine = ((TextMessage) message).getText();
                // for product
                deriveProduct(msgLine);
                //for sales operation
                deriveOperation(msgLine);
                //calculation
                if(messageCounter%10==0)
                    salesService.calculateSalesPrice(salesOperation);


            } catch (JMSException ex) {
                log.error(Level.SEVERE+"Message Consumer :: JMSException exception caused by {}",ex.getMessage());
            }
            catch (MessageOperationException ex)
            {
                log.error(Level.SEVERE+" Message Consumer :: MessageOperationException caused by {}",ex.getMessage());
            }
            catch (Exception ex) {
                log.error(Level.SEVERE+"Message Consumer :: Error {}",ex.getMessage());
            }
            messageCounter++;
        } else {
            log.error("Message must be of type TextMessage");
        }
    }

    private void deriveOperation(String msgLine) throws MessageOperationException {
        if(msgLine.contains("Add") ||msgLine.contains("Sub")||msgLine.contains("Mul")) {
            Operation operation = messageOperationService.parseOperationalMessages(msgLine);

            if(null!=operation && StringUtils.isNotEmpty(operation.getOpProdName())) {
                operation.setPosition(messageCounter);
                salesOperation.getOperationList().add(operation);
                log.info("Message Consumer :: Operation {} received {}", messageCounter, operation);
            }
            else
               throw new MessageOperationException("Message format is not supported for Operation");

        }
    }

    private void deriveProduct(String msgLine) throws MessageOperationException, ParseAndProcessMessageException {
        if(!msgLine.contains("Add") && ! msgLine.contains("Sub")&&!msgLine.contains("Mul")) {
            Product product = messageParserService.parseAndProcessMessages(msgLine);

            if (null != product && StringUtils.isNotEmpty( product.getProductName())) {
                product.setProductPosition(messageCounter);
                salesOperation.getProductList().add(product);
                log.info("Message Consumer :: Product {} received {}", messageCounter, product);
            }
            else
                throw new MessageOperationException("Message format is not supported for Product");
        }
    }


}
