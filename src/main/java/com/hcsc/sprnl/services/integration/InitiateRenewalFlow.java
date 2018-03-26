package com.hcsc.sprnl.services.integration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.PollableChannel;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.hcsc.sprnl.services.exception.MyResponseErrorHandler;

@Configuration
public class InitiateRenewalFlow {

	@Bean
	public IntegrationFlow initiateFlow() {
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(requestFactory());
		//restTemplate.setErrorHandler(new MyResponseErrorHandler());
		return IntegrationFlows.from("initiateRenewal")
				.transform(Transformers.toJson())
				.handle(Http.outboundGateway("http://localhost:8086/process",restTemplate).charset("UTF-8")
                        .httpMethod(HttpMethod.POST).expectedResponseType(String.class).errorHandler(new MyResponseErrorHandler()))
				.gateway("initiateRenewal1").get();
				//.handle(t -> System.out.println("AFTER REST CALL"+t.toString()+t)).get();
	}
	

	@Bean
	public IntegrationFlow initiateFlow1() {
		
	
		return IntegrationFlows.from("initiateRenewal1")
				.transform(Transformers.toJson())
				.log("IN INITIATE GATEWAY !")
				.handle(t -> System.out.println("IN INITIATE GATEWAY ! MESSAGE"+t.toString()+t)).get();
	}
	
	public SimpleClientHttpRequestFactory requestFactory() {
		// Disable auto redirect on 3xx HTTP responses
		SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		// set this value as low as possible, since we're connecting locally 
		simpleClientHttpRequestFactory.setConnectTimeout(500);
		simpleClientHttpRequestFactory.setReadTimeout(2000);
		
		return simpleClientHttpRequestFactory;
		
	}
	
	
	   @Bean
	    public IntegrationFlow errorResponse() {
	        return IntegrationFlows.from(renewalErrorChannel()).log("ERROR FLOW TRIGGERED")
	                   /* .<MessagingException, Message<?>>transform(MessagingException::getFailedMessage,
	                            e -> e.poller(p -> p.fixedDelay(100)))*/
	                    .get();
	    }

	    @Bean
	    public IntegrationFlow type1() {
	            return f -> f
	                    .enrichHeaders(h -> h.header("ABCDEF", "ABCDEF", true))
	                    .handle((p, h) -> { throw new RuntimeException("intentional"); });
	    }

	    @Bean
	    public PollableChannel renewalErrorChannel() {
	        return new QueueChannel();
	    }
	
}
