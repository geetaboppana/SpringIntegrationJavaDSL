package com.hcsc.sprnl.services.gateway;
import com.hcsc.sprnl.services.domain.Process;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(errorChannel = "renewalErrorChannel")
public interface InitiateRenewalGateway {
	@Gateway(requestChannel = "initiateRenewal")
	void generate(Process process);
	
}
