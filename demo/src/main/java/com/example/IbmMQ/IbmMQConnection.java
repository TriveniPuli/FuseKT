package com.example.IbmMQ;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.jms.JMSException;
import javax.net.ssl.SSLSocketFactory;

import org.apache.camel.component.jms.JmsComponent;
import org.messaginghub.pooled.jms.JmsPoolConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsExceptionFactory;
@Configuration
public class IbmMQConnection {
	@Value("${ibmmq.connectionNameList}") 
	String connectionNameList;
    @Value("${ibmmq.port}") 
    int port;
    @Value("${ibmmq.queueManager}") 
    String queueManager;
    @Value("${ibmmq.channel}") 
    String channel;
    @Value("${ibmmq.sslCipherSuite}") 
    String sslCipherSuite;
    @Value("${ibmmq.sslFipsRequired}") 
    boolean sslFipsRequired;
    
	 @Bean("ibmmq")
	    public JmsComponent ibmmqComponent(
	            @Qualifier("ibmmqJmsFactory") JmsPoolConnectionFactory mqConnectionFactory) {
	        JmsComponent jmsComponent = new JmsComponent();
			JmsComponent.jmsComponentAutoAcknowledge(mqConnectionFactory);
	       jmsComponent.setConnectionFactory(mqConnectionFactory);
	        jmsComponent.setAcknowledgementModeName("CLIENT_ACKNOWLEDGE");
	        return jmsComponent;
	    }
	 
	@Bean("ibmmqJmsFactory")
	public JmsPoolConnectionFactory getMqConnectionFactory() throws JMSException, KeyManagementException, UnrecoverableKeyException, KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        MQConnectionFactory mqConnectionFactory = new MQConnectionFactory();
        SSLSocketFactory sslSocketFactory = MQSocketFactory.getSSLSocketFactory("TLSv1.2");
        mqConnectionFactory.setSSLSocketFactory(sslSocketFactory);
        mqConnectionFactory.setTransportType(1);
        mqConnectionFactory.setConnectionNameList(connectionNameList);
        mqConnectionFactory.setPort(port);
        mqConnectionFactory.setQueueManager(queueManager);
        mqConnectionFactory.setChannel(channel);
        mqConnectionFactory.setSSLCipherSuite(sslCipherSuite);
        mqConnectionFactory.setSSLFipsRequired(sslFipsRequired);
        org.messaginghub.pooled.jms.JmsPoolConnectionFactory jmsPoolConnectionFactory = new JmsPoolConnectionFactory();
        jmsPoolConnectionFactory.setMaxConnections(1);
        jmsPoolConnectionFactory.setConnectionFactory(mqConnectionFactory);
        return jmsPoolConnectionFactory;
    }
}


