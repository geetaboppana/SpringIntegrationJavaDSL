package com.hcsc.sprnl.services.exception;

import java.io.IOException;

import javax.naming.AuthenticationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

public class MyResponseErrorHandler extends DefaultResponseErrorHandler {

private static final Log logger = LogFactory.getLog(MyResponseErrorHandler.class);

@Override
public void handleError(ClientHttpResponse clienthttpresponse) throws IOException {
System.out.println("IN HANDLE ERROR CLASS");
    if (clienthttpresponse.getStatusCode() == HttpStatus.FORBIDDEN) {
        logger.debug(HttpStatus.FORBIDDEN + " response. Throwing authentication exception");
        throw new IOException();
    }
}

@Override
public boolean hasError(ClientHttpResponse clienthttpresponse) throws IOException {
	System.out.println("IN HAS ERROR CLASS");

    if (clienthttpresponse.getStatusCode() != HttpStatus.OK) {
        logger.debug("Status code: " + clienthttpresponse.getStatusCode());
        logger.debug("Response" + clienthttpresponse.getStatusText());
        logger.debug(clienthttpresponse.getBody());

        if (clienthttpresponse.getStatusCode() == HttpStatus.FORBIDDEN) {
            logger.debug("Call returned a error 403 forbidden resposne ");
            return true;
        }
    }
    return false;
}
}