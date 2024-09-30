/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.parser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.deque.html.axecore.results.CheckedNode;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.dexbackend.dexbackend.pageanalyzer.model.accessibility.AccessibilityIssues;
import com.dexbackend.dexbackend.pageanalyzer.model.accessibility.AccessibilityPassedAudits;
import com.dexbackend.dexbackend.pageanalyzer.utilities.ElasticSearchUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;


/**
 * The Class AccessibilityDataIngest.
 *
 * @author surendrane
 */
@Qualifier("AccessibilityDataIngest")
@Service
public class AccessibilityDataIngest implements DataAudits {

	/** The Constant ANALYZERACCPASSAUDITS. */
	private static final String ANALYZERACCPASSAUDITS = "analyzeraccpassaudits";

	/** The Constant ANALYZERACCISSUES. */
	private static final String ANALYZERACCISSUES = "analyzeraccissues";

	/** The Constant _508. */
	private static final String _508 = "508";

	/** The Constant WCAG. */
	private static final String WCAG = "wcag";

	/** The Constant CAT. */
	private static final String CAT = "cat";

	/** The Constant RUNTIME_ERROR. */
	private static final String RUNTIME_ERROR = "runtimeError";

	/** The Constant REPORTS. */
	private static final String REPORTS = "/reports/";

	/** The Constant USER_DIR. */
	private static final String USER_DIR = "user.dir";

	/** The elastic search utils. */
	@Autowired
	ElasticSearchUtils elasticSearchUtils;

	/** The logger. */
	private static Logger LOGGER = Logger.getLogger(AccessibilityDataIngest.class);

	/**
	 * Ingest data.
	 *
	 * @param fileName        the file name
	 * @param buildID         the build ID
	 * @param pageTitle       the page title
	 * @param applicationName the application name
	 * @throws Exception the exception
	 */
	@Override
	@Async("asyncExecutor")
	public void ingestData(final String fileName, final String buildID, final String pageTitle,
			final String applicationName) throws Exception {
		String requestedUrl = "requestedUrl";
		String fetchTime = "fetchTime";
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode node = null;
		try {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(System.getProperty(USER_DIR));
			stringBuilder.append(REPORTS);
			stringBuilder.append(pageTitle);
			stringBuilder.append(".axe.report.json");
			File axeFile = new File(stringBuilder.toString());
			Reader reader = new FileReader(axeFile);
			Results axeResults = new Gson().fromJson(reader, Results.class);
			Thread.sleep(10000);
			objectMapper = new ObjectMapper();
			StringBuilder stringBuilder2 = new StringBuilder();
			stringBuilder2.append(System.getProperty(USER_DIR));
			stringBuilder2.append(REPORTS);
			stringBuilder2.append(fileName);
			node = objectMapper.readTree(Paths.get(stringBuilder2.toString()).toFile());
			if ((!node.has(RUNTIME_ERROR)) && (!axeResults.isErrored())) {
				requestedUrl = node.path("requestedUrl").asText();
				fetchTime = node.path("fetchTime").asText();		
				List<AccessibilityPassedAudits> accessibilityPassedAuditsList = new ArrayList<AccessibilityPassedAudits>();
				for (Rule rule : axeResults.getPasses()) {
					AccessibilityPassedAudits accessibilityPassedAudits = new AccessibilityPassedAudits();
					accessibilityPassedAudits.setApplicationName(applicationName);
					accessibilityPassedAudits.setBuildId(buildID);
					accessibilityPassedAudits.setDescription(rule.getDescription());
					accessibilityPassedAudits.setFetchTime(fetchTime);
					accessibilityPassedAudits.setRequestedUrl(requestedUrl);
					accessibilityPassedAudits.setTitle(rule.getHelp());
					accessibilityPassedAudits.setPageName(pageTitle);
					accessibilityPassedAuditsList.add(accessibilityPassedAudits);
				}
				List<AccessibilityIssues> accessibilityIssuesList = new ArrayList<AccessibilityIssues>();
				for (Rule rule : axeResults.getViolations()) {
					for (CheckedNode checkNode : rule.getNodes()) {
						AccessibilityIssues accessibilityIssues = new AccessibilityIssues();
						accessibilityIssues.setApplicationName(applicationName);
						accessibilityIssues.setBuildId(buildID);
						accessibilityIssues.setDescription(rule.getDescription());
						accessibilityIssues.setFetchTime(fetchTime);
						accessibilityIssues.setExplanation(checkNode.getFailureSummary());
						accessibilityIssues.setImpact(rule.getImpact());
						accessibilityIssues.setTitle(rule.getHelp());
						accessibilityIssues.setPageName(pageTitle);
						accessibilityIssues.setPath(checkNode.getHtml());
						accessibilityIssues.setRequestedUrl(requestedUrl);
						List<String> catRules = new ArrayList<String>();
						List<String> wcagRules = new ArrayList<String>();
						List<String> section508 = new ArrayList<String>();
						for (String tag : rule.getTags()) {
							if (tag.contains(CAT)) {
								catRules.add(tag);
							} else if (tag.contains(WCAG)) {
								wcagRules.add(tag);
							} else if (tag.contains(_508)) {
								section508.add(tag);
							}
						}
						accessibilityIssues.setCat(StringUtils.join(catRules, ","));
						accessibilityIssues.setWcagRule(StringUtils.join(wcagRules, ","));
						accessibilityIssues.setSection(StringUtils.join(section508, ","));
						accessibilityIssues.setRequestedUrl(requestedUrl);
						accessibilityIssues.setLabel(rule.getId());
						accessibilityIssues.setSnippet(checkNode.getTarget().toString());
						accessibilityIssues.setSelector(checkNode.getHtml());
						accessibilityIssuesList.add(accessibilityIssues);
					}
				}

//				elasticSearchUtils.createIndex(ANALYZERACCISSUES);
//				for (AccessibilityIssues finalFormatForIngest : accessibilityIssuesList)
//				{
//				elasticSearchUtils.ingestDataToIndex(ANALYZERACCISSUES, new Gson().toJson(finalFormatForIngest));
//				}
//			elasticSearchUtils.ingestAccessibilityIssuesToIndex(ANALYZERACCISSUES, accessibilityIssuesList);
//
//				elasticSearchUtils.createIndex(ANALYZERACCPASSAUDITS);
//				elasticSearchUtils.ingestAccessibilityPassedAuditsItemsToIndex(ANALYZERACCPASSAUDITS,
//						accessibilityPassedAuditsList);
				
				// Ensure the lists are not empty before making the Elasticsearch call
				if (!accessibilityIssuesList.isEmpty()) {
				    LOGGER.info("Ingesting accessibility issues to index.");
				    elasticSearchUtils.ingestAccessibilityIssuesToIndex(ANALYZERACCISSUES, accessibilityIssuesList);
				} else {
				    LOGGER.warn("No accessibility issues found to ingest.");
				}

				if (!accessibilityPassedAuditsList.isEmpty()) {
				    LOGGER.info("Ingesting passed audits to index.");
				    elasticSearchUtils.ingestAccessibilityPassedAuditsItemsToIndex(ANALYZERACCPASSAUDITS, accessibilityPassedAuditsList);
				} else {
				    LOGGER.warn("No passed audits found to ingest.");
				}


			}
		} catch (InterruptedException | IOException e) {
			  LOGGER.error(e.getMessage(),e);
		}
	}

	@Override
	public void ingestGeneralData(String fileName, String buildID, String pageTitle, String applicationName, int scans)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
