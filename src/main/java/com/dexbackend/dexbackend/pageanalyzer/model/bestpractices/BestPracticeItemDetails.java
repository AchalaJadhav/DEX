package com.dexbackend.dexbackend.pageanalyzer.model.bestpractices;

import java.util.ArrayList;
import java.util.Map;

public class BestPracticeItemDetails {


	/** The type. */
	String type;
	
	/** The network rtt heading items map. */
	ArrayList<Map<Object,Object>> bestpracticesHeadingItemsMap;
	
	
	/**
	 * Instantiates a new network rtt details.
	 */
	public BestPracticeItemDetails() 
	{
		super();
		bestpracticesHeadingItemsMap = new ArrayList<Map<Object,Object>>();
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public ArrayList<Map<Object, Object>> getBestpracticesHeadingItemsMap() {
		return bestpracticesHeadingItemsMap;
	}


	public void setBestpracticesHeadingItemsMap(ArrayList<Map<Object, Object>> bestpracticesHeadingItemsMap) {
		this.bestpracticesHeadingItemsMap = bestpracticesHeadingItemsMap;
	}
}
