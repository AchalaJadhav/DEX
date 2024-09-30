package com.dexbackend.dexbackend.pageanalyzer.model.diagnostics;


/**
 * The Class DiagnosticsDetails.
 */
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
	
	/**
	 * Gets the num tasks.
	 *
	 * @return the num tasks
	 */
	public Long getNumTasks() {
		return numTasks;
	}
	
	/**
	 * Sets the num tasks.
	 *
	 * @param numTasks the new num tasks
	 */
	public void setNumTasks(Long numTasks) {
		this.numTasks = numTasks;
	}
	
	/**
	 * Gets the num tasks over 10 ms.
	 *
	 * @return the num tasks over 10 ms
	 */
	public int getNumTasksOver10ms() {
		return numTasksOver10ms;
	}
	
	/**
	 * Sets the num tasks over 10 ms.
	 *
	 * @param numTasksOver10ms the new num tasks over 10 ms
	 */
	public void setNumTasksOver10ms(int numTasksOver10ms) {
		this.numTasksOver10ms = numTasksOver10ms;
	}
	
	/**
	 * Gets the num tasks over 25 ms.
	 *
	 * @return the num tasks over 25 ms
	 */
	public int getNumTasksOver25ms() {
		return numTasksOver25ms;
	}
	
	/**
	 * Sets the num tasks over 25 ms.
	 *
	 * @param numTasksOver25ms the new num tasks over 25 ms
	 */
	public void setNumTasksOver25ms(int numTasksOver25ms) {
		this.numTasksOver25ms = numTasksOver25ms;
	}
	
	/**
	 * Gets the num tasks over 50 ms.
	 *
	 * @return the num tasks over 50 ms
	 */
	public int getNumTasksOver50ms() {
		return numTasksOver50ms;
	}
	
	/**
	 * Sets the num tasks over 50 ms.
	 *
	 * @param numTasksOver50ms the new num tasks over 50 ms
	 */
	public void setNumTasksOver50ms(int numTasksOver50ms) {
		this.numTasksOver50ms = numTasksOver50ms;
	}
	
	/**
	 * Gets the num tasks over 100 ms.
	 *
	 * @return the num tasks over 100 ms
	 */
	public int getNumTasksOver100ms() {
		return numTasksOver100ms;
	}
	
	/**
	 * Sets the num tasks over 100 ms.
	 *
	 * @param numTasksOver100ms the new num tasks over 100 ms
	 */
	public void setNumTasksOver100ms(int numTasksOver100ms) {
		this.numTasksOver100ms = numTasksOver100ms;
	}
	
	/**
	 * Gets the num tasks over 500 ms.
	 *
	 * @return the num tasks over 500 ms
	 */
	public int getNumTasksOver500ms() {
		return numTasksOver500ms;
	}
	
	/**
	 * Sets the num tasks over 500 ms.
	 *
	 * @param numTasksOver500ms the new num tasks over 500 ms
	 */
	public void setNumTasksOver500ms(int numTasksOver500ms) {
		this.numTasksOver500ms = numTasksOver500ms;
	}
	
	/**
	 * Gets the throughput.
	 *
	 * @return the throughput
	 */
	public Double getThroughput() {
		return throughput;
	}
	
	/**
	 * Sets the throughput.
	 *
	 * @param throughput the new throughput
	 */
	public void setThroughput(Double throughput) {
		this.throughput = throughput;
	}
	
	/**
	 * Gets the max rtt.
	 *
	 * @return the max rtt
	 */
	public Double getMaxRtt() {
		return maxRtt;
	}
	
	/**
	 * Sets the max rtt.
	 *
	 * @param maxRtt the new max rtt
	 */
	public void setMaxRtt(Double maxRtt) {
		this.maxRtt = maxRtt;
	}
	
	/**
	 * Gets the max server latency.
	 *
	 * @return the max server latency
	 */
	public Double getMaxServerLatency() {
		return maxServerLatency;
	}
	
	/**
	 * Sets the max server latency.
	 *
	 * @param maxServerLatency the new max server latency
	 */
	public void setMaxServerLatency(Double maxServerLatency) {
		this.maxServerLatency = maxServerLatency;
	}
	
	/**
	 * Gets the total byte weight.
	 *
	 * @return the total byte weight
	 */
	public Double getTotalByteWeight() {
		return totalByteWeight;
	}
	
	/**
	 * Sets the total byte weight.
	 *
	 * @param totalByteWeight the new total byte weight
	 */
	public void setTotalByteWeight(Double totalByteWeight) {
		this.totalByteWeight = totalByteWeight;
	}
	
	/**
	 * Gets the total task time.
	 *
	 * @return the total task time
	 */
	public Double getTotalTaskTime() {
		return totalTaskTime;
	}
	
	/**
	 * Sets the total task time.
	 *
	 * @param totalTaskTime the new total task time
	 */
	public void setTotalTaskTime(Double totalTaskTime) {
		this.totalTaskTime = totalTaskTime;
	}
	
	/**
	 * Gets the main document transfer size.
	 *
	 * @return the main document transfer size
	 */
	public Double getMainDocumentTransferSize() {
		return mainDocumentTransferSize;
	}
	
	/**
	 * Sets the main document transfer size.
	 *
	 * @param mainDocumentTransferSize the new main document transfer size
	 */
	public void setMainDocumentTransferSize(Double mainDocumentTransferSize) {
		this.mainDocumentTransferSize = mainDocumentTransferSize;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
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
	 * Gets the score.
	 *
	 * @return the score
	 */
	public String getScore() {
		return score;
	}
	
	/**
	 * Sets the score.
	 *
	 * @param score the new score
	 */
	public void setScore(String score) {
		this.score = score;
	}
	
	/**
	 * Gets the score display mode.
	 *
	 * @return the score display mode
	 */
	public String getScoreDisplayMode() {
		return scoreDisplayMode;
	}
	
	/**
	 * Sets the score display mode.
	 *
	 * @param scoreDisplayMode the new score display mode
	 */
	public void setScoreDisplayMode(String scoreDisplayMode) {
		this.scoreDisplayMode = scoreDisplayMode;
	}
	
	/**
	 * Gets the display value.
	 *
	 * @return the display value
	 */
	public String getDisplayValue() {
		return displayValue;
	}
	
	/**
	 * Sets the display value.
	 *
	 * @param displayValue the new display value
	 */
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	
	/**
	 * Gets the num requests.
	 *
	 * @return the num requests
	 */
	public Long getNumRequests() {
		return numRequests;
	}
	
	/**
	 * Sets the num requests.
	 *
	 * @param numRequests the new num requests
	 */
	public void setNumRequests(Long numRequests) {
		this.numRequests = numRequests;
	}
	
	/**
	 * Gets the num scripts.
	 *
	 * @return the num scripts
	 */
	public Long getNumScripts() {
		return numScripts;
	}
	
	/**
	 * Sets the num scripts.
	 *
	 * @param numScripts the new num scripts
	 */
	public void setNumScripts(Long numScripts) {
		this.numScripts = numScripts;
	}
	
	/**
	 * Gets the num style sheets.
	 *
	 * @return the num style sheets
	 */
	public Long getNumStyleSheets() {
		return numStyleSheets;
	}
	
	/**
	 * Sets the num style sheets.
	 *
	 * @param numStyleSheets the new num style sheets
	 */
	public void setNumStyleSheets(Long numStyleSheets) {
		this.numStyleSheets = numStyleSheets;
	}
	
	/**
	 * Gets the num fonts.
	 *
	 * @return the num fonts
	 */
	public int getNumFonts() {
		return numFonts;
	}
	
	/**
	 * Sets the num fonts.
	 *
	 * @param numFonts the new num fonts
	 */
	public void setNumFonts(int numFonts) {
		this.numFonts = numFonts;
	}
	
	/**
	 * Gets the rtt.
	 *
	 * @return the rtt
	 */
	public Long getRtt() {
		return rtt;
	}
	
	/**
	 * Sets the rtt.
	 *
	 * @param rtt the new rtt
	 */
	public void setRtt(Long rtt) {
		this.rtt = rtt;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
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


}
