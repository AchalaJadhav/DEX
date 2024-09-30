/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.model.performance;


/**
 * The Class PerformanceMetrics.
 *
 * @author surendrane
 */
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
	
	public String getProtocol() {
			return protocol;
		}

		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}

		public long getStartTime() {
			return startTime;
		}

		public void setStartTime(long startTime) {
			this.startTime = startTime;
		}

		public long getEndTime() {
			return endTime;
		}

		public void setEndTime(long endTime) {
			this.endTime = endTime;
		}

		public String getFinished() {
			return finished;
		}

		public void setFinished(String finished) {
			this.finished = finished;
		}

		public long getTransferSize() {
			return transferSize;
		}

		public void setTransferSize(long transferSize) {
			this.transferSize = transferSize;
		}

		public long getResourceSize() {
			return resourceSize;
		}

		public void setResourceSize(long resourceSize) {
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

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
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

		public long getServerResponseTime() {
			return serverResponseTime;
		}

		public void setServerResponseTime(long serverResponseTime) {
			this.serverResponseTime = serverResponseTime;
		}

		public long getDuration() {
			return duration;
		}

		public void setDuration(long duration) {
			this.duration = duration;
		}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
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

	public long getRtt() {
		return rtt;
	}

	public void setRtt(long rtt) {
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

	/**
	 * Gets the builds the id.
	 *
	 * @return the builds the id
	 */
	public String getBuildId() {
		return buildId;
	}

	PerformanceItemDetails performanceItemDetails;

	public PerformanceItemDetails getPerformanceItemDetails() {
		return performanceItemDetails;
	}

	public void setPerformanceItemDetails(PerformanceItemDetails performanceItemDetails) {
		this.performanceItemDetails = performanceItemDetails;
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
	 * @param numericValue the new numeric value
	 */
	public void setNumericValue(long numericValue) {
		this.numericValue = numericValue;
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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Boolean getIsPass() {
		return isPass;
	}

	public void setIsPass(Boolean isPass) {
		this.isPass = isPass;
	}

	public long getTotalBytes() {
		return totalBytes;
	}

	public void setTotalBytes(long totalBytes) {
		this.totalBytes = totalBytes;
	}

	public long getWastedBytes() {
		return wastedBytes;
	}

	public void setWastedBytes(long wastedBytes) {
		this.wastedBytes = wastedBytes;
	}

	public double getWastedPercent() {
		return wastedPercent;
	}

	public void setWastedPercent(double wastedPercent) {
		this.wastedPercent = wastedPercent;
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

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public String getStatistic() {
		return statistic;
	}

	public void setStatistic(String statistic) {
		this.statistic = statistic;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getScripting() {
		return scripting;
	}

	public void setScripting(long scripting) {
		this.scripting = scripting;
	}

	public long getScriptParseCompile() {
		return scriptParseCompile;
	}

	public void setScriptParseCompile(long scriptParseCompile) {
		this.scriptParseCompile = scriptParseCompile;
	}

	public long getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(long requestCount) {
		this.requestCount = requestCount;
	}

	public String getMainThreadTime() {
		return mainThreadTime;
	}

	public void setMainThreadTime(String mainThreadTime) {
		this.mainThreadTime = mainThreadTime;
	}

	public long getBlockingTime() {
		return blockingTime;
	}

	public void setBlockingTime(long blockingTime) {
		this.blockingTime = blockingTime;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public long getCacheLifetimeMs() {
		return cacheLifetimeMs;
	}

	public void setCacheLifetimeMs(long cacheLifetimeMs) {
		this.cacheLifetimeMs = cacheLifetimeMs;
	}

	public String getGroupLabel() {
		return groupLabel;
	}

	public void setGroupLabel(String groupLabel) {
		this.groupLabel = groupLabel;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getOverallSavingsMs() {
		return overallSavingsMs;
	}

	public void setOverallSavingsMs(long overallSavingsMs) {
		this.overallSavingsMs = overallSavingsMs;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public String getAnimation() {
		return animation;
	}

	public void setAnimation(String animation) {
		this.animation = animation;
	}

	
	
	

}
