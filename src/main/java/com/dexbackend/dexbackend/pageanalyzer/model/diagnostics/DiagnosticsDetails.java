package com.dexbackend.dexbackend.pageanalyzer.model.diagnostics;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class DiagnosticsDetails.
 */
@Getter
@Setter
public class DiagnosticsDetails {
	
	/** The build id. */
	private String buildId;
	
	/** The fetch time. */
	private String fetchTime;
	
	/** The requested url. */
	private String requestedUrl;
	
	/** The page name. */
	public String pageName;
	
	/** The application name. */
	public String applicationName;
	
	/** The id. */
	private String id;
	
	/** The title. */
	private String title;
	
	/** The description. */
	private String description;
	
	/** The score. */
	private String score ;
	
	/** The score display mode. */
	private String scoreDisplayMode;
	
	/** The display value. */
	private String displayValue ;
		
	/** The type. */
	private String type;
	
	/** The num requests. */
	private Long numRequests;
	
	/** The num scripts. */
	private Long numScripts;
	
	/** The num style sheets. */
	private Long numStyleSheets;
	
	/** The num fonts. */
	private int numFonts;
	
	/** The num tasks. */
	private Long numTasks;
	
	/** The num tasks over 10 ms. */
	private int numTasksOver10ms;
	
	/** The num tasks over 25 ms. */
	private int numTasksOver25ms;

	/** The num tasks over 50 ms. */
	private int numTasksOver50ms;
	
	/** The num tasks over 100 ms. */
	private int numTasksOver100ms;
	
	/** The num tasks over 500 ms. */
	private int numTasksOver500ms;


	/** The rtt. */
	private Long rtt;
	
	/** The throughput. */
	private Double throughput;
	
	/** The max rtt. */
	private Double maxRtt;
	
	/** The max server latency. */
	private Double maxServerLatency;

	/** The total byte weight. */
	private Double totalByteWeight;
	
	/** The total task time. */
	private Double totalTaskTime;
	
	/** The main document transfer size. */
	private Double mainDocumentTransferSize;
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "DiagnosticsDetails [id=" + id + ", title=" + title + ", description=" + description + ", score=" + score
				+ ", scoreDisplayMode=" + scoreDisplayMode + ", displayValue=" + displayValue + ", type=" + type
				+ ", numRequests=" + numRequests + ", numScripts=" + numScripts + ", numStyleSheets=" + numStyleSheets
				+ ", numFonts=" + numFonts + ", numTasks=" + numTasks + ", numTasksOver10ms=" + numTasksOver10ms
				+ ", numTasksOver25ms=" + numTasksOver25ms + ", numTasksOver50ms=" + numTasksOver50ms
				+ ", numTasksOver100ms=" + numTasksOver100ms + ", numTasksOver500ms=" + numTasksOver500ms + ", rtt="
				+ rtt + ", throughput=" + throughput + ", maxRtt=" + maxRtt + ", maxServerLatency=" + maxServerLatency
				+ ", totalByteWeight=" + totalByteWeight + ", totalTaskTime=" + totalTaskTime
				+ ", mainDocumentTransferSize=" + mainDocumentTransferSize + ", fetchTime=" + fetchTime
				+ ", requestedUrl=" + requestedUrl + "]";
	}
}
