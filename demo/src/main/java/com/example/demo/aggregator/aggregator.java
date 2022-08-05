package com.example.demo.aggregator;

import org.apache.camel.Exchange;

public class aggregator implements org.apache.camel.AggregationStrategy{

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		if(oldExchange!=null){
			String oldValue=oldExchange.getIn().getBody(String.class);
			String newValue=newExchange.getIn().getBody(String.class);
			oldExchange.getIn().setHeader("newEchange", newValue);
			return oldExchange;
		}else{
			return newExchange;
		}
		
	}

}
