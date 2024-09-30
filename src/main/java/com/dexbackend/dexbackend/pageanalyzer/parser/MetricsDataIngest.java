package com.dexbackend.dexbackend.pageanalyzer.parser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.dexbackend.dexbackend.pageanalyzer.model.metrics.MetricsDetails;
import com.dexbackend.dexbackend.pageanalyzer.utilities.ElasticSearchUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


/**
 * The Class MetricsDataIngest.
 */
@Qualifier("MetricsDataIngest")
@Component
public class MetricsDataIngest implements DataAudits {
	
	/** The Constant ANALYZERPERFOBSERVEDMETRICS. */
	private static final String ANALYZERPERFOBSERVEDMETRICS = "analyzerperfobservedmetrics";

	/** The Constant OBSERVED_TRACE_END_TS. */
	private static final String OBSERVED_TRACE_END_TS = "observedTraceEndTs";

	/** The Constant OBSERVED_TRACE_END. */
	private static final String OBSERVED_TRACE_END = "observedTraceEnd";

	/** The Constant OBSERVED_SPEED_INDEX_TS. */
	private static final String OBSERVED_SPEED_INDEX_TS = "observedSpeedIndexTs";

	/** The Constant OBSERVED_SPEED_INDEX. */
	private static final String OBSERVED_SPEED_INDEX = "observedSpeedIndex";

	/** The Constant OBSERVED_NAVIGATION_START_TS. */
	private static final String OBSERVED_NAVIGATION_START_TS = "observedNavigationStartTs";

	/** The Constant OBSERVED_NAVIGATION_START. */
	private static final String OBSERVED_NAVIGATION_START = "observedNavigationStart";

	/** The Constant OBSERVED_LOAD_TS. */
	private static final String OBSERVED_LOAD_TS = "observedLoadTs";

	/** The Constant OBSERVED_LOAD. */
	private static final String OBSERVED_LOAD = "observedLoad";

	/** The Constant OBSERVED_LAST_VISUAL_CHANGE_TS. */
	private static final String OBSERVED_LAST_VISUAL_CHANGE_TS = "observedLastVisualChangeTs";

	/** The Constant OBSERVED_LAST_VISUAL_CHANGE. */
	private static final String OBSERVED_LAST_VISUAL_CHANGE = "observedLastVisualChange";

	/** The Constant OBSERVED_LARGEST_CONTENTFUL_PAINT_TS. */
	private static final String OBSERVED_LARGEST_CONTENTFUL_PAINT_TS = "observedLargestContentfulPaintTs";

	/** The Constant OBSERVED_LARGEST_CONTENTFUL_PAINT. */
	private static final String OBSERVED_LARGEST_CONTENTFUL_PAINT = "observedLargestContentfulPaint";

	/** The Constant OBSERVED_FIRST_VISUAL_CHANGE_TS. */
	private static final String OBSERVED_FIRST_VISUAL_CHANGE_TS = "observedFirstVisualChangeTs";

	/** The Constant OBSERVED_FIRST_VISUAL_CHANGE. */
	private static final String OBSERVED_FIRST_VISUAL_CHANGE = "observedFirstVisualChange";

	/** The Constant OBSERVED_FIRST_PAINT_TS. */
	private static final String OBSERVED_FIRST_PAINT_TS = "observedFirstPaintTs";

	/** The Constant OBSERVED_FIRST_PAINT. */
	private static final String OBSERVED_FIRST_PAINT = "observedFirstPaint";

	/** The Constant OBSERVED_FIRST_MEANINGFUL_PAINT_TS. */
	private static final String OBSERVED_FIRST_MEANINGFUL_PAINT_TS = "observedFirstMeaningfulPaintTs";

	/** The Constant OBSERVED_FIRST_MEANINGFUL_PAINT. */
	private static final String OBSERVED_FIRST_MEANINGFUL_PAINT = "observedFirstMeaningfulPaint";

	/** The Constant OBSERVED_FIRST_CONTENTFUL_PAINT_TS. */
	private static final String OBSERVED_FIRST_CONTENTFUL_PAINT_TS = "observedFirstContentfulPaintTs";

	/** The Constant OBSERVED_FIRST_CONTENTFUL_PAINT. */
	private static final String OBSERVED_FIRST_CONTENTFUL_PAINT = "observedFirstContentfulPaint";

	/** The Constant OBSERVED_DOM_CONTENT_LOADED_TS. */
	private static final String OBSERVED_DOM_CONTENT_LOADED_TS = "observedDomContentLoadedTs";

	/** The Constant OBSERVED_DOM_CONTENT_LOADED. */
	private static final String OBSERVED_DOM_CONTENT_LOADED = "observedDomContentLoaded";

	/** The Constant OBSERVED_CUMULATIVE_LAYOUT_SHIFT. */
	private static final String OBSERVED_CUMULATIVE_LAYOUT_SHIFT = "observedCumulativeLayoutShift";

	/** The Constant ITEMS2. */
	private static final String ITEMS2 = "items";

	/** The Constant DETAILS. */
	private static final String DETAILS = "details";

	/** The Constant METRICS. */
	private static final String METRICS = "metrics";

	/** The Constant AUDITS. */
	private static final String AUDITS = "audits";

	/** The Constant REQUESTED_URL. */
	private static final String REQUESTED_URL = "requestedUrl";

	/** The Constant FETCH_TIME. */
	private static final String FETCH_TIME = "fetchTime";

	/** The Constant REPORTS. */
	private static final String REPORTS = "/reports/";

	/** The Constant USER_DIR. */
	private static final String USER_DIR = "user.dir";

	/** The elastic search utils. */
	@Autowired
	ElasticSearchUtils elasticSearchUtils;
	
	/** The logger. */
	private static Logger LOGGER=Logger.getLogger(DiagnosticsDataIngest.class);
	
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
		
			ObjectMapper objectMapper = new ObjectMapper();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(System.getProperty(USER_DIR));
			stringBuilder.append(REPORTS);
			stringBuilder.append(fileName);
			JsonNode node = objectMapper
					.readTree(Paths.get(stringBuilder.toString()).toFile());
			MetricsDetails metricsDetails = new MetricsDetails();
	
			metricsDetails.setBuildId(buildID);
			metricsDetails.setPageName(pageTitle);
			metricsDetails.setApplicationName(applicationName);
			metricsDetails.setFetchTime(node.get(FETCH_TIME).asText());
			metricsDetails.setRequestedUrl(node.get(REQUESTED_URL).asText());
			node = node.get(AUDITS);
			node = node.get(METRICS);
			ArrayList<MetricsDetails> metricsArrayList = new ArrayList<MetricsDetails>();
			node = node.get(DETAILS);
			JsonNode items = node.get(ITEMS2);
	
			metricsDetails.setObservedCumulativeLayoutShift(items.get(0).get(OBSERVED_CUMULATIVE_LAYOUT_SHIFT).asDouble());
			metricsDetails.setObservedDomContentLoaded(items.get(0).get(OBSERVED_DOM_CONTENT_LOADED).asDouble());
			metricsDetails.setObservedDomContentLoadedTs(items.get(0).get(OBSERVED_DOM_CONTENT_LOADED_TS).asDouble());
	
			metricsDetails.setObservedFirstContentfulPaint(items.get(0).get(OBSERVED_FIRST_CONTENTFUL_PAINT).asDouble());
	
			metricsDetails.setObservedFirstContentfulPaintTs(items.get(0).get(OBSERVED_FIRST_CONTENTFUL_PAINT_TS).asDouble());
	
			metricsDetails.setObservedFirstMeaningfulPaint(items.get(0).get(OBSERVED_FIRST_MEANINGFUL_PAINT).asDouble());
	
			metricsDetails.setObservedFirstMeaningfulPaintTs(items.get(0).get(OBSERVED_FIRST_MEANINGFUL_PAINT_TS).asDouble());
	
			metricsDetails.setObservedFirstPaint(items.get(0).get(OBSERVED_FIRST_PAINT).asDouble());
	
			metricsDetails.setObservedFirstPaintTs(items.get(0).get(OBSERVED_FIRST_PAINT_TS).asDouble());
	
			metricsDetails.setObservedFirstVisualChange(items.get(0).get(OBSERVED_FIRST_VISUAL_CHANGE).asDouble());
	
			metricsDetails.setObservedFirstVisualChangeTs(items.get(0).get(OBSERVED_FIRST_VISUAL_CHANGE_TS).asDouble());
	
			metricsDetails.setObservedLargestContentfulPaint(items.get(0).get(OBSERVED_LARGEST_CONTENTFUL_PAINT).asDouble());
	
			metricsDetails
					.setObservedLargestContentfulPaintTs(items.get(0).get(OBSERVED_LARGEST_CONTENTFUL_PAINT_TS).asDouble());
			metricsDetails.setObservedLastVisualChange(items.get(0).get(OBSERVED_LAST_VISUAL_CHANGE).asDouble());
	
			metricsDetails.setObservedLastVisualChangeTs(items.get(0).get(OBSERVED_LAST_VISUAL_CHANGE_TS).asDouble());
	
			metricsDetails.setObservedLoad(items.get(0).get(OBSERVED_LOAD).asDouble());
	
			metricsDetails.setObservedLoadTs(items.get(0).get(OBSERVED_LOAD_TS).asDouble());
	
			metricsDetails.setObservedNavigationStart(items.get(0).get(OBSERVED_NAVIGATION_START).asDouble());
	
			metricsDetails.setObservedNavigationStartTs(items.get(0).get(OBSERVED_NAVIGATION_START_TS).asDouble());
	
			metricsDetails.setObservedSpeedIndex(items.get(0).get(OBSERVED_SPEED_INDEX).asDouble());
	
			metricsDetails.setObservedSpeedIndexTs(items.get(0).get(OBSERVED_SPEED_INDEX_TS).asDouble());
	
			metricsDetails.setObservedTraceEnd(items.get(0).get(OBSERVED_TRACE_END).asDouble());
	
			metricsDetails.setObservedTraceEndTs(items.get(0).get(OBSERVED_TRACE_END_TS).asDouble());
	
			metricsArrayList.add(metricsDetails);
			elasticSearchUtils.createIndex(ANALYZERPERFOBSERVEDMETRICS);
//			for (MetricsDetails finalFormatForIngest : metricsArrayList)
//			{
//			elasticSearchUtils.ingestDataToIndex(ANALYZERPERFOBSERVEDMETRICS, new Gson().toJson(finalFormatForIngest));
//			}
			 elasticSearchUtils.ingestObservedMetricsDetailsToIndex(ANALYZERPERFOBSERVEDMETRICS, metricsArrayList);
		} catch (InterruptedException  |IOException e) {
			  LOGGER.error(e.getMessage(),e);
		}
	}

	@Override
	public void ingestGeneralData(String fileName, String buildID, String pageTitle, String applicationName, int scans)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
