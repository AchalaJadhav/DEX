package com.dexbackend.dexbackend.pageanalyzer.model.bestpractices;

import org.apache.commons.lang.StringUtils;

public class BestPracticesFailedAudits {

	/** The build id. */
	public String buildId;
	
	/** The requested url. */
	public String requestedUrl;
	
	/** The requested url. */
	public String url;
	
	/** The requested resolution. */
	public String resolution;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

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
	
	BestPracticeItemDetails bestPracticeItemDetails;
	
	/** The group. */
	public String group;
	
	/** The score. */
	public String score;

	public BestPracticesFailedAudits() {
		super();
		bestPracticeItemDetails = new BestPracticeItemDetails();
	}
	
	public BestPracticeItemDetails getBestPracticeItemDetails() {
		return bestPracticeItemDetails;
	}

	public void setBestPracticeItemDetails(BestPracticeItemDetails bestPracticeItemDetails) {
		this.bestPracticeItemDetails = bestPracticeItemDetails;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	/** The target. */
	public String target = StringUtils.EMPTY;
	
	/** The summary. */
	public String summary = StringUtils.EMPTY;

	/**
	 * Gets the summary.
	 *
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Sets the summary.
	 *
	 * @param summary the new summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * Gets the target.
	 *
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * Sets the target.
	 *
	 * @param target the new target
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * Sets the group.
	 *
	 * @param group the new group
	 */
	public void setGroup(String group) {
		this.group = group;
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
}
