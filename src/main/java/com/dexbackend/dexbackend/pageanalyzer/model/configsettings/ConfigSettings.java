package com.dexbackend.dexbackend.pageanalyzer.model.configsettings;


/**
 * The Class ConfigSettings.
 */
public class ConfigSettings {

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
	
	/** The max wait for fcp. */
	private Integer maxWaitForFcp;
	
	/** The max wait for load. */
	private Integer maxWaitForLoad;
	
	/** The throttling method. */
	private String throttlingMethod;
	
	/** The audit mode. */
	private Boolean auditMode;
	
	/** The gather mode. */
	private Boolean gatherMode;
	
	/** The disable storage reset. */
	private Boolean disableStorageReset;
	
	/** The emulated form factor. */
	private String emulatedFormFactor;
	
	/** The internal disable device screen emulation. */
	private boolean internalDisableDeviceScreenEmulation;
	
	/** The channel. */
	private String channel;
	
	/** The budgets. */
	private Object budgets;
	
	/** The locale. */
	private String locale;
	
	/** The blocked url patterns. */
	private Object blockedUrlPatterns;
	
	/** The additional trace categories. */
	private Object additionalTraceCategories;
	
	/** The extra headers. */
	private Object extraHeaders;
	
	/** The precomputed lantern data. */
	private Object precomputedLanternData;
	
	/** The only audits. */
	private Object onlyAudits;
	
	/** The only categories. */
	private Object onlyCategories;
	
	/** The skip audits. */
	private Object skipAudits;
	
	/** The rtt ms. */
	private Double rttMs;
	
	/** The throughput kbps. */
	private Double throughputKbps;
	
	/** The request latency ms. */
	private Double requestLatencyMs;
	
	/** The download throughput kbps. */
	private Double downloadThroughputKbps;
	
	/** The upload throughput kbps. */
	private Double uploadThroughputKbps;
	
	/** The cpu slowdown multiplier. */
	private Double cpuSlowdownMultiplier;
	
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
	
	/**
	 * Gets the max wait for fcp.
	 *
	 * @return the max wait for fcp
	 */
	public Integer getMaxWaitForFcp() {
		return maxWaitForFcp;
	}
	
	/**
	 * Sets the max wait for fcp.
	 *
	 * @param maxWaitForFcp the new max wait for fcp
	 */
	public void setMaxWaitForFcp(Integer maxWaitForFcp) {
		this.maxWaitForFcp = maxWaitForFcp;
	}
	
	/**
	 * Gets the max wait for load.
	 *
	 * @return the max wait for load
	 */
	public Integer getMaxWaitForLoad() {
		return maxWaitForLoad;
	}
	
	/**
	 * Sets the max wait for load.
	 *
	 * @param maxWaitForLoad the new max wait for load
	 */
	public void setMaxWaitForLoad(Integer maxWaitForLoad) {
		this.maxWaitForLoad = maxWaitForLoad;
	}
	
	/**
	 * Gets the throttling method.
	 *
	 * @return the throttling method
	 */
	public String getThrottlingMethod() {
		return throttlingMethod;
	}
	
	/**
	 * Sets the throttling method.
	 *
	 * @param throttlingMethod the new throttling method
	 */
	public void setThrottlingMethod(String throttlingMethod) {
		this.throttlingMethod = throttlingMethod;
	}
	
	/**
	 * Gets the audit mode.
	 *
	 * @return the audit mode
	 */
	public Boolean getAuditMode() {
		return auditMode;
	}
	
	/**
	 * Sets the audit mode.
	 *
	 * @param auditMode the new audit mode
	 */
	public void setAuditMode(Boolean auditMode) {
		this.auditMode = auditMode;
	}
	
	/**
	 * Gets the gather mode.
	 *
	 * @return the gather mode
	 */
	public Boolean getGatherMode() {
		return gatherMode;
	}
	
	/**
	 * Sets the gather mode.
	 *
	 * @param gatherMode the new gather mode
	 */
	public void setGatherMode(Boolean gatherMode) {
		this.gatherMode = gatherMode;
	}
	
	/**
	 * Gets the disable storage reset.
	 *
	 * @return the disable storage reset
	 */
	public Boolean getDisableStorageReset() {
		return disableStorageReset;
	}
	
	/**
	 * Sets the disable storage reset.
	 *
	 * @param disableStorageReset the new disable storage reset
	 */
	public void setDisableStorageReset(Boolean disableStorageReset) {
		this.disableStorageReset = disableStorageReset;
	}
	
	/**
	 * Gets the emulated form factor.
	 *
	 * @return the emulated form factor
	 */
	public String getEmulatedFormFactor() {
		return emulatedFormFactor;
	}
	
	/**
	 * Sets the emulated form factor.
	 *
	 * @param emulatedFormFactor the new emulated form factor
	 */
	public void setEmulatedFormFactor(String emulatedFormFactor) {
		this.emulatedFormFactor = emulatedFormFactor;
	}
	
	/**
	 * Checks if is internal disable device screen emulation.
	 *
	 * @return true, if is internal disable device screen emulation
	 */
	public boolean isInternalDisableDeviceScreenEmulation() {
		return internalDisableDeviceScreenEmulation;
	}
	
	/**
	 * Sets the internal disable device screen emulation.
	 *
	 * @param internalDisableDeviceScreenEmulation the new internal disable device screen emulation
	 */
	public void setInternalDisableDeviceScreenEmulation(boolean internalDisableDeviceScreenEmulation) {
		this.internalDisableDeviceScreenEmulation = internalDisableDeviceScreenEmulation;
	}
	
	/**
	 * Gets the channel.
	 *
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}
	
	/**
	 * Sets the channel.
	 *
	 * @param channel the new channel
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	/**
	 * Gets the budgets.
	 *
	 * @return the budgets
	 */
	public Object getBudgets() {
		return budgets;
	}
	
	/**
	 * Sets the budgets.
	 *
	 * @param budgets the new budgets
	 */
	public void setBudgets(Object budgets) {
		this.budgets = budgets;
	}
	
	/**
	 * Gets the locale.
	 *
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}
	
	/**
	 * Sets the locale.
	 *
	 * @param locale the new locale
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	/**
	 * Gets the blocked url patterns.
	 *
	 * @return the blocked url patterns
	 */
	public Object getBlockedUrlPatterns() {
		return blockedUrlPatterns;
	}
	
	/**
	 * Sets the blocked url patterns.
	 *
	 * @param blockedUrlPatterns the new blocked url patterns
	 */
	public void setBlockedUrlPatterns(Object blockedUrlPatterns) {
		this.blockedUrlPatterns = blockedUrlPatterns;
	}
	
	/**
	 * Gets the additional trace categories.
	 *
	 * @return the additional trace categories
	 */
	public Object getAdditionalTraceCategories() {
		return additionalTraceCategories;
	}
	
	/**
	 * Sets the additional trace categories.
	 *
	 * @param additionalTraceCategories the new additional trace categories
	 */
	public void setAdditionalTraceCategories(Object additionalTraceCategories) {
		this.additionalTraceCategories = additionalTraceCategories;
	}
	
	/**
	 * Gets the extra headers.
	 *
	 * @return the extra headers
	 */
	public Object getExtraHeaders() {
		return extraHeaders;
	}
	
	/**
	 * Sets the extra headers.
	 *
	 * @param extraHeaders the new extra headers
	 */
	public void setExtraHeaders(Object extraHeaders) {
		this.extraHeaders = extraHeaders;
	}
	
	/**
	 * Gets the precomputed lantern data.
	 *
	 * @return the precomputed lantern data
	 */
	public Object getPrecomputedLanternData() {
		return precomputedLanternData;
	}
	
	/**
	 * Sets the precomputed lantern data.
	 *
	 * @param precomputedLanternData the new precomputed lantern data
	 */
	public void setPrecomputedLanternData(Object precomputedLanternData) {
		this.precomputedLanternData = precomputedLanternData;
	}
	
	/**
	 * Gets the only audits.
	 *
	 * @return the only audits
	 */
	public Object getOnlyAudits() {
		return onlyAudits;
	}
	
	/**
	 * Sets the only audits.
	 *
	 * @param onlyAudits the new only audits
	 */
	public void setOnlyAudits(Object onlyAudits) {
		this.onlyAudits = onlyAudits;
	}
	
	/**
	 * Gets the only categories.
	 *
	 * @return the only categories
	 */
	public Object getOnlyCategories() {
		return onlyCategories;
	}
	
	/**
	 * Sets the only categories.
	 *
	 * @param onlyCategories the new only categories
	 */
	public void setOnlyCategories(Object onlyCategories) {
		this.onlyCategories = onlyCategories;
	}
	
	/**
	 * Gets the skip audits.
	 *
	 * @return the skip audits
	 */
	public Object getSkipAudits() {
		return skipAudits;
	}
	
	/**
	 * Sets the skip audits.
	 *
	 * @param skipAudits the new skip audits
	 */
	public void setSkipAudits(Object skipAudits) {
		this.skipAudits = skipAudits;
	}
	
	/**
	 * Gets the rtt ms.
	 *
	 * @return the rtt ms
	 */
	public Double getRttMs() {
		return rttMs;
	}
	
	/**
	 * Sets the rtt ms.
	 *
	 * @param rttMs the new rtt ms
	 */
	public void setRttMs(Double rttMs) {
		this.rttMs = rttMs;
	}
	
	/**
	 * Gets the throughput kbps.
	 *
	 * @return the throughput kbps
	 */
	public Double getThroughputKbps() {
		return throughputKbps;
	}
	
	/**
	 * Sets the throughput kbps.
	 *
	 * @param throughputKbps the new throughput kbps
	 */
	public void setThroughputKbps(Double throughputKbps) {
		this.throughputKbps = throughputKbps;
	}
	
	/**
	 * Gets the request latency ms.
	 *
	 * @return the request latency ms
	 */
	public Double getRequestLatencyMs() {
		return requestLatencyMs;
	}
	
	/**
	 * Sets the request latency ms.
	 *
	 * @param requestLatencyMs the new request latency ms
	 */
	public void setRequestLatencyMs(Double requestLatencyMs) {
		this.requestLatencyMs = requestLatencyMs;
	}
	
	/**
	 * Gets the download throughput kbps.
	 *
	 * @return the download throughput kbps
	 */
	public Double getDownloadThroughputKbps() {
		return downloadThroughputKbps;
	}
	
	/**
	 * Sets the download throughput kbps.
	 *
	 * @param downloadThroughputKbps the new download throughput kbps
	 */
	public void setDownloadThroughputKbps(Double downloadThroughputKbps) {
		this.downloadThroughputKbps = downloadThroughputKbps;
	}
	
	/**
	 * Gets the upload throughput kbps.
	 *
	 * @return the upload throughput kbps
	 */
	public Double getUploadThroughputKbps() {
		return uploadThroughputKbps;
	}
	
	/**
	 * Sets the upload throughput kbps.
	 *
	 * @param uploadThroughputKbps the new upload throughput kbps
	 */
	public void setUploadThroughputKbps(Double uploadThroughputKbps) {
		this.uploadThroughputKbps = uploadThroughputKbps;
	}
	
	/**
	 * Gets the cpu slowdown multiplier.
	 *
	 * @return the cpu slowdown multiplier
	 */
	public Double getCpuSlowdownMultiplier() {
		return cpuSlowdownMultiplier;
	}
	
	/**
	 * Sets the cpu slowdown multiplier.
	 *
	 * @param cpuSlowdownMultiplier the new cpu slowdown multiplier
	 */
	public void setCpuSlowdownMultiplier(Double cpuSlowdownMultiplier) {
		this.cpuSlowdownMultiplier = cpuSlowdownMultiplier;
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "ConfigSettings [buildId=" + buildId + ", requestedUrl=" + requestedUrl + ", fetchTime=" + fetchTime
				+ ", pageName=" + pageName + ", applicationName=" + applicationName + ", maxWaitForFcp=" + maxWaitForFcp
				+ ", maxWaitForLoad=" + maxWaitForLoad + ", throttlingMethod=" + throttlingMethod + ", auditMode="
				+ auditMode + ", gatherMode=" + gatherMode + ", disableStorageReset=" + disableStorageReset
				+ ", emulatedFormFactor=" + emulatedFormFactor + ", internalDisableDeviceScreenEmulation="
				+ internalDisableDeviceScreenEmulation + ", channel=" + channel + ", budgets=" + budgets + ", locale="
				+ locale + ", blockedUrlPatterns=" + blockedUrlPatterns + ", additionalTraceCategories="
				+ additionalTraceCategories + ", extraHeaders=" + extraHeaders + ", precomputedLanternData="
				+ precomputedLanternData + ", onlyAudits=" + onlyAudits + ", onlyCategories=" + onlyCategories
				+ ", skipAudits=" + skipAudits + ", rttMs=" + rttMs + ", throughputKbps=" + throughputKbps
				+ ", requestLatencyMs=" + requestLatencyMs + ", downloadThroughputKbps=" + downloadThroughputKbps
				+ ", uploadThroughputKbps=" + uploadThroughputKbps + ", cpuSlowdownMultiplier=" + cpuSlowdownMultiplier
				+ ", getBuildId()=" + getBuildId() + ", getRequestedUrl()=" + getRequestedUrl() + ", getFetchTime()="
				+ getFetchTime() + ", getPageName()=" + getPageName() + ", getApplicationName()=" + getApplicationName()
				+ ", getMaxWaitForFcp()=" + getMaxWaitForFcp() + ", getMaxWaitForLoad()=" + getMaxWaitForLoad()
				+ ", getThrottlingMethod()=" + getThrottlingMethod() + ", getAuditMode()=" + getAuditMode()
				+ ", getGatherMode()=" + getGatherMode() + ", getDisableStorageReset()=" + getDisableStorageReset()
				+ ", getEmulatedFormFactor()=" + getEmulatedFormFactor() + ", isInternalDisableDeviceScreenEmulation()="
				+ isInternalDisableDeviceScreenEmulation() + ", getChannel()=" + getChannel() + ", getBudgets()="
				+ getBudgets() + ", getLocale()=" + getLocale() + ", getBlockedUrlPatterns()=" + getBlockedUrlPatterns()
				+ ", getAdditionalTraceCategories()=" + getAdditionalTraceCategories() + ", getExtraHeaders()="
				+ getExtraHeaders() + ", getPrecomputedLanternData()=" + getPrecomputedLanternData()
				+ ", getOnlyAudits()=" + getOnlyAudits() + ", getOnlyCategories()=" + getOnlyCategories()
				+ ", getSkipAudits()=" + getSkipAudits() + ", getRttMs()=" + getRttMs() + ", getThroughputKbps()="
				+ getThroughputKbps() + ", getRequestLatencyMs()=" + getRequestLatencyMs()
				+ ", getDownloadThroughputKbps()=" + getDownloadThroughputKbps() + ", getUploadThroughputKbps()="
				+ getUploadThroughputKbps() + ", getCpuSlowdownMultiplier()=" + getCpuSlowdownMultiplier()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
	
}
