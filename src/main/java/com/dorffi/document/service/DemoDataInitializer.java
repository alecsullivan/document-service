package com.dorffi.document.service;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.dorffi.document.model.Document;

@Component
public class DemoDataInitializer implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoDataInitializer.class);
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private DocumentService service;	
	
	@Override
	public void afterPropertiesSet() throws Exception {		
		this.service.saveDocument(createDocument("Test Content 1"));
		this.service.saveDocument(createDocument("Test Content 2"));
	}
	
	private Document createDocument(String content) {
		Document doc = new Document();
		
		String id = null;
		
		List<ServiceInstance> serviceInstances = discoveryClient.getInstances("id-service");
		LOGGER.debug("Got instances: %s", serviceInstances);
		if(serviceInstances != null && serviceInstances.size() > 0) {
			LOGGER.debug("Found %s instances.", serviceInstances.size());
			ServiceInstance serviceInstance = serviceInstances.get(0);
			URI serviceInstanceURI = serviceInstance.getUri();
			LOGGER.debug("Attempting to locate id-service...");
			id = restTemplate.getForObject(serviceInstanceURI.toString() + "/id-generator", String.class);
			LOGGER.debug("Retrieved id: %s", id);
		} else {
			LOGGER.debug("Failed to locate an instance.");
		}		
		
		doc.setId(id);		
		doc.setContent(content);
		
		return doc;
	}
}
