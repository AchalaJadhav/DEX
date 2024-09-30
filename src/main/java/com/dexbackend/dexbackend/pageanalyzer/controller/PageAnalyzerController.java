/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dexbackend.dexbackend.pageanalyzer.driver.PageStatDriver;
import com.dexbackend.dexbackend.pageanalyzer.model.AnalyzerResponse;
import com.dexbackend.dexbackend.pageanalyzer.model.PageDetails;
import com.dexbackend.dexbackend.pageanalyzer.model.PageDetailsWithUrlList;
import com.dexbackend.dexbackend.pageanalyzer.model.WebCrawlerModel;
import com.dexbackend.dexbackend.pageanalyzer.utilities.ElasticsearchService;
import com.dexbackend.dexbackend.pageanalyzer.utilities.PageStatUtils;
import com.dexbackend.dexbackend.pageanalyzer.utilities.PowerPointService;
import com.dexbackend.dexbackend.pageanalyzer.utilities.WebCrawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import org.apache.xmlbeans.XmlException;

/**
 * The Class PageAnalyzerController.
 *
 * @author surendrane
 */

@RestController
@RequestMapping("/api/v1")
public class PageAnalyzerController {
	
	int scanCount = 0;
	@Autowired
	PageStatUtils pageStatUtils;

	/** The page stat driver. */
	@Autowired
	PageStatDriver pageStatDriver;

	/** The driver. */
	WebDriver driver;

	PageDetails pageDetails;
	
	@Autowired
	WebCrawler webCrawler;
	
	private PowerPointService powerPointService;
    private ElasticsearchService elasticsearchService;

    @Autowired
    public PageAnalyzerController(ElasticsearchService elasticsearchService, PowerPointService powerPointService) {
        this.elasticsearchService = elasticsearchService;
        this.powerPointService = powerPointService;
    }


	private static final Logger LOGGER = LoggerFactory.getLogger(PageAnalyzerController.class);
//	private static final String AutomationPracticeLandingPage = "AutomationPracticeLandingPage";

	/**
	 * Instantiates a new page analyzer controller.
	 */
	public PageAnalyzerController() {
		pageStatDriver = new PageStatDriver();
	}

	/**
	 * Initialize And Launch analyzer.
	 *
	 * @return the response entity
	 */
	@CrossOrigin(origins = "*")
	@PostMapping("/initializeAndLaunch")
	public ResponseEntity<AnalyzerResponse> initializeAndAnalyzer() {
		AnalyzerResponse analyzerResponse = new AnalyzerResponse();
		ResponseEntity<AnalyzerResponse> responseEntity;
		if (pageStatUtils.startChromeDriver()) {
			driver = pageStatDriver.getPageStatDriver();
			if (Objects.isNull(driver)) {
				analyzerResponse
						.setResponseMessage("Please check whether the pre-requisites installations have been done.");
				responseEntity = new ResponseEntity<>(analyzerResponse, HttpStatus.FAILED_DEPENDENCY);
			} else {
				analyzerResponse.setResponseMessage("Successfully launched browser, type in your application url.");
				responseEntity = new ResponseEntity<>(analyzerResponse, HttpStatus.OK);
			}
		} else {
			analyzerResponse.setResponseMessage("Pre-requisite not met, please install required software installed.");
			responseEntity = new ResponseEntity<>(analyzerResponse, HttpStatus.FORBIDDEN);
		}
		return responseEntity;
	}

	/**
	 * Attach analyzer.
	 *
	 * @return the response entity
	 */
	@CrossOrigin(origins = "*")
	@PostMapping("/initialize")
	public ResponseEntity<AnalyzerResponse> initializeAnalyzer() {
		AnalyzerResponse analyzerResponse = new AnalyzerResponse();
		ResponseEntity<AnalyzerResponse> responseEntity;

		if (pageStatUtils.startChromeDriver()) {
			driver = pageStatDriver.getPageStatDriver();
			analyzerResponse.setResponseMessage("Successfully launched browser, type in your application url.");
			responseEntity = new ResponseEntity<>(analyzerResponse, HttpStatus.OK);
		} else {
			analyzerResponse.setResponseMessage("Pre-requisite not met, please install required software installed.");
			responseEntity = new ResponseEntity<>(analyzerResponse, HttpStatus.FORBIDDEN);
		}
		return responseEntity;
	}

	/**
	 * Scan current page.
	 *
	 * @param pageDetails the page details
	 * @return the response entity
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws TimeoutException
	 * @throws ExecutionException
	 */
	@CrossOrigin(origins = "*")
	@PostMapping("/scanPage")
	public ResponseEntity<AnalyzerResponse> scanCurrentPage(  @RequestBody PageDetails pageDetails) {
		
		boolean flag = true;
		String applicationUrl = "";
		AnalyzerResponse analyzerResponse = new AnalyzerResponse();
		ResponseEntity<AnalyzerResponse> responseEntity;
		//this.scanCount = this.scanCount + pageDetails.getScans();
		LOGGER.info("Scan API count : " + this.scanCount);
		if (Objects.isNull(driver) && pageDetails.getTypeScan().equalsIgnoreCase("Manual")) {
			flag = false;
		} else {

			try {
				applicationUrl = driver.getCurrentUrl();
				
				// pageDetails.getPageTitle()
				pageStatUtils.measureEndUserPerformance(driver, applicationUrl, pageDetails);

				Thread.sleep(10000);
				pageDetails.setUserId("admin");
				flag = pageStatUtils.sendStatistics(pageDetails.getPageTitle() + ".report.json",
						pageDetails.getBuildId(), pageDetails.getPageTitle(), pageDetails.getApplicationName(),
						this.scanCount, pageDetails.getUserId());
				Thread.sleep(20000);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);

			}
			analyzerResponse.setResponseMessage("Analysis and Ingetion Done Successfully");
		}
		if (!flag) {
			analyzerResponse.setResponseMessage("Something went wrong during Analysis and Ingestion");
			responseEntity = new ResponseEntity<>(analyzerResponse, HttpStatus.FORBIDDEN);
		} else {
			responseEntity = new ResponseEntity<>(HttpStatus.OK);
		}
		return responseEntity;
	}

	/**
	 * Ingest Data.
	 *
	 * @param pageDetails the page details
	 * @return the response entity
	 * @throws InterruptedException
	 */
	@CrossOrigin(origins = "*")
	@PostMapping("/ingestPage")
	public ResponseEntity<AnalyzerResponse> ingestCurrentPageData(  @RequestBody PageDetails pageDetails,
			@RequestParam(value = "url", required = false) String applicationUrl) throws InterruptedException {
		boolean flag = true;
		AnalyzerResponse analyzerResponse = new AnalyzerResponse();
		ResponseEntity<AnalyzerResponse> responseEntity;
		flag = pageStatUtils.sendStatistics(pageDetails.getPageTitle() + ".report.json", pageDetails.getBuildId(),
				pageDetails.getPageTitle(), pageDetails.getApplicationName(), pageDetails.getScans(), pageDetails.getUserId());
		analyzerResponse.setResponseMessage("Ingetion Done Successfully");

		if (!flag) {
			analyzerResponse.setResponseMessage("Something went wrong during Ingestion");
			responseEntity = new ResponseEntity<>(analyzerResponse, HttpStatus.FORBIDDEN);
		} else {
			responseEntity = new ResponseEntity<>(HttpStatus.OK);
		}
		return responseEntity;
	}

	/**
	 * Stop analyzer.
	 *
	 * @return the response entity
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	public ResponseEntity<AnalyzerResponse> stopAnalyzer() {
		AnalyzerResponse analyzerResponse = new AnalyzerResponse();
		ResponseEntity<AnalyzerResponse> responseEntity;
		if (pageStatDriver.closePageStatDriver()) {
			analyzerResponse.setResponseMessage("Successfully Stopped Engine");
			responseEntity = new ResponseEntity<>(analyzerResponse, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return responseEntity;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/healthcheck", method = RequestMethod.GET)
	public ResponseEntity<AnalyzerResponse> healthCheck() {
		AnalyzerResponse analyzerResponse = new AnalyzerResponse();
		ResponseEntity<AnalyzerResponse> responseEntity;
		analyzerResponse.setResponseMessage("Running");
		responseEntity = new ResponseEntity<>(analyzerResponse, HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	 * Initialize And Launch analyzer.
	 *
	 * @return the response entity
	 */
	@CrossOrigin(origins = "*")
	@PostMapping("/initBrowserWithUrl")
	public ResponseEntity<AnalyzerResponse> initializeBrowserWithUrl(  @RequestBody PageDetails pageDetails) 
	{
		AnalyzerResponse analyzerResponse = new AnalyzerResponse();
		ResponseEntity<AnalyzerResponse> responseEntity;
		boolean flag = true;
		this.scanCount = this.scanCount + pageDetails.getScans();
		
		if (pageStatUtils.startChromeDriver()) {
			driver = pageStatDriver.getPageStatDriver();
			if (Objects.isNull(driver)) {
				analyzerResponse
						.setResponseMessage("Please check whether the pre-requisites installations have been done.");
				responseEntity = new ResponseEntity<>(analyzerResponse, HttpStatus.FAILED_DEPENDENCY);
			} else {
				
				driver.navigate().to(pageDetails.getUrl());
				analyzerResponse.setResponseMessage("Successfully launched browser with application url.");
				responseEntity = new ResponseEntity<>(analyzerResponse, HttpStatus.OK);
			}
		} else {
			analyzerResponse.setResponseMessage("Pre-requisite not met, please install required software installed.");
			responseEntity = new ResponseEntity<>(analyzerResponse, HttpStatus.FORBIDDEN);
		}
		return responseEntity;
	}
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/crawl", method = RequestMethod.POST)
	public Map<String, String> webCrawling(@RequestBody WebCrawlerModel webCrawlerModel)
	{
		ResponseEntity<Map<String, String>> responseEntity;
		Map<String, String> crawlList = new HashMap<>();
		
        try {
        	crawlList = webCrawler.startCrawling(webCrawlerModel.getBaseUrl());
        	responseEntity = new ResponseEntity<>(crawlList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
	
			return crawlList;
	}
	
	
	@CrossOrigin(origins = "*")
	@PostMapping("/scanCrawlList")
//	public ResponseEntity<AnalyzerResponse> scanCrawlList(@RequestBody PageDetails pageDetails) {
	public ResponseEntity<AnalyzerResponse> scanCrawlList(  @RequestBody PageDetails pageDetails) {
		
		boolean flag = true;
		String applicationUrl = "";
		AnalyzerResponse analyzerResponse = new AnalyzerResponse();
		ResponseEntity<AnalyzerResponse> responseEntity;
		//this.scanCount = this.scanCount + pageDetails.getScans();
		LOGGER.info("Scan API count : " + this.scanCount);
		if (Objects.isNull(driver) && pageDetails.getTypeScan().equalsIgnoreCase("Manual")) {
			flag = false;
		} else {

			driver.navigate().to(pageDetails.getUrl());
			try {
				applicationUrl = driver.getCurrentUrl();
				

				// pageDetails.getPageTitle()
				pageStatUtils.measureEndUserPerformance(driver, applicationUrl, pageDetails);

				Thread.sleep(10000);
				flag = pageStatUtils.sendStatistics(pageDetails.getPageTitle() + ".report.json",
						pageDetails.getBuildId(), pageDetails.getPageTitle(), pageDetails.getApplicationName(),
						this.scanCount, pageDetails.getUserId());
				Thread.sleep(20000);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);

			}
			analyzerResponse.setResponseMessage("Analysis and Ingetion Done Successfully");
		}
		if (!flag) {
			analyzerResponse.setResponseMessage("Something went wrong during Analysis and Ingestion");
			responseEntity = new ResponseEntity<>(analyzerResponse, HttpStatus.FORBIDDEN);
		} else {
			responseEntity = new ResponseEntity<>(HttpStatus.OK);
		}
		
//		analyzerResponse.setResponseMessage("Something went wrong during Analysis and Ingestion");
//		responseEntity = new ResponseEntity<>(analyzerResponse, HttpStatus.OK);
		return responseEntity;
	}

//	//sequence +
//		@CrossOrigin(origins = "*")
//		@PostMapping("/scanUrlList")
//		public ResponseEntity<AnalyzerResponse> scanUrlList( @RequestBody PageDetailsWithUrlList pageDetailsWithUrlList) 
//		{	
//
//
//			AnalyzerResponse analyzerResponse = new AnalyzerResponse();
//			final ResponseEntity<AnalyzerResponse> responseEntity;
//			PageDetails pageDetails = new PageDetails();
//			pageDetails.setApplicationName(pageDetailsWithUrlList.getApplicationName());
//			pageDetails.setBuildId(pageDetailsWithUrlList.getBuildId());
//			pageDetails.setDeviceType(pageDetailsWithUrlList.getDeviceType());
//			pageDetails.setScans(pageDetailsWithUrlList.getScans());
//	 
//			this.scanCount = this.scanCount + pageDetails.getScans();
//			System.out.println("Scan API count : " + this.scanCount);
//			pageDetailsWithUrlList.getUrls().forEach((pageName, url) -> 
//			   {
//				   initializeAndAnalyzer();
//					try {
//						Thread.sleep(25000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				   driver.navigate().to(url);
//		            System.out.println("Key: " + pageName + ", Value: " + url);
//		            pageDetails.setPageTitle(pageName);
//		            pageDetails.setUrl(url);
//					{
//						boolean flag = true;
//						try {
//							// pageDetails.getPageTitle()
//							pageStatUtils.measureEndUserPerformance(driver, pageDetails.getUrl(), pageDetails);
//	 
//							Thread.sleep(10000);
//							flag = pageStatUtils.sendStatistics(pageDetails.getPageTitle() + ".report.json",
//									pageDetails.getBuildId(), pageDetails.getPageTitle(), pageDetails.getApplicationName(),
//									this.scanCount);
//							Thread.sleep(20000);
//							if(!flag)
//							{
//								analyzerResponse.setResponseMessage("Analysis and Ingetion Done Successfully");
//								//return new ResponseEntity<>(analyzerResponse, HttpStatus.OK);
//							}
//						} catch (Exception e) {
//							LOGGER.error(e.getMessage(), e);
//	 
//						}
//						analyzerResponse.setResponseMessage("Analysis and Ingetion Done Successfully");
//						//responseEntity = new ResponseEntity<>(analyzerResponse, HttpStatus.OK);
//						System.out.println("Injetion Done for : " +pageName);
//					}
//					stopAnalyzer();
//		        });
//			return null;
//		}
	
	boolean scanUrlListFlag = true;
	//sequence +
	@CrossOrigin(origins = "*")
	@PostMapping("/scanUrlList")
	public ResponseEntity<AnalyzerResponse> scanUrlList( @RequestBody PageDetailsWithUrlList pageDetailsWithUrlList) 
	{	


		AnalyzerResponse analyzerResponse = new AnalyzerResponse();
		final ResponseEntity<AnalyzerResponse> responseEntity;
		PageDetails pageDetails = new PageDetails();
		pageDetails.setApplicationName(pageDetailsWithUrlList.getApplicationName());
		pageDetails.setBuildId(pageDetailsWithUrlList.getBuildId());
		pageDetails.setDeviceType(pageDetailsWithUrlList.getDeviceType());
		pageDetails.setScans(pageDetailsWithUrlList.getScans());
		pageDetails.setUserId(pageDetailsWithUrlList.getUserId());
 
		this.scanCount = this.scanCount + pageDetails.getScans();
		LOGGER.info("Scan API count : " + this.scanCount);
		pageDetailsWithUrlList.getUrls().forEach((pageName, url) -> 
		   {
			   initializeAndAnalyzer();
				try {
					Thread.sleep(25000);
					driver.navigate().to(url);
				} catch (Exception e) {
					scanUrlListFlag = false;
					e.printStackTrace();
				}

				LOGGER.info("Key: " + pageName + ", Value: " + url);
	            pageDetails.setPageTitle(pageName);
	            pageDetails.setUrl(url);
				{
					try {
						// pageDetails.getPageTitle()
						pageStatUtils.measureEndUserPerformance(driver, pageDetails.getUrl(), pageDetails);
 
						Thread.sleep(10000);
						scanUrlListFlag = pageStatUtils.sendStatistics(pageDetails.getPageTitle() + ".report.json",
								pageDetails.getBuildId(), pageDetails.getPageTitle(), pageDetails.getApplicationName(),
								this.scanCount, pageDetails.getUserId());
						Thread.sleep(20000);
 
					} catch (Exception e) {
						scanUrlListFlag = false;
						LOGGER.error(e.getMessage(), e);
 
					}
 
					LOGGER.info("Injetion Done for : " +pageName);
				}
				stopAnalyzer();
	        });
		if(scanUrlListFlag)
		{
			analyzerResponse.setResponseMessage("Analysis and Ingetion Done Successfully");
			responseEntity = new ResponseEntity<>(analyzerResponse, HttpStatus.OK);
		}
		else
		{
			analyzerResponse.setResponseMessage("Something went wrong during Analysis and Ingestion");
			responseEntity = new ResponseEntity<>(analyzerResponse, HttpStatus.FORBIDDEN);
		}
		return responseEntity;
	}
	
	//Download PPT Logic
	
	@CrossOrigin(origins = "*")
	@PostMapping("/download")
	public ResponseEntity<Resource> downloadPpt(@RequestBody Map<String, String> requestBody) throws IOException, XmlException {
	    String applicationName = requestBody.get("applicationName");
	    String buildId = requestBody.get("buildId");
	    JSONObject response = elasticsearchService.getDataForSeverity(applicationName, buildId);
	    JSONObject mimeResponse = elasticsearchService.getPerformanceMimePie(applicationName, buildId);
	    JSONObject wcvResponse = elasticsearchService.getPerformanceWCV(applicationName, buildId);

	    JSONObject bpFailCatResponse = elasticsearchService.getBPFailCat(applicationName, buildId);
	    JSONObject bpSEOCatResponse = elasticsearchService.getSEOFailCat(applicationName, buildId);
	    JSONObject getDataForTableInfoResponse = elasticsearchService.getDataForTableInfo(applicationName, buildId);

	    double Accessibilityvalue = elasticsearchService.getAccessibilityScore(applicationName, buildId);
	    powerPointService.createPresentationMetered(applicationName, buildId, Accessibilityvalue, response, getDataForTableInfoResponse, wcvResponse, "accessibility");
	    
	    double Performancevalue = elasticsearchService.getPerformanceScore(applicationName, buildId);
	    powerPointService.createPresentationMetered(applicationName, buildId, Performancevalue, mimeResponse, getDataForTableInfoResponse, wcvResponse, "performance");
	    
	    double SEOvalue = elasticsearchService.getSEOScore(applicationName, buildId);
	    powerPointService.createPresentationMetered(applicationName, buildId, SEOvalue, bpSEOCatResponse, getDataForTableInfoResponse, wcvResponse, "seo");

	    double BPvalue = elasticsearchService.getBPScore(applicationName, buildId);
	    powerPointService.createPresentationMetered(applicationName, buildId, BPvalue, bpFailCatResponse, getDataForTableInfoResponse, wcvResponse, "bp");

	    Resource file = new FileSystemResource("reports/" + applicationName + "_" + buildId + ".pptx");
	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + applicationName + "_" + buildId + ".pptx\"")
	            .body(file);
	}
	
	
	@GetMapping("/hello")
	public String hello() {
		
		return "hello";
		
	}
	
}


