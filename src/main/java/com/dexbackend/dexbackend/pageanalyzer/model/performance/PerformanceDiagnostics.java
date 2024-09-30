/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.model.performance;


/**
 * The Class PerformanceDiagnostics.
 *
 * @author surendrane
 */
public class PerformanceDiagnostics {

	/** The build id. */
	public String buildId;
	
	/** The requested url. */
	public String requestedUrl;
	
	/** The fetch time. */
	public String fetchTime;

	/** The title. */
	public String title;
	
	/** The description. */
	public String description;
	
	/** The display value. */
	public String displayValue;

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

}
