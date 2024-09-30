package com.dexbackend.dexbackend.pageanalyzer.parser;

import java.io.IOException;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.dexbackend.dexbackend.pageanalyzer.model.configsettings.ConfigSettings;
import com.dexbackend.dexbackend.pageanalyzer.utilities.ElasticSearchUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


/**
 * The Class ConfigSettingsDataIngest.
 */
@Qualifier("ConfigSettingsDataIngest")
@Component
public class ConfigSettingsDataIngest implements DataAudits{

	/** The Constant ANALYZERPERFCONFIGSETTING. */
	private static final String ANALYZERPERFCONFIGSETTING = "analyzerperfconfigsetting";
	
	/** The Constant FETCH_TIME. */
	private static final String FETCH_TIME = "fetchTime";
	
	/** The Constant REQUESTED_URL. */
	private static final String REQUESTED_URL = "requestedUrl";
	
	/** The Constant CPU_SLOWDOWN_MULTIPLIER. */
	private static final String CPU_SLOWDOWN_MULTIPLIER = "cpuSlowdownMultiplier";
	
	/** The Constant UPLOAD_THROUGHPUT_KBPS. */
	private static final String UPLOAD_THROUGHPUT_KBPS = "uploadThroughputKbps";
	
	/** The Constant DOWNLOAD_THROUGHPUT_KBPS. */
	private static final String DOWNLOAD_THROUGHPUT_KBPS = "downloadThroughputKbps";
	
	/** The Constant REQUEST_LATENCY_MS. */
	private static final String REQUEST_LATENCY_MS = "requestLatencyMs";
	
	/** The Constant THROUGHPUT_KBPS. */
	private static final String THROUGHPUT_KBPS = "throughputKbps";
	
	/** The Constant RTT_MS. */
	private static final String RTT_MS = "rttMs";
	
	/** The Constant THROTTLING. */
	private static final String THROTTLING = "throttling";
	
	/** The Constant SKIP_AUDITS. */
	private static final String SKIP_AUDITS = "skipAudits";
	
	/** The Constant ONLY_CATEGORIES. */
	private static final String ONLY_CATEGORIES = "onlyCategories";
	
	/** The Constant ONLY_AUDITS. */
	private static final String ONLY_AUDITS = "onlyAudits";
	
	/** The Constant PRECOMPUTED_LANTERN_DATA. */
	private static final String PRECOMPUTED_LANTERN_DATA = "precomputedLanternData";
	
	/** The Constant EXTRA_HEADERS. */
	private static final String EXTRA_HEADERS = "extraHeaders";
	
	/** The Constant ADDITIONAL_TRACE_CATEGORIES. */
	private static final String ADDITIONAL_TRACE_CATEGORIES = "additionalTraceCategories";
	
	/** The Constant BLOCKED_URL_PATTERNS. */
	private static final String BLOCKED_URL_PATTERNS = "blockedUrlPatterns";
	
	/** The Constant LOCALE. */
	private static final String LOCALE = "locale";
	
	/** The Constant BUDGETS. */
	private static final String BUDGETS = "budgets";
	
	/** The Constant CHANNEL. */
	private static final String CHANNEL = "channel";
	
	/** The Constant FORM_FACTOR. */
	private static final String FORM_FACTOR = "formFactor";
	
	/** The Constant DISABLE_STORAGE_RESET. */
	private static final String DISABLE_STORAGE_RESET = "disableStorageReset";
	
	/** The Constant GATHER_MODE. */
	private static final String GATHER_MODE = "gatherMode";
	
	/** The Constant AUDIT_MODE. */
	private static final String AUDIT_MODE = "auditMode";
	
	/** The Constant THROTTLING_METHOD. */
	private static final String THROTTLING_METHOD = "throttlingMethod";
	
	/** The Constant MAX_WAIT_FOR_LOAD. */
	private static final String MAX_WAIT_FOR_LOAD = "maxWaitForLoad";
	
	/** The Constant MAX_WAIT_FOR_FCP. */
	private static final String MAX_WAIT_FOR_FCP = "maxWaitForFcp";
	
	/** The Constant CONFIG_SETTINGS. */
	private static final String CONFIG_SETTINGS = "configSettings";
	
	/** The Constant REPORTS. */
	private static final String REPORTS = "/reports/";
	
	/** The Constant USER_DIR. */
	private static final String USER_DIR = "user.dir";
	
	/** The elastic search utils. */
	@Autowired
	ElasticSearchUtils elasticSearchUtils;
	
	/**
	 * Ingest data.
	 *
	 * @param fileName the file name
	 * @param buildID the build ID
	 * @param pageTitle the page title
	 * @param applicationName the application name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	@Async("asyncExecutor")
	public void ingestData(String fileName, String buildID, String pageTitle, String applicationName)
			throws IOException {
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ObjectMapper objectMapper = new ObjectMapper();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(System.getProperty(USER_DIR));
		stringBuilder.append(REPORTS);
		stringBuilder.append(fileName);
		JsonNode node = objectMapper
				.readTree(Paths.get(stringBuilder.toString()).toFile());
		
		ConfigSettings configsettings=new ConfigSettings();
		configsettings.setMaxWaitForFcp(node.get(CONFIG_SETTINGS).get(MAX_WAIT_FOR_FCP).asInt());
		configsettings.setMaxWaitForLoad(node.get(CONFIG_SETTINGS).get(MAX_WAIT_FOR_LOAD).asInt());
		configsettings.setThrottlingMethod(node.get(CONFIG_SETTINGS).get(THROTTLING_METHOD).asText());
		configsettings.setAuditMode(node.get(CONFIG_SETTINGS).get(AUDIT_MODE).asBoolean());
		configsettings.setGatherMode(node.get(CONFIG_SETTINGS).get(GATHER_MODE).asBoolean());
		configsettings.setDisableStorageReset(node.get(CONFIG_SETTINGS).get(DISABLE_STORAGE_RESET).asBoolean());
		configsettings.setEmulatedFormFactor(node.get(CONFIG_SETTINGS).get(FORM_FACTOR).asText());
		//configsettings.setInternalDisableDeviceScreenEmulation(node.get(configSettings).get("internalDisableDeviceScreenEmulation").asBoolean());
		configsettings.setChannel(node.get(CONFIG_SETTINGS).get(CHANNEL).asText());
		configsettings.setBudgets(node.get(CONFIG_SETTINGS).get(BUDGETS));
		configsettings.setLocale(node.get(CONFIG_SETTINGS).get(LOCALE).asText());
		configsettings.setBlockedUrlPatterns(node.get(CONFIG_SETTINGS).get(BLOCKED_URL_PATTERNS));
		configsettings.setAdditionalTraceCategories(node.get(CONFIG_SETTINGS).get(ADDITIONAL_TRACE_CATEGORIES));
		configsettings.setExtraHeaders(node.get(CONFIG_SETTINGS).get(EXTRA_HEADERS));
		configsettings.setPrecomputedLanternData(node.get(CONFIG_SETTINGS).get(PRECOMPUTED_LANTERN_DATA));
		configsettings.setOnlyAudits(node.get(CONFIG_SETTINGS).get(ONLY_AUDITS));
		configsettings.setOnlyCategories(node.get(CONFIG_SETTINGS).get(ONLY_CATEGORIES));
		configsettings.setSkipAudits(node.get(CONFIG_SETTINGS).get(SKIP_AUDITS));		
		configsettings.setRttMs(node.get(CONFIG_SETTINGS).get(THROTTLING).get(RTT_MS).asDouble());
		configsettings.setThroughputKbps(node.get(CONFIG_SETTINGS).get(THROTTLING).get(THROUGHPUT_KBPS).asDouble());
		configsettings.setRequestLatencyMs(node.get(CONFIG_SETTINGS).get(THROTTLING).get(REQUEST_LATENCY_MS).asDouble());
		configsettings.setDownloadThroughputKbps(node.get(CONFIG_SETTINGS).get(THROTTLING).get(DOWNLOAD_THROUGHPUT_KBPS).asDouble());
		configsettings.setUploadThroughputKbps(node.get(CONFIG_SETTINGS).get(THROTTLING).get(UPLOAD_THROUGHPUT_KBPS).asDouble());
		configsettings.setCpuSlowdownMultiplier(node.get(CONFIG_SETTINGS).get(THROTTLING).get(CPU_SLOWDOWN_MULTIPLIER).asDouble());
		configsettings.setRequestedUrl(node.path(REQUESTED_URL).asText());
		configsettings.setFetchTime(node.path(FETCH_TIME).asText());
		configsettings.setBuildId(buildID);
		configsettings.setPageName(pageTitle);
		configsettings.setApplicationName(applicationName);
		
		elasticSearchUtils.createIndex(ANALYZERPERFCONFIGSETTING);
		elasticSearchUtils.ingestPerformanceConfigDataToIndex(ANALYZERPERFCONFIGSETTING, new Gson().toJson(configsettings));
	}

	@Override
	public void ingestGeneralData(String fileName, String buildID, String pageTitle, String applicationName, int scans)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
