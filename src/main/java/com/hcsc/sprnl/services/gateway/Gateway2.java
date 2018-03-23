package com.hcsc.sprnl.services.gateway;
import com.hcsc.sprnl.services.domain.Process;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(errorChannel = "gatewayErrorChannel")
public interface Gateway2 {
	@Gateway(requestChannel = "initiateRenewal1")
	void generate1(Process process);

}
