/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.driver;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.bouncycastle.asn1.x509.qualified.TypeOfBiometricData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dexbackend.dexbackend.pageanalyzer.config.ConfigProperties;
import com.dexbackend.dexbackend.pageanalyzer.utilities.PageStatUtils;

import io.github.bonigarcia.wdm.WebDriverManager;


/**
 * The Class PageStatDriver.
 *
 * @author surendrane
 */
@Component
public class PageStatDriver {

	/** The Constant WEBDRIVER_CHROME_DRIVER. */
	private static final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";

	/** The Constant IGNORE_CERTIFICATE_ERRORS. */
	private static final String IGNORE_CERTIFICATE_ERRORS = "--ignore-certificate-errors";

	/** The Constant SERVER_HOST. */
	private static final String SERVER_HOST= "127.0.0.1";
	
	/** The Constant SERVER_PORT. */
	private static final String SERVER_PORT= "9222";


	/** The Constant DEBUGGER_ADDRESS. */
	private static final String DEBUGGER_ADDRESS = "debuggerAddress";

	/** The Constant CHROMEDRIVER. */
	private static final String CHROMEDRIVER = "chromedriver";

	/** The Constant CHROMEDRIVER_EXE. */
	private static final String CHROMEDRIVER_EXE = "chromedriver.exe";

	/** The Constant WINDOWS. */
	private static final String WINDOWS = "windows";

	/** The Constant OS_NAME. */
	private static final String OS_NAME = "os.name";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PageStatDriver.class);
	
	@Autowired
	private ConfigProperties configProperties;

	/** The driver. */
	WebDriver driver;

	/** The page stat utils. */
	@Autowired
	PageStatUtils pageStatUtils;

	/**
	 * Gets the page stat driver.
	 *
	 * @return the page stat driver
	 */
	public WebDriver getPageStatDriver() {
		try {
			if (System.getProperty(OS_NAME).toLowerCase().contains(WINDOWS)) {
				System.setProperty("webdriver.http.factory", "jdk-http-client");
//				System.setProperty(WEBDRIVER_CHROME_DRIVER, CHROMEDRIVER_EXE);
			} else {
				System.setProperty(WEBDRIVER_CHROME_DRIVER, CHROMEDRIVER);
			}
			ChromeOptions options = new ChromeOptions();
			StringBuilder stringBuilder = new StringBuilder();
			options.addArguments("--remote-allow-origins=*","ignore-certificate-errors");
			stringBuilder.append(SERVER_HOST);
			stringBuilder.append(":");
			stringBuilder.append(SERVER_PORT);
			options.setExperimentalOption(DEBUGGER_ADDRESS, stringBuilder.toString());
			options.addArguments(IGNORE_CERTIFICATE_ERRORS);
			options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			if(configProperties.getChromedriver().trim().equalsIgnoreCase("automatic"))
			{
				LOGGER.info("ChromeDriver activated method: "+configProperties.getChromedriver());
				WebDriverManager.chromedriver().setup();	
			}
			LOGGER.info("ChromeDriverMethod : "+configProperties.getChromedriver());
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
		
		} catch (Exception e) {
			driver = null;
			LOGGER.error("ChromeDriver is null");
			LOGGER.error(e.getMessage(), e);
		}
		return driver;
	}

	/**
	 * Close page stat driver.
	 *
	 * @return true, if successful
	 */
	public boolean closePageStatDriver() {
		if (!Objects.isNull(driver)) {
			driver.quit();
		}
		return pageStatUtils.stopChromeDriver();
	}

}




