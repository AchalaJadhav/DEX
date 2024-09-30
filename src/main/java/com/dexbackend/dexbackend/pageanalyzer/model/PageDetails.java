/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.model;

/**
 * The Class PageDetails.
 *
 * @author surendrane
 */
public class PageDetails {

	/** The page title. */
	private String pageTitle;

	/**  The build Id. */
	private String buildId;

	/** The application Name. */
	private String applicationName;

	/** The type scan. */
	private String typeScan = "Manual";
	
//	private Integer deviceScaleFactor;
//	
//	private Integer height;
//	
//	private Integer width;
	
	private String deviceType;

	private int scans = 0;
	
	private String url;
	
	private String userId;

	public int getScans() {
		return scans;
	}

	public void setScans(int scans) {
		this.scans = scans;
	}

	/**
	 * Gets the page title.
	 *
	 * @return the page title
	 */
	public String getPageTitle() {
		return pageTitle;
	}

	/**
	 * Sets the page title.
	 *
	 * @param pageTitle the new page title
	 */
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
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
	 * Gets the type scan.
	 *
	 * @return the type scan
	 */
	public String getTypeScan() {
		return typeScan;
	}

	/**
	 * Sets the type scan.
	 *
	 * @param typeScan the new type scan
	 */
	public void setTypeScan(String typeScan) {
		this.typeScan = typeScan;
	}


	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
