package com.dexbackend.dexbackend.pageanalyzer.parser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.dexbackend.dexbackend.pageanalyzer.model.diagnostics.DiagnosticsDetails;
import com.dexbackend.dexbackend.pageanalyzer.utilities.ElasticSearchUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


/**
 * The Class DiagnosticsDataIngest.
 *
 * @author mukundz
 */
@Qualifier("DiagnosticsDataIngest")
@Component
public class DiagnosticsDataIngest implements DataAudits {

	/** The Constant ANALYZERPERFDIAGNOSTICS. */
	private static final String ANALYZERPERFDIAGNOSTICS = "analyzerperfdiagnostics";

	/** The Constant MAIN_DOCUMENT_TRANSFER_SIZE. */
	private static final String MAIN_DOCUMENT_TRANSFER_SIZE = "mainDocumentTransferSize";

	/** The Constant TOTAL_TASK_TIME. */
	private static final String TOTAL_TASK_TIME = "totalTaskTime";

	/** The Constant TOTAL_BYTE_WEIGHT. */
	private static final String TOTAL_BYTE_WEIGHT = "totalByteWeight";

	/** The Constant MAX_SERVER_LATENCY. */
	private static final String MAX_SERVER_LATENCY = "maxServerLatency";

	/** The Constant MAX_RTT. */
	private static final String MAX_RTT = "maxRtt";

	/** The Constant THROUGHPUT. */
	private static final String THROUGHPUT = "throughput";

	/** The Constant RTT. */
	private static final String RTT = "rtt";

	/** The Constant NUM_TASKS_OVER500MS. */
	private static final String NUM_TASKS_OVER500MS = "numTasksOver500ms";

	/** The Constant NUM_TASKS_OVER100MS. */
	private static final String NUM_TASKS_OVER100MS = "numTasksOver100ms";

	/** The Constant NUM_TASKS_OVER50MS. */
	private static final String NUM_TASKS_OVER50MS = "numTasksOver50ms";

	/** The Constant NUM_TASKS_OVER25MS. */
	private static final String NUM_TASKS_OVER25MS = "numTasksOver25ms";

	/** The Constant NUM_TASKS_OVER10MS. */
	private static final String NUM_TASKS_OVER10MS = "numTasksOver10ms";

	/** The Constant NUM_TASKS. */
	private static final String NUM_TASKS = "numTasks";

	/** The Constant NUM_FONTS. */
	private static final String NUM_FONTS = "numFonts";

	/** The Constant NUM_STYLESHEETS. */
	private static final String NUM_STYLESHEETS = "numStylesheets";

	/** The Constant NUM_SCRIPTS. */
	private static final String NUM_SCRIPTS = "numScripts";

	/** The Constant NUM_REQUESTS. */
	private static final String NUM_REQUESTS = "numRequests";

	/** The Constant ITEMS2. */
	private static final String ITEMS2 = "items";

	/** The Constant TYPE. */
	private static final String TYPE = "type";

	/** The Constant DETAILS. */
	private static final String DETAILS = "details";

	/** The Constant NULL. */
	private static final String NULL = "null";

	/** The Constant SCORE. */
	private static final String SCORE = "score";

	/** The Constant SCORE_DISPLAY_MODE. */
	private static final String SCORE_DISPLAY_MODE = "scoreDisplayMode";

	/** The Constant DESCRIPTION. */
	private static final String DESCRIPTION = "description";

	/** The Constant TITLE. */
	private static final String TITLE = "title";

	/** The Constant ID. */
	private static final String ID = "id";

	/** The Constant DIAGNOSTICS2. */
	private static final String DIAGNOSTICS2 = "diagnostics";

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
	private static Logger LOGGER = Logger.getLogger(DiagnosticsDataIngest.class);

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

		try {
			Thread.sleep(10000);
			ObjectMapper objectMapper = new ObjectMapper();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(System.getProperty(USER_DIR));
			stringBuilder.append(REPORTS);
			stringBuilder.append(fileName);
			JsonNode node = objectMapper.readTree(Paths.get(stringBuilder.toString()).toFile());
			DiagnosticsDetails diagnostics = new DiagnosticsDetails();
			diagnostics.setBuildId(buildID);
			diagnostics.setPageName(pageTitle);
			diagnostics.setApplicationName(applicationName);
			diagnostics.setFetchTime(node.get(FETCH_TIME).asText());
			diagnostics.setRequestedUrl(node.get(REQUESTED_URL).asText());
			node = node.get(AUDITS);
			node = node.get(DIAGNOSTICS2);

			ArrayList<DiagnosticsDetails> diagnosticsArrayList = new ArrayList<DiagnosticsDetails>();

			diagnostics.setId(node.get(ID).asText());
			diagnostics.setTitle(node.get(TITLE).asText());
			diagnostics.setDescription(node.get(DESCRIPTION).asText());
			diagnostics.setScoreDisplayMode(node.get(SCORE_DISPLAY_MODE).asText());

			if (node.get(SCORE).asText().equals(NULL)) {
				diagnostics.setScore(null);
			} else {
				diagnostics.setScore(node.get(SCORE).asText());
			}

			node = node.get(DETAILS);
			diagnostics.setType(isNull((JsonNode) node.get(TYPE)));

			JsonNode items = node.get(ITEMS2);
			for (int i = 0; i < items.size(); i++) {

				diagnostics.setNumRequests(items.get(i).get(NUM_REQUESTS).asLong());
				diagnostics.setNumScripts(items.get(i).get(NUM_SCRIPTS).asLong());
				diagnostics.setNumStyleSheets(items.get(i).get(NUM_STYLESHEETS).asLong());
				diagnostics.setNumFonts(items.get(i).get(NUM_FONTS).asInt());
				diagnostics.setNumTasks(items.get(i).get(NUM_TASKS).asLong());
				diagnostics.setNumTasksOver10ms(items.get(i).get(NUM_TASKS_OVER10MS).asInt());
				diagnostics.setNumTasksOver25ms(items.get(i).get(NUM_TASKS_OVER25MS).asInt());
				diagnostics.setNumTasksOver50ms(items.get(i).get(NUM_TASKS_OVER50MS).asInt());
				diagnostics.setNumTasksOver100ms(items.get(i).get(NUM_TASKS_OVER100MS).asInt());
				diagnostics.setNumTasksOver500ms(items.get(i).get(NUM_TASKS_OVER500MS).asInt());
				diagnostics.setRtt(items.get(i).get(RTT).asLong());
				diagnostics.setThroughput(items.get(i).get(THROUGHPUT).asDouble());
				diagnostics.setMaxRtt(items.get(i).get(MAX_RTT).asDouble());
				diagnostics.setMaxServerLatency(items.get(i).get(MAX_SERVER_LATENCY).asDouble());
				diagnostics.setTotalByteWeight(items.get(i).get(TOTAL_BYTE_WEIGHT).asDouble());
				diagnostics.setTotalTaskTime(items.get(i).get(TOTAL_TASK_TIME).asDouble());
				diagnostics.setMainDocumentTransferSize(items.get(i).get(MAIN_DOCUMENT_TRANSFER_SIZE).asDouble());

			}
			diagnosticsArrayList.add(diagnostics);
			elasticSearchUtils.createIndex(ANALYZERPERFDIAGNOSTICS);
//			for (DiagnosticsDetails finalFormatForIngest : diagnosticsArrayList)
//			{
//			elasticSearchUtils.ingestDataToIndex(ANALYZERACCISSUES, new Gson().toJson(finalFormatForIngest));
//			}
			elasticSearchUtils.ingestDiagnostics(ANALYZERPERFDIAGNOSTICS, diagnosticsArrayList);
		} catch (InterruptedException | IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
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
