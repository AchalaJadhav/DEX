/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.parser.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class AnalyzerAudits.
 *
 * @author surendrane
 */
@Getter
@Setter
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
}
