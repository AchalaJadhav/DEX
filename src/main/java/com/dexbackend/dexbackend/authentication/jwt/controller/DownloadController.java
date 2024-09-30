package com.dexbackend.dexbackend.authentication.jwt.controller;



import org.apache.xmlbeans.XmlException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dexbackend.dexbackend.authentication.jwt.jwtconfig.CustomJwt;
import com.dexbackend.dexbackend.pageanalyzer.utilities.PowerPointService;
import com.dexbackend.dexbackend.pageanalyzer.utilities.PrometheusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins ="*")
public class DownloadController {

	@Autowired
	private PowerPointService powerPointService;
	@Autowired
	private PrometheusService prometheusService;
	
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
	@CrossOrigin(origins = "*")
	public ResponseEntity<Resource> downloadPpt(@RequestBody Map<String, String> requestBody) throws IOException, XmlException {

	    
		String applicationName = requestBody.get("applicationName");
	    String buildId = requestBody.get("buildId");
	    JSONObject response = prometheusService.getDataForSeverityPrometheus(applicationName, buildId);
	    JSONObject mimeResponse = prometheusService.getPerformanceMimePiePrometheus(applicationName, buildId);
	    JSONObject wcvResponse = prometheusService.getPerformanceWCVPrometheus(applicationName, buildId);

	    JSONObject bpFailCatResponse = prometheusService.getBPFailCatPrometheus(applicationName, buildId);
	    JSONObject bpSEOCatResponse = prometheusService.getSEOFailCatPrometheus(applicationName, buildId);
	    JSONObject getDataForTableInfoResponse = prometheusService.getDataForTableInfoPrometheus(applicationName, buildId);

	    double accessibilityvalue = prometheusService.getAuditScoreFromPrometheus(applicationName, buildId, "accessibilityScore");
	    powerPointService.createPresentationMetered(applicationName, buildId, accessibilityvalue, response, getDataForTableInfoResponse, wcvResponse, "accessibility");

	    double performancevalue = prometheusService.getAuditScoreFromPrometheus(applicationName, buildId, "performanceScore");
	    powerPointService.createPresentationMetered(applicationName, buildId, performancevalue, mimeResponse, getDataForTableInfoResponse, wcvResponse, "performance");

	    double SEOvalue = prometheusService.getAuditScoreFromPrometheus(applicationName, buildId, "seoscore");
	    powerPointService.createPresentationMetered(applicationName, buildId, SEOvalue, bpSEOCatResponse, getDataForTableInfoResponse, wcvResponse, "seo");

	    double BPvalue = prometheusService.getAuditScoreFromPrometheus(applicationName, buildId, "bestpracticesScore");
	    powerPointService.createPresentationMetered(applicationName, buildId, BPvalue, bpFailCatResponse, getDataForTableInfoResponse, wcvResponse, "bp");
	    
	    Resource file = new FileSystemResource("pptreports/" + applicationName + "_" + buildId + ".pptx");
	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + applicationName + "_" + buildId + ".pptx\"")
	            .body(file);
	    
	}
}












