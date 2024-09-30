/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.model.accessibility;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class AccessibilityIssues.
 *
 * @author surendrane
 */
@Getter
@Setter
public class AccessibilityIssues {

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

	/** The title. */
	private String title;
	
	/** The description. */
	private String description;

	/** The wcag standard. */
	private String wcagStandard;
	
	/** The cat. */
	private String cat;
	
	/** The wcag rule. */
	private String wcagRule;
	
	/** The section. */
	private String section;

	/** The path. */
	private String path;
	
	/** The selector. */
	private String selector;
	
	/** The snippet. */
	private String snippet;
	
	/** The explanation. */
	private String explanation;
	
	/** The label. */
	private String label;
	
	/** The impact. */
	private String impact;
}
