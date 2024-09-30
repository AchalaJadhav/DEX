/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.model.networkrtt;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class NetworkRttItems.
 *
 * @author surendrane
 */
@Getter
@Setter
public class NetworkRttItems {
	
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
	

	/** The id. */
	public String id;
	
	/** The title. */
	public String title;
	
	/** The description. */
	public String description;
	
	/** The score display mode. */
	public String scoreDisplayMode;
	
	/** The score. */
	public String score;
	
	/** The numeric unit. */
	public String numericUnit;
	
	/** The numeric value. */
	public String numericValue;
	
	/** The display value. */
	public String displayValue;

	/** The origin. */
	public String origin;
	
	/** The rtt. */
	public double rtt;
}
