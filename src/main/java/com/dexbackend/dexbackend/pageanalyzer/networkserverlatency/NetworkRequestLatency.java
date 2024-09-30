package com.dexbackend.dexbackend.pageanalyzer.networkserverlatency;


/**
 * The Class NetworkRequestLatency.
 */
public class NetworkRequestLatency {

	/** The id. */
	private String id;

	/** The title. */
	private String title;

	/** The description. */
	private String description;

	/** The score. */
	private String score;

	/** The score display mode. */
	private String scoreDisplayMode;

	/** The numeric value. */
	private String numericValue;

	/** The numeric unit. */
	private String numericUnit;

	/** The display value. */
	private String displayValue;

	/** The requested url. */
	private String requestedUrl;

	/** The fetch time. */
	private String fetchTime;

	/** The network request latency details. */
	NetworkRequestLatencyDetails networkRequestLatencyDetails;

	/**
	 * Instantiates a new network request latency.
	 */
	public NetworkRequestLatency() {
		super();
		networkRequestLatencyDetails = new NetworkRequestLatencyDetails();
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
	 * Gets the numeric value.
	 *
	 * @return the numeric value
	 */
	public String getNumericValue() {
		return numericValue;
	}

	/**
	 * Sets the numeric value.
	 *
	 * @param numericValue the new numeric value
	 */
	public void setNumericValue(String numericValue) {
		this.numericValue = numericValue;
	}

	/**
	 * Gets the numeric unit.
	 *
	 * @return the numeric unit
	 */
	public String getNumericUnit() {
		return numericUnit;
	}

	/**
	 * Sets the numeric unit.
	 *
	 * @param numericUnit the new numeric unit
	 */
	public void setNumericUnit(String numericUnit) {
		this.numericUnit = numericUnit;
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
	 * Gets the network request latency details.
	 *
	 * @return the network request latency details
	 */
	public NetworkRequestLatencyDetails getNetworkRequestLatencyDetails() {
		return networkRequestLatencyDetails;
	}

	/**
	 * Sets the network request latency details.
	 *
	 * @param networkRequestLatencyDetails the new network request latency details
	 */
	public void setNetworkRequestLatencyDetails(NetworkRequestLatencyDetails networkRequestLatencyDetails) {
		this.networkRequestLatencyDetails = networkRequestLatencyDetails;
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

}
