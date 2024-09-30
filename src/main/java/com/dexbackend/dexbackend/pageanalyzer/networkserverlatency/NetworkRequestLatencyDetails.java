package com.dexbackend.dexbackend.pageanalyzer.networkserverlatency;

import java.util.ArrayList;
import java.util.Map;


/**
 * The Class NetworkRequestLatencyDetails.
 */
public class NetworkRequestLatencyDetails {

	/** The type. */
	String type;

	/** The network request latency heading items map. */
	ArrayList<Map<Object, Object>> networkRequestLatencyHeadingItemsMap;

	/**
	 * Instantiates a new network request latency details.
	 */
	public NetworkRequestLatencyDetails() {
		super();
		networkRequestLatencyHeadingItemsMap = new ArrayList<Map<Object, Object>>();
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
	 * Gets the network request latency heading items map.
	 *
	 * @return the network request latency heading items map
	 */
	public ArrayList<Map<Object, Object>> getNetworkRequestLatencyHeadingItemsMap() {
		return networkRequestLatencyHeadingItemsMap;
	}

	/**
	 * Sets the network request latency heading items map.
	 *
	 * @param networkRequestLatencyHeadingItemsMap the network request latency
	 *                                             heading items map
	 */
	public void setNetworkRequestLatencyHeadingItemsMap(
			ArrayList<Map<Object, Object>> networkRequestLatencyHeadingItemsMap) {
		this.networkRequestLatencyHeadingItemsMap = networkRequestLatencyHeadingItemsMap;
	}

}
