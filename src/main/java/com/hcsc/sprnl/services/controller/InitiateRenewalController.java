package com.hcsc.sprnl.services.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hcsc.sprnl.services.domain.Process;
import com.hcsc.sprnl.services.gateway.InitiateRenewalGateway;

@RestController
public class InitiateRenewalController {
	
	@Autowired
	InitiateRenewalGateway initiateRenewalGateway;

    
    @RequestMapping(value="/initiateRenewal", method= RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public String initiateRenewal() {
    	
    	
    	
    	Process process = new Process();
    	process.setAssignee("jbarrez");
		initiateRenewalGateway.generate(process );
    	
    	
     
      return "test";
    }
    


}