/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.model.networkrequests;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class NetworkRequestItems.
 *
 * @author surendrane
 */
@Getter
@Setter
public class NetworkRequestItems {
	
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

	
	/** The url. */
	public String url;
	
	/** The transfer size. */
	public int transferSize;
	
	/** The status code. */
	public int statusCode;
	
	/** The start time. */
	public double startTime;
	
	/** The resource type. */
	public String resourceType;
	
	/** The resource size. */
	public int resourceSize;
	
	/** The mime type. */
	public String mimeType;
	
	/** The finished. */
	public boolean finished;
	
	/** The end time. */
	public double endTime;
}
