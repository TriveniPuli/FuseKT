package com.example.IbmMQ;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public class MQSocketFactory {
	
	 private static Logger logger = LogManager.getLogger("net.atos");
	    private static final String KEYSTORE_PROPERTY = "javax.net.ssl.keyStore";
	    private static final String KEYSTOREPASSWORD_PROPERTY = "javax.net.ssl.keyStorePassword";
	    private static final String TRUSTSTOREFILEPATH_PROPERTY = "javax.net.ssl.trustStore";
	    private static final String TRUSTSTOREPASSWORD_PROPERTY = "javax.net.ssl.trustStorePassword";
	    private static final String KEYSTORE_INSTANCE = "JKS";

	    private MQSocketFactory() {}

	    public static SSLSocketFactory getSSLSocketFactory(String sslAlgorythm) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException {
	        logger.info("Class:MqSocketFactory, Method:getSSLSocketFactory --execution has been started");

	        String keyStore = System.getProperty(KEYSTORE_PROPERTY);
	        String keyStorePassword = System.getProperty(KEYSTOREPASSWORD_PROPERTY);

	        String trustStoreFilePath = System.getProperty(TRUSTSTOREFILEPATH_PROPERTY);
	        String trustStorePassword = System.getProperty(TRUSTSTOREPASSWORD_PROPERTY);

	        KeyStore ks = KeyStore.getInstance(KEYSTORE_INSTANCE);
	        ks.load(new FileInputStream(keyStore), keyStorePassword.toCharArray());

	        KeyStore trustStore = KeyStore.getInstance(KEYSTORE_INSTANCE);
	        if(trustStoreFilePath != null && !trustStoreFilePath.equalsIgnoreCase("")){
	            trustStore.load(new FileInputStream(trustStoreFilePath), trustStorePassword.toCharArray());
	        }

	        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

	        trustManagerFactory.init(trustStore);
	        keyManagerFactory.init(ks, keyStorePassword.toCharArray());

	        SSLContext sslContext = SSLContext.getInstance(sslAlgorythm);
	        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

	        logger.info("Class:MqSocketFactory, Method:getSSLSocketFactory --successfully executed");
	        return sslContext.getSocketFactory();
	    }
	}


