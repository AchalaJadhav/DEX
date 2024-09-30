/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.parser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.dexbackend.dexbackend.pageanalyzer.model.seo.SEOFailedAudits;
import com.dexbackend.dexbackend.pageanalyzer.model.seo.SEOItemDetails;
import com.dexbackend.dexbackend.pageanalyzer.model.seo.SEOItems;
import com.dexbackend.dexbackend.pageanalyzer.model.seo.SEOPassedAudits;
import com.dexbackend.dexbackend.pageanalyzer.parser.model.Categories;
import com.dexbackend.dexbackend.pageanalyzer.utilities.PrometheusUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.prometheus.client.Gauge;


/**
 * The Class SEODataIngest.
 *
 * @author surendrane
 */
@Qualifier("SEODataIngest")
@Service
public class SEODataIngest implements DataAudits {

	/** The Constant ANALYZERSEOFAILAUDITS. */
	private static final String ANALYZERSEOFAILAUDITS = "analyzerseofailaudits";

	/** The Constant ANALYZERSEOPASSAUDITS. */
	private static final String ANALYZERSEOPASSAUDITS = "analyzerseopassaudits";

	/** The Constant NODE2. */
	private static final String NODE2 = "node";

	/** The Constant KEY. */
	private static final String KEY = "key";

	/** The Constant URL. */
	private static final String URL = "url";

	/** The Constant ITEM_TYPE. */
	private static final String ITEM_TYPE = "itemType";

	/** The Constant DISPLAY_VALUE. */
	private static final String DISPLAY_VALUE = "displayValue";

	/** The Constant HEADINGS2. */
	private static final String HEADINGS2 = "headings";

	/** The Constant DETAILS. */
	private static final String DETAILS = "details";

	/** The Constant DESCRIPTION. */
	private static final String DESCRIPTION = "description";

	/** The Constant FETCH_TIME. */
	private static final String FETCH_TIME = "fetchTime";

	/** The Constant REQUESTED_URL. */
	private static final String REQUESTED_URL = "requestedUrl";

	/** The Constant CANONICAL. */
	private static final String CANONICAL = "canonical";

	/** The Constant SCORE2. */
	private static final String AUDIT_SCORE = "score";

	/** The Constant TITLE2. */
	private static final String TITLE2 = "title";

	/** The Constant AUDITS. */
	private static final String AUDITS = "audits";

	/** The Constant AUDIT_REFS. */
	private static final String AUDIT_REFS = "auditRefs";

	/** The Constant SEO. */
	private static final String SEO = "seo";

	/** The Constant CATEGORIES2. */
	private static final String CATEGORIES2 = "categories";

	/** The Constant RUNTIME_ERROR. */
	private static final String RUNTIME_ERROR = "runtimeError";

	/** The Constant REPORTS. */
	private static final String REPORTS = "/reports/";

	/** The Constant USER_DIR. */
	private static final String USER_DIR = "user.dir";

	private static final String CRAWLABLEANCHOR = "crawlable-anchors";

	private static final String ITEMS = "items";
	
	/** The Constant TYPE. */
	private static final String TYPE = "type";
	
	/** The Constant TYPE for crawlable-anchors */
	private static final String NODE = "node";
	/** The Constant TYPE for crawlable-anchors */
	private static final String PATH = "path";
	/** The Constant TYPE for crawlable-anchors */
	private static final String SELECTOR = "selector";
	/** The Constant TYPE for crawlable-anchors */
	private static final String CNODELABEL = "nodeLabel";
	/** The Constant TYPE for link-text */
	private static final String HREF = "href";
	/** The Constant TYPE for link-text */
	private static final String TEXT = "text";
	/** The Constant TYPE for tap-targets */
	private static final String TAPTARGETS = "tapTarget";
	/** The Constant TYPE for tap-targets */
	private static final String OVERLAPPINGTARGETS = "overlappingTarget";
	/** The Constant TYPE for tap-targets */
	private static final String BOUNDINGRECT = "boundingRect";
	/** The Constant TYPE for tap-targets */
	private static final String WIDTH = "width";
	/** The Constant TYPE for tap-targets */
	private static final String HEIGHT = "height";
	/** The Constant TYPE for tap-targets */
	private static final String NODELABELS = "nodeLabel";
	/** The Constant TYPE for tap-targets */
	private static final String TPATH = "path";
	/** The Constant TYPE for tap-targets */
	private static final String TSELECTOR = "selector";
	/** The Constant TYPE for tap-targets */
	private static final String OPATH = "path";
	/** The Constant TYPE for tap-targets */
	private static final String OSELECTOR = "selector";
	/** The Constant TYPE for tap-targets */
	private static final String TNODELABELS = "nodeLabel";
	/** The Constant TYPE for tap-targets */
	private static final String ONODELABELS = "nodeLabel";

	/** The Constant TYPE for image-alt */
	private static final String IPATH = "path";
	/** The Constant TYPE for image-alt */
	private static final String ISELECTOR = "selector";
	/** The Constant TYPE for image-alt */
	private static final String ISNIPPET = "snippet";
	/** The Constant TYPE for image-alt */
	private static final String INODELABEL = "nodeLabel";
	/** The Constant TYPE for robots-txt */
	private static final String INDEX = "index";
	/** The Constant TYPE for robots-txt */
	private static final String LINE = "line";
	/** The Constant TYPE for robots-txt */
	private static final String MESSAGE = "message";
	
	@Autowired
	static PrometheusUtil prometheusUtil = new PrometheusUtil();
	
	/** The logger. */
	private static Logger LOGGER=LoggerFactory.getLogger(SEODataIngest.class);
	
	private static final Gauge analyzerSeoPassAuditsGauge = Gauge.build().name(ANALYZERSEOPASSAUDITS).help("exported from JSON data.")
			.labelNames(prometheusUtil.getDeclaredFields(SEOPassedAudits.class))
			.register();
	
	private static final Gauge analyzerSeoFailAuditsGauge = Gauge.build().name(ANALYZERSEOFAILAUDITS).help("exported from JSON data.")
			.labelNames(prometheusUtil.getDeclaredFields(SEOFailedAudits.class))
			.register();
	
	public static Gauge getAnalyzerSeoPassAuditsGauge() {
        return analyzerSeoPassAuditsGauge;
    }
	
	public static Gauge getAnalyzerSeoFailAuditsGauge() {
        return analyzerSeoFailAuditsGauge;
    }
	
	@Override
	@Async("asyncExecutor")
	public void ingestData(String fileName, String buildID, String pageTitle, String applicationName, String userId) throws Exception 
	{
		try 
		{
			Thread.sleep(10000);

			ObjectMapper objectMapper = new ObjectMapper();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(System.getProperty(USER_DIR));
			stringBuilder.append(REPORTS);
			stringBuilder.append(fileName);
			JsonNode node = objectMapper
					.readTree(Paths.get(stringBuilder.toString()).toFile());
			if (!node.has(RUNTIME_ERROR)) 
			{
				List<SEOFailedAudits> seoFailedAuditLists = new ArrayList<SEOFailedAudits>();
				List<SEOPassedAudits> seoPassedAuditLists = new ArrayList<SEOPassedAudits>();
				SEOItemDetails seoItemDetails = new SEOItemDetails();
				ArrayList<SEOItems> tm = new ArrayList<SEOItems>();
				
				Categories[] seoCategories = objectMapper
						.treeToValue(node.path(CATEGORIES2).get(SEO).get(AUDIT_REFS), Categories[].class);
	
				for (Categories categories : seoCategories) 
				{
					if (!categories.getGroup().isEmpty()) 
					{
						String group = categories.getGroup().toUpperCase().replace("-", "_");				
						String id = categories.getId();
						String weight = categories.getWeight();
						JsonNode auditNode = node.get(AUDITS).get(id);
						JsonNode items = auditNode.findPath(DETAILS).get(ITEMS);
						String title = auditNode.get(TITLE2).asText();
						if (auditNode.get(AUDIT_SCORE).isNull()) 
						{
							SEOFailedAudits seoFailedAudit = new SEOFailedAudits();
							seoFailedAudit.setAuditId(id);

							seoFailedAudit.setRequestedUrl(isNull((JsonNode)node.path(REQUESTED_URL)));
							seoFailedAudit.setFetchTime(isNull((JsonNode)node.path(FETCH_TIME)));
							seoFailedAudit.setBuildId(buildID);
							seoFailedAudit.setPageName(pageTitle);
							seoFailedAudit.setApplicationName(applicationName);
							seoFailedAudit.setDescription(isNull((JsonNode)auditNode.get(DESCRIPTION)));
							seoFailedAudit.setTitle(title);
							seoFailedAudit.setGroup(changeGroupName(group));
							seoFailedAudit.setScore(weight);


							if (auditNode.has(DISPLAY_VALUE)) {
								seoFailedAudit.setSummary(auditNode.get(DISPLAY_VALUE).asText());
							}
							
							
							
							seoFailedAuditLists.add(seoFailedAudit);	
						} 
						//If Audit Fails Then It Will Invoke This Else Block
						else 
						{
							double score = auditNode.get(AUDIT_SCORE).asDouble();
							if (score < 1.0 || auditNode.get(AUDIT_SCORE).isNull()) 
							{		
		
								//
								JsonNode jsonNodeOfDetails = auditNode.findPath(DETAILS);
								if(jsonNodeOfDetails.isEmpty())
								{
									
									LOGGER.error("Details of fail audit '"+categories.id+"' are not available");
									SEOFailedAudits seoFailedAudit = new SEOFailedAudits();
									seoFailedAudit.setRequestedUrl(isNull((JsonNode)node.path(REQUESTED_URL)));
									seoFailedAudit.setFetchTime(isNull((JsonNode)node.path(FETCH_TIME)));
									seoFailedAudit.setBuildId(buildID);
									seoFailedAudit.setPageName(pageTitle);
									seoFailedAudit.setApplicationName(applicationName);
									seoFailedAudit.setDescription(isNull((JsonNode)auditNode.get(DESCRIPTION)));
									seoFailedAudit.setTitle(title);
									seoFailedAudit.setGroup(changeGroupName(group));
									seoFailedAudit.setScore(weight);


									if (auditNode.has(DISPLAY_VALUE)) {
										seoFailedAudit.setSummary(auditNode.get(DISPLAY_VALUE).asText());
									}
												
									seoFailedAuditLists.add(seoFailedAudit);	
								}
								//
								else 
								{
								ArrayNode headings = (ArrayNode)auditNode.findPath(DETAILS).get(HEADINGS2);
								
								seoItemDetails.setType(node.findPath(DETAILS).get(TYPE).asText());
								Map<String, String> uniqueHeadings = new HashedMap();
								{
									for (int i = 0; i < headings.size(); ++i) {
										JsonNode objects = headings.get(i);
										uniqueHeadings.put(objects.get(KEY).asText(), null);
									}
									
								}
								
								Set<String> keysHeading = uniqueHeadings.keySet();
								ArrayList<Map<Object, Object>> seoHeadingKeyItemsMap = new ArrayList<Map<Object, Object>>();
								for (int i = 0; i < items.size(); ++i) {
									Map<Object, Object> headingItems = new HashedMap();
									JsonNode objects = items.get(i);
									for (String head : keysHeading) {
										 
										headingItems.put(head, objects.get(head));
									}
									System.out.println(headingItems);
									seoHeadingKeyItemsMap.add(headingItems);
					
								}
								seoItemDetails.setSEOHeadingItemsMap(seoHeadingKeyItemsMap); 
								System.out.println("seoItemDetails: " + seoItemDetails.getSEOHeadingItemsMap());
								SEOFailedAudits seoFailedAudits = new SEOFailedAudits();
								seoFailedAudits.setSeoItemDetails(seoItemDetails);
								
								ArrayList<Map<Object, Object>> sm = seoFailedAudits.getSeoItemDetails().getSEOHeadingItemsMap();
								
								for (Map<Object, Object> map : sm ) {
									ObjectMapper mapper = new ObjectMapper();
									JsonNode childNode = mapper.convertValue(map, JsonNode.class);
									SEOFailedAudits seoFailedAudit = new SEOFailedAudits();
									SEOItems seoItems = new SEOItems();
									seoItems.setAuditId(id);
									switch(id) {
									//SEO-CONTENT
									case "link-text":
									{
										seoFailedAudit.setHref(isNull((JsonNode) map.get(HREF)));
										seoFailedAudit.setText(isNull((JsonNode) map.get(TEXT)));
										
										break;
									}
									
									case "image-alt":
									{
										seoFailedAudit.setIPath(isNull((JsonNode)childNode.findPath(NODE).get(IPATH)));
										seoFailedAudit.setISelector(isNull((JsonNode)childNode.findPath(NODE).get(ISELECTOR)));
										seoFailedAudit.setISnippet(isNull((JsonNode)childNode.findPath(NODE).get(ISNIPPET)));
										seoFailedAudit.setINodeLabel(isNull((JsonNode)childNode.findPath(NODE).get(INODELABEL)));

										break;
									}
									
									//SEO-CRAWL
									case "crawlable-anchors":
									{
										seoFailedAudit.setPath(isNull((JsonNode)childNode.findPath(NODE).get(PATH)));
										seoFailedAudit.setSelector(isNull((JsonNode)childNode.findPath(NODE).get(SELECTOR)));
										seoFailedAudit.setCNodeLabel(isNull((JsonNode)childNode.findPath(NODE).get(CNODELABEL)));
										break;
									}
									
									case "robots-txt":
									{
										seoFailedAudit.setIndex(isNull((JsonNode) map.get(INDEX)));
										seoFailedAudit.setLine(isNull((JsonNode) map.get(LINE)));
										seoFailedAudit.setMessage(isNull((JsonNode) map.get(MESSAGE)));

										break;
									}
									
									//SEO-MOBILE
									case "tap-targets":
									{
										seoFailedAudit.setTNodeLabel(isNull((JsonNode) childNode.findPath(TAPTARGETS).get(TNODELABELS)));
										seoFailedAudit.setTPath(isNull((JsonNode)childNode.findPath(TAPTARGETS).get(TPATH)));
										seoFailedAudit.setTSelector(isNull((JsonNode)childNode.findPath(TAPTARGETS).get(TSELECTOR)));
										seoFailedAudit.setTWidth(isNull((JsonNode)childNode.findPath(TAPTARGETS).findPath(BOUNDINGRECT).get(WIDTH)));
										seoFailedAudit.setTHeight(isNull((JsonNode)childNode.findPath(TAPTARGETS).findPath(BOUNDINGRECT).get(HEIGHT)));

										seoFailedAudit.setONodeLabel(isNull((JsonNode)childNode.findPath(OVERLAPPINGTARGETS).get(ONODELABELS)));
										seoFailedAudit.setOPath(isNull((JsonNode)childNode.findPath(OVERLAPPINGTARGETS).get(OPATH)));
										seoFailedAudit.setOSelector(isNull((JsonNode)childNode.findPath(OVERLAPPINGTARGETS).get(OSELECTOR)));
										seoFailedAudit.setOWidth(isNull((JsonNode)childNode.findPath(OVERLAPPINGTARGETS).findPath(BOUNDINGRECT).get(WIDTH)));
										seoFailedAudit.setOHeight(isNull((JsonNode)childNode.findPath(OVERLAPPINGTARGETS).findPath(BOUNDINGRECT).get(HEIGHT)));

										break;
									}
									
									
									default:{
										
									}
									}
									seoFailedAudit.setRequestedUrl(isNull((JsonNode)node.path(REQUESTED_URL)));
									seoFailedAudit.setFetchTime(isNull((JsonNode)node.path(FETCH_TIME)));
									seoFailedAudit.setBuildId(buildID);
									seoFailedAudit.setPageName(pageTitle);
									seoFailedAudit.setApplicationName(applicationName);
									seoFailedAudit.setDescription(isNull((JsonNode)auditNode.get(DESCRIPTION)));
									seoFailedAudit.setTitle(title);
									seoFailedAudit.setGroup(changeGroupName(group));
									seoFailedAudit.setScore(weight);


									if (auditNode.has(DISPLAY_VALUE)) {
										seoFailedAudit.setSummary(auditNode.get(DISPLAY_VALUE).asText());
									}
									
									
									
									seoFailedAuditLists.add(seoFailedAudit);	
									}		
								}
							}
							
							else {
								SEOPassedAudits seoPassedAudits = new SEOPassedAudits();
								seoPassedAudits.setRequestedUrl(node.path(REQUESTED_URL).asText());
								seoPassedAudits.setFetchTime(node.path(FETCH_TIME).asText());
								seoPassedAudits.setBuildId(buildID);
								seoPassedAudits.setPageName(pageTitle);
								seoPassedAudits.setApplicationName(applicationName);
								seoPassedAudits.setDescription(auditNode.get(DESCRIPTION).asText());
								seoPassedAudits.setTitle(title);
								seoPassedAudits.setGroup(changeGroupName(group));
								seoPassedAuditLists.add(seoPassedAudits);
							}
						}
					}
				}
				if (!seoPassedAuditLists.isEmpty()) {
					LOGGER.info("Started - Ingesting analyzerseopassaudits to prometheus");
					for (SEOPassedAudits seoPassedAudits : seoPassedAuditLists) {
						prometheusUtil.ingestAuditsToPrometheus(getAnalyzerSeoPassAuditsGauge(), seoPassedAudits, prometheusUtil.getDeclaredFields(SEOPassedAudits.class), userId);
					}
					LOGGER.info("Completed - Ingesting analyzerseopassaudits to prometheus");
				}
				if (!seoFailedAuditLists.isEmpty()) {
					LOGGER.info("Started - Ingesting analyzerseofailaudits to prometheus");
					for (SEOFailedAudits seoFailedAudits : seoFailedAuditLists) {
						prometheusUtil.ingestAuditsToPrometheus(getAnalyzerSeoFailAuditsGauge(), seoFailedAudits, prometheusUtil.getDeclaredFields(SEOFailedAudits.class), userId);
					}
					LOGGER.info("Completed - Ingesting analyzerseofailaudits to prometheus");
				}
			}
		} 
		catch (InterruptedException | IOException e) 
			{
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
		if (group.contains("SEO") ) {
			//String after _
			groupNameAlter = group.substring(4, group.length());
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
		return groupNameAlter;
	}

	@Override
	public void ingestGeneralData(String fileName, String buildID, String pageTitle, String applicationName, int scans,
			String userId) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
