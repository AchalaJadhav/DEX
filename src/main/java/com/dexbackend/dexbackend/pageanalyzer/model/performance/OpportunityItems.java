/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.model.performance;


/**
 * The Class OpportunityItems.
 *
 * @author surendrane
 */
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	/**
	 * Gets the builds the id.
	 *
	 * @return the builds the id
	 */
	public String getBuildId() {
		return buildId;
	}

	/**
	 * Sets the builds the id.
	 *
	 * @param buildId the new builds the id
	 */
	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}

	/**
	 * Gets the requested url.
	 *
	 * @return the requested url
	 */
	public String getRequestedUrl() {
		return requestedUrl;
	}

	/**
	 * Sets the requested url.
	 *
	 * @param requestedUrl the new requested url
	 */
	public void setRequestedUrl(String requestedUrl) {
		this.requestedUrl = requestedUrl;
	}

	/**
	 * Gets the fetch time.
	 *
	 * @return the fetch time
	 */
	public String getFetchTime() {
		return fetchTime;
	}

	/**
	 * Sets the fetch time.
	 *
	 * @param fetchTime the new fetch time
	 */
	public void setFetchTime(String fetchTime) {
		this.fetchTime = fetchTime;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	/**
	 * Gets the numeric value.
	 *
	 * @return the numeric value
	 */
	public long getNumericValue() {
		return numericValue;
	}

	/**
	 * Sets the numeric value.
	 *
	 * @param string the new numeric value
	 */
	public void setNumericValue(long numericValue) {
		this.numericValue = numericValue;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the total bytes.
	 *
	 * @return the total bytes
	 */
	public long getTotalBytes() {
		return totalBytes;
	}

	/**
	 * Sets the total bytes.
	 *
	 * @param totalBytes the new total bytes
	 */
	public void setTotalBytes(long totalBytes) {
		this.totalBytes = totalBytes;
	}

	/**
	 * Gets the wasted bytes.
	 *
	 * @return the wasted bytes
	 */
	public long getWastedBytes() {
		return wastedBytes;
	}

	/**
	 * Sets the wasted bytes.
	 *
	 * @param wastedBytes the new wasted bytes
	 */
	public void setWastedBytes(long wastedBytes) {
		this.wastedBytes = wastedBytes;
	}

	/**
	 * Gets the wasted percent.
	 *
	 * @return the wasted percent
	 */
	public double getWastedPercent() {
		return wastedPercent;
	}

	/**
	 * Sets the wasted percent.
	 *
	 * @param wastedPercent the new wasted percent
	 */
	public void setWastedPercent(double wastedPercent) {
		this.wastedPercent = wastedPercent;
	}

	/**
	 * Gets the page name.
	 *
	 * @return the page name
	 */
	public String getPageName() {
		return pageName;
	}

	/**
	 * Sets the page name.
	 *
	 * @param pageName the new page name
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	/**
	 * Gets the application name.
	 *
	 * @return the application name
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * Sets the application name.
	 *
	 * @param applicationName the new application name
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public long getWastedMs() {
		return wastedMs;
	}

	public void setWastedMs(long wastedMs) {
		this.wastedMs = wastedMs;
	}

	public String getLhId() {
		return lhId;
	}

	public void setLhId(String lhId) {
		this.lhId = lhId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

	public String getRequestStartTime() {
		return requestStartTime;
	}

	public void setRequestStartTime(String requestStartTime) {
		this.requestStartTime = requestStartTime;
	}

	public String getWastedWebpBytes() {
		return wastedWebpBytes;
	}

	public void setWastedWebpBytes(String wastedWebpBytes) {
		this.wastedWebpBytes = wastedWebpBytes;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	public String getStatistic() {
		return statistic;
	}

	public void setStatistic(String statistic) {
		this.statistic = statistic;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getScripting() {
		return scripting;
	}

	public void setScripting(String scripting) {
		this.scripting = scripting;
	}

	public String getScriptParseCompile() {
		return scriptParseCompile;
	}

	public void setScriptParseCompile(String scriptParseCompile) {
		this.scriptParseCompile = scriptParseCompile;
	}

	public String getNumRequests() {
		return numRequests;
	}

	public void setNumRequests(String numRequests) {
		this.numRequests = numRequests;
	}

	public String getNumScripts() {
		return numScripts;
	}

	public void setNumScripts(String numScripts) {
		this.numScripts = numScripts;
	}

	public String getNumStylesheets() {
		return numStylesheets;
	}

	public void setNumStylesheets(String numStylesheets) {
		this.numStylesheets = numStylesheets;
	}

	public String getNumFonts() {
		return numFonts;
	}

	public void setNumFonts(String numFonts) {
		this.numFonts = numFonts;
	}

	public String getNumTasks() {
		return numTasks;
	}

	public void setNumTasks(String numTasks) {
		this.numTasks = numTasks;
	}

	public String getNumTasksOver10ms() {
		return numTasksOver10ms;
	}

	public void setNumTasksOver10ms(String numTasksOver10ms) {
		this.numTasksOver10ms = numTasksOver10ms;
	}

	public String getNumTasksOver25ms() {
		return numTasksOver25ms;
	}

	public void setNumTasksOver25ms(String numTasksOver25ms) {
		this.numTasksOver25ms = numTasksOver25ms;
	}

	public String getNumTasksOver50ms() {
		return numTasksOver50ms;
	}

	public void setNumTasksOver50ms(String numTasksOver50ms) {
		this.numTasksOver50ms = numTasksOver50ms;
	}

	public String getNumTasksOver100ms() {
		return numTasksOver100ms;
	}

	public void setNumTasksOver100ms(String numTasksOver100ms) {
		this.numTasksOver100ms = numTasksOver100ms;
	}

	public String getNumTasksOver500ms() {
		return numTasksOver500ms;
	}

	public void setNumTasksOver500ms(String numTasksOver500ms) {
		this.numTasksOver500ms = numTasksOver500ms;
	}

	public String getRtt() {
		return rtt;
	}

	public void setRtt(String rtt) {
		this.rtt = rtt;
	}

	public String getThroughput() {
		return throughput;
	}

	public void setThroughput(String throughput) {
		this.throughput = throughput;
	}

	public String getMaxRtt() {
		return maxRtt;
	}

	public void setMaxRtt(String maxRtt) {
		this.maxRtt = maxRtt;
	}

	public String getMaxServerLatency() {
		return maxServerLatency;
	}

	public void setMaxServerLatency(String maxServerLatency) {
		this.maxServerLatency = maxServerLatency;
	}

	public String getTotalByteWeight() {
		return totalByteWeight;
	}

	public void setTotalByteWeight(String totalByteWeight) {
		this.totalByteWeight = totalByteWeight;
	}

	public String getTotalTaskTime() {
		return totalTaskTime;
	}

	public void setTotalTaskTime(String totalTaskTime) {
		this.totalTaskTime = totalTaskTime;
	}

	public String getMainDocumentTransferSize() {
		return mainDocumentTransferSize;
	}

	public void setMainDocumentTransferSize(String mainDocumentTransferSize) {
		this.mainDocumentTransferSize = mainDocumentTransferSize;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getFinished() {
		return finished;
	}

	public void setFinished(String finished) {
		this.finished = finished;
	}

	public String getTransferSize() {
		return transferSize;
	}

	public void setTransferSize(String transferSize) {
		this.transferSize = transferSize;
	}

	public String getResourceSize() {
		return resourceSize;
	}

	public void setResourceSize(String resourceSize) {
		this.resourceSize = resourceSize;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResourceBytes() {
		return resourceBytes;
	}

	public void setResourceBytes(String resourceBytes) {
		this.resourceBytes = resourceBytes;
	}

	public String getServerResponseTime() {
		return serverResponseTime;
	}

	public void setServerResponseTime(String serverResponseTime) {
		this.serverResponseTime = serverResponseTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		displayValue = displayValue;
	}
	
	public long getOverallSavingsMs() {
		return overallSavingsMs;
	}

	public void setOverallSavingsMs(long overallSavingsMs) {
		this.overallSavingsMs = overallSavingsMs;
	}

}
