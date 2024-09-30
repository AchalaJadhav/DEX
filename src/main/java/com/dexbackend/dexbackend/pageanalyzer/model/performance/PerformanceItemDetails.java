package com.dexbackend.dexbackend.pageanalyzer.model.performance;

import java.util.ArrayList;
import java.util.Map;

public class PerformanceItemDetails {
	/** The type. */
	String type;
	
	/** The network rtt heading items map. */
	ArrayList<Map<Object,Object>> performanceHeadingItemsMap;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<Map<Object, Object>> getPerformanceHeadingItemsMap() {
		return performanceHeadingItemsMap;
	}

	public void setPerformanceHeadingItemsMap(ArrayList<Map<Object, Object>> performanceHeadingItemsMap) {
		this.performanceHeadingItemsMap = performanceHeadingItemsMap;
	}
	

}
