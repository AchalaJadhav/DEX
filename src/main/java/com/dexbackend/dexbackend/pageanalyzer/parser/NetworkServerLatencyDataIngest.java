package com.dexbackend.dexbackend.pageanalyzer.parser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.dexbackend.dexbackend.pageanalyzer.networkserverlatency.NetworkRequestLatency;
import com.dexbackend.dexbackend.pageanalyzer.networkserverlatency.NetworkRequestLatencyDetails;
import com.dexbackend.dexbackend.pageanalyzer.networkserverlatency.NetworkRequestLatencyItems;
import com.dexbackend.dexbackend.pageanalyzer.utilities.ElasticSearchUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * The Class NetworkServerLatencyDataIngest.
 *
 * @author mukundz
 */
@Qualifier("NetworkServerLatencyDataIngest")
@Component
public class NetworkServerLatencyDataIngest implements DataAudits {

	/** The Constant SERVER_RESPONSE_TIME. */
	private static final String SERVER_RESPONSE_TIME = "serverResponseTime";

	/** The Constant ORIGIN. */
	private static final String ORIGIN = "origin";

	/** The Constant ANALYZERPERFNETWORKLATENCY. */
	private static final String ANALYZERPERFNETWORKLATENCY = "analyzerperfnetworklatency";

	/** The Constant ITEMS2. */
	private static final String ITEMS2 = "items";

	/** The Constant KEY. */
	private static final String KEY = "key";

	/** The Constant HEADINGS. */
	private static final String HEADINGS = "headings";

	/** The Constant TYPE. */
	private static final String TYPE = "type";

	/** The Constant DETAILS. */
	private static final String DETAILS = "details";

	/** The Constant DISPLAY_VALUE. */
	private static final String DISPLAY_VALUE = "displayValue";

	/** The Constant NUMERIC_UNIT. */
	private static final String NUMERIC_UNIT = "numericUnit";

	/** The Constant NUMERIC_VALUE. */
	private static final String NUMERIC_VALUE = "numericValue";

	/** The Constant SCORE_DISPLAY_MODE. */
	private static final String SCORE_DISPLAY_MODE = "scoreDisplayMode";

	/** The Constant SCORE. */
	private static final String SCORE = "score";

	/** The Constant NULL. */
	private static final String NULL = "null";

	/** The Constant DESCRIPTION. */
	private static final String DESCRIPTION = "description";

	/** The Constant TITLE. */
	private static final String TITLE = "title";

	/** The Constant ID. */
	private static final String ID = "id";

	/** The Constant NETWORK_SERVER_LATENCY. */
	private static final String NETWORK_SERVER_LATENCY = "network-server-latency";

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

	/** The elastic search utils. */
	@Autowired
	ElasticSearchUtils elasticSearchUtils;
	
	/** The logger. */
	private static Logger LOGGER=Logger.getLogger(NetworkServerLatencyDataIngest.class); 
	
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
		try {
			Thread.sleep(10000);
		
		NetworkRequestLatency networkRequestLatency = new NetworkRequestLatency();
		NetworkRequestLatencyDetails networkRequestLatencyDetails = new NetworkRequestLatencyDetails();
		ObjectMapper objectMapper = new ObjectMapper();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(System.getProperty(USER_DIR));
		stringBuilder.append(REPORTS);
		stringBuilder.append(fileName);
		JsonNode node = objectMapper
				.readTree(Paths.get(stringBuilder.toString()).toFile());
		networkRequestLatency.setRequestedUrl(node.path(REQUESTED_URL).asText());
		networkRequestLatency.setFetchTime(node.path(FETCH_TIME).asText());
		node = node.get(AUDITS);
		node = node.get(NETWORK_SERVER_LATENCY);
		networkRequestLatency.setId(node.get(ID).asText());
		networkRequestLatency.setTitle(node.get(TITLE).asText());
		networkRequestLatency.setDescription(node.get(DESCRIPTION).asText());
		if (node.get(SCORE).asText().equals(NULL)) {
			networkRequestLatency.setScore(null);
		} else {
			networkRequestLatency.setScore(node.get(SCORE).asText());
		}
		networkRequestLatency.setScoreDisplayMode(node.get(SCORE_DISPLAY_MODE).asText());
		networkRequestLatency.setNumericValue(node.get(NUMERIC_VALUE).asText());
		networkRequestLatency.setNumericUnit(node.get(NUMERIC_UNIT).asText());
		networkRequestLatency.setDisplayValue(node.get(DISPLAY_VALUE).asText());

		node = node.get(DETAILS);
		networkRequestLatencyDetails.setType(node.get(TYPE).asText());
		Map<String, String> uniqueHeadings = new HashedMap();
		{
			JsonNode heading = node.get(HEADINGS);
			for (int i = 0; i < heading.size(); ++i) {
				JsonNode objects = heading.get(i);
				uniqueHeadings.put(objects.get(KEY).asText(), null);
			}
		}
		{
			JsonNode items = node.get(ITEMS2);
			Set<String> keysHeading = uniqueHeadings.keySet();
			ArrayList<Map<Object, Object>> networkRequestLatencyHeadingItemsMap = new ArrayList<Map<Object, Object>>();
			for (int i = 0; i < items.size(); ++i) {
				Map<Object, Object> headingItems = new HashedMap();
				JsonNode objects = items.get(i);
				for (String head : keysHeading) {
					headingItems.put(head, objects.get(head));
				}
				networkRequestLatencyHeadingItemsMap.add(headingItems);

			}
			networkRequestLatencyDetails.setNetworkRequestLatencyHeadingItemsMap(networkRequestLatencyHeadingItemsMap);
		}
		networkRequestLatency.setNetworkRequestLatencyDetails(networkRequestLatencyDetails);

		ArrayList<Map<Object, Object>> sm = networkRequestLatency.getNetworkRequestLatencyDetails()
				.getNetworkRequestLatencyHeadingItemsMap();

		ArrayList<NetworkRequestLatencyItems> tm = new ArrayList<NetworkRequestLatencyItems>();

		for (Map<Object, Object> map : sm) {
			NetworkRequestLatencyItems networkRequestLatencyItems = new NetworkRequestLatencyItems();
			networkRequestLatencyItems.setBuildId(buildID);
			networkRequestLatencyItems.setPageName(pageTitle);
			networkRequestLatencyItems.setApplicationName(applicationName);
			networkRequestLatencyItems.setRequestedUrl(networkRequestLatency.getRequestedUrl());
			networkRequestLatencyItems.setFetchTime(networkRequestLatency.getFetchTime());
			networkRequestLatencyItems.setId(networkRequestLatency.getId());
			networkRequestLatencyItems.setTitle(networkRequestLatency.getTitle());
			networkRequestLatencyItems.setDescription(networkRequestLatency.getDescription());
			networkRequestLatencyItems.setScoreDisplayMode(networkRequestLatency.getScoreDisplayMode());
			networkRequestLatencyItems.setScore(networkRequestLatency.getScore());
			networkRequestLatencyItems.setDisplayValue(networkRequestLatency.getDisplayValue());
			networkRequestLatencyItems.setNumericUnit(networkRequestLatency.getNumericUnit());
			networkRequestLatencyItems.setNumericValue(networkRequestLatency.getNumericValue());

			networkRequestLatencyItems.setOrigin(isNull((JsonNode) map.get(ORIGIN)));
			networkRequestLatencyItems
					.setServerResponseTime(Double.parseDouble(isNull((JsonNode) map.get(SERVER_RESPONSE_TIME))));

			tm.add(networkRequestLatencyItems);
		}
		elasticSearchUtils.createIndex(ANALYZERPERFNETWORKLATENCY);
		elasticSearchUtils.ingestNetworkRequestLatencyItemsToIndex(ANALYZERPERFNETWORKLATENCY, tm);
		} catch (InterruptedException | IOException e) {
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

	@Override
	public void ingestGeneralData(String fileName, String buildID, String pageTitle, String applicationName, int scans)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
