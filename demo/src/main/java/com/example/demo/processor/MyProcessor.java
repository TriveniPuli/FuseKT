package com.example.demo.processor;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;


	@Configuration("myProcessor")
	public class MyProcessor implements Processor {
		
		private static Logger logger = LogManager.getLogger(MyProcessor.class);


		@Override
		public void process(Exchange exchange) throws Exception {
			
			String value = exchange.getIn().getBody(String.class);
			logger.info("Ready For Concatenating, {}", value);
	        value = value.concat(" this is using processor");
	        logger.info("Done Concatenation, {}", value);
	        exchange.getIn().setBody(value);

			
		}
	}
