package com.dexbackend.dexbackend.pageanalyzer.model.networkrequests;

import java.util.ArrayList;
import java.util.Map;


/**
 * The Class NetworkRequesDetails.
 */
public class NetworkRequesDetails 
{
	
	/** The type. */
	String type;
	
	/** The network request heading items map. */
	ArrayList<Map<Object,Object>> networkRequestHeadingItemsMap;

	
	/**
	 * Instantiates a new network reques details.
	 */
	public NetworkRequesDetails() 
	{
		super();
		networkRequestHeadingItemsMap = new ArrayList<Map<Object,Object>>();
	}


	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}


	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * Gets the network request heading items map.
	 *
	 * @return the network request heading items map
	 */
	public ArrayList<Map<Object, Object>> getNetworkRequestHeadingItemsMap() {
		return networkRequestHeadingItemsMap;
	}


	/**
	 * Sets the network request heading items map.
	 *
	 * @param networkRequestHeadingItemsMap the network request heading items map
	 */
	public void setNetworkRequestHeadingItemsMap(ArrayList<Map<Object, Object>> networkRequestHeadingItemsMap) {
		this.networkRequestHeadingItemsMap = networkRequestHeadingItemsMap;
	}


}
