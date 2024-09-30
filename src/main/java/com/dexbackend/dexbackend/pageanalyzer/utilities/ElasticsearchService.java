package com.dexbackend.dexbackend.pageanalyzer.utilities;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ElasticsearchService {
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

	    public JSONObject getDataForTable(String applicationName, String buildId) {
	        String apiUrl = "http://localhost:9200/analyzeraccissues*/_search";
	        String requestBody = "{\"aggs\":{\"2\":{\"terms\":{\"field\":\"selector.keyword\",\"order\":{\"_count\":\"desc\"},\"size\":50},\"aggs\":{\"3\":{\"terms\":{\"field\":\"impact.keyword\",\"order\":{\"_count\":\"desc\"},\"size\":5},\"aggs\":{\"4\":{\"terms\":{\"field\":\"wcagRule.keyword\",\"order\":{\"_count\":\"desc\"},\"size\":5},\"aggs\":{\"5\":{\"terms\":{\"field\":\"explanation.keyword\",\"order\":{\"_count\":\"desc\"},\"size\":500}}}}}}}},\"size\":0,\"fields\":[{\"field\":\"fetchTime\",\"format\":\"date_time\"}],\"script_fields\":{},\"stored_fields\":[\"*\"],\"runtime_mappings\":{},\"_source\":{\"excludes\":[]},\"query\":{\"bool\":{\"must\":[],\"filter\":[{\"match_phrase\":{\"applicationName.keyword\":\"" + applicationName + "\"}},{\"match_phrase\":{\"buildId.keyword\":\"" + buildId + "\"}},{\"range\":{\"fetchTime\":{\"format\":\"strict_date_optional_time\",\"gte\":\"" + getGteDateTime() + "\",\"lte\":\"" + getCurrentDateTime() + "\"}}}],\"should\":[],\"must_not\":[]}}}";


	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

	        return new JSONObject(response.getBody());
	    }


	    //Accessibility

	    public double getAccessibilityScore(String applicationName, String buildId) {
	        String apiUrl = "http://localhost:9200/analyzeraudits*/_search";
	        String requestBody = "{ \"aggs\": { \"1\": { \"avg\": { \"field\": \"accessibilityScore\", \"script\": { \"inline\": \"doc['accessibilityScore'].value * 100\", \"lang\": \"painless\" } } } }, \"size\": 0, \"fields\": [ { \"field\": \"fetchTime\", \"format\": \"date_time\" } ], \"script_fields\": {}, \"stored_fields\": [ \"*\" ], \"runtime_mappings\": {}, \"_source\": { \"excludes\": [] }, \"query\": { \"bool\": { \"must\": [], \"filter\": [ { \"match_phrase\": { \"applicationName.keyword\": \"" + applicationName + "\" } }, { \"match_phrase\": { \"buildId.keyword\": \"" + buildId + "\" } }, { \"range\": { \"fetchTime\": { \"format\": \"strict_date_optional_time\", \"gte\": \"" + getGteDateTime() + "\", \"lte\": \"" + getCurrentDateTime() + "\" } } } ], \"should\": [], \"must_not\": [] } } }";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
	        JSONObject jsonResponse = new JSONObject(response.getBody());
	        JSONObject aggregations = jsonResponse.getJSONObject("aggregations");
	        JSONObject valueAggregation = aggregations.getJSONObject("1");
	        double value = valueAggregation.getDouble("value");

	        return value;
	    }

	    public JSONObject getDataForSeverity(String applicationName, String buildId) {
	        String apiUrl = "http://localhost:9200/analyzeraccissues*/_search";
	        String requestBody = "{ \"aggs\": { \"2\": { \"terms\": { \"field\": \"impact.keyword\", \"order\": { \"_count\": \"desc\" }, \"size\": 5 } } }, \"size\": 0, \"fields\": [ { \"field\": \"fetchTime\", \"format\": \"date_time\" } ], \"script_fields\": {}, \"stored_fields\": [ \"*\" ], \"runtime_mappings\": {}, \"_source\": { \"excludes\": [] }, \"query\": { \"bool\": { \"must\": [], \"filter\": [ { \"match_phrase\": { \"applicationName.keyword\": \"" + applicationName + "\" } }, { \"match_phrase\": { \"buildId.keyword\": \"" + buildId + "\" } }, { \"range\": { \"fetchTime\": { \"format\": \"strict_date_optional_time\", \"gte\": \"" + getGteDateTime() + "\", \"lte\": \"" + getCurrentDateTime() + "\" } } } ], \"should\": [], \"must_not\": [] } } }";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

	        return new JSONObject(response.getBody());
	    }

	    public JSONObject getDataForTableInfo(String applicationName, String buildId) {
	        String apiUrl = "http://localhost:9200/analyzeraudits*/_search";
	        String requestBody = "{ \"aggs\": { \"0\": { \"terms\": { \"field\": \"requestedUrl.keyword\", \"order\": { \"2\": \"desc\" }, \"size\": 3 }, \"aggs\": { \"1\": { \"date_histogram\": { \"field\": \"fetchTime\", \"fixed_interval\": \"12h\", \"time_zone\": \"Asia/Calcutta\" }, \"aggs\": { \"2\": { \"cardinality\": { \"field\": \"pageName.keyword\" } } } }, \"2\": { \"cardinality\": { \"field\": \"pageName.keyword\" } } } } }, \"size\": 0, \"fields\": [ { \"field\": \"fetchTime\", \"format\": \"date_time\" } ], \"script_fields\": {}, \"stored_fields\": [ \"*\" ], \"runtime_mappings\": {}, \"_source\": { \"excludes\": [] }, \"query\": { \"bool\": { \"must\": [], \"filter\": [ { \"match_phrase\": { \"applicationName.keyword\": \"" + applicationName + "\" } }, { \"match_phrase\": { \"buildId.keyword\": \"" + buildId + "\" } }, { \"range\": { \"fetchTime\": { \"format\": \"strict_date_optional_time\", \"gte\": \"" + getGteDateTime() + "\", \"lte\": \"" + getCurrentDateTime() + "\" } } } ], \"should\": [], \"must_not\": [] } } }";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

	        return new JSONObject(response.getBody());
	    }
	    
	    //Performance
	    
	    public double getPerformanceScore(String applicationName, String buildId) {
	        String apiUrl = "http://localhost:9200/analyzeraudits*/_search";
	        String requestBody = "{ \"aggs\": { \"1\": { \"avg\": { \"field\": \"performanceScore\", \"script\": { \"inline\": \"doc['performanceScore'].value * 100\", \"lang\": \"painless\" } } } }, \"size\": 0, \"fields\": [ { \"field\": \"fetchTime\", \"format\": \"date_time\" } ], \"script_fields\": {}, \"stored_fields\": [ \"*\" ], \"runtime_mappings\": {}, \"_source\": { \"excludes\": [] }, \"query\": { \"bool\": { \"must\": [], \"filter\": [ { \"match_phrase\": { \"applicationName.keyword\": \"" + applicationName + "\" } }, { \"match_phrase\": { \"buildId.keyword\": \"" + buildId + "\" } }, { \"range\": { \"fetchTime\": { \"format\": \"strict_date_optional_time\", \"gte\": \"" + getGteDateTime() + "\", \"lte\": \"" + getCurrentDateTime() + "\" } } } ], \"should\": [], \"must_not\": [] } } }";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
	        JSONObject jsonResponse = new JSONObject(response.getBody());
	        JSONObject aggregations = jsonResponse.getJSONObject("aggregations");
	        JSONObject valueAggregation = aggregations.getJSONObject("1");
	        double value = valueAggregation.getDouble("value");

	        return value;
	    }
	    
	    public JSONObject getPerformanceMimePie(String applicationName, String buildId) {
	        String apiUrl = "http://localhost:9200/analyzerperfnetworkrequest*/_search";
	        String requestBody = "{ \"aggs\": { \"2\": { \"terms\": { \"field\": \"mimeType.keyword\", \"order\": { \"_count\": \"desc\" }, \"size\": 20 } } }, \"size\": 0, \"fields\": [ { \"field\": \"fetchTime\", \"format\": \"date_time\" } ], \"script_fields\": { \"RequestTime\": { \"script\": { \"source\": \"doc['endTime'].value - doc['startTime'].value\", \"lang\": \"painless\" } } }, \"stored_fields\": [ \"*\" ], \"runtime_mappings\": {}, \"_source\": { \"excludes\": [] }, \"query\": { \"bool\": { \"must\": [], \"filter\": [ { \"match_phrase\": { \"applicationName.keyword\": \"" + applicationName + "\" } }, { \"match_phrase\": { \"buildId.keyword\": \"" + buildId + "\" } }, { \"range\": { \"fetchTime\": { \"format\": \"strict_date_optional_time\", \"gte\": \"" + getGteDateTime() + "\", \"lte\": \"" + getCurrentDateTime() + "\" } } } ], \"should\": [], \"must_not\": [ { \"match_phrase\": { \"mimeType.keyword\": \"\" } } ] } } }";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

	        return new JSONObject(response.getBody());
	    }

	    public JSONObject getPerformanceWCV(String applicationName, String buildId) {
	        String apiUrl = "http://localhost:9200/analyzerperfmetrics/_search";
	        String requestBody = "{ \"aggs\": { \"2\": { \"terms\": { \"field\": \"title.keyword\", \"order\": { \"1\": \"desc\" }, \"size\": 6 }, \"aggs\": { \"1\": { \"avg\": { \"field\": \"numericValue\", \"script\": { \"inline\": \"doc['numericValue'].value / 1000\", \"lang\": \"painless\" } } } } } }, \"size\": 0, \"fields\": [ { \"field\": \"fetchTime\", \"format\": \"date_time\" } ], \"script_fields\": {}, \"stored_fields\": [ \"*\" ], \"runtime_mappings\": {}, \"_source\": { \"excludes\": [] }, \"query\": { \"bool\": { \"must\": [], \"filter\": [ { \"match_phrase\": { \"applicationName.keyword\": \"" + applicationName + "\" } }, { \"match_phrase\": { \"buildId.keyword\": \"" + buildId + "\" } }, { \"bool\": { \"minimum_should_match\": 1, \"should\": [ { \"match_phrase\": { \"title.keyword\": \"Speed Index\" } }, { \"match_phrase\": { \"title.keyword\": \"First Contentful Paint\" } }, { \"match_phrase\": { \"title.keyword\": \"First Meaningful Paint\" } }, { \"match_phrase\": { \"title.keyword\": \"Largest Contentful Paint\" } }, { \"match_phrase\": { \"title.keyword\": \"Total Blocking Time\" } }, { \"match_phrase\": { \"title.keyword\": \"Cummulative Layout Shift\" } } ] } }, { \"range\": { \"fetchTime\": { \"format\": \"strict_date_optional_time\", \"gte\": \"" + getGteDateTime() + "\", \"lte\": \"" + getCurrentDateTime() + "\" } } } ], \"should\": [], \"must_not\": [] } } }";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

	        return new JSONObject(response.getBody());
	    }
	    //SEO
	    
	    public double getSEOScore(String applicationName, String buildId) {
	        String apiUrl = "http://localhost:9200/analyzeraudits*/_search";
	        String requestBody = "{ \"aggs\": { \"1\": { \"avg\": { \"field\": \"seoscore\", \"script\": { \"inline\": \"doc['seoscore'].value * 100\", \"lang\": \"painless\" } } } }, \"size\": 0, \"fields\": [ { \"field\": \"fetchTime\", \"format\": \"date_time\" } ], \"script_fields\": {}, \"stored_fields\": [ \"*\" ], \"runtime_mappings\": {}, \"_source\": { \"excludes\": [] }, \"query\": { \"bool\": { \"must\": [], \"filter\": [ { \"match_phrase\": { \"applicationName.keyword\": \"" + applicationName + "\" } }, { \"match_phrase\": { \"buildId.keyword\": \"" + buildId + "\" } }, { \"range\": { \"fetchTime\": { \"format\": \"strict_date_optional_time\", \"gte\": \"" + getGteDateTime() + "\", \"lte\": \"" + getCurrentDateTime() + "\" } } } ], \"should\": [], \"must_not\": [] } } }";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
	        JSONObject jsonResponse = new JSONObject(response.getBody());
	        JSONObject aggregations = jsonResponse.getJSONObject("aggregations");
	        JSONObject valueAggregation = aggregations.getJSONObject("1");
	        double value = valueAggregation.getDouble("value");

	        return value;
	    }
	    

	    public JSONObject getSEOFailCat(String applicationName, String buildId) {
	        String apiUrl = "http://localhost:9200/analyzerseofailaudits*/_search";
	        String requestBody = "{ \"aggs\": { \"2\": { \"terms\": { \"field\": \"group.keyword\", \"order\": { \"_count\": \"desc\" }, \"size\": 5 } } }, \"size\": 0, \"fields\": [ { \"field\": \"fetchTime\", \"format\": \"date_time\" } ], \"script_fields\": {}, \"stored_fields\": [ \"*\" ], \"runtime_mappings\": {}, \"_source\": { \"excludes\": [] }, \"query\": { \"bool\": { \"must\": [], \"filter\": [ { \"match_phrase\": { \"applicationName.keyword\": \"" + applicationName + "\" } }, { \"match_phrase\": { \"buildId.keyword\": \"" + buildId + "\" } }, { \"range\": { \"fetchTime\": { \"format\": \"strict_date_optional_time\", \"gte\": \"" + getGteDateTime() + "\", \"lte\": \"" + getCurrentDateTime() + "\" } } } ], \"should\": [], \"must_not\": [] } } }";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

	        return new JSONObject(response.getBody());
	    }
	    
	    //Best Practices
	    
	    public double getBPScore(String applicationName, String buildId) {
	        String apiUrl = "http://localhost:9200/analyzeraudits*/_search";
	        String requestBody = "{ \"aggs\": { \"1\": { \"avg\": { \"field\": \"bestpracticesScore\", \"script\": { \"inline\": \"doc['bestpracticesScore'].value * 100\", \"lang\": \"painless\" } } } }, \"size\": 0, \"fields\": [ { \"field\": \"fetchTime\", \"format\": \"date_time\" } ], \"script_fields\": {}, \"stored_fields\": [ \"*\" ], \"runtime_mappings\": {}, \"_source\": { \"excludes\": [] }, \"query\": { \"bool\": { \"must\": [], \"filter\": [ { \"match_phrase\": { \"applicationName.keyword\": \"" + applicationName + "\" } }, { \"match_phrase\": { \"buildId.keyword\": \"" + buildId + "\" } }, { \"range\": { \"fetchTime\": { \"format\": \"strict_date_optional_time\", \"gte\": \"" + getGteDateTime() + "\", \"lte\": \"" + getCurrentDateTime() + "\" } } } ], \"should\": [], \"must_not\": [] } } }";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
	        JSONObject jsonResponse = new JSONObject(response.getBody());
	        JSONObject aggregations = jsonResponse.getJSONObject("aggregations");
	        JSONObject valueAggregation = aggregations.getJSONObject("1");
	        double value = valueAggregation.getDouble("value");

	        return value;
	    }
	    
	    public JSONObject getBPFailCat(String applicationName, String buildId) {
	        String apiUrl = "http://localhost:9200/analyzerbpfailaudits/_search";
	        String requestBody = "{ \"aggs\": { \"2\": { \"terms\": { \"field\": \"group.keyword\", \"order\": { \"_count\": \"desc\" }, \"size\": 5 } } }, \"size\": 0, \"fields\": [ { \"field\": \"fetchTime\", \"format\": \"date_time\" } ], \"script_fields\": {}, \"stored_fields\": [ \"*\" ], \"runtime_mappings\": {}, \"_source\": { \"excludes\": [] }, \"query\": { \"bool\": { \"must\": [], \"filter\": [ { \"match_phrase\": { \"applicationName.keyword\": \"" + applicationName + "\" } }, { \"match_phrase\": { \"buildId.keyword\": \"" + buildId + "\" } }, { \"range\": { \"fetchTime\": { \"format\": \"strict_date_optional_time\", \"gte\": \"" + getGteDateTime() + "\", \"lte\": \"" + getCurrentDateTime() + "\" } } } ], \"should\": [], \"must_not\": [] } } }";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

	        return new JSONObject(response.getBody());
	    }
	    
	}
