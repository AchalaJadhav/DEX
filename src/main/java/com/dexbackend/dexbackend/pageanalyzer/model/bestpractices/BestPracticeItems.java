package com.dexbackend.dexbackend.pageanalyzer.model.bestpractices;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;

public class BestPracticeItems {
 
	
	//Audit wise items 
	
	//is-on-https
	/** The requested url. */
	public String url;
	
	/** The requested resolution. */
	public String resolution;
	
	//no-vulnerable-libraries
	String hightestSeverity;

	String vulnCount;
	String dectectedLibText; 
	
	String dectectedLibUrl; 

	
	
	// external-anchors-use-rel-noopener

	
	// valid-source-maps


	//General 
	String auditId;
	String GDescription;
	String GUrl;
	String scriptUrl;
	String sourceMapUrl;
	String error;
	String IUrl;
	String issueType;
	String noType;
	String NoUrl;
	String path;
	String value;

	//TrustAndSafety
	String severity;
	String description2;
	
	//UX
	String tsurl;
	String expextedSize;
	String displayedSize;
	String RUrl;
	String DisplayedAspectRatio;
	String ActualAspectRatio;

	/** The build id. */
	public String buildId;
	
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
	
	/** The target. */
	public String target = StringUtils.EMPTY;
	
	/** The summary. */
	public String summary = StringUtils.EMPTY;

	


	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

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

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public String getHightestSeverity() {
		return hightestSeverity;
	}

	public void setHightestSeverity(String hightestSeverity) {
		this.hightestSeverity = hightestSeverity;
	}

	public String getVulnCount() {
		return vulnCount;
	}

	public void setVulnCount(String vulnCount) {
		this.vulnCount = vulnCount;
	}

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
	public String getDectectedLibText() {
		return dectectedLibText;
	}


	
	public String getCSPSeverity() {
		return severity;
	}
	
	public String getCSPDescription() {
		return description2;
	}

	public void setCSPSeverity(String severity) {
		this.severity = severity;
		
	}

	public void setCSPDescription(String description) {
		this.description2 = description;
		
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getDescription2() {
		return description2;
	}

	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	public String getTsurl() {
		return tsurl;
	}

	public void setTsurl(String tsurl) {
		this.tsurl = tsurl;
	}

	public String getExpextedSize() {
		return expextedSize;
	}

	public void setExpextedSize(String expextedSize) {
		this.expextedSize = expextedSize;
	}

	public String getDisplayedSize() {
		return displayedSize;
	}

	public void setDisplayedSize(String displayedSize) {
		this.displayedSize = displayedSize;
	}

	public void setTSUrl(String url) {
		this.tsurl = url;
		
	}

	public String getGDescription() {
		return GDescription;
	}

	public void setGDescription(String gDescription) {
		GDescription = gDescription;
	}

	public String getGUrl() {
		return GUrl;
	}

	public void setGUrl(String gUrl) {
		GUrl = gUrl;
	}
	

	public void setDectectedLibText(String dectectedLibText) {
		this.dectectedLibText = dectectedLibText;
	}

	public String getDectectedLibUrl() {
		return dectectedLibUrl;
	}

	public void setDectectedLibUrl(String dectectedLibUrl) {
		this.dectectedLibUrl = dectectedLibUrl;
	}
	
	public String getScriptUrl() {
		return scriptUrl;
	}

	public void setScriptUrl(String scriptUrl) {
		this.scriptUrl = scriptUrl;
	}

	public String getSourceMapUrl() {
		return sourceMapUrl;
	}

	public void setSourceMapUrl(String sourceMapUrl) {
		this.sourceMapUrl = sourceMapUrl;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}


	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getIUrl() {
		return IUrl;
	}

	public void setIUrl(String IURL) {
		IUrl = IURL;
	}

	public String getNoType() {
		return noType;
	}

	public void setNoType(String noType) {
		this.noType = noType;
	}

	public String getNoUrl() {
		return NoUrl;
	}

	public void setNoUrl(String noUrl) {
		NoUrl = noUrl;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRUrl() {
		return RUrl;
	}

	public void setRUrl(String rUrl) {
		RUrl = rUrl;
	}

	public String getDisplayedAspectRatio() {
		return DisplayedAspectRatio;
	}

	public void setDisplayedAspectRatio(String displayedAspectRatio) {
		DisplayedAspectRatio = displayedAspectRatio;
	}

	public String getActualAspectRatio() {
		return ActualAspectRatio;
	}

	public void setActualAspectRatio(String actualAspectRatio) {
		ActualAspectRatio = actualAspectRatio;
	}

	
	
	
}
