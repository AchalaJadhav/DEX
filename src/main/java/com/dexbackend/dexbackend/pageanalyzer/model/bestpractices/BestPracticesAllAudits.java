package com.dexbackend.dexbackend.pageanalyzer.model.bestpractices;

public class BestPracticesAllAudits {

	
	/** The build id. */
	public String buildId;
	
	public String getBuildId() {
		return buildId;
	}

	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}

	public String getRequestedUrl() {
		return requestedUrl;
	}

	public void setRequestedUrl(String requestedUrl) {
		this.requestedUrl = requestedUrl;
	}

	public String getFetchTime() {
		return fetchTime;
	}

	public void setFetchTime(String fetchTime) {
		this.fetchTime = fetchTime;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
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
	
	/** The group. */
	public String group;
	
	/** The score. */
	public String score;
}
