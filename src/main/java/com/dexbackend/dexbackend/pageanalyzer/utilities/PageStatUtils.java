/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import com.deque.html.axecore.axeargs.AxeRunOptions;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.dexbackend.dexbackend.pageanalyzer.model.PageDetails;
import com.google.gson.Gson;


/**
 * The Class PageStatUtils.
 *
 * @author surendrane
 */
@Component
public class PageStatUtils {

	private int callCount = 0;
	/** The Constant REPORTS2. */
	private static final String REPORTS2 = "/reports/";

	/** The Constant USER_DIR. */
	private static final String USER_DIR = "user.dir";

	/** The Constant REPORTS. */
	private static final String REPORTS = "reports";

	/** The Constant OS_NAME. */
	private static final String OS_NAME = "os.name";

	/** The Constant WINDOWS. */
	private static final String WINDOWS = "windows";

	/** The Constant PORT. */
	private final static int PORT = 9222;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PageStatUtils.class);
	
	/** The export data. */
	@Autowired
	ExportData exportData;

	/** The env. */
	@Autowired
	Environment env;

	/**
	 * Validate node installation.
	 *
	 * @return true, if successful
	 */
	public boolean validateNodeInstallation() {
		boolean install = true;
		String command = "lighthouse --version";
		if (System.getProperty(OS_NAME).toLowerCase().contains(WINDOWS)) {
			command = new StringBuilder("cmd.exe /c ").append(command).toString();
		} else {
			command = new StringBuilder("/bin/sh -c ").append(command).toString();
		}
		ExecCommand execCommand = new ExecCommand(command);
		String error = execCommand.getError();
		if (error.contains("is not recognized")) {
			LOGGER.error(error);
			install = false;
		}
		return install;
	}

	/**
	 * Measure end user performance.
	 *
	 * @param driver the driver
	 * @param url       the url
	 * @param pageTitle the page title
	 */
	public void measureEndUserPerformance(final WebDriver driver, final String url, PageDetails pageDetails) {
		try {
			if (!new File(REPORTS).exists()) {
				new File(REPORTS).mkdir();
			}

			String currentDir = System.getProperty(USER_DIR);

			StringBuilder stringBuilder2 = new StringBuilder();
			stringBuilder2.append("lighthouse \"");
			stringBuilder2.append(url);
			stringBuilder2.append("\" --port=");
			stringBuilder2.append(PORT);
			
			if(pageDetails.getDeviceType() != null)
			
			{
				
				if(pageDetails.getDeviceType().equalsIgnoreCase("mobile"))
				{
					stringBuilder2.append(" --form-factor=mobile");
				}
				else if(pageDetails.getDeviceType().equalsIgnoreCase("desktop"))
				{
					stringBuilder2.append(" --preset=desktop");
				}
				else
				{
					stringBuilder2.append(" --preset=desktop");
				}
				
			}
			else
			{
				stringBuilder2.append(" --preset=desktop");
			}
			
//			if(pageDetails.getWidth() >= 1 && pageDetails.getHeight() >= 1) {
//				stringBuilder2.append("--screenEmulation.mobile --screenEmulation.width="+pageDetails.getWidth()+" --screenEmulation.height="+pageDetails.getHeight());
//			}
//			
//			if(pageDetails.getDeviceScaleFactor() <= 1 ) {
//				stringBuilder2.append("--screenEmulation.deviceScaleFactor="+pageDetails.getDeviceScaleFactor());
//			}
			
			stringBuilder2.append(" --output=json --output=html --output-path=./reports/");
			stringBuilder2.append(pageDetails.getPageTitle());
			stringBuilder2.append(".json");
//			stringBuilder2.append(".json --config-path=");
//			stringBuilder2.append(currentDir);
//			if(deviceType == "mobile")
//			stringBuilder2.append("\\config\\config_mobile.json");
//			else
//			stringBuilder2.append("\\config\\config_desktop.json");

			String command = stringBuilder2.toString();
			if (System.getProperty(OS_NAME).toLowerCase().contains(WINDOWS)) {
				command = new StringBuilder("cmd.exe /c ").append(command).toString();
			} else {
				command = new StringBuilder("/bin/sh -c ").append(command).toString();
			}

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(System.getProperty(USER_DIR));
			stringBuilder.append(REPORTS2);
			stringBuilder.append(pageDetails.getPageTitle());
			stringBuilder.append(".report.json");
			File f = new File(stringBuilder.toString());
			if (f.exists()) {
				if (f.delete()) {
					LOGGER.info(f.getName() + " deleted existing file with same name");
				}
			}

			File axe = new File(System.getProperty(USER_DIR) + REPORTS2 + pageDetails.getPageTitle() + ".axe.report.json");
			if (f.exists()) {
				if (f.delete()) {
					LOGGER.info(f.getName() + " deleted existing file with same name");
				}
			}
			while(callCount<4)
			{
				boolean status = new ExecCommand().execCommandforReportGenration(command);
				if(status == true)
				{
					break;
				}
				
				List<String> openWindows = new ArrayList<String>();
				Set<String> openWindowIDs= driver.getWindowHandles();
				openWindows.addAll(openWindowIDs);
//				close about.blank
				driver.switchTo().window(openWindows.get(1));
				if(driver.getCurrentUrl().equals("about:blank"))
				{
					driver.close();
				}
				driver.switchTo().window(openWindows.get(0));				
				callCount++;
			}
			AxeRunOptions runOptions = new AxeRunOptions();
			runOptions.setXPath(true);
			runOptions.setAbsolutePaths(true);
			runOptions.setIFrames(true);

			AxeBuilder builder = new AxeBuilder().withOptions(runOptions).withOutputFile(axe.getAbsolutePath());
			Results results = builder.analyze(driver);

			String resultsJson = new Gson().toJson(results);
			FileUtils.writeStringToFile(axe, resultsJson, Charset.defaultCharset());

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Start chrome driver.
	 * 
	 * @return true, if successful
	 */
	public boolean startChromeDriver() {
		boolean bool = true;
		if (validateNodeInstallation()) {
			try {
				String command = "chrome-debug --port=" + PORT;
				if (System.getProperty(OS_NAME).toLowerCase().contains(WINDOWS)) {
					command = new StringBuilder("cmd.exe /c ").append(command).toString();
				} else if (System.getProperty(OS_NAME).toLowerCase().contains("mac")) {

				} else {
					command = new StringBuilder("/bin/sh -c ").append(command).toString();
				}
				new ExecCommand(command);
			} catch (Exception e) {
				bool = false;
				LOGGER.error(e.getMessage(), e);
			}
		} else {
			bool = false;
			LOGGER.error("Failed to Detect Lighthouse");
		}
		if (bool == true)
			LOGGER.info("Starting ChromeDriver");
		return bool;
	}

	/**
	 * Stop chrome driver.
	 *
	 * @return true, if successful
	 */
	public boolean stopChromeDriver() {
		boolean bool = true;
		try {
			if (System.getProperty(OS_NAME).toLowerCase().contains(WINDOWS)) {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("cmd.exe /c for /f \"tokens=5\" %a in ('netstat -aon ^| find \":");
				stringBuilder.append(PORT);
				stringBuilder.append("\" ^| find \"LISTENING\"') do taskkill /f /pid %a");
				Runtime.getRuntime().exec(stringBuilder.toString());
			} else {
				Runtime.getRuntime().exec("sudo kill -9 $(sudo lsof -t -i:" + PORT + ")");
			}
		} catch (Exception e) {
			bool = false;
			LOGGER.error(e.getMessage(), e);
		}
		if (bool == true)
			LOGGER.info("ChromeDriver Stopped Successfully");
		return bool;
	}

	/**
	 * Gets the statistics.
	 *
	 * @param fileName the file name
	 * @param buildID the build ID
	 * @param pageTitle the page title
	 * @param applicationName the application name
	 * @param userId 
	 * @return the statistics
	 */
	public Boolean sendStatistics(final String fileName, final String buildID, final String pageTitle,
			final String applicationName, final int scans, String userId) {
		Boolean flag = true;
		try {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("./reports/");
			stringBuilder.append(pageTitle);
			stringBuilder.append(".report.json");
			File f = new File(stringBuilder.toString());
			long endTime = System.currentTimeMillis() + Integer.parseInt(env.getProperty("server.time"));
			while (!f.exists()) {

				if (System.currentTimeMillis() > endTime) {
					LOGGER.error("Problem in File Generation");
					break;
				}
			}

			if (f.exists()) {
				LOGGER.info(f.getName() + " found,proceeding ingestion");
				exportData.ingestToPrometheus(fileName, buildID, pageTitle, applicationName, scans, userId);
			} else {
				LOGGER.error(f.getName() + " not found,ingestion failed");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
		}
		return flag;
	}

	/**
	 * Show report.
	 *
	 * @param outputFile the output file
	 */
	public void showReport(final String outputFile) {
	}

	/**
	 * Execute command.
	 *
	 * @param command the command
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void executeCommand(String command) throws IOException {
		ProcessBuilder builder = new ProcessBuilder(command);
		builder.redirectErrorStream(true);
		Process p = builder.start();
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while (true) {
			line = r.readLine();
			if (line == null) {
				break;
			}
		}
	}
}