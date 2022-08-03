package com.example.demo;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.processor.MyProcessor;

@Component
public class SampleRoute extends RouteBuilder {
	/*
	 * @Autowired private MyProcessor myProcessor;
	 */

	@Override
	public void configure() throws Exception {
		/*fileComponent();
		fileMove();
		amqComponent();
		localAmq();*/
		amqLocal();
	}
	private void fileComponent() {
		// file component
		from("file:C:/Users/a857206/Documents/Test/Input/").to("file:C:/Users/a857206/Documents/Test/Output/")
				.log(LoggingLevel.INFO, "Successfully file transfer to destination");
	}

	private void fileMove() {
		// to search with file name and move
		from("file:C:/Users/a857206/Documents/Test/Input/?filename=myfile.txt").routeId("Specific file tranfer")
				.log(LoggingLevel.INFO, "Ready for processing Body:${body}").process("myProcessor")
				.log(LoggingLevel.INFO, "After processing Body:${body}")
				.to("file:C:/Users/a857206/Documents/Test/Output/").log(LoggingLevel.INFO,
						"sucessfully found the file moved the file to destination Header:${headers} Body:${body}");
	}

	

	private void amqComponent() {
		// Active Mq component
		from("activemq:queue:book").routeId("amq-component").log(LoggingLevel.INFO,
				"sucessfully found the file moved the file to destination Body:${body}");
	}

	private void localAmq() {
		// from local to AMQ
		from("file:C:/Users/a857206/Documents/Test/camel/AMQ-transfer/").routeId("Transferring from local to AMQ")
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

}