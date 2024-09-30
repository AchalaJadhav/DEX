package com.dexbackend.dexbackend.pageanalyzer.model.bestpractices;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
	
	public void setCSPSeverity(String severity) {
		this.severity = severity;
	}
	
	public void setCSPDescription(String description) {
		this.description2 = description;
	}
	
	public void setTSUrl(String url) {
		this.tsurl = url;
	}
}
