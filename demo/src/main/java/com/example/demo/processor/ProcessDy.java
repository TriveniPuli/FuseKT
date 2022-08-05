package com.example.demo.processor;

import java.util.Objects;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.context.annotation.Configuration;

@Configuration("processordy")
public class ProcessDy implements Processor{
	@Override
	public void process(Exchange exchange) throws Exception {
		 String value = exchange.getIn().getBody(String.class);
	        System.out.println("The Value is : "+value);
	        
	        if(Objects.nonNull(value)){
	            if(value.trim().equalsIgnoreCase("1")) {
	                exchange.setProperty("DynamicRoute","Route1");
	            } 
	            else if (value.trim().equalsIgnoreCase("2")) {
	                exchange.setProperty("DynamicRoute","Route2");
	            }
	        }

	    }
		
	}

	

	

		
	

