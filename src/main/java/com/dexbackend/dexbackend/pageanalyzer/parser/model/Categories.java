/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.parser.model;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


/**
 * The Class Categories.
 *
 * @author surendrane
 */
public class Categories {

	/** The id. */
	public String id;
	
	
	/** The weight. */
	public String weight;
	
	/** The group. */
	public String group = StringUtils.EMPTY;

	String acronym;
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	


	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * Sets the weight.
	 *
	 * @param weight the new weight
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * Sets the group.
	 *
	 * @param group the new group
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	

}
