package com.jpmc.salesreport;

import com.jpmc.salesreport.exception.ResourcePathException;
import com.jpmc.salesreport.producer.SalesMessageProducer;
import com.jpmc.salesreport.util.ReadAndParseMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class SalesReportApplication implements CommandLineRunner {

	@Autowired
	private SalesMessageProducer messageProducer;

	@Resource
	ReadAndParseMessages readMessages;

	public static void main(String[] args) {
		SpringApplication.run(SalesReportApplication.class, args);
	}

	//Read sales messages, from the file and post it into message consumer using method, run().
	@Override
	public void run(String... args) {

		List<String> messageList ;
		try {
			messageList = readMessages.readFile();
			messageList.forEach( msgLine -> messageProducer.sendMessageToDestination(msgLine));
		} catch (ResourcePathException | NullPointerException  e) {
				log.error("Exception Caused By {} ",e.getMessage());
		} catch (Exception e) {
			log.error("Generic Exception Caused By {} ",e.getMessage());
		}

	}
}
