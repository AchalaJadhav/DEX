package com.dexbackend.dexbackend.pageanalyzer.model.configsettings;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ConfigSettings.
 */
@Getter
@Setter
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
