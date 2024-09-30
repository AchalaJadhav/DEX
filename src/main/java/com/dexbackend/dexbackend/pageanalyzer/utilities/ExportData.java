/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.utilities;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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
	
	private static HTTPServer server;
	
	@Value("${prometheus.port}")
	private int prometheusPort;
	
	public void ingestToPrometheus(final String fileName, final String buildID, final String pageTitle,
			final String applicationName, final int scans, String userId) throws Exception {
		
		try {
			if (!isPortInUse(prometheusPort)) {
				server = new HTTPServer(prometheusPort);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		performanceDataIngest.ingestData(fileName, buildID, pageTitle, applicationName, userId);
		analyzerGeneralDataIngest.ingestGeneralData(fileName, buildID, pageTitle, applicationName, scans, userId);
		bestPracticesDataIngest.ingestData(fileName, buildID, pageTitle, applicationName, userId);
		accessibilityDataIngest.ingestData(fileName, buildID, pageTitle, applicationName, userId);
		seoDataIngest.ingestData(fileName, buildID, pageTitle, applicationName, userId);
		diagnosticsDataIngest.ingestData(fileName, buildID, pageTitle, applicationName, userId);
		networkRequestDataIngest.ingestData(fileName, buildID, pageTitle, applicationName, userId);
		//networkRttDataIngest.ingestData(fileName, buildID, pageTitle, applicationName, userId);
		networkServerLatencyDataIngest.ingestData(fileName, buildID, pageTitle, applicationName, userId);
		metricsDataIngest.ingestData(fileName, buildID, pageTitle, applicationName, userId);
		configSettingsDataIngest.ingestData(fileName, buildID, pageTitle, applicationName, userId);
	}
	
	private static boolean isPortInUse(int port) {
		try (ServerSocket serverSocket = new ServerSocket()) {
			serverSocket.setReuseAddress(true);
			serverSocket.bind(new InetSocketAddress(port));
			return false;
		} catch (IOException e) {
			return true;
		}
	}
}
