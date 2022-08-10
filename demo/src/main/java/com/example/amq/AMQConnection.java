package com.example.amq;
import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQConnection {
	@Bean("amq")
	public JmsComponent amqJmsComponent(@Qualifier("amqJmsFactory") ConnectionFactory activeMQConnectionFactory) {
		JmsComponent jmsComponent = new JmsComponent();
		jmsComponent.setConnectionFactory(activeMQConnectionFactory);
		jmsComponent.setAcknowledgementModeName("CLIENT_ACKNOWLEDGE");
		return jmsComponent;
	}

	@Bean("amqJmsFactory")
	public ActiveMQConnectionFactory activeMQConnectionFactory(@Value("${amq.brokerUrl}") String brokerUrl) {
		return new ActiveMQConnectionFactory(brokerUrl);
	}

}

