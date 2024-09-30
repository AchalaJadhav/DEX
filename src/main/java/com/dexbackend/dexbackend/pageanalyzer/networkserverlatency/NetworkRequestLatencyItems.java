package com.dexbackend.dexbackend.pageanalyzer.networkserverlatency;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class NetworkRequestLatencyItems.
 */
@Getter
@Setter
public class NetworkRequestLatencyItems {

	/** The build id. */
	public String buildId;

	/** The id. */
	public String id;

	/** The title. */
	public String title;

	/** The description. */
	public String description;

	/** The score. */
	public String score;

	/** The score display mode. */
	public String scoreDisplayMode;

	/** The numeric value. */
	public String numericValue;

	/** The numeric unit. */
	public String numericUnit;

	/** The display value. */
	public String displayValue;

	/** The requested url. */
	public String requestedUrl;

	/** The fetch time. */
	public String fetchTime;

	/** The page name. */
	public String pageName;

	/** The application name. */
	public String applicationName;

	/** The origin. */
	public String origin;

	/** The server response time. */
	public double serverResponseTime;
}
