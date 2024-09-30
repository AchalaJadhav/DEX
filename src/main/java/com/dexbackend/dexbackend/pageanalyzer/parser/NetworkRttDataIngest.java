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

import com.dexbackend.dexbackend.pageanalyzer.model.networkrtt.NetworkRtt;
import com.dexbackend.dexbackend.pageanalyzer.model.networkrtt.NetworkRttDetails;
import com.dexbackend.dexbackend.pageanalyzer.model.networkrtt.NetworkRttItems;
import com.dexbackend.dexbackend.pageanalyzer.utilities.PrometheusUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.prometheus.client.Gauge;


/**
 * The Class NetworkRttDataIngest.
 *
 * @author mukundz
 */
@Qualifier("NetworkRttDataIngest")
@Component
public class NetworkRttDataIngest implements DataAudits {

	/** The Constant ANALYZERPERFNETWORKRTT. */
	private static final String ANALYZERPERFNETWORKRTT = "analyzerperfnetworkrtt";

	/** The Constant ORIGIN. */
	private static final String ORIGIN = "origin";

	/** The Constant KEY. */
	private static final String KEY = "key";

	/** The Constant ITEMS2. */
	private static final String ITEMS2 = "items";

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

	/** The Constant RTT. */
	private static final String RTT = "rtt";

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

	/** The Constant NETWORK_RTT. */
	private static final String NETWORK_RTT = "network-rtt";

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
	private static Logger LOGGER=LoggerFactory.getLogger(NetworkRttDataIngest.class); 
	
	private static final Gauge analyzerPerfNetworkrttGauge = Gauge.build().name(ANALYZERPERFNETWORKRTT).help("exported from JSON data.")
			.labelNames(prometheusUtil.getDeclaredFields(NetworkRttItems.class))
			.register();
	
	public static Gauge getAnalyzerPerfNetworkrttGauge() {
        return analyzerPerfNetworkrttGauge;
    }
	
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
			final String applicationName, final String userId) throws Exception {
		
		try {
			Thread.sleep(10000);
		
			NetworkRtt networkRtt = new NetworkRtt();
			NetworkRttDetails networkRttDetails = new NetworkRttDetails();
			ObjectMapper objectMapper = new ObjectMapper();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(System.getProperty(USER_DIR));
			stringBuilder.append(REPORTS);
			stringBuilder.append(fileName);
			JsonNode node = objectMapper
					.readTree(Paths.get(stringBuilder.toString()).toFile());
			networkRtt.setRequestedUrl(node.path(REQUESTED_URL).asText());
			networkRtt.setFetchTime(node.path(FETCH_TIME).asText());
			node = node.get(AUDITS);
			node = node.get(NETWORK_RTT);
			networkRtt.setId(node.get(ID).asText());
			networkRtt.setTitle(node.get(TITLE).asText());
			networkRtt.setDescription(node.get(DESCRIPTION).asText());
			if (node.get(SCORE).asText().equals(NULL)) {
				networkRtt.setScore(null);
			} else {
				networkRtt.setScore(node.get(SCORE).asText());
			}
			networkRtt.setScoreDisplayMode(node.get(SCORE_DISPLAY_MODE).asText());
			networkRtt.setNumericValue(node.get(NUMERIC_VALUE).asText());
			networkRtt.setNumericUnit(node.get(NUMERIC_UNIT).asText());
			networkRtt.setDisplayValue(node.get(DISPLAY_VALUE).asText());
	
			node = node.get(DETAILS);
			networkRttDetails.setType(node.get(TYPE).asText());
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
				ArrayList<Map<Object, Object>> networkRtttHeadingItemsMap = new ArrayList<Map<Object, Object>>();
				for (int i = 0; i < items.size(); ++i) {
					Map<Object, Object> headingItems = new HashedMap();
					JsonNode objects = items.get(i);
					for (String head : keysHeading) {
						headingItems.put(head, objects.get(head));
					}
					networkRtttHeadingItemsMap.add(headingItems);
	
				}
				networkRttDetails.setNetworkRttHeadingItemsMap(networkRtttHeadingItemsMap);
			}
			networkRtt.setNetworkRttDetails(networkRttDetails);
	
			ArrayList<Map<Object, Object>> sm = networkRtt.getNetworkRttDetails().getNetworkRttHeadingItemsMap();
			ArrayList<NetworkRttItems> networkRttItemsList = new ArrayList<NetworkRttItems>();
	
			for (Map<Object, Object> map : sm) {
				NetworkRttItems networkRttItems = new NetworkRttItems();
				networkRttItems.setBuildId(buildID);
				networkRttItems.setPageName(pageTitle);
				networkRttItems.setApplicationName(applicationName);
				networkRttItems.setRequestedUrl(networkRtt.getRequestedUrl());
				networkRttItems.setFetchTime(networkRtt.getFetchTime());
				networkRttItems.setId(networkRtt.getId());
				networkRttItems.setTitle(networkRtt.getTitle());
				networkRttItems.setDescription(networkRtt.getDescription());
				networkRttItems.setScoreDisplayMode(networkRtt.getScoreDisplayMode());
				networkRttItems.setScore(networkRtt.getScore());
				networkRttItems.setDisplayValue(networkRtt.getDisplayValue());
				networkRttItems.setNumericUnit(networkRtt.getNumericUnit());
				networkRttItems.setNumericValue(networkRtt.getNumericValue());
	
				networkRttItems.setRtt(Double.parseDouble(isNull((JsonNode) map.get(RTT))));
				networkRttItems.setOrigin(isNull((JsonNode) map.get(ORIGIN)));
	
				networkRttItemsList.add(networkRttItems);
			}
			
			if (!networkRttItemsList.isEmpty()) {
				LOGGER.info("Started - Ingesting analyzerperfnetworkrtt to prometheus");
				for (NetworkRttItems networkRttItems : networkRttItemsList) {
					//prometheusUtil.ingestAnalyzerbpPassAudits(getAnalyzerBppassAuditGauge(), bestPracticesPassItem);
					prometheusUtil.ingestAuditsToPrometheus(getAnalyzerPerfNetworkrttGauge(), networkRttItems, prometheusUtil.getDeclaredFields(NetworkRttItems.class), userId);
				}
				LOGGER.info("Completed - Ingesting analyzerperfnetworkrtt to prometheus");
			}
			
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
	public void ingestGeneralData(String fileName, String buildID, String pageTitle, String applicationName, int scans,
			String userId) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
