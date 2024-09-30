package com.dexbackend.dexbackend.authentication.jwt.controller;



import org.apache.xmlbeans.XmlException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dexbackend.dexbackend.authentication.jwt.jwtconfig.CustomJwt;
import com.dexbackend.dexbackend.pageanalyzer.utilities.ElasticsearchService;
import com.dexbackend.dexbackend.pageanalyzer.utilities.PowerPointService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins ="*")
public class HelloController {

	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
    //@PreAuthorize("hasAuthority('ROLE_fullstack-developer')")
	public ResponseEntity<Map<String, String>> hello() 
	{
         var jwt = (CustomJwt) SecurityContextHolder.getContext().getAuthentication();
        
        
         String message = MessageFormat
                .format("Hello fullstack master {0} {1}, how is it going today?",
                        jwt.getFirstname(), jwt.getLastname());
        

		 Map<String, String> messageMap = new HashMap<>();
		 messageMap.put("message", message);
		  
		 return ResponseEntity.ok(messageMap);
	}
	
	@RequestMapping(value = "/getppt", method = RequestMethod.POST)
//	@CrossOrigin(origins = "*")
//	@PostMapping("/download")
	public ResponseEntity<Resource> downloadPpt(@RequestBody Map<String, String> requestBody) throws IOException, XmlException {
	    

	    PowerPointService powerPointService = new PowerPointService();

		ElasticsearchService elasticsearchService = new ElasticsearchService();
		
		String applicationName = requestBody.get("applicationName");
	    String buildId = requestBody.get("buildId");
	    JSONObject response = elasticsearchService.getDataForSeverity(applicationName, buildId);
	    JSONObject mimeResponse = elasticsearchService.getPerformanceMimePie(applicationName, buildId);
	    JSONObject wcvResponse = elasticsearchService.getPerformanceWCV(applicationName, buildId);

	    JSONObject bpFailCatResponse = elasticsearchService.getBPFailCat(applicationName, buildId);
	    JSONObject bpSEOCatResponse = elasticsearchService.getSEOFailCat(applicationName, buildId);
	    JSONObject getDataForTableInfoResponse = elasticsearchService.getDataForTableInfo(applicationName, buildId);

	    double Accessibilityvalue = elasticsearchService.getAccessibilityScore(applicationName, buildId);
	    powerPointService.createPresentationMetered(applicationName, buildId, Accessibilityvalue, response, getDataForTableInfoResponse, wcvResponse, "accessibility");
	    
	    double Performancevalue = elasticsearchService.getPerformanceScore(applicationName, buildId);
	    powerPointService.createPresentationMetered(applicationName, buildId, Performancevalue, mimeResponse, getDataForTableInfoResponse, wcvResponse, "performance");
	    
	    double SEOvalue = elasticsearchService.getSEOScore(applicationName, buildId);
	    powerPointService.createPresentationMetered(applicationName, buildId, SEOvalue, bpSEOCatResponse, getDataForTableInfoResponse, wcvResponse, "seo");

	    double BPvalue = elasticsearchService.getBPScore(applicationName, buildId);
	    powerPointService.createPresentationMetered(applicationName, buildId, BPvalue, bpFailCatResponse, getDataForTableInfoResponse, wcvResponse, "bp");

	    Resource file = new FileSystemResource("pptreports/" + applicationName + "_" + buildId + ".pptx");
	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + applicationName + "_" + buildId + ".pptx\"")
	            .body(file);
//	    return ResponseEntity.ok("hi");
	    
	}
	
//	@RequestMapping(value = "/getppt", method = RequestMethod.POST)
//	public ResponseEntity<Map<String, String>> getppt() 
//	{ 
//         String message = "Hello";
//
// 
//		 Map<String, String> messageMap = new HashMap<>();
//		 messageMap.put("message", message);
//		 return ResponseEntity.ok(messageMap);
//	}
	

    
}












