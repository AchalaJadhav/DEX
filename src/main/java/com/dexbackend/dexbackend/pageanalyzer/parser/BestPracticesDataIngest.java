package com.dexbackend.dexbackend.pageanalyzer.parser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.dexbackend.dexbackend.pageanalyzer.model.bestpractices.BestPracticeItemDetails;
import com.dexbackend.dexbackend.pageanalyzer.model.bestpractices.BestPracticeItems;
import com.dexbackend.dexbackend.pageanalyzer.model.bestpractices.BestPracticesAllAudits;
import com.dexbackend.dexbackend.pageanalyzer.model.bestpractices.BestPracticesFailedAudits;
import com.dexbackend.dexbackend.pageanalyzer.model.bestpractices.BestPracticesPassedAudits;
import com.dexbackend.dexbackend.pageanalyzer.parser.model.Categories;
import com.dexbackend.dexbackend.pageanalyzer.utilities.PrometheusUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.prometheus.client.Gauge;

/**
 * The Class BestPracticesDataIngest.
 *
 * 
 */
@Qualifier("BestPracticesDataIngest")
@Service
public class BestPracticesDataIngest implements DataAudits {
	
	/** The Constant ANALYZERSEOFAILAUDITS. */
	private static final String BPFAILAUDITS = "analyzerbpfailaudits";

	/** The Constant ANALYZERSEOPASSAUDITS. */
	private static final String BPPASSAUDITS = "analyzerbppassaudits";
	
	/** The Constant ANALYZERSEOALLAUDITS. */
	private static final String BPALLAUDITS = "analyzerbpallaudits";

	/** The Constant REPORTS. */
	private static final String REPORTS = "/reports/";

	/** The Constant USER_DIR. */
	private static final String USER_DIR = "user.dir";

	private static final String RUNTIME_ERROR = "runtimeError";

	private static final String BestPractices = "best-practices";

	private static final String CATEGORIES3 = "categories";

	private static final String AUDIT_REFS = "auditRefs";

	private static final String AUDITS = "audits";

	private static final String TITLE3 = "title";

	private static final String SCORE3 = "score";
	
	/** The Constant REQUESTED_URL. */
	private static final String REQUESTED_URL = "requestedUrl";

	/** The Constant CANONICAL. */
	private static final String CANONICAL = "canonical";
	
	/** The Constant FETCH_TIME. */
	private static final String FETCH_TIME = "fetchTime";
	
	/** The Constant DESCRIPTION. */
	private static final String DESCRIPTION = "description";
	

	/** The Constant DESCRIPTION. */
	private static final String RESOLUTION = "resolution";
	
	/** The Constant SCORE. */
	private static final String SCORE = "weight";

	/** The Constant DETAILS. */
	private static final String DETAILS = "details";
	
	/** The Constant DISPLAY_VALUE. */
	private static final String DISPLAY_VALUE = "displayValue";

	/** The Constant ITEMS. */
	private static final String ITEMS = "items";
	
	/** The Constant highestSeverity. */
	private static final String HIGHTESTSEVERITY = "highestSeverity";
	
	//vulnCount
	/** The Constant vulnCount. */
	private static final String VULNCOUNT = "vulnCount";
	
	/** The Constant HEADINGS2. */
	private static final String HEADINGS2 = "headings";
	
	/** The Constant KEY. */
	private static final String KEY = "key";

	/** The Constant URL. */
	private static final String URL = "url";

	/** The Constant ITEM_TYPE. */
	private static final String ITEM_TYPE = "itemType";
	/** The Constant NODE2. */
	private static final String NODE2 = "node";
	/** The Constant TYPE. */
	private static final String TYPE = "type";
	/** The Constant TYPE for csp-xss severity */
	private static final String SEVERITY = "severity";
	/** The Constant for csp-xss DESCRIPTION. */
	private static final String DESCRIPTION2 = "description";
	/** The Constant for image-size-responsive url. */
	private static final String TS_URL = "url";
	/** The Constant for image-size-responsive displayedSize. */
	private static final String TS_DISPLAYEDSIZE = "displayedSize";
	/** The Constant for image-size-responsive expectedSize. */
	private static final String TS_EXPECTEDSIZE = "expectedSize";
	/** The Constant for errors-in-console description. */
	private static final String G_Description = "description";
	/** The Constant for errors-in-console url. */
	private static final String G_URL = "url";

	private static final String SOURCELOCATION = "sourceLocation";
	
	/** The Constant DETECTEDLIB. */
	private static final String DETECTEDLIB = "detectedLib";
	
	/** The Constant TEXT. */
	private static final String TEXT = "text";
	/** The Constant highestSeverity. */
	private static final String SCRIPTURL = "scriptUrl";

	private static final String SOURCEMAPURL = "sourceMapUrl";

	private static final String SUBITEMS = "subItems";

	private static final String ERROR = "error";
	/** The Constant for inspector-issues issuetype. */
	private static final String ISSUETYPE = "issueType";
	/** The Constant for no-unload-listeners source. */
	private static final String SOURCE = "source";
	/** The Constant for external-anchors-use-rel-noopener node. */
	private static final String NODE = "node";
	/** The Constant for external-anchors-use-rel-noopener path. */
	private static final String PATH = "path";

	private static final String IURL = "url";

	private static final String VALUE = "value";
	/** The Constant for image-aspect-ratio. */
	private static final String R_URL = "url";
	/** The Constant for image-aspect-ratio. */
	private static final String DISPLAYEDRATIO = "displayedAspectRatio";
	/** The Constant for image-aspect-ratio. */
	private static final String ACTUALRATIO = "actualAspectRatio";
	
	@Autowired
	static PrometheusUtil prometheusUtil = new PrometheusUtil();
	
	/** The logger. */
	private static Logger LOGGER=LoggerFactory.getLogger(BestPracticesDataIngest.class);
	
//	private static final Gauge analyzerBpPassAuditGauge = Gauge.build().name(BPPASSAUDITS)
//			.help("exported from JSON data.")
//			.labelNames("buildId", "requestedUrl", "fetchTime", "pageName",
//					"applicationName", "title", "description", "group")
//			.register();
	
	
//	private static final Gauge analyzerbpFailAuditGauge = Gauge.build().name(BPFAILAUDITS)
//	.help("exported from JSON data.")
//	.labelNames("url", "resolution", "hightestSeverity", "vulnCount", "dectectedLibText", "dectectedLibUrl",
//			"auditId", "scriptUrl", "error", "issueType", "noType", "path", "value", "severity", "description2",
//			"tsurl", "expextedSize", "displayedSize", "buildId", "requestedUrl", "fetchTime", "pageName",
//			"applicationName", "title", "description", "group", "score", "target", "summary", "cspseverity",
//			"cspdescription", "displayedAspectRatio", "actualAspectRatio", "gdescription", "gurl", "iurl",
//			"rurl", "noUrl")
//	.register();
	
	private static final Gauge analyzerBpPassAuditGauge = Gauge.build().name(BPPASSAUDITS)
			.help("exported from JSON data.")
			.labelNames(prometheusUtil.getDeclaredFields(BestPracticesPassedAudits.class))
			.register();

	private static final Gauge analyzerbpFailAuditGauge = Gauge.build().name(BPFAILAUDITS)
			.help("exported from JSON data.")
			.labelNames(prometheusUtil.getDeclaredFields(BestPracticeItems.class))
			.register();
	
	public static Gauge getAnalyzerBppassAuditGauge() {
        return analyzerBpPassAuditGauge;
    }
	
	public static Gauge getAnalyzerBpFailAuditGauge() {
        return analyzerbpFailAuditGauge;
    }
	
	@Override
	public void ingestData(String fileName, String buildID, String pageTitle, String applicationName, String userId) throws Exception {
		try {
			Thread.sleep(10000);

			ObjectMapper objectMapper = new ObjectMapper();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(System.getProperty(USER_DIR));
			stringBuilder.append(REPORTS);
			stringBuilder.append(fileName);
			JsonNode node = objectMapper.readTree(Paths.get(stringBuilder.toString()).toFile());
			if (!node.has(RUNTIME_ERROR)) {
				List<BestPracticesPassedAudits> bestPracticesPassedAuditLists = new ArrayList<BestPracticesPassedAudits>();
				List<BestPracticesAllAudits> bestPracticesAllAuditsLists = new ArrayList<BestPracticesAllAudits>();
				BestPracticeItemDetails bestPracticeItemDetails = new BestPracticeItemDetails();
				ArrayList<BestPracticeItems> bestPracticeFailedAuditsList = new ArrayList<BestPracticeItems>();

				Categories[] bestPracticesCategories = objectMapper
						.treeToValue(node.path(CATEGORIES3).get(BestPractices).get(AUDIT_REFS), Categories[].class);
				for (Categories categories : bestPracticesCategories) {
					if (!categories.getGroup().isEmpty()) {
						String group = categories.getGroup().toUpperCase().replace("-", "_");

						String id = categories.getId();
						String weight = categories.getWeight();
						JsonNode auditNode = node.get(AUDITS).get(id);
						JsonNode items = auditNode.findPath(DETAILS).get(ITEMS);
						String title = auditNode.get(TITLE3).asText();

						double score = auditNode.get(SCORE3).asDouble();
						if (score < 1.0 || auditNode.get(SCORE3) == null) {

							ArrayNode headings = (ArrayNode) auditNode.findPath(DETAILS).get(HEADINGS2);

							bestPracticeItemDetails.setType(node.findPath(DETAILS).get(TYPE).asText());
							Map<String, String> uniqueHeadings = new HashedMap();

							if (headings != null) {
								for (int i = 0; i < headings.size(); ++i) {
									JsonNode objects = headings.get(i);
									uniqueHeadings.put(objects.get(KEY).asText(), null);
								}

								Set<String> keysHeading = uniqueHeadings.keySet();
								ArrayList<Map<Object, Object>> bestPracticesHeadingKeyItemsMap = new ArrayList<Map<Object, Object>>();
								for (int i = 0; i < items.size(); ++i) {
									Map<Object, Object> headingItems = new HashedMap();
									JsonNode objects = items.get(i);
									for (String head : keysHeading) {

										headingItems.put(head, objects.get(head));
									}

									bestPracticesHeadingKeyItemsMap.add(headingItems);

								}
								bestPracticeItemDetails
										.setBestpracticesHeadingItemsMap(bestPracticesHeadingKeyItemsMap);

								BestPracticesFailedAudits bestPracticesFailedAudits = new BestPracticesFailedAudits();
								bestPracticesFailedAudits.setBestPracticeItemDetails(bestPracticeItemDetails);

								ArrayList<Map<Object, Object>> sm = bestPracticesFailedAudits
										.getBestPracticeItemDetails().getBestpracticesHeadingItemsMap();

								for (Map<Object, Object> map : sm) {
									ObjectMapper mapper = new ObjectMapper();
									JsonNode childNode = mapper.convertValue(map, JsonNode.class);
									BestPracticesFailedAudits bestPracticesFailedAudit = new BestPracticesFailedAudits();
									BestPracticeItems bestPracticeItems = new BestPracticeItems();
									bestPracticeItems.setAuditId(id);
									switch (id) {
									case "is-on-https": {
										bestPracticeItems.setResolution(isNull((JsonNode) map.get(RESOLUTION)));
										bestPracticeItems.setUrl(isNull((JsonNode) map.get(URL)));
										break;
									}
									case "external-anchors-use-rel-noopener": {
										// specific items
										bestPracticeItems
												.setPath(isNull((JsonNode) childNode.findPath(NODE).get(PATH)));

										break;
									}
									case "no-vulnerable-libraries": {
										bestPracticeItems
												.setHightestSeverity(isNull((JsonNode) map.get(HIGHTESTSEVERITY)));
										bestPracticeItems.setVulnCount(isNull((JsonNode) map.get(VULNCOUNT)));
										bestPracticeItems.setDectectedLibText(
												isNull((JsonNode) childNode.findPath(DETECTEDLIB).get(TEXT)));
										bestPracticeItems.setDectectedLibUrl(
												isNull((JsonNode) childNode.findPath(DETECTEDLIB).get(URL)));

										break;
									}

									case "csp-xss": {
										bestPracticeItems.setCSPSeverity(isNull((JsonNode) map.get(SEVERITY)));
										bestPracticeItems.setCSPDescription(isNull((JsonNode) map.get(DESCRIPTION2)));
										break;
									}

									case "image-size-responsive": {
										bestPracticeItems.setTSUrl(isNull((JsonNode) map.get(TS_URL)));
										bestPracticeItems
												.setDisplayedSize(isNull((JsonNode) map.get(TS_DISPLAYEDSIZE)));
										bestPracticeItems.setExpextedSize(isNull((JsonNode) map.get(TS_EXPECTEDSIZE)));

										break;
									}

									case "errors-in-console": {
										bestPracticeItems.setGDescription(isNull((JsonNode) map.get(G_Description)));
										bestPracticeItems.setGUrl(
												isNull((JsonNode) childNode.findPath(SOURCELOCATION).get(G_URL)));
										break;
									}

									// valid-source-maps
									case "valid-source-maps": {

										bestPracticeItems.setScriptUrl(isNull((JsonNode) map.get(SCRIPTURL)));
										bestPracticeItems.setSourceMapUrl(isNull((JsonNode) map.get(SOURCEMAPURL)));
										// specific items
										break;
									}

									case "inspector-issues": {
										bestPracticeItems.setIssueType(isNull((JsonNode) map.get(ISSUETYPE)));
										// specific items
										bestPracticeItems.setIUrl(isNull(
												(JsonNode) childNode.findPath(SUBITEMS).findPath(ITEMS).get(IURL)));
										break;
									}
									case "no-unload-listeners": {
										bestPracticeItems
												.setNoType(isNull((JsonNode) childNode.findPath(SOURCE).get(TYPE)));
										bestPracticeItems
												.setValue(isNull((JsonNode) childNode.findPath(SOURCE).get(VALUE)));
										bestPracticeItems
												.setNoUrl(isNull((JsonNode) childNode.findPath(SOURCE).get(URL)));

										break;
									}

									case "image-aspect-ratio": {
										bestPracticeItems.setRUrl(isNull((JsonNode) map.get(R_URL)));
										bestPracticeItems
												.setDisplayedAspectRatio(isNull((JsonNode) map.get(DISPLAYEDRATIO)));
										bestPracticeItems.setActualAspectRatio(isNull((JsonNode) map.get(ACTUALRATIO)));

										break;
									}

									default: {

									}
									}

									bestPracticeItems.setRequestedUrl(node.path(REQUESTED_URL).asText());
									bestPracticeItems.setFetchTime(node.path(FETCH_TIME).asText());
									bestPracticeItems.setBuildId(buildID);
									bestPracticeItems.setPageName(pageTitle);
									bestPracticeItems.setApplicationName(applicationName);
									bestPracticeItems.setDescription(auditNode.get(DESCRIPTION).asText());
									bestPracticeItems.setTitle(title);
									bestPracticeItems.setGroup(changeGroupName(group));
									bestPracticeItems.setScore(weight);

									if (auditNode.has(DISPLAY_VALUE)) {
										bestPracticeItems.setSummary(auditNode.get(DISPLAY_VALUE).asText());
									}

									bestPracticeFailedAuditsList.add(bestPracticeItems);
									// bestPracticesFailedAuditLists.add(bestPracticesFailedAudit);
								}
							}
						}

						else {
							BestPracticesPassedAudits bestPracticesPassedAudits = new BestPracticesPassedAudits();
							BestPracticesAllAudits bestPracticesAllAudits = new BestPracticesAllAudits();
							bestPracticesPassedAudits.setRequestedUrl(node.path(REQUESTED_URL).asText());
							bestPracticesPassedAudits.setFetchTime(node.path(FETCH_TIME).asText());
							bestPracticesPassedAudits.setBuildId(buildID);
							bestPracticesPassedAudits.setPageName(pageTitle);
							bestPracticesPassedAudits.setApplicationName(applicationName);
							bestPracticesPassedAudits.setDescription(auditNode.get(DESCRIPTION).asText());
							bestPracticesPassedAudits.setTitle(title);
							bestPracticesPassedAudits.setGroup(changeGroupName(group));
							bestPracticesPassedAuditLists.add(bestPracticesPassedAudits);
							bestPracticesAllAudits.setApplicationName(applicationName);
							bestPracticesAllAudits.setBuildId(buildID);
							bestPracticesAllAudits.setGroup(changeGroupName(group));
							bestPracticesAllAudits.setTitle(title);
							bestPracticesAllAudits.setPageName(pageTitle);
							bestPracticesAllAudits.setScore(weight);
							bestPracticesAllAudits.setDescription(auditNode.get(DESCRIPTION).asText());
							bestPracticesAllAuditsLists.add(bestPracticesAllAudits);
						}
					}

				}
				
				if (!bestPracticesPassedAuditLists.isEmpty()) {
					LOGGER.info("Started - Ingesting analyzerbppassaudits to prometheus");
					for (BestPracticesPassedAudits bestPracticesPassItem : bestPracticesPassedAuditLists) {
						//prometheusUtil.ingestAnalyzerbpPassAudits(getAnalyzerBppassAuditGauge(), bestPracticesPassItem);
						prometheusUtil.ingestAuditsToPrometheus(getAnalyzerBppassAuditGauge(), bestPracticesPassItem, prometheusUtil.getDeclaredFields(BestPracticesPassedAudits.class), userId);
					}
					LOGGER.info("Completed - Ingesting analyzerbppassaudits to prometheus");
				}
				
				if (!bestPracticeFailedAuditsList.isEmpty()) {
					LOGGER.info("Started - Ingesting analyzerbpfailaudits to prometheus");
					for (BestPracticeItems bestPracticeItem : bestPracticeFailedAuditsList) {
						//prometheusUtil.ingestAnalyzerbpFailAudits(getAnalyzerBpFailAuditGauge(), bestPracticeItem);
						prometheusUtil.ingestAuditsToPrometheus(getAnalyzerBpFailAuditGauge(), bestPracticeItem, prometheusUtil.getDeclaredFields(BestPracticeItems.class), userId);
					}
					LOGGER.info("Completed - Ingesting analyzerbpfailaudits to prometheus");
				}
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
	/**
	 * Changes the group names.
	 *
	 * @return the string
	 */
	private static String changeGroupName(String group) {
		String groupNameAlter = "";
		if (group.contains("BEST_PRACTICES") ) {
			
			if(group.equals("BEST_PRACTICES_UX")) {
					String changed="UX";
					return group.replaceAll(group, changed);
			}
			else if (group.equals("BEST_PRACTICES_TRUST_SAFETY") ) {
					String changed="Trust and Safety";
					return group.replaceAll(group, changed);
				} 
			else
			{
			//String after _
			groupNameAlter = group.substring(15, group.length());
			//Replace _ with space and all string to lowercase
			groupNameAlter = groupNameAlter.replaceAll("_", " ").toLowerCase();
			//First char to caps
			groupNameAlter=Character.toUpperCase(groupNameAlter.charAt(0))+groupNameAlter.substring(1,groupNameAlter.length());
			//Iterating to find space
			for(int i =0;i<groupNameAlter.length();i++)
			{
				if(groupNameAlter.charAt(i)==' ')
					//If space exists, caps the next char
					groupNameAlter=groupNameAlter.substring(0,i+1)+Character.toUpperCase(groupNameAlter.charAt(i+1))+groupNameAlter.substring(i+2,groupNameAlter.length());
				}
			}
		}
		return groupNameAlter;
	}

	@Override
	public void ingestGeneralData(String fileName, String buildID, String pageTitle, String applicationName, int scans,
			String userId) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
