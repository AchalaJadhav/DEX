/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.model.performance;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class PerformanceMetrics.
 *
 * @author surendrane
 */
@Getter
@Setter
public class PerformanceCategories {

	/** The build id. */
	public String buildId;
	
	/** The build id. */
	public double score;
	public String group;

	//metrics
	public String displayValue;
	
	public long totalBytes;
	
	/** The wasted bytes. */
	public long wastedBytes;
	
	/** The wasted percent. */
	public double wastedPercent= 0.0;
	
	public long overallSavingsMs;
	
	
	//diagnostic
	
	public long wastedMs;
	public String lhId;
	public String path;
	public String selector;
	public String requestStartTime;
	
	public String wastedWebpBytes;
	public String responseTime;
	public String statistic;
	public long value;
	public long total;
	public long scripting;
	public long scriptParseCompile;
	public String product;

	
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
		public long rtt;
		public String throughput;
		public String maxRtt;
		public String maxServerLatency;
		public String totalByteWeight;
		public String totalTaskTime;
		public String mainDocumentTransferSize;
		
		public String protocol;
		public long startTime;
		public long endTime;
		public String finished;
		public long transferSize;
		public long resourceSize;
		public String statusCode;
		public String mimeType;
		public String resourceType;
		public String url;
		public String origin;
		public String label;
		public String text;
		public String data;
		
		public String name;
		public String resourceBytes;
		
		public long serverResponseTime;
		public long duration;
		
		public Boolean isPass;
		
		public long requestCount;
		public String mainThreadTime;
		
		public long blockingTime;
		public long cacheLifetimeMs;
		public String groupLabel;
		
		public String failureReason;
		public String animation;
		
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
		long numericValue = 0;
}
