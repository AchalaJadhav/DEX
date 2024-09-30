package com.dexbackend.dexbackend.pageanalyzer.model.metrics;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class MetricsDetails.
 */
@Getter
@Setter
public class MetricsDetails {
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "MetricsDetails [buildId=" + buildId + ", fetchTime=" + fetchTime + ", requestedUrl=" + requestedUrl
				+ ", pageName=" + pageName + ", applicationName=" + applicationName + ", observedCumulativeLayoutShift="
				+ observedCumulativeLayoutShift + ", observedDomContentLoaded=" + observedDomContentLoaded
				+ ", observedDomContentLoadedTs=" + observedDomContentLoadedTs + ", observedFirstContentfulPaint="
				+ observedFirstContentfulPaint + ", observedFirstContentfulPaintTs=" + observedFirstContentfulPaintTs
				+ ", observedFirstMeaningfulPaint=" + observedFirstMeaningfulPaint + ", observedFirstMeaningfulPaintTs="
				+ observedFirstMeaningfulPaintTs + ", observedFirstPaint=" + observedFirstPaint
				+ ", observedFirstPaintTs=" + observedFirstPaintTs + ", observedFirstVisualChange="
				+ observedFirstVisualChange + ", observedFirstVisualChangeTs=" + observedFirstVisualChangeTs
				+ ", observedLargestContentfulPaint=" + observedLargestContentfulPaint
				+ ", observedLargestContentfulPaintTs=" + observedLargestContentfulPaintTs
				+ ", observedLastVisualChange=" + observedLastVisualChange + ", observedLastVisualChangeTs="
				+ observedLastVisualChangeTs + ", observedLoad=" + observedLoad + ", observedLoadTs=" + observedLoadTs
				+ ", observedNavigationStart=" + observedNavigationStart + ", observedNavigationStartTs="
				+ observedNavigationStartTs + ", observedSpeedIndex=" + observedSpeedIndex + ", observedSpeedIndexTs="
				+ observedSpeedIndexTs + ", observedTraceEnd=" + observedTraceEnd + ", observedTraceEndTs="
				+ observedTraceEndTs + ", getBuildId()=" + getBuildId() + ", getFetchTime()=" + getFetchTime()
				+ ", getRequestedUrl()=" + getRequestedUrl() + ", getPageName()=" + getPageName()
				+ ", getApplicationName()=" + getApplicationName() + ", getObservedCumulativeLayoutShift()="
				+ getObservedCumulativeLayoutShift() + ", getObservedDomContentLoaded()="
				+ getObservedDomContentLoaded() + ", getObservedDomContentLoadedTs()=" + getObservedDomContentLoadedTs()
				+ ", getObservedFirstContentfulPaint()=" + getObservedFirstContentfulPaint()
				+ ", getObservedFirstContentfulPaintTs()=" + getObservedFirstContentfulPaintTs()
				+ ", getObservedFirstMeaningfulPaint()=" + getObservedFirstMeaningfulPaint()
				+ ", getObservedFirstMeaningfulPaintTs()=" + getObservedFirstMeaningfulPaintTs()
				+ ", getObservedFirstPaint()=" + getObservedFirstPaint() + ", getObservedFirstPaintTs()="
				+ getObservedFirstPaintTs() + ", getObservedFirstVisualChange()=" + getObservedFirstVisualChange()
				+ ", getObservedFirstVisualChangeTs()=" + getObservedFirstVisualChangeTs()
				+ ", getObservedLargestContentfulPaint()=" + getObservedLargestContentfulPaint()
				+ ", getObservedLargestContentfulPaintTs()=" + getObservedLargestContentfulPaintTs()
				+ ", getObservedLastVisualChange()=" + getObservedLastVisualChange()
				+ ", getObservedLastVisualChangeTs()=" + getObservedLastVisualChangeTs() + ", getObservedLoad()="
				+ getObservedLoad() + ", getObservedLoadTs()=" + getObservedLoadTs() + ", getObservedNavigationStart()="
				+ getObservedNavigationStart() + ", getObservedNavigationStartTs()=" + getObservedNavigationStartTs()
				+ ", getObservedSpeedIndex()=" + getObservedSpeedIndex() + ", getObservedSpeedIndexTs()="
				+ getObservedSpeedIndexTs() + ", getObservedTraceEnd()=" + getObservedTraceEnd()
				+ ", getObservedTraceEndTs()=" + getObservedTraceEndTs() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	/** The build id. */
	private String buildId;
	
	/** The fetch time. */
	private String fetchTime;
	
	/** The requested url. */
	private String requestedUrl;
	
	/** The page name. */
	public String pageName;
	
	/** The application name. */
	public String applicationName;
	
	/** The observed cumulative layout shift. */
	public double observedCumulativeLayoutShift;
	
	/** The observed dom content loaded. */
	public double observedDomContentLoaded;
	
	/** The observed dom content loaded ts. */
	public double observedDomContentLoadedTs;
	
	/** The observed first contentful paint. */
	public double observedFirstContentfulPaint;
	
	/** The observed first contentful paint ts. */
	public double observedFirstContentfulPaintTs;
	
	/** The observed first meaningful paint. */
	public double observedFirstMeaningfulPaint;
	
	/** The observed first meaningful paint ts. */
	public double observedFirstMeaningfulPaintTs;
	
	/** The observed first paint. */
	public double observedFirstPaint;
	
	/** The observed first paint ts. */
	public double observedFirstPaintTs;
	
	/** The observed first visual change. */
	public double observedFirstVisualChange;
	
	/** The observed first visual change ts. */
	public double observedFirstVisualChangeTs;
	
	/** The observed largest contentful paint. */
	public double observedLargestContentfulPaint;
	
	/** The observed largest contentful paint ts. */
	public double observedLargestContentfulPaintTs;
	
	/** The observed last visual change. */
	public double observedLastVisualChange;
	
	/** The observed last visual change ts. */
	public double observedLastVisualChangeTs;
	
	/** The observed load. */
	public double observedLoad;
	
	/** The observed load ts. */
	public double observedLoadTs;
	
	/** The observed navigation start. */
	public double observedNavigationStart;	
	
	/** The observed navigation start ts. */
	public double observedNavigationStartTs;
	
	/** The observed speed index. */
	public double observedSpeedIndex;
	
	/** The observed speed index ts. */
	public double observedSpeedIndexTs;
	
	/** The observed trace end. */
	public double observedTraceEnd;
	
	/** The observed trace end ts. */
	public double observedTraceEndTs;
}
