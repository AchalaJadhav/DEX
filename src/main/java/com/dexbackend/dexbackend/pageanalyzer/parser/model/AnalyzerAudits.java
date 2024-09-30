/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.parser.model;


/**
 * The Class AnalyzerAudits.
 *
 * @author surendrane
 */
public class AnalyzerAudits {

	/** The performance score. */
	private double performanceScore = 0.0;
	
	/** The accessibility score. */
	private double accessibilityScore = 0.0;
	
	/** The seoscore. */
	private double seoscore = 0.0;
	
	/** The pwascore. */
	private double pwascore = 0.0;
	
	/** The bestpractices score. */
	private double bestpracticesScore = 0.0;

	/** The build id. */
	private String buildId;
	
	/** The requested url. */
	private String requestedUrl;
	
	/** The fetch time. */
	private String fetchTime;
	
	/** The page name. */
	private String pageName;
	
	/** The application name. */
	private String applicationName;

	private int scans = 0;
	public int getScans() {
		return scans;
	}

	public void setScans(int scans) {
		this.scans = scans;
	}

	/**
	 * Gets the performance score.
	 *
	 * @return the performance score
	 */
	public double getPerformanceScore() {
		return performanceScore;
	}

	/**
	 * Sets the performance score.
	 *
	 * @param performanceScore the new performance score
	 */
	public void setPerformanceScore(double performanceScore) {
		this.performanceScore = performanceScore;
	}

	/**
	 * Gets the accessibility score.
	 *
	 * @return the accessibility score
	 */
	public double getAccessibilityScore() {
		return accessibilityScore;
	}

	/**
	 * Sets the accessibility score.
	 *
	 * @param accessibilityScore the new accessibility score
	 */
	public void setAccessibilityScore(double accessibilityScore) {
		this.accessibilityScore = accessibilityScore;
	}

	/**
	 * Gets the seoscore.
	 *
	 * @return the seoscore
	 */
	public double getSeoscore() {
		return seoscore;
	}

	/**
	 * Sets the seoscore.
	 *
	 * @param seoscore the new seoscore
	 */
	public void setSeoscore(double seoscore) {
		this.seoscore = seoscore;
	}

	/**
	 * Gets the pwascore.
	 *
	 * @return the pwascore
	 */
	public double getPwascore() {
		return pwascore;
	}

	/**
	 * Sets the pwascore.
	 *
	 * @param pwascore the new pwascore
	 */
	public void setPwascore(double pwascore) {
		this.pwascore = pwascore;
	}

	/**
	 * Gets the bestpractices score.
	 *
	 * @return the bestpractices score
	 */
	public double getBestpracticesScore() {
		return bestpracticesScore;
	}

	/**
	 * Sets the bestpractices score.
	 *
	 * @param bestpracticesScore the new bestpractices score
	 */
	public void setBestpracticesScore(double bestpracticesScore) {
		this.bestpracticesScore = bestpracticesScore;
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
