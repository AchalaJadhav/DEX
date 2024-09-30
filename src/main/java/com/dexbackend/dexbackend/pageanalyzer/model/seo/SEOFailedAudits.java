/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.model.seo;

import org.apache.commons.lang.StringUtils;




/**
 * The Class SEOFailedAudits.
 *
 * @author surendrane
 */
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
	

	/**
	 * Gets the summary.
	 *
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Sets the summary.
	 *
	 * @param summary the new summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * Gets the target.
	 *
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * Sets the target.
	 *
	 * @param target the new target
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * Sets the group.
	 *
	 * @param group the new group
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * Gets the builds the id.
	 *
	 * @return the builds the id
	 */
	public String getBuildId() {
		return buildId;
	}

	/**
	 * Sets the builds the id.
	 *
	 * @param buildId the new builds the id
	 */
	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}

	/**
	 * Gets the requested url.
	 *
	 * @return the requested url
	 */
	public String getRequestedUrl() {
		return requestedUrl;
	}

	/**
	 * Sets the requested url.
	 *
	 * @param requestedUrl the new requested url
	 */
	public void setRequestedUrl(String requestedUrl) {
		this.requestedUrl = requestedUrl;
	}

	/**
	 * Gets the fetch time.
	 *
	 * @return the fetch time
	 */
	public String getFetchTime() {
		return fetchTime;
	}

	/**
	 * Sets the fetch time.
	 *
	 * @param fetchTime the new fetch time
	 */
	public void setFetchTime(String fetchTime) {
		this.fetchTime = fetchTime;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the page name.
	 *
	 * @return the page name
	 */
	public String getPageName() {
		return pageName;
	}

	/**
	 * Sets the page name.
	 *
	 * @param pageName the new page name
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	/**
	 * Gets the application name.
	 *
	 * @return the application name
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * Sets the application name.
	 *
	 * @param applicationName the new application name
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	public SEOItemDetails getSEOItemDetails() {
		return seoItemDetails;
	}

	public void setSEOItemDetails(SEOItemDetails seoItemDetails) {
		this.seoItemDetails = seoItemDetails;
	}

	public SEOItemDetails getSeoItemDetails() {
		return seoItemDetails;
	}

	public void setSeoItemDetails(SEOItemDetails seoItemDetails) {
		this.seoItemDetails = seoItemDetails;
	}

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIPath() {
		return IPath;
	}

	public void setIPath(String iPath) {
		IPath = iPath;
	}

	public String getISelector() {
		return ISelector;
	}

	public void setISelector(String iSelector) {
		ISelector = iSelector;
	}

	public String getISnippet() {
		return ISnippet;
	}

	public void setISnippet(String iSnippet) {
		ISnippet = iSnippet;
	}

	public String getINodeLabel() {
		return INodeLabel;
	}

	public void setINodeLabel(String iNodeLabel) {
		INodeLabel = iNodeLabel;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

	public String getCNodeLabel() {
		return CNodeLabel;
	}

	public void setCNodeLabel(String cNodeLabel) {
		CNodeLabel = cNodeLabel;
	}

	public String getIndex() {
		return Index;
	}

	public void setIndex(String index) {
		Index = index;
	}

	public String getLine() {
		return Line;
	}

	public void setLine(String line) {
		Line = line;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public String getTNodeLabel() {
		return TNodeLabel;
	}

	public void setTNodeLabel(String tNodeLabel) {
		TNodeLabel = tNodeLabel;
	}

	public String getTWidth() {
		return TWidth;
	}

	public void setTWidth(String tWidth) {
		TWidth = tWidth;
	}

	public String getTHeight() {
		return THeight;
	}

	public void setTHeight(String tHeight) {
		THeight = tHeight;
	}

	public String getONodeLabel() {
		return ONodeLabel;
	}

	public void setONodeLabel(String oNodeLabel) {
		ONodeLabel = oNodeLabel;
	}

	public String getOWidth() {
		return OWidth;
	}

	public void setOWidth(String oWidth) {
		OWidth = oWidth;
	}

	public String getOHeight() {
		return OHeight;
	}

	public void setOHeight(String oHeight) {
		OHeight = oHeight;
	}

	public String getTPath() {
		return TPath;
	}

	public void setTPath(String tPath) {
		TPath = tPath;
	}

	public String getTSelector() {
		return TSelector;
	}

	public void setTSelector(String tSelector) {
		TSelector = tSelector;
	}

	public String getOPath() {
		return OPath;
	}

	public void setOPath(String oPath) {
		OPath = oPath;
	}

	public String getOSelector() {
		return OSelector;
	}

	public void setOSelector(String oSelector) {
		OSelector = oSelector;
	}
	
	

}
