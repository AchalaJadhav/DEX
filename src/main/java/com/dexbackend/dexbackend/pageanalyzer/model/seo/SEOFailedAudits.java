/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.model.seo;

import org.apache.commons.lang.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class SEOFailedAudits.
 *
 * @author surendrane
 */
@Getter
@Setter
public class SEOFailedAudits {

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
	
	SEOItemDetails seoItemDetails;

	/** The target. */
	public String target = StringUtils.EMPTY;
	
	/** The summary. */
	public String summary = StringUtils.EMPTY;
	
	String auditId;
	
	private String score;
	//SEO Content
	private String href;
	private String text;
	private String IPath;
	private String ISelector;
	private String ISnippet;
	private String INodeLabel;
	//SEO Crawl
	private String path;
	private String selector;
	private String CNodeLabel;
	private String Index;
	private String Line;
	private String Message;

	//SEO Mobile
	private String TNodeLabel;
	private String TWidth;
	private String THeight;
	private String ONodeLabel;
	private String OWidth;
	private String OHeight;
	private String TPath;
	private String TSelector;
	private String OPath;
	private String OSelector;
}
