package com.dexbackend.dexbackend.pageanalyzer.model.networkrequests;



/**
 * The Class NetworkRequest.
 */
public class NetworkRequest 
{
	
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
	
	/** The fetch time. */
	private String fetchTime;
	
	/** The requested url. */
	private String requestedUrl;
	
	/** The network reques details. */
	NetworkRequesDetails networkRequesDetails;
	
	/**
	 * Instantiates a new network request.
	 */
	public NetworkRequest() 
	{
		super();
		networkRequesDetails = new NetworkRequesDetails();
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
	 * Gets the network reques details.
	 *
	 * @return the network reques details
	 */
	public NetworkRequesDetails getNetworkRequesDetails() {
		return networkRequesDetails;
	}

	/**
	 * Sets the network reques details.
	 *
	 * @param networkRequesDetails the new network reques details
	 */
	public void setNetworkRequesDetails(NetworkRequesDetails networkRequesDetails) {
		this.networkRequesDetails = networkRequesDetails;
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
	
}
