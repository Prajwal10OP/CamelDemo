package com.ex.camel.camelservicea.routes.a;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class MyFirstTimerRouter extends RouteBuilder {

	

	@Autowired
	private GetCurrentTimeBean getCurrentTimeBean;

	@Autowired
	private SimpleLoggingProcessingComponent loggingComponent;

	@Override
	public void configure() throws Exception {
		// timer
		// transformation/Enrichment
		// log
		// Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
		from("timer:My First Timer")
		.log("${body}")

				.transform().constant("My Constant Message")
				.log("${body}")
				// .transform().constant("Time is now"+ LocalDateTime.now())

				// process: no change in body
				// Transformation: Change in body
				.bean(getCurrentTimeBean)
				.log("${body}")
				.bean(loggingComponent)
				.log("${body}")
				.process(new Simpleloggingprocessor())
				.to("log: My First Timer");

	}

}

@Component
class GetCurrentTimeBean {

	public String getCurrentTime() {
		return "Time is now" + LocalDateTime.now();

	}
}

@Component
class SimpleLoggingProcessingComponent {

	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

	public void process(String message) {
		logger.info("SimpleLoggingProcessingComponent{}", message);

	}
}

 class Simpleloggingprocessor implements Processor {
 
	 private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("SimpleLoggingProcessing{}", exchange.getMessage().getBody());
	}
	
}