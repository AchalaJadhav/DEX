/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.model.seo;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class SEOPassedAudits.
 *
 * @author surendrane
 */
@Getter
@Setter
public class SEOPassedAudits {

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
}
