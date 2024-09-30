/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.parser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.dexbackend.dexbackend.pageanalyzer.model.performance.OpportunityItems;
import com.dexbackend.dexbackend.pageanalyzer.model.performance.PerformanceCategories;
import com.dexbackend.dexbackend.pageanalyzer.model.performance.PerformanceItemDetails;
import com.dexbackend.dexbackend.pageanalyzer.model.performance.PerformanceMiscellaneous;
import com.dexbackend.dexbackend.pageanalyzer.model.performance.PerformancePassedAudits;
import com.dexbackend.dexbackend.pageanalyzer.parser.model.Categories;
import com.dexbackend.dexbackend.pageanalyzer.utilities.PrometheusUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.prometheus.client.Gauge;


/**
 * The Class PerformanceDataIngest.
 *
 * @author surendrane
 */
@Qualifier("PerformanceDataIngest")
@Component
public class PerformanceDataIngest implements DataAudits {


	/** The Constant ANALYZERPERFPASSAUDITS. */
	private static final String ANALYZERPERFPASSAUDITS = "analyzerperfpassaudits";

	/** The Constant ANALYZERPERFMETRICS. */
	private static final String ANALYZERPERFMETRICS = "analyzerperfmetrics";

	private static final String ANALYZERPERFMISCELLANEOUS = "analyzerperfmiscellaneous";

	/** The Constant ANALYZERPERFOPPO. */
	private static final String ANALYZERPERFOPPO = "analyzerperfoppo";

	/** The Constant NUMERIC_VALUE. */
	private static final String NUMERIC_VALUE = "numericValue";

	/** The Constant DESCRIPTION. */
	private static final String DESCRIPTION = "description";

	/** The Constant FETCH_TIME. */
	private static final String FETCH_TIME = "fetchTime";

	/** The Constant REQUESTED_URL. */
	private static final String REQUESTED_URL = "requestedUrl";

	/** The Constant WASTED_BYTES. */
	private static final String WASTED_BYTES = "wastedBytes";

	/** The Constant TOTAL_BYTES. */
	private static final String TOTAL_BYTES = "totalBytes";

	/** The Constant URL. */
	private static final String URL = "url";

	/** The Constant ITEMS2. */
	private static final String ITEMS2 = "items";

	/** The Constant DETAILS. */
	private static final String DETAILS = "details";

	/** The Constant SCORE2. */
	private static final String SCORE2 = "score";

	/** The Constant TITLE2. */
	private static final String TITLE2 = "title";

	/** The Constant AUDITS. */
	private static final String AUDITS = "audits";

	/** The Constant AUDIT_REFS. */
	private static final String AUDIT_REFS = "auditRefs";

	/** The Constant PERFORMANCE. */
	private static final String PERFORMANCE = "performance";
	private static final String ITEMS = "items";

	private static final String S = "s";

	/** The Constant CATEGORIES2. */
	private static final String CATEGORIES2 = "categories";

	/** The Constant RUNTIME_ERROR. */
	private static final String RUNTIME_ERROR = "runtimeError";

	/** The Constant REPORTS. */
	private static final String REPORTS = "/reports/";

	/** The Constant USER_DIR. */
	private static final String USER_DIR = "user.dir";

	private static final String TYPE = "type";

	private static final String HEADINGS = "headings";

	private static final String KEY = "key";

	private static final String DISPLAYVALUE = "displayValue";

	private static final String NUMREQUESTS = "numRequests";

	private static final String NUMSCRIPTS = "numScripts";

	private static final String NUMSTYLESHEETS = "numStylesheets";

	private static final String NUMFONTS = "numFonts";

	private static final String NUMTASKS = "numTasks";

	private static final String NUMTASKS10 = "numTasksOver10ms";

	private static final String NUMTASKS25 = "numTasksOver25ms";

	private static final String NUMTASKS50 = "numTasksOver50ms";

	private static final String NUMTASKS100 = "numTasksOver100ms";

	private static final String NUMTASKS500 = "numTasksOver500ms";

	private static final String RTT = "rtt";

	private static final String THROUGHPUT = "throughput";

	private static final String MAXRTT = "maxRtt";

	private static final String MAXSERVERLATENCY = "maxServerLatency";

	private static final String TOTALBYTEWEIGHT = "totalByteWeight";

	private static final String TOTALTASKTIME = "totalTaskTime";

	private static final String MAINDOC = "mainDocumentTransferSize";

	private static final String PROTOCOL = "protocol";

	private static final String STARTTIME = "startTime";

	private static final String ENDTIME = "endTime";

	private static final String FINISHED = "finished";

	private static final String TRANSFERSIZE = "transferSize";

	private static final String RESOURCESIZE = "resourceSize";

	private static final String STATUSCODE = "statusCode";

	private static final String MIMETYPE = "mimeType";

	private static final String RESOURCETYPE = "resourceType";

	private static final String ORIGIN = "origin";

	private static final String DATA = "data";

	private static final String NODE = "node";

	private static final String NAME = "name";

	private static final String RESOURCEBYTES = "resourceBytes";

	private static final String SERVERRESPONCETIME = "serverResponseTime";

	private static final String DURATION = "duration";

	private static final String TOTALBYTES = "totalBytes";

	private static final String WASTEDMS = "wastedMs";

	private static final String WASTEDBYTES = "wastedBytes";

	private static final String REQUESTSTARTTIME = "requestStartTime";

	private static final String WASTEDPERCENT = "wastedPercent";

	private static final String IHID = "lhId";

	private static final String PATH = "path";

	private static final String SELECTOR = "selector";

	private static final String WASTEDWEBPBYTES = "wastedWebpBytes";

	private static final String RESPONSETIME = "responseTime";

	private static final String STATISTIC = "statistic";

	private static final String VALUE = "value";

	private static final String TOTAL = "total";

	private static final String SCRIPTING = "scripting";

	private static final String SCRIPTINGPARSECOMPILE = "scriptParseCompile";

	private static final String NODES = "nodes";

	private static final String REQUESTCOUNT = "requestCount";

	private static final String MAINTHREADTIME = "mainThreadTime";

	private static final String BLOCKINGTIME = "blockingTime";

	private static final String ENTITY = "entity";

	private static final String PRODUCT = "product";

	private static final String LABEL = "label";

	private static final String GROUPLABEL = "groupLabel";

	private static final String DEBUGDATA = "debugData";

	private static final String CACHE = "cacheLifetimeMs";

	private static final String TEXT = "text";

	private static final String OVERALLSAVINGSMS = "overallSavingsMs";

	private static final String SUBITEMS = "subItems";

	private static final String FAILUREREASON = "failureReason";

	private static final String ANIMATION = "animation";
	
	@Autowired
	static PrometheusUtil prometheusUtil = new PrometheusUtil();
	
	/** The logger. */
	private static Logger LOGGER=LoggerFactory.getLogger(PerformanceDataIngest.class);
	
	private static final Gauge analyzerPerfoppoGauge = Gauge.build().name(ANALYZERPERFOPPO).help("exported from JSON data.")
			.labelNames(prometheusUtil.getDeclaredFields(OpportunityItems.class))
			.register();
	
	private static final Gauge analyzerPerfMetricsGauge = Gauge.build().name(ANALYZERPERFMETRICS).help("exported from JSON data.")
			.labelNames(prometheusUtil.getDeclaredFields(PerformanceCategories.class))
			.register();
	
//	private static final Gauge analyzerPerfPassAuditsGauge = Gauge.build().name(ANALYZERPERFPASSAUDITS).help("exported from JSON data.")
//			.labelNames()
//			.register();
	
	public static Gauge getAnalyzerPerfoppoGauge() {
        return analyzerPerfoppoGauge;
    }
	
	public static Gauge getAnalyzerPerfMetricsGauge() {
        return analyzerPerfMetricsGauge;
    }
	
//	public static Gauge getAnalyzerPerfPassAuditsGauge() {
//        return analyzerPerfPassAuditsGauge;
//    }
	
	@Override
	@Async("asyncExecutor")
	public void ingestData(final String fileName, final String buildID, final String pageTitle,
			final String applicationName, final String userId) throws Exception  {
		try {
			Thread.sleep(10000);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		  StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(System.getProperty(USER_DIR));
		stringBuilder.append(REPORTS);
		stringBuilder.append(fileName);
		JsonNode node = objectMapper
		  .readTree(Paths.get(stringBuilder.toString()).toFile());
		
		JsonNode auditRefs =   node.path(CATEGORIES2).get(PERFORMANCE).get(AUDIT_REFS);
		auditRefs.forEach(e -> {
		     if (e.has("relevantAudits")) {
		         ObjectNode objNode = (ObjectNode) e;
		         objNode.remove("relevantAudits");
		     }
		 });
		// System.out.println("Start Performance Data Ingest ");
		if (!node.has(RUNTIME_ERROR)) {

			Categories[] performanceCategories = objectMapper
			.treeToValue(auditRefs, Categories[].class);
			List<OpportunityItems> opportunityItemsList = new ArrayList<OpportunityItems>();
			List<PerformancePassedAudits> performancePassedAuditsList = new ArrayList<PerformancePassedAudits>();
			List<PerformanceCategories> performanceCategoryList = new ArrayList<PerformanceCategories>();
			List<PerformanceMiscellaneous> performanceMiscellaneousList = new ArrayList<PerformanceMiscellaneous>();
			PerformanceItemDetails performanceItemDetails = new PerformanceItemDetails();

			//PerformanceMiscellaneous
			// List<PerformanceDiagnostics> performanceDiagnosticsList = new
			// ArrayList<PerformanceDiagnostics>();

			for (Categories categories : performanceCategories) {
				
				String id = categories.getId();

				JsonNode auditNode = node.get(AUDITS).get(id);
				String title = auditNode.get(TITLE2).asText();
				String weight = categories.getWeight();
				String type = isNull(auditNode.findPath(DETAILS).get(TYPE));
				
				JsonNode items = auditNode.findPath(DETAILS).get(ITEMS);
				
				ArrayNode headings = (ArrayNode) auditNode.findPath(DETAILS).get(HEADINGS);
				double score = auditNode.get(SCORE2).asDouble();
				
				//Web-core vital audits
				PerformanceCategories webCoreVitals = new PerformanceCategories();
				
				webCoreVitals.setRequestedUrl(node.path(REQUESTED_URL).asText());
				webCoreVitals.setFetchTime(node.path(FETCH_TIME).asText());
				webCoreVitals.setBuildId(buildID);
				webCoreVitals.setPageName(pageTitle);
				webCoreVitals.setApplicationName(applicationName);
				webCoreVitals.setDescription(auditNode.get(DESCRIPTION).asText());
				webCoreVitals.setTitle(title);
				webCoreVitals.setScore(score);
					if(score >= 1.0 )
					{
						webCoreVitals.setIsPass(true);
					}
					else {
						webCoreVitals.setIsPass(false);
					}
				
				
				Set<String> auditHeadings = new HashSet<String>();
				auditHeadings.add("first-contentful-paint");
				auditHeadings.add("largest-contentful-paint");
				auditHeadings.add("first-meaningful-paint");
				auditHeadings.add("speed-index");
				auditHeadings.add("total-blocking-time");
				auditHeadings.add("cumulative-layout-shift");
				
				
				for (String head : auditHeadings) {
				if(id.matches(head) || id.contains(head)) {
					if (auditNode.get(NUMERIC_VALUE) != null && !auditNode.get(NUMERIC_VALUE).toString().isEmpty()) {
						webCoreVitals.setNumericValue(auditNode.get(NUMERIC_VALUE).asLong());
						webCoreVitals.setDisplayValue(auditNode.get(DISPLAYVALUE).asText());
						}
					break;
				}
				}
								
					if(items != null && !items.isEmpty() && headings != null) {
					Map<String, String> uniqueHeadings = new HashedMap();
					
						for (int i = 0; i < headings.size(); ++i) {
							JsonNode objects = headings.get(i);
							uniqueHeadings.put(objects.get(KEY).asText(), null);
						}
						Set<String> keysHeading = uniqueHeadings.keySet();
						ArrayList<Map<Object, Object>> performanceHeadingKeyItemsMap = new ArrayList<Map<Object, Object>>();
						
							
							for (int i = 0; i < items.size(); ++i) {
								Map<Object, Object> headingItems = new HashedMap();
								JsonNode objects = items.get(i);
								for (String head : keysHeading) {
									 
									headingItems.put(head, objects.get(head));
								}
								
								performanceHeadingKeyItemsMap.add(headingItems);
				
							}
						performanceItemDetails.setPerformanceHeadingItemsMap(performanceHeadingKeyItemsMap); 
						ArrayList<Map<Object, Object>> sm = performanceHeadingKeyItemsMap;
						for (Map<Object, Object> map : sm ) {
							ObjectMapper mapper = new ObjectMapper();
							JsonNode nodedetails = (JsonNode) map.get(NODE);
							JsonNode childNode = mapper.convertValue(nodedetails, JsonNode.class);
							
							JsonNode debugData = (JsonNode) map.get(DEBUGDATA);
							JsonNode childNode1 = mapper.convertValue(debugData, JsonNode.class);

							JsonNode entity = (JsonNode) map.get(ENTITY);
							JsonNode childNode2 = mapper.convertValue(entity, JsonNode.class);
							
							
						if(type.contains("opportunity") ) {
							OpportunityItems opportunityItems = new OpportunityItems();

							opportunityItems.setRequestedUrl(node.path(REQUESTED_URL).asText());
							opportunityItems.setFetchTime(node.path(FETCH_TIME).asText());
							opportunityItems.setBuildId(buildID);
							opportunityItems.setPageName(pageTitle);
							opportunityItems.setApplicationName(applicationName);
							opportunityItems.setDescription(auditNode.get(DESCRIPTION).asText());
							opportunityItems.setTitle(title);
							
							opportunityItems.setDisplayValue(isNull(auditNode.get(DISPLAYVALUE)));
							
							opportunityItems.setNumericValue(isJsonNodeNull(auditNode.get(NUMERIC_VALUE)));
							
							opportunityItems.setType(auditNode.findPath(DETAILS).get("type").asText());
							opportunityItems.setUrl(isNull((JsonNode) map.get(URL)));
						
								opportunityItems.setTotalBytes(isJsonNodeNull((JsonNode) map.get(TOTALBYTES)));
							
								opportunityItems.setWastedBytes(isJsonNodeNull((JsonNode) map.get(WASTEDBYTES)));
							
							
								opportunityItems.setWastedMs(isJsonNodeNull((JsonNode) map.get(WASTEDMS)));
							
							
								opportunityItems.setResponseTime(isJsonNodeNull((JsonNode) map.get(RESPONSETIME)));
							
							if (map.get(WASTEDPERCENT) != null && !map.get(WASTEDPERCENT).toString().isEmpty()) {
								opportunityItems.setWastedPercent(((JsonNode) map.get(WASTEDPERCENT)).asDouble());
							}
							
							opportunityItems.setOverallSavingsMs(isJsonNodeNull((JsonNode) auditNode.findPath(DETAILS).get(OVERALLSAVINGSMS)));
							
							
							switch(id) {
							//Opportunity
							case "render-blocking-resources":{
								
								//opportunityItems.setWastedMs(isNull((JsonNode) map.get(WASTEDMS)));
								break;
							}
							

							
							case "offscreen-images":{
							
								opportunityItems.setRequestStartTime(isNull((JsonNode) map.get(REQUESTSTARTTIME)));
								opportunityItems.setLhId(isNull((JsonNode) childNode.get(IHID)));
								opportunityItems.setPath(isNull((JsonNode) childNode.get(PATH)));
								opportunityItems.setSelector(isNull((JsonNode) childNode.get(SELECTOR)));

								break;
							}
							
							case "modern-image-formats":{
								opportunityItems.setWastedWebpBytes(isNull((JsonNode) map.get(WASTEDWEBPBYTES)));
								
								opportunityItems.setLhId(isNull((JsonNode) childNode.get(IHID)));
								opportunityItems.setPath(isNull((JsonNode) childNode.get(PATH)));
								opportunityItems.setSelector(isNull((JsonNode) childNode.get(SELECTOR)));

								break;
							}

							
							case "server-response-time":{
								opportunityItems.setUrl(isNull((JsonNode) map.get(URL)));
								//opportunityItems.setResponseTime(isNull((JsonNode) map.get(RESPONSETIME)));
								break;
							}
							
							case "uses-http2":{
								
								opportunityItems.setProtocol(isNull((JsonNode) map.get(PROTOCOL)));
								break;
							}
						
							
										
				}
							opportunityItemsList.add(opportunityItems);		

						}
						else {
							PerformanceCategories performanceCategory = new PerformanceCategories();
							performanceCategory.setRequestedUrl(node.path(REQUESTED_URL).asText());
							performanceCategory.setFetchTime(node.path(FETCH_TIME).asText());
							performanceCategory.setBuildId(buildID);
							performanceCategory.setPageName(pageTitle);
							performanceCategory.setApplicationName(applicationName);
							performanceCategory.setDescription(auditNode.get(DESCRIPTION).asText());
							performanceCategory.setTitle(title);
							performanceCategory.setScore(score);
							if(score >= 1.0 )
							{
								performanceCategory.setIsPass(true);
							}
							else {
								performanceCategory.setIsPass(false);
							}
							if (!categories.getGroup().isEmpty() ) {
								String group = categories.getGroup().toUpperCase().replace("-", "_");
								performanceCategory.setGroup(changeGroupName(group));
							}
							
							if (type.contains("table") && categories.getGroup().isEmpty()) {
								performanceCategory.setGroup(changeGroupName(type));
							}
							
							// Long Fields
							
							performanceCategory.setTotalBytes(isJsonNodeNull((JsonNode) map.get(TOTALBYTES)));
							
							
							performanceCategory.setWastedBytes(isJsonNodeNull((JsonNode) map.get(WASTEDBYTES)));
						
							if (map.get(WASTEDPERCENT) != null && !map.get(WASTEDPERCENT).toString().isEmpty()) {
								performanceCategory.setWastedPercent(((JsonNode) map.get(WASTEDPERCENT)).asDouble());
							}
					
							performanceCategory.setNumericValue(isJsonNodeNull(auditNode.get(NUMERIC_VALUE)));
							
						
							performanceCategory.setDisplayValue(isNull(auditNode.get(DISPLAYVALUE)));
							
						
							performanceCategory.setWastedMs(isJsonNodeNull((JsonNode) map.get(WASTEDMS)));
						
						
							performanceCategory.setDuration(isJsonNodeNull((JsonNode) map.get(DURATION)));
						
						
							performanceCategory.setStartTime(isJsonNodeNull((JsonNode) map.get(STARTTIME)));
						
						
							performanceCategory.setServerResponseTime(isJsonNodeNull((JsonNode) map.get(SERVERRESPONCETIME)));
						
						
							performanceCategory.setRtt(isJsonNodeNull((JsonNode) map.get(RTT)));
						
						
							performanceCategory.setRequestCount(isJsonNodeNull((JsonNode) map.get(REQUESTCOUNT)));
						
						
							performanceCategory.setTransferSize(isJsonNodeNull((JsonNode) map.get(TRANSFERSIZE)));
						

							performanceCategory.setEndTime(isJsonNodeNull((JsonNode) map.get(ENDTIME)));
							
	
							performanceCategory.setResourceSize(isJsonNodeNull((JsonNode) map.get(RESOURCESIZE)));
							
	
							performanceCategory.setCacheLifetimeMs(isJsonNodeNull((JsonNode) map.get(CACHE)));
							
							
							performanceCategory.setTotal(isJsonNodeNull((JsonNode) map.get(TOTAL)));
							
	
							performanceCategory.setScripting(isJsonNodeNull((JsonNode) map.get(SCRIPTING)));
							
	
							performanceCategory.setScriptParseCompile(isJsonNodeNull((JsonNode) map.get(SCRIPTINGPARSECOMPILE)));
							
							
						
							performanceCategory.setValue(isJsonNodeNull((JsonNode) map.get(VALUE)));
							
	
							
							performanceCategory.setBlockingTime(isJsonNodeNull((JsonNode) map.get(BLOCKINGTIME)));
							
							
							if (map.get(SCORE2) != null && !map.get(SCORE2).toString().isEmpty()) {
								performanceCategory.setScore(((JsonNode) map.get(SCORE2)).asDouble());}
							
							performanceCategory.setUrl(isNull((JsonNode) map.get(URL)));



							switch (id) {
							//Hidden				
							case "first-meaningful-paint":{	
								break;
							}
							
							case "max-potential-fid":{
								break;
							}
							
							//items
							case "final-screenshot":{
								performanceCategory.setData(isNull((JsonNode) auditNode.findPath(DETAILS).get(DATA)));
								break;
							}
							
							case "screenshot-thumbnails":{
								performanceCategory.setOrigin(isNull((JsonNode) map.get(DATA)));
								break;
							}
							
							case "network-rtt":{
								performanceCategory.setOrigin(isNull((JsonNode) map.get(ORIGIN)));
								//performanceCategory.setRtt(isNull((JsonNode) map.get(RTT)));
								break;
							}
							
							case "script-treemap-data":{
								performanceCategory.setName(isNull((JsonNode) childNode.get(NAME)));
								performanceCategory.setResourceBytes(isNull((JsonNode) childNode.get(RESOURCEBYTES)));
								break;
							}
							
							case "main-thread-tasks":{
								//performanceCategory.setDuration(isNull((JsonNode) map.get(DURATION)));
								//performanceCategory.setStartTime(isNull((JsonNode) map.get(STARTTIME)));							
								break;
							}
							
							case "uses-long-cache-ttl":{
								//performanceCategory.setCacheLifetimeMs(isNull((JsonNode) map.get(CACHE)));
														
								break;
							}
							
							case "resource-summary":{
								performanceCategory.setLabel(isNull((JsonNode) map.get(LABEL)));
//								performanceCategory.setRequestCount(isNull((JsonNode) map.get(REQUESTCOUNT)));		
//								performanceCategory.setTransferSize(isNull((JsonNode) map.get(TRANSFERSIZE)));							

								break;
							}
								
							case "network-server-latency":{
								performanceCategory.setOrigin(isNull((JsonNode) map.get(ORIGIN)));
								//performanceCategory.setServerResponseTime(isNull((JsonNode) map.get(SERVERRESPONCETIME)));
								
								break;
							}
							
							case "dom-size":{
								performanceCategory.setStatistic(isNull((JsonNode) map.get(STATISTIC)));
//								performanceCategory.setValue(isNull((JsonNode) map.get(VALUE)));
								performanceCategory.setLhId(isNull((JsonNode) childNode.get(IHID)));
								performanceCategory.setPath(isNull((JsonNode) childNode.get(PATH)));
								performanceCategory.setSelector(isNull((JsonNode) childNode.get(SELECTOR)));
								break;
							}
					
							case "bootup-time":{
								
//								performanceCategory.setTotal(isNull((JsonNode) map.get(TOTAL)));
//								performanceCategory.setScripting(isNull((JsonNode) map.get(SCRIPTING)));
//								performanceCategory.setScriptParseCompile(isNull((JsonNode) map.get(SCRIPTINGPARSECOMPILE)));

								break;
							}
							
							case "mainthread-work-breakdown":{
								//performanceCategory.setDuration(isNull((JsonNode) map.get(DURATION)));
								performanceCategory.setGroupLabel(isNull((JsonNode) map.get(GROUPLABEL)));

								break;
							}
							
							case "font-display":{
								
								//performanceCategory.setWastedMs(isNull((JsonNode) map.get(WASTEDMS)));
								break;
							}
							
							case "largest-contentful-paint-element":{
								performanceCategory.setLhId(isNull((JsonNode) childNode.get(IHID)));
								performanceCategory.setPath(isNull((JsonNode) childNode.get(PATH)));
								performanceCategory.setSelector(isNull((JsonNode) childNode.get(SELECTOR)));

								break;
							}
							
							case "unsized-images":{
								performanceCategory.setLhId(isNull((JsonNode) childNode.get(IHID)));
								performanceCategory.setPath(isNull((JsonNode) childNode.get(PATH)));
								performanceCategory.setSelector(isNull((JsonNode) childNode.get(SELECTOR)));

								break;
							}
							
							case "non-composited-animations":{
								performanceCategory.setLhId(isNull((JsonNode) childNode.get(IHID)));
								performanceCategory.setPath(isNull((JsonNode) childNode.get(PATH)));
								performanceCategory.setSelector(isNull((JsonNode) childNode.get(SELECTOR)));
//								JsonNode subItems = (JsonNode) map.get(SUBITEMS);
//								JsonNode items2 = (JsonNode) subItems.get(ITEMS);
//								JsonNode childNode3 = mapper.convertValue(items2, JsonNode.class);
//								performanceCategory.setFailureReason(isNull((JsonNode) childNode3.get(FAILUREREASON)));
//								performanceCategory.setAnimation(isNull((JsonNode) childNode3.get(ANIMATION)));

								break;
							}
							
							case "third-party-facades":{
//								performanceCategory.setTransferSize(isNull((JsonNode) map.get(TRANSFERSIZE)));
								performanceCategory.setProduct(isNull((JsonNode) map.get(PRODUCT)));
								break;
							}
							
							
							case "third-party-summary":{
								performanceCategory.setMainThreadTime(isNull((JsonNode) map.get(MAINTHREADTIME)));
//								performanceCategory.setBlockingTime(isNull((JsonNode) map.get(BLOCKINGTIME)));
//								performanceCategory.setTransferSize(isNull((JsonNode) map.get(TRANSFERSIZE)));
								performanceCategory.setUrl(isNull((JsonNode) childNode2.get(URL)));
								performanceCategory.setText(isNull((JsonNode) childNode2.get(TEXT)));
								break;
							}
							
							
							case "layout-shift-elements":{
								performanceCategory.setLhId(isNull((JsonNode) childNode.get(IHID)));
								performanceCategory.setPath(isNull((JsonNode) childNode.get(PATH)));
								performanceCategory.setSelector(isNull((JsonNode) childNode.get(SELECTOR)));
//								if (map.get(SCORE2) != null && !map.get(SCORE2).toString().isEmpty()) {
//									performanceCategory.setScore(((JsonNode) map.get(SCORE2)).asDouble());}

								break;
							}
							
							case "long-tasks":{
//								performanceCategory.setUrl(isNull((JsonNode) map.get(URL)));
//								performanceCategory.setDuration(isNull((JsonNode) map.get(DURATION)));
//								performanceCategory.setStartTime(isNull((JsonNode) map.get(STARTTIME)));

								break;
							}										
							

							case "network-requests":{

								performanceCategory.setProtocol(isNull((JsonNode) map.get(PROTOCOL)));
//								performanceCategory.setStartTime(isNull((JsonNode) map.get(STARTTIME)));
//								performanceCategory.setEndTime(isNull((JsonNode) map.get(ENDTIME)));
								performanceCategory.setFinished(isNull((JsonNode) map.get(FINISHED)));
//								performanceCategory.setTransferSize(isNull((JsonNode) map.get(TRANSFERSIZE)));
//								performanceCategory.setResourceSize(isNull((JsonNode) map.get(RESOURCESIZE)));
								performanceCategory.setStatusCode(isNull((JsonNode) map.get(STATUSCODE)));
								performanceCategory.setMimeType(isNull((JsonNode) map.get(MIMETYPE)));
								performanceCategory.setResourceType(isNull((JsonNode) map.get(RESOURCETYPE)));
//								performanceCategoryList.add(performanceCategory);

								break;
							}
							
							case "diagnostics":{
								performanceCategory.setNumRequests(isNull((JsonNode) map.get(NUMREQUESTS)));
								performanceCategory.setNumScripts(isNull((JsonNode) map.get(NUMSCRIPTS)));
								performanceCategory.setNumStylesheets(isNull((JsonNode) map.get(NUMSTYLESHEETS)));
								performanceCategory.setNumFonts(isNull((JsonNode) map.get(NUMFONTS)));
								performanceCategory.setNumTasks(isNull((JsonNode) map.get(NUMTASKS)));
								performanceCategory.setNumTasksOver10ms(isNull((JsonNode) map.get(NUMTASKS10)));
								performanceCategory.setNumTasksOver25ms(isNull((JsonNode) map.get(NUMTASKS25)));
								performanceCategory.setNumTasksOver50ms(isNull((JsonNode) map.get(NUMTASKS50)));
								performanceCategory.setNumTasksOver100ms(isNull((JsonNode) map.get(NUMTASKS100)));
								performanceCategory.setNumTasksOver500ms(isNull((JsonNode) map.get(NUMTASKS500)));
								//performanceCategory.setRtt(isNull((JsonNode) map.get(RTT)));
								performanceCategory.setThroughput(isNull((JsonNode) map.get(THROUGHPUT)));
								performanceCategory.setMaxRtt(isNull((JsonNode) map.get(MAXRTT)));
								performanceCategory.setMaxServerLatency(isNull((JsonNode) map.get(MAXSERVERLATENCY)));
								performanceCategory.setTotalByteWeight(isNull((JsonNode) map.get(TOTALBYTEWEIGHT)));
								performanceCategory.setTotalTaskTime(isNull((JsonNode) map.get(TOTALTASKTIME)));
								performanceCategory.setMainDocumentTransferSize(isNull((JsonNode) map.get(MAINDOC)));
								break;
							}
												
							//Budget
							case "performance-budget":{
								break;}							
							
							}
							performanceCategoryList.add(performanceCategory);

						}
						}
						
						}				
					else {
						// passed audits
						performanceCategoryList.add(webCoreVitals);
					}
				}
				
				if (!opportunityItemsList.isEmpty()) {
					LOGGER.info("Started - Ingesting analyzerperfoppo to prometheus");
					for(OpportunityItems opportunityItems : opportunityItemsList) {
						//prometheusUtil.ingestAnalyzerPerfOppo(getAnalyzerPerfoppoGauge(), opportunityItems);
						prometheusUtil.ingestAuditsToPrometheus(getAnalyzerPerfoppoGauge(), opportunityItems, prometheusUtil.getDeclaredFields(OpportunityItems.class), userId);
					}
					LOGGER.info("Completed - Ingesting analyzerperfoppo to prometheus");
				}

				if (!performanceCategoryList.isEmpty()) {
					LOGGER.info("Started - Ingesting analyzerperfmetrics to prometheus");
					for(PerformanceCategories performanceCategory : performanceCategoryList) {
						//prometheusUtil.ingestAnalyzerPerfOppo(getAnalyzerPerfoppoGauge(), opportunityItems);
						prometheusUtil.ingestAuditsToPrometheus(getAnalyzerPerfMetricsGauge(), performanceCategory, prometheusUtil.getDeclaredFields(PerformanceCategories.class), userId);
					}
					LOGGER.info("Completed - Ingesting analyzerperfoppo to prometheus");
				}

//				if (!performancePassedAuditsList.isEmpty()) {
//					
//				}

			}
		}catch (InterruptedException | IOException e) {
			  LOGGER.error(e.getMessage(),e);
		}
	}
	
	private static String isNull(JsonNode obj) {
		if (obj == null || obj.toString().isEmpty()) {
			return "";
		} else {
			return obj.asText();
		}

	}
	
	private static long isJsonNodeNull(JsonNode obj) {
		if (obj == null || obj.toString().isEmpty()) {
			return -1;
		} else {
			return obj.asLong();
		}
	}
	
	private static int isNodeNullInt(JsonNode obj) {
		if (obj == null || obj.toString().isEmpty()) {
			return -1;
		} else {
			return obj.asInt();
		}
	}

	
	private static String changeGroupName(String group) {
		String groupNameAlter = "";
		if(group.equals("METRICS")) {
			String changed="Metrics";
			return group.replaceAll(group, changed);
		}
		else if (group.equals("HIDDEN") ) {
			String changed="Network";
			return group.replaceAll(group, changed);
		} 
		else if (group.equals("BUDGETS") ) {
			String changed="Budget";
			return group.replaceAll(group, changed);
		} 
		else if (group.equals("OPPORTUNITY") ) {
			String changed="Opportunity";
			return group.replaceAll(group, changed);
		} 
		else if (group.equals("table") ) {
			String changed="Diagnostics";
			return group.replaceAll(group, changed);
		} 
		return groupNameAlter;
	}

	@Override
	public void ingestGeneralData(String fileName, String buildID, String pageTitle, String applicationName, int scans,
			String userId) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
