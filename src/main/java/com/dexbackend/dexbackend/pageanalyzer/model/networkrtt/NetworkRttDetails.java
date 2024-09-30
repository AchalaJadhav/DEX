package com.dexbackend.dexbackend.pageanalyzer.model.networkrtt;

import java.util.ArrayList;
import java.util.Map;


/**
 * The Class NetworkRttDetails.
 */
public class NetworkRttDetails 
{
	
	/** The type. */
	String type;
	
	/** The network rtt heading items map. */
	ArrayList<Map<Object,Object>> networkRttHeadingItemsMap;
	
	
	/**
	 * Instantiates a new network rtt details.
	 */
	public NetworkRttDetails() 
	{
		super();
		networkRttHeadingItemsMap = new ArrayList<Map<Object,Object>>();
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
	 * Gets the network rtt heading items map.
	 *
	 * @return the network rtt heading items map
	 */
	public ArrayList<Map<Object, Object>> getNetworkRttHeadingItemsMap() {
		return networkRttHeadingItemsMap;
	}


	/**
	 * Sets the network rtt heading items map.
	 *
	 * @param networkRttHeadingItemsMap the network rtt heading items map
	 */
	public void setNetworkRttHeadingItemsMap(ArrayList<Map<Object, Object>> networkRttHeadingItemsMap) {
		this.networkRttHeadingItemsMap = networkRttHeadingItemsMap;
	}
	
	
	

	
	


	
 

}
