package com.dexbackend.dexbackend.pageanalyzer.model.seo;

import java.util.ArrayList;
import java.util.Map;

public class SEOItemDetails {
	
	/** The type. */
	String type;
	
	/** The seo heading items map. */
	ArrayList<Map<Object,Object>> seoHeadingItemsMap;
	
	
	/**
	 * Instantiates a new network rtt details.
	 */
	public SEOItemDetails() 
	{
		super();
		seoHeadingItemsMap = new ArrayList<Map<Object,Object>>();
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public ArrayList<Map<Object, Object>> getSEOHeadingItemsMap() {
		return seoHeadingItemsMap;
	}


	public void setSEOHeadingItemsMap(ArrayList<Map<Object, Object>> seoHeadingItemsMap) {
		this.seoHeadingItemsMap = seoHeadingItemsMap;
	}
}



