package com.dexbackend.dexbackend.pageanalyzer.parser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.dexbackend.dexbackend.pageanalyzer.model.networkrequests.NetworkRequesDetails;
import com.dexbackend.dexbackend.pageanalyzer.model.networkrequests.NetworkRequest;
import com.dexbackend.dexbackend.pageanalyzer.model.networkrequests.NetworkRequestItems;
import com.dexbackend.dexbackend.pageanalyzer.utilities.PrometheusUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.prometheus.client.Gauge;


/**
 * The Class NetworkRequestDataIngest.
 *
 * @author mukundz
 */
@Qualifier("NetworkRequestDataIngest")
@Component
public class NetworkRequestDataIngest implements DataAudits {

	/** The Constant ANALYZERPERFNETWORKREQUEST. */
	private static final String ANALYZERPERFNETWORKREQUEST = "analyzerperfnetworkrequest";

	/** The Constant END_TIME. */
	private static final String END_TIME = "endTime";

	/** The Constant MIME_TYPE. */
	private static final String MIME_TYPE = "mimeType";

	/** The Constant RESOURCE_TYPE. */
	private static final String RESOURCE_TYPE = "resourceType";

	/** The Constant RESOURCE_SIZE. */
	private static final String RESOURCE_SIZE = "resourceSize";

	/** The Constant START_TIME. */
	private static final String START_TIME = "startTime";

	/** The Constant STATUS_CODE. */
	private static final String STATUS_CODE = "statusCode";

	/** The Constant TRANSFER_SIZE. */
	private static final String TRANSFER_SIZE = "transferSize";

	/** The Constant URL. */
	private static final String URL = "url";

	/** The Constant ITEMS2. */
	private static final String ITEMS2 = "items";

	/** The Constant FINISHED. */
	private static final String FINISHED = "finished";

	/** The Constant KEY. */
	private static final String KEY = "key";

	/** The Constant HEADINGS. */
	private static final String HEADINGS = "headings";

	/** The Constant TYPE. */
	private static final String TYPE = "type";

	/** The Constant DETAILS. */
	private static final String DETAILS = "details";

	/** The Constant SCORE_DISPLAY_MODE. */
	private static final String SCORE_DISPLAY_MODE = "scoreDisplayMode";

	/** The Constant NULL. */
	private static final String NULL = "null";

	/** The Constant SCORE. */
	private static final String SCORE = "score";

	/** The Constant DESCRIPTION. */
	private static final String DESCRIPTION = "description";

	/** The Constant TITLE. */
	private static final String TITLE = "title";

	/** The Constant ID. */
	private static final String ID = "id";

	/** The Constant NETWORK_REQUESTS. */
	private static final String NETWORK_REQUESTS = "network-requests";

	/** The Constant AUDITS. */
	private static final String AUDITS = "audits";

	/** The Constant FETCH_TIME. */
	private static final String FETCH_TIME = "fetchTime";

	/** The Constant REQUESTED_URL. */
	private static final String REQUESTED_URL = "requestedUrl";

	/** The Constant REPORTS. */
	private static final String REPORTS = "/reports/";

	/** The Constant USER_DIR. */
	private static final String USER_DIR = "user.dir";

	@Autowired
	static PrometheusUtil prometheusUtil = new PrometheusUtil();
	
	/** The logger. */
	private static Logger LOGGER=LoggerFactory.getLogger(NetworkRequestDataIngest.class); 
	
	private static final Gauge analyzerPerfNetworkRequestGauge = Gauge.build().name(ANALYZERPERFNETWORKREQUEST).help("exported from JSON data.")
			.labelNames(prometheusUtil.getDeclaredFields(NetworkRequestItems.class))
			.register();
	
	public static Gauge getAnalyzerPerfNetworkRequestGauge() {
        return analyzerPerfNetworkRequestGauge;
    }
	
	@Override
	@Async("asyncExecutor")
	public void ingestData(final String fileName, final String buildID, final String pageTitle,
			final String applicationName, final String userId) throws Exception {
		try {
			Thread.sleep(10000);
		
			NetworkRequest networkRequest = new NetworkRequest();
			NetworkRequesDetails networkRequesDetails = new NetworkRequesDetails();
			ObjectMapper objectMapper = new ObjectMapper();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(System.getProperty(USER_DIR));
			stringBuilder.append(REPORTS);
			stringBuilder.append(fileName);
			JsonNode node = objectMapper
					.readTree(Paths.get(stringBuilder.toString()).toFile());
			networkRequest.setRequestedUrl(node.path(REQUESTED_URL).asText());
			networkRequest.setFetchTime(node.path(FETCH_TIME).asText());
			node = node.get(AUDITS);
			node = node.get(NETWORK_REQUESTS);
			networkRequest.setId(node.get(ID).asText());
			networkRequest.setTitle(node.get(TITLE).asText());
			networkRequest.setDescription(node.get(DESCRIPTION).asText());
	
			if (node.get(SCORE).asText().equals(NULL)) {
				networkRequest.setScore(null);
			} else {
				networkRequest.setScore(node.get(SCORE).asText());
			}
			networkRequest.setScoreDisplayMode(node.get(SCORE_DISPLAY_MODE).asText());
	
			node = node.get(DETAILS);
			networkRequesDetails.setType(isNull((JsonNode) node.get(TYPE)));
			Map<String, String> uniqueHeadings = new HashedMap();
			{
				JsonNode heading = node.get(HEADINGS);
				for (int i = 0; i < heading.size(); ++i) {
					JsonNode objects = heading.get(i);
					uniqueHeadings.put(objects.get(KEY).asText(), null);
				}
				uniqueHeadings.put(FINISHED, null);
			}
			{
				JsonNode items = node.get(ITEMS2);
				Set<String> keysHeading = uniqueHeadings.keySet();
				ArrayList<Map<Object, Object>> networkRequestHeadingItemsMap = new ArrayList<Map<Object, Object>>();
				for (int i = 0; i < items.size(); ++i) {
					Map<Object, Object> headingItems = new HashedMap();
					JsonNode objects = items.get(i);
					for (String head : keysHeading) {
						headingItems.put(head, objects.get(head));
					}
					networkRequestHeadingItemsMap.add(headingItems);
	
				}
				networkRequesDetails.setNetworkRequestHeadingItemsMap(networkRequestHeadingItemsMap);
			}
			networkRequest.setNetworkRequesDetails(networkRequesDetails);
	
			ArrayList<Map<Object, Object>> sm = networkRequest.getNetworkRequesDetails().getNetworkRequestHeadingItemsMap();
			ArrayList<NetworkRequestItems> networkRequestItemsList = new ArrayList<NetworkRequestItems>();
	
			for (Map<Object, Object> map : sm) {
				NetworkRequestItems networkRequestItems = new NetworkRequestItems();
				networkRequestItems.setBuildId(buildID);
				networkRequestItems.setPageName(pageTitle);
				networkRequestItems.setApplicationName(applicationName);
				networkRequestItems.setRequestedUrl(networkRequest.getRequestedUrl());
				networkRequestItems.setFetchTime(networkRequest.getFetchTime());
				networkRequestItems.setId(networkRequest.getId());
				networkRequestItems.setTitle(networkRequest.getTitle());
				networkRequestItems.setDescription(networkRequest.getDescription());
				networkRequestItems.setScoreDisplayMode(networkRequest.getScoreDisplayMode());
				networkRequestItems.setScore(networkRequest.getScore());
	
				networkRequestItems.setUrl(isNull((JsonNode) map.get(URL)));
				networkRequestItems.setTransferSize(Integer.parseInt(isNull((JsonNode) map.get(TRANSFER_SIZE))));
				networkRequestItems.setStatusCode(Integer.parseInt(isNull((JsonNode) map.get(STATUS_CODE))));
				networkRequestItems.setStartTime(ParseDouble(isNull((JsonNode) map.get(START_TIME))));
				networkRequestItems.setResourceSize(Integer.parseInt(isNull((JsonNode) map.get(RESOURCE_SIZE))));
				networkRequestItems.setResourceType(isNull((JsonNode) map.get(RESOURCE_TYPE)));
				networkRequestItems.setMimeType(isNull((JsonNode) map.get(MIME_TYPE)));
				networkRequestItems.setFinished(Boolean.parseBoolean(isNull((JsonNode) map.get(FINISHED))));
				networkRequestItems.setEndTime(ParseDouble(isNull((JsonNode) map.get(END_TIME))));
	
				networkRequestItemsList.add(networkRequestItems);
			}
	
			if (!networkRequestItemsList.isEmpty()) {
				LOGGER.info("Started - Ingesting analyzerperfnetworkrequest to prometheus");
				for (NetworkRequestItems networkRequestItems : networkRequestItemsList) {
					prometheusUtil.ingestAuditsToPrometheus(getAnalyzerPerfNetworkRequestGauge(), networkRequestItems, prometheusUtil.getDeclaredFields(NetworkRequestItems.class), userId);
				}
				LOGGER.info("Completed - Ingesting analyzerperfnetworkrequest to prometheus");
			}
		
		} catch (InterruptedException  | IOException e) {
			  LOGGER.error(e.getMessage(),e);
		}
	}
	
		/**
		 * Checks if is null.
		 *
		 * @param obj the obj
		 * @return the string
		 */
		private static String isNull(JsonNode obj) {
			if (obj == null || obj.toString().isEmpty()) {
				return "";
			} else {
				return obj.asText();
			}
	
		}
		
		/**
		 * Parses the double.
		 *
		 * @param strNumber the str number
		 * @return the double
		 */
		double ParseDouble(String strNumber) {
			   if (strNumber != null && strNumber.length() > 0) {
			       try {
			          return Double.parseDouble(strNumber);
			       } catch(Exception e) {
			          return -1;   
			       }
			   }
			   else return 0;  
	}

		@Override
		public void ingestGeneralData(String fileName, String buildID, String pageTitle, String applicationName,
				int scans, String userId) throws Exception {
			// TODO Auto-generated method stub
			
		}

}
