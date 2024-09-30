/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.parser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.apache.http.HttpHost;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.dexbackend.dexbackend.pageanalyzer.parser.model.AnalyzerAudits;
import com.dexbackend.dexbackend.pageanalyzer.utilities.ElasticSearchUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import io.prometheus.client.Gauge;


/**
 * The Class AnalyzerGeneralDataIngest.
 *
 * @author surendrane
 */
@Qualifier("AnalyzerGeneralDataIngest")
@Component
public class AnalyzerGeneralDataIngest implements DataAudits {

	/** The Constant ANALYZERAUDITS. */
	private static final String ANALYZERAUDITS = "analyzeraudits";

	/** The Constant FETCH_TIME. */
	private static final String FETCH_TIME = "fetchTime";

	/** The Constant REQUESTED_URL. */
	private static final String REQUESTED_URL = "requestedUrl";

	/** The Constant BEST_PRACTICES. */
	private static final String BEST_PRACTICES = "best-practices";

	/** The Constant SEO. */
	private static final String SEO = "seo";

	/** The Constant PWA. */
	private static final String PWA = "pwa";

	/** The Constant ACCESSIBILITY. */
	private static final String ACCESSIBILITY = "accessibility";

	/** The Constant SCORE. */
	private static final String SCORE = "score";

	/** The Constant PERFORMANCE. */
	private static final String PERFORMANCE = "performance";

	/** The Constant CATEGORIES. */
	private static final String CATEGORIES = "categories";

	/** The Constant RUNTIME_ERROR. */
	private static final String RUNTIME_ERROR = "runtimeError";

	/** The Constant REPORTS. */
	private static final String REPORTS = "/reports/";

	/** The Constant USER_DIR. */
	private static final String USER_DIR = "user.dir";
	/** The host. */
	private String host = "localhost";

	/** The port. */
	private int PORT = 9200;

	/** The add port. */
	private final int ADD_PORT = 9201;

	/** The protocol. */
	private String PROTOCOL = "http";

	
	/** The elastic search utils. */
	@Autowired
	ElasticSearchUtils elasticSearchUtils;
	
	/** The logger. */
	private static Logger LOGGER=Logger.getLogger(AnalyzerGeneralDataIngest.class);

	/**
	 * Ingest data.
	 *
	 * @param fileName the file name
	 * @param buildID the build ID
	 * @param pageTitle the page title
	 * @param applicationName the application name
	 * @throws Exception the exception
	 */
	@Override
	@Async("asyncExecutor")
	public void ingestData(final String fileName, final String buildID, final String pageTitle,
			final String applicationName) throws Exception {
	
	}

//	@Override
//	public void ingestGeneralData(String fileName, String buildID, String pageTitle, String applicationName, int scans)
//			throws Exception {
//ObjectMapper objectMapper=new ObjectMapper();
//		
//		JsonNode node = null;
//		try {
//			Thread.sleep(10000);
//			objectMapper = new ObjectMapper();
//			StringBuilder stringBuilder = new StringBuilder();
//			stringBuilder.append(System.getProperty(USER_DIR));
//			stringBuilder.append(REPORTS);
//			stringBuilder.append(fileName);
//			node = objectMapper
//						.readTree(Paths.get(stringBuilder.toString()).toFile());
//			if (!node.has(RUNTIME_ERROR)) {
//	
//				AnalyzerAudits analyzerAudits = new AnalyzerAudits();
//				analyzerAudits.setPerformanceScore(node.path(CATEGORIES).get(PERFORMANCE).get(SCORE).asDouble());
//				analyzerAudits.setAccessibilityScore(node.path(CATEGORIES).get(ACCESSIBILITY).get(SCORE).asDouble());
//				analyzerAudits.setPwascore(node.path(CATEGORIES).get(PWA).get(SCORE).asDouble());
//				analyzerAudits.setBestpracticesScore(node.path(CATEGORIES).get(BEST_PRACTICES).get(SCORE).asDouble());
//				analyzerAudits.setSeoscore(node.path(CATEGORIES).get(SEO).get(SCORE).asDouble());
//
//				analyzerAudits.setRequestedUrl(node.path(REQUESTED_URL).asText());
//				analyzerAudits.setFetchTime(node.path(FETCH_TIME).asText());
//				analyzerAudits.setBuildId(buildID);
//				analyzerAudits.setPageName(pageTitle);
//				int scount = getScanCountFromElasticSearch() + scans;
//				analyzerAudits.setScans(scount);
//				analyzerAudits.setApplicationName(applicationName);
//				elasticSearchUtils.createIndex(ANALYZERAUDITS);
//				
//				elasticSearchUtils.ingestDataToIndex(ANALYZERAUDITS, new Gson().toJson(analyzerAudits));
//
//			}
//		} catch (InterruptedException e) {
//			  LOGGER.error(e.getMessage(),e);
//		} catch (IOException e) {
//			  LOGGER.error(e.getMessage(),e);
//		}
//		
//	}
	
	@SuppressWarnings("static-access")
	@Override
	public void ingestGeneralData(String fileName, String buildID, String pageTitle, String applicationName, int scans)
			throws Exception {
 
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode node = null;
		AnalyzerAudits analyzerAudits = new AnalyzerAudits();
		try {
			Thread.sleep(10000);
			objectMapper = new ObjectMapper();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(System.getProperty(USER_DIR));
			stringBuilder.append(REPORTS);
			stringBuilder.append(fileName);
			node = objectMapper.readTree(Paths.get(stringBuilder.toString()).toFile());
 
			if (!node.has(RUNTIME_ERROR)) {
				analyzerAudits.setPerformanceScore(node.path(CATEGORIES).get(PERFORMANCE).get(SCORE).asDouble());
				analyzerAudits.setAccessibilityScore(node.path(CATEGORIES).get(ACCESSIBILITY).get(SCORE).asDouble());
				analyzerAudits.setPwascore(node.path(CATEGORIES).get(PWA).get(SCORE).asDouble());
				analyzerAudits.setBestpracticesScore(node.path(CATEGORIES).get(BEST_PRACTICES).get(SCORE).asDouble());
				analyzerAudits.setSeoscore(node.path(CATEGORIES).get(SEO).get(SCORE).asDouble());
 
				analyzerAudits.setRequestedUrl(node.path(REQUESTED_URL).asText());
				analyzerAudits.setFetchTime(node.path(FETCH_TIME).asText());
				analyzerAudits.setBuildId(buildID);
				analyzerAudits.setPageName(pageTitle);
				int scount = getScanCountFromElasticSearch() + scans;
				//analyzerAudits.setScans(scount);
				analyzerAudits.setApplicationName(applicationName);
			}
 
			Gauge analyzerGauge = Gauge.build().name(ANALYZERAUDITS).help("exported from JSON data.")
					.labelNames("performanceScore", "accessibilityScore", "seoscore", "pwascore", "bestpracticesScore",
							"buildId", "requestedUrl", "fetchTime", "pageName", "applicationName")
					.register();
 
			analyzerGauge.labels(String.valueOf(analyzerAudits.getPerformanceScore()),
					String.valueOf(analyzerAudits.getAccessibilityScore()),
					String.valueOf(analyzerAudits.getSeoscore()), String.valueOf(analyzerAudits.getPwascore()),
					String.valueOf(analyzerAudits.getBestpracticesScore()), String.valueOf(analyzerAudits.getBuildId()),
					analyzerAudits.getRequestedUrl(), analyzerAudits.getFetchTime(), analyzerAudits.getPageName(),
					analyzerAudits.getApplicationName()).set(1);
 
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	public int getScanCountFromElasticSearch() {
        RestHighLevelClient client = new RestHighLevelClient(
        		RestClient.builder(new HttpHost(host, PORT, PROTOCOL), new HttpHost(host, ADD_PORT, PROTOCOL)));
        int outputString = 0;
        try {
            SearchRequest searchRequest = new SearchRequest();
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            //("analyzeraudits.scans")
            //("versionControlData.timeOfCommit")).sort("versionControlData.timeOfCommit",SortOrder.DESC);
            searchSourceBuilder.query(QueryBuilders.matchAllQuery().queryName("scans")).sort("scans",SortOrder.DESC);
            searchRequest.indices("analyzeraudits");
            searchRequest.source(searchSourceBuilder);

 

            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            Iterator<SearchHit> itr = searchResponse.getHits().iterator();
            
            while(itr.hasNext())
            {
                SearchHit searchHit = itr.next();
                Map<String,Object> map = searchHit.getSourceAsMap();
                @SuppressWarnings("unchecked")
               int versionControlData =  (Integer) map.get("scans");
                System.out.println("scans: "+ versionControlData);
                outputString = versionControlData;
                break;
            }
        } catch (Exception e) {
        } finally {
            try {
                client.close();
            } catch (IOException e) {
            }
        }
        return outputString;
    }

}
