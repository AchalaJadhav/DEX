package com.dexbackend.dexbackend.pageanalyzer.model.metrics;


/**
 * The Class MetricsDetails.
 */
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
	
	/**
	 * Gets the observed cumulative layout shift.
	 *
	 * @return the observed cumulative layout shift
	 */
	public double getObservedCumulativeLayoutShift() {
		return observedCumulativeLayoutShift;
	}
	
	/**
	 * Sets the observed cumulative layout shift.
	 *
	 * @param observedCumulativeLayoutShift the new observed cumulative layout shift
	 */
	public void setObservedCumulativeLayoutShift(double observedCumulativeLayoutShift) {
		this.observedCumulativeLayoutShift = observedCumulativeLayoutShift;
	}
	
	/**
	 * Gets the observed dom content loaded.
	 *
	 * @return the observed dom content loaded
	 */
	public double getObservedDomContentLoaded() {
		return observedDomContentLoaded;
	}
	
	/**
	 * Sets the observed dom content loaded.
	 *
	 * @param observedDomContentLoaded the new observed dom content loaded
	 */
	public void setObservedDomContentLoaded(double observedDomContentLoaded) {
		this.observedDomContentLoaded = observedDomContentLoaded;
	}
	
	/**
	 * Gets the observed dom content loaded ts.
	 *
	 * @return the observed dom content loaded ts
	 */
	public double getObservedDomContentLoadedTs() {
		return observedDomContentLoadedTs;
	}
	
	/**
	 * Sets the observed dom content loaded ts.
	 *
	 * @param observedDomContentLoadedTs the new observed dom content loaded ts
	 */
	public void setObservedDomContentLoadedTs(double observedDomContentLoadedTs) {
		this.observedDomContentLoadedTs = observedDomContentLoadedTs;
	}
	
	/**
	 * Gets the observed first contentful paint.
	 *
	 * @return the observed first contentful paint
	 */
	public double getObservedFirstContentfulPaint() {
		return observedFirstContentfulPaint;
	}
	
	/**
	 * Sets the observed first contentful paint.
	 *
	 * @param observedFirstContentfulPaint the new observed first contentful paint
	 */
	public void setObservedFirstContentfulPaint(double observedFirstContentfulPaint) {
		this.observedFirstContentfulPaint = observedFirstContentfulPaint;
	}
	
	/**
	 * Gets the observed first contentful paint ts.
	 *
	 * @return the observed first contentful paint ts
	 */
	public double getObservedFirstContentfulPaintTs() {
		return observedFirstContentfulPaintTs;
	}
	
	/**
	 * Sets the observed first contentful paint ts.
	 *
	 * @param observedFirstContentfulPaintTs the new observed first contentful paint ts
	 */
	public void setObservedFirstContentfulPaintTs(double observedFirstContentfulPaintTs) {
		this.observedFirstContentfulPaintTs = observedFirstContentfulPaintTs;
	}
	
	/**
	 * Gets the observed first meaningful paint.
	 *
	 * @return the observed first meaningful paint
	 */
	public double getObservedFirstMeaningfulPaint() {
		return observedFirstMeaningfulPaint;
	}
	
	/**
	 * Sets the observed first meaningful paint.
	 *
	 * @param observedFirstMeaningfulPaint the new observed first meaningful paint
	 */
	public void setObservedFirstMeaningfulPaint(double observedFirstMeaningfulPaint) {
		this.observedFirstMeaningfulPaint = observedFirstMeaningfulPaint;
	}
	
	/**
	 * Gets the observed first meaningful paint ts.
	 *
	 * @return the observed first meaningful paint ts
	 */
	public double getObservedFirstMeaningfulPaintTs() {
		return observedFirstMeaningfulPaintTs;
	}
	
	/**
	 * Sets the observed first meaningful paint ts.
	 *
	 * @param observedFirstMeaningfulPaintTs the new observed first meaningful paint ts
	 */
	public void setObservedFirstMeaningfulPaintTs(double observedFirstMeaningfulPaintTs) {
		this.observedFirstMeaningfulPaintTs = observedFirstMeaningfulPaintTs;
	}
	
	/**
	 * Gets the observed first paint.
	 *
	 * @return the observed first paint
	 */
	public double getObservedFirstPaint() {
		return observedFirstPaint;
	}
	
	/**
	 * Sets the observed first paint.
	 *
	 * @param observedFirstPaint the new observed first paint
	 */
	public void setObservedFirstPaint(double observedFirstPaint) {
		this.observedFirstPaint = observedFirstPaint;
	}
	
	/**
	 * Gets the observed first paint ts.
	 *
	 * @return the observed first paint ts
	 */
	public double getObservedFirstPaintTs() {
		return observedFirstPaintTs;
	}
	
	/**
	 * Sets the observed first paint ts.
	 *
	 * @param observedFirstPaintTs the new observed first paint ts
	 */
	public void setObservedFirstPaintTs(double observedFirstPaintTs) {
		this.observedFirstPaintTs = observedFirstPaintTs;
	}
	
	/**
	 * Gets the observed first visual change.
	 *
	 * @return the observed first visual change
	 */
	public double getObservedFirstVisualChange() {
		return observedFirstVisualChange;
	}
	
	/**
	 * Sets the observed first visual change.
	 *
	 * @param observedFirstVisualChange the new observed first visual change
	 */
	public void setObservedFirstVisualChange(double observedFirstVisualChange) {
		this.observedFirstVisualChange = observedFirstVisualChange;
	}
	
	/**
	 * Gets the observed first visual change ts.
	 *
	 * @return the observed first visual change ts
	 */
	public double getObservedFirstVisualChangeTs() {
		return observedFirstVisualChangeTs;
	}
	
	/**
	 * Sets the observed first visual change ts.
	 *
	 * @param observedFirstVisualChangeTs the new observed first visual change ts
	 */
	public void setObservedFirstVisualChangeTs(double observedFirstVisualChangeTs) {
		this.observedFirstVisualChangeTs = observedFirstVisualChangeTs;
	}
	
	/**
	 * Gets the observed largest contentful paint.
	 *
	 * @return the observed largest contentful paint
	 */
	public double getObservedLargestContentfulPaint() {
		return observedLargestContentfulPaint;
	}
	
	/**
	 * Sets the observed largest contentful paint.
	 *
	 * @param observedLargestContentfulPaint the new observed largest contentful paint
	 */
	public void setObservedLargestContentfulPaint(double observedLargestContentfulPaint) {
		this.observedLargestContentfulPaint = observedLargestContentfulPaint;
	}
	
	/**
	 * Gets the observed largest contentful paint ts.
	 *
	 * @return the observed largest contentful paint ts
	 */
	public double getObservedLargestContentfulPaintTs() {
		return observedLargestContentfulPaintTs;
	}
	
	/**
	 * Sets the observed largest contentful paint ts.
	 *
	 * @param observedLargestContentfulPaintTs the new observed largest contentful paint ts
	 */
	public void setObservedLargestContentfulPaintTs(double observedLargestContentfulPaintTs) {
		this.observedLargestContentfulPaintTs = observedLargestContentfulPaintTs;
	}
	
	/**
	 * Gets the observed last visual change.
	 *
	 * @return the observed last visual change
	 */
	public double getObservedLastVisualChange() {
		return observedLastVisualChange;
	}
	
	/**
	 * Sets the observed last visual change.
	 *
	 * @param observedLastVisualChange the new observed last visual change
	 */
	public void setObservedLastVisualChange(double observedLastVisualChange) {
		this.observedLastVisualChange = observedLastVisualChange;
	}
	
	/**
	 * Gets the observed last visual change ts.
	 *
	 * @return the observed last visual change ts
	 */
	public double getObservedLastVisualChangeTs() {
		return observedLastVisualChangeTs;
	}
	
	/**
	 * Sets the observed last visual change ts.
	 *
	 * @param observedLastVisualChangeTs the new observed last visual change ts
	 */
	public void setObservedLastVisualChangeTs(double observedLastVisualChangeTs) {
		this.observedLastVisualChangeTs = observedLastVisualChangeTs;
	}
	
	/**
	 * Gets the observed load.
	 *
	 * @return the observed load
	 */
	public double getObservedLoad() {
		return observedLoad;
	}
	
	/**
	 * Sets the observed load.
	 *
	 * @param observedLoad the new observed load
	 */
	public void setObservedLoad(double observedLoad) {
		this.observedLoad = observedLoad;
	}
	
	/**
	 * Gets the observed load ts.
	 *
	 * @return the observed load ts
	 */
	public double getObservedLoadTs() {
		return observedLoadTs;
	}
	
	/**
	 * Sets the observed load ts.
	 *
	 * @param observedLoadTs the new observed load ts
	 */
	public void setObservedLoadTs(double observedLoadTs) {
		this.observedLoadTs = observedLoadTs;
	}
	
	/**
	 * Gets the observed navigation start.
	 *
	 * @return the observed navigation start
	 */
	public double getObservedNavigationStart() {
		return observedNavigationStart;
	}
	
	/**
	 * Sets the observed navigation start.
	 *
	 * @param observedNavigationStart the new observed navigation start
	 */
	public void setObservedNavigationStart(double observedNavigationStart) {
		this.observedNavigationStart = observedNavigationStart;
	}
	
	/**
	 * Gets the observed navigation start ts.
	 *
	 * @return the observed navigation start ts
	 */
	public double getObservedNavigationStartTs() {
		return observedNavigationStartTs;
	}
	
	/**
	 * Sets the observed navigation start ts.
	 *
	 * @param observedNavigationStartTs the new observed navigation start ts
	 */
	public void setObservedNavigationStartTs(double observedNavigationStartTs) {
		this.observedNavigationStartTs = observedNavigationStartTs;
	}
	
	/**
	 * Gets the observed speed index.
	 *
	 * @return the observed speed index
	 */
	public double getObservedSpeedIndex() {
		return observedSpeedIndex;
	}
	
	/**
	 * Sets the observed speed index.
	 *
	 * @param observedSpeedIndex the new observed speed index
	 */
	public void setObservedSpeedIndex(double observedSpeedIndex) {
		this.observedSpeedIndex = observedSpeedIndex;
	}
	
	/**
	 * Gets the observed speed index ts.
	 *
	 * @return the observed speed index ts
	 */
	public double getObservedSpeedIndexTs() {
		return observedSpeedIndexTs;
	}
	
	/**
	 * Sets the observed speed index ts.
	 *
	 * @param observedSpeedIndexTs the new observed speed index ts
	 */
	public void setObservedSpeedIndexTs(double observedSpeedIndexTs) {
		this.observedSpeedIndexTs = observedSpeedIndexTs;
	}
	
	/**
	 * Gets the observed trace end.
	 *
	 * @return the observed trace end
	 */
	public double getObservedTraceEnd() {
		return observedTraceEnd;
	}
	
	/**
	 * Sets the observed trace end.
	 *
	 * @param observedTraceEnd the new observed trace end
	 */
	public void setObservedTraceEnd(double observedTraceEnd) {
		this.observedTraceEnd = observedTraceEnd;
	}
	
	/**
	 * Gets the observed trace end ts.
	 *
	 * @return the observed trace end ts
	 */
	public double getObservedTraceEndTs() {
		return observedTraceEndTs;
	}
	
	/**
	 * Sets the observed trace end ts.
	 *
	 * @param observedTraceEndTs the new observed trace end ts
	 */
	public void setObservedTraceEndTs(double observedTraceEndTs) {
		this.observedTraceEndTs = observedTraceEndTs;
	}
	
}
