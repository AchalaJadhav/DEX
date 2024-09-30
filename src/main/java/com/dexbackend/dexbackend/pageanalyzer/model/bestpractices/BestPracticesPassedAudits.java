package com.dexbackend.dexbackend.pageanalyzer.model.bestpractices;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BestPracticesPassedAudits {
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
	
	private String group;
}
