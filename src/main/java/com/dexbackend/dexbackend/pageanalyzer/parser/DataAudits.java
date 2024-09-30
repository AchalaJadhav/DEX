/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.parser;


/**
 * The Interface DataAudits.
 *
 * @author surendrane
 */
public interface DataAudits {

	/**
	 * Ingest data.
	 *
	 * @param fileName the file name
	 * @param buildID the build ID
	 * @param pageTitle the page title
	 * @param applicationName the application name
	 * @param scans 
	 * @throws Exception the exception
	 */
	public void ingestData(final String fileName, final String buildID, final String pageTitle, final String applicationName) throws Exception;
	public void ingestGeneralData(final String fileName, final String buildID, final String pageTitle, final String applicationName, final int scans) throws Exception;

	// void ingestGeneralData(String fileName, String buildID, String pageTitle, String applicationName) throws Exception;

	// public void ingestData(String fileName, String buildID, String pageTitle, String applicationName, int scans);

}
