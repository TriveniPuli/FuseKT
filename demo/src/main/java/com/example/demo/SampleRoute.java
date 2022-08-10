package com.example.demo;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.processor.MyProcessor;
import com.example.demo.processor.ProcessDy;

@Component
public class SampleRoute extends RouteBuilder {
	/*
	 * @Autowired private MyProcessor myProcessor;
	 */
	 ProcessDy processorDy;
	private AggregationStrategy aggregationStrategy;

	/*This is clonned change*/
	@Override
	public void configure() throws Exception {
		/*fileComponent();*/
		/*fileMove();*/
		/*amqComponent();*/
		/*localAmq();
*/		/*amqLocal();
		dynamicRoute();*/
		/*split();
		multiCast();*/
		/*aggregator();*/
		amqToIbmmq();
		/*sftp();*/
	}
	private void fileComponent() {
		// file component
		from("file:C:/Users/a857206/Documents/Test/Input/")
		.to("file:C:/Users/a857206/Documents/Test/Output/")
				.log(LoggingLevel.INFO, "Successfully file transfer to destination");
	}

	private void fileMove() {
		// to search with file name and move
		from("file:C:/Users/a857206/Documents/Test/Input/?filename=myfile.txt")
		.routeId("Specific file tranfer")
				.log(LoggingLevel.INFO, "Ready for processing Body:${body}")
				.process("myProcessor")
				.log(LoggingLevel.INFO, "After processing Body:${body}")
				.to("file:C:/Users/a857206/Documents/Test/Output/")
      .log(LoggingLevel.INFO,
						"sucessfully found the file moved file to destination Header:${headers} Body:${body}");
	}

	

	private void amqComponent() {
		// Active Mq component
		from("activemq:queue:book")
		.routeId("amq-component")
		.log(LoggingLevel.INFO,
				"sucessfully found the file moved the file to destination Body:${body}");
	}

	private void localAmq() {
		// from local to AMQ
		from("file:C:/Users/a857206/Documents/Test/camel/AMQ-transfer/")
		.routeId("Transferring from local to AMQ")
				.log(LoggingLevel.INFO, "ready to send to queue").to("activemq:queue:book")
				.log(LoggingLevel.INFO, "queue successfully recieved from local ${body}");

	}

	private void amqLocal() {
		// from Amq to local
		from("activemq:queue:data").routeId("Transferring from local to AMQ")
				.log(LoggingLevel.INFO, "ready to send to queue")
				.to("file:C:/Users/a857206/Documents/Test/camel/AMQ-transfer/")
				.log(LoggingLevel.INFO, "queue successfully recieved from local ${body}");
   }
	private void dynamicRoute() {
		//dynamic routing
		from("activemq:queue:dynamicRoute")
		.process("processordy")
		.toD("direct:${exchangeProperty.DynamicRoute}");

		from("direct:Route1")
				.log("Inside Route 1");

		from("direct:Route2")
				.log("Inside Route 2");
}
	private void split() {
		//split
		from("file:C:/Users/a857206/Documents/Test/split/")
				.split(body().tokenize("a"))
				.to("activemq:queue:splitdemo")
				.id("Split demo");
	}
	private void multiCast() {
		//Multicast
		from("activemq:queue:multicast1")
				.multicast()
					.process("myProcessor")
				.to("activemq:queue:multicast2")
				.id("Multicast Demo")
				.end();
}
	private void aggregator() {
		//Aggregator
		from("activemq:queue:aggregation")
		.multicast().aggregationStrategy(aggregationStrategy)
		.process("processordy")
		.end()
		.log("${body}");
}
private void amqToIbmmq() {
	//Amq to Ibmq
	
	from("activemq:queue:amq")
	.log("after amq")
	.to("ibmmq:queue:Test.sample1");
}
private void sftp() {
	 //sftp
	from("sftp:aegon@35.246.5.162:2222/ftphome/input?password=P455w0rd&delete=true")
	.to("file:C:/Users/a857206/Documents/Test/sftp")
	.log("successfully transfered file");
}
}
