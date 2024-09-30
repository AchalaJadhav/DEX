/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.model.performance;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class OpportunityItems.
 *
 * @author surendrane
 */
@Getter
@Setter
public class OpportunityItems {
	
	//opportunity
		
		public long wastedMs;
		public String lhId;
		public String path;
		public String selector;
		public String requestStartTime;
		
		public String wastedWebpBytes;
		public long responseTime;
		public String statistic;
		public String value;
		public String total;
		public String scripting;
		public String scriptParseCompile;
		public String displayValue;

		//hidden
				public String numRequests;
				public String numScripts;
				public String numStylesheets;
				public String numFonts;
				public String numTasks;
				public String numTasksOver10ms;
				public String numTasksOver25ms;
				public String numTasksOver50ms;
				public String numTasksOver100ms;
				public String numTasksOver500ms;
				public String rtt;
				public String throughput;
				public String maxRtt;
				public String maxServerLatency;
				public String totalByteWeight;
				public String totalTaskTime;
				public String mainDocumentTransferSize;
				
				public String protocol;
				public String startTime;
				public String endTime;
				public String finished;
				public String transferSize;
				public String resourceSize;
				public String statusCode;
				public String mimeType;
				public String resourceType;
				public String origin;
				public String score;
				public String data;
				
				public String name;
				public String resourceBytes;
				
				public String serverResponseTime;
				public String duration;
	
	/** The build id. */
	public String buildId;
	
	/** The type. */
	public String type;
	
	public String group;
	
	/** The requested url. */
	public String requestedUrl;
	
	/** The fetch time. */
	public String fetchTime;
	
	/** The page name. */
	public String pageName;
	
	/** The application name. */
	public String applicationName;

	/** The title. */
	public String title;
	
	/** The description. */
	public String description;
	
	/** The numeric value. */
	public long numericValue;
	
	/** The url. */
	public String url;
	
	/** The total bytes. */
	public long totalBytes;
	
	/** The wasted bytes. */
	public long wastedBytes;
	
	/** The wasted percent. */
	public double wastedPercent= 0.0;
	private long overallSavingsMs;
}
