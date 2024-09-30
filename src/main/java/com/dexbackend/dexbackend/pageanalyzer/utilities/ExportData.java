/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.utilities;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.dexbackend.dexbackend.pageanalyzer.parser.ConfigSettingsDataIngest;
import com.dexbackend.dexbackend.pageanalyzer.parser.DataAudits;

import io.prometheus.client.exporter.HTTPServer;


/**
 * The Class ExportData.
 *
 * @author surendrane
 */
@Component
public class ExportData {

	/** The analyzer general data ingest. */
	@Autowired
	@Qualifier("AnalyzerGeneralDataIngest")
	DataAudits analyzerGeneralDataIngest;

	/** The performance data ingest. */
	@Autowired
	@Qualifier("PerformanceDataIngest")
	DataAudits performanceDataIngest;

	/** The accessibility data ingest. */
	@Autowired
	@Qualifier("AccessibilityDataIngest")
	DataAudits accessibilityDataIngest;

	/** The seo data ingest. */
	@Autowired
	@Qualifier("SEODataIngest")
	DataAudits seoDataIngest;
	
	/** The bestPractices data ingest. */
	@Autowired
	@Qualifier("BestPracticesDataIngest")
	DataAudits bestPracticesDataIngest;

	/** The diagnostics data ingest. */
	@Autowired
	@Qualifier("DiagnosticsDataIngest")
	DataAudits diagnosticsDataIngest;

	/** The network request data ingest. */
	@Autowired
	@Qualifier("NetworkRequestDataIngest")
	DataAudits networkRequestDataIngest;

	/** The network rtt data ingest. */
	@Autowired
	@Qualifier("NetworkRttDataIngest")
	DataAudits networkRttDataIngest;

	/** The network server latency data ingest. */
	@Autowired
	@Qualifier("NetworkServerLatencyDataIngest")
	DataAudits networkServerLatencyDataIngest;

	/** The metrics data ingest. */
	@Autowired
	@Qualifier("MetricsDataIngest")
	DataAudits metricsDataIngest;

	/** The config settings data ingest. */
	@Autowired
	@Qualifier("ConfigSettingsDataIngest")
	DataAudits configSettingsDataIngest;
	
	/**
	 * Ingest to elastic search.
	 *
	 * @param fileName the file name
	 * @param buildID the build ID
	 * @param pageTitle the page title
	 * @param applicationName the application name
	 * @throws Exception the exception
	 */
	public void ingestToElasticSearch(final String fileName, final String buildID, final String pageTitle,
			final String applicationName, final int scans) throws Exception {
		
		HTTPServer server = new HTTPServer(9098);
//		performanceDataIngest.ingestData(fileName, buildID, pageTitle, applicationName);

		analyzerGeneralDataIngest.ingestGeneralData(fileName, buildID, pageTitle, applicationName, scans);
//		bestPracticesDataIngest.ingestData(fileName, buildID, pageTitle, applicationName);
//
//		accessibilityDataIngest.ingestData(fileName, buildID, pageTitle, applicationName);
//		seoDataIngest.ingestData(fileName, buildID, pageTitle, applicationName);
//		diagnosticsDataIngest.ingestData(fileName, buildID, pageTitle, applicationName);
//		networkRequestDataIngest.ingestData(fileName, buildID, pageTitle, applicationName);
		//networkRttDataIngest.ingestData(fileName, buildID, pageTitle, applicationName);
//		networkServerLatencyDataIngest.ingestData(fileName, buildID, pageTitle, applicationName);
//		metricsDataIngest.ingestData(fileName, buildID, pageTitle, applicationName);
//		configSettingsDataIngest.ingestData(fileName, buildID, pageTitle, applicationName);
	}
}
