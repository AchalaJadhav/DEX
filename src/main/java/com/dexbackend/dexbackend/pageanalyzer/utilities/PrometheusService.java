package com.dexbackend.dexbackend.pageanalyzer.utilities;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class PrometheusService {
	
	   @Value("${prometheusApiUrl}")
	   private String prometheusApiUrl;
	
	   public String getCurrentDateTime() {
	        // Get current date and time in the specified format
	        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	        return now.format(formatter);
	    }

	    public String getGteDateTime() {
	        // Get current date and time minus 30 days in the specified format
	        LocalDateTime gteDateTime = LocalDateTime.now(ZoneOffset.UTC).minusDays(30);
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	        return gteDateTime.format(formatter);
	    }

	    public JSONObject getDataForSeverityPrometheus(String applicationName, String buildId) {

	        RestTemplate restTemplate = new RestTemplate();
	        String apiUrl = prometheusApiUrl.concat("/api/v1/query");
	        
	        String query = "count by (impact) (increase(analyzeraccissues{applicationName=\"" + applicationName + "\", buildId=\"" + buildId + "\"}[1h]))";
	        
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	        String requestBody = "query=" + query;

	        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

	        return new JSONObject(response.getBody());
	    }
	    
	    public JSONObject getPerformanceMimePiePrometheus(String applicationName, String buildId) {

	        RestTemplate restTemplate = new RestTemplate();
	        String apiUrl = prometheusApiUrl.concat("/api/v1/query");
	        
	        // PromQL query: Sum requests by mimeType over time for the specified application and build
	        String query = "count by (mimeType) (rate(analyzerperfnetworkrequest{applicationName=\"" + applicationName + "\", buildId=\"" + buildId + "\"}[1h]))";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	        // Prepare request body with the PromQL query
	        String requestBody = "query=" + query;

	        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

	        return new JSONObject(response.getBody());
	    }
	    
	    public JSONObject getPerformanceWCVPrometheus(String applicationName, String buildId) {

	        RestTemplate restTemplate = new RestTemplate();
	        String apiUrl = prometheusApiUrl.concat("/api/v1/query");
	        
	     // Prometheus query: Fetch Web Core Vitals (Speed Index, FCP, LCP, etc.) with average over time
	     	String query = "count by (title) (count_over_time(analyzerperfmetrics{applicationName=\"" + applicationName + "\", buildId=\"" + buildId + "\", title=~\"Speed Index|First Contentful Paint|First Meaningful Paint|Largest Contentful Paint|Total Blocking Time\"}[1h]))";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	        // Prepare request body with the PromQL query
	        String requestBody = "query=" + query;

	        // Create a request entity with headers and body
	        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

	        // Send POST request to Prometheus
	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

	        // Convert the response to JSON object
	        return new JSONObject(response.getBody());
	    }
	    
	    //SEO
	    
	    public JSONObject getSEOFailCatPrometheus(String applicationName, String buildId) {

	        RestTemplate restTemplate = new RestTemplate();
	        String apiUrl = prometheusApiUrl.concat("/api/v1/query");
	        
	        // PromQL query to sum the SEO failure categories by 'group' for the given application and build
	        String query = "count by (group) (rate(analyzerseofailaudits{applicationName=\"" + applicationName + "\", buildId=\"" + buildId + "\"}[1h]))";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	        
	        // Prepare the request body with the PromQL query
	        String requestBody = "query=" + query;

	        // Create a request entity with the headers and body
	        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

	        // Send POST request to Prometheus
	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

	        // Return the response as a JSON object
	        return new JSONObject(response.getBody());
	    }
	    
	  //Best Practices
	    
	    public JSONObject getBPFailCatPrometheus(String applicationName, String buildId) {

	        RestTemplate restTemplate = new RestTemplate();
	        String apiUrl = prometheusApiUrl.concat("/api/v1/query");
	        
	        // Adjusted Prometheus query: Aggregate failures by group for the application and build within a time range
	        String query = "count by (group) (rate(analyzerbpfailaudits{applicationName=\"" + applicationName + "\", buildId=\"" + buildId + "\"}[1h]))";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	        // Prepare request body with the PromQL query
	        String requestBody = "query=" + query;

	        // Create a request entity with headers and body
	        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

	        // Send POST request to Prometheus
	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

	        // Convert the response to JSON object
	        return new JSONObject(response.getBody());
	    }
	    
	    public JSONObject getDataForTableInfoPrometheus(String applicationName, String buildId) {

	        RestTemplate restTemplate = new RestTemplate();
	        String apiUrl = prometheusApiUrl.concat("/api/v1/query");

	        // PromQL query to get the top 3 requested URLs and count distinct page names
	        String queryRequestedUrls = "topk(3, sum by (requestedUrl) (rate(analyzeraudits{applicationName=\"" + applicationName + "\", buildId=\"" + buildId + "\"}[12h])))";
	        String queryPageNameCardinality = "count by (pageName) (rate(analyzeraudits{applicationName=\"" + applicationName + "\", buildId=\"" + buildId + "\"}[12h]))";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	        // Request body for both queries
	        String requestBody = "query=" + queryRequestedUrls + "&query=" + queryPageNameCardinality;

	        // Create a request entity with the headers and body
	        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

	        // Send POST request to Prometheus
	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

	        // Return the response as a JSON object
	        return new JSONObject(response.getBody());
	    }

	    
	    public double getAuditScoreFromPrometheus(String applicationName, String buildId, String auditType) {
	    	RestTemplate restTemplate = new RestTemplate();
	    	double score = 0;
	    	String apiUrl = prometheusApiUrl.concat("/api/v1/query");
	        String query = "topk(1, avg_over_time((analyzeraudits{applicationName=\"" + applicationName + "\", buildId=\"" + buildId + "\"} * 100)[48h:]))";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	        String requestBody = "query=" + query;

	        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

	        JSONObject jsonResponse = new JSONObject(response.getBody());
	        JSONArray resultsArray = jsonResponse.getJSONObject("data").getJSONArray("result");
			if (resultsArray.length() > 0) {
				JSONObject metricObject = resultsArray.getJSONObject(0).getJSONObject("metric");
				score = Double.parseDouble(metricObject.getString(auditType)) * 100;
			}
	        return score;
	    }

	    
	    
	}
