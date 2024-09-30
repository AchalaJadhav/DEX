///**
// * 
// */
//package com.dexbackend.dexbackend.pageanalyzer.config;
//
//import static springfox.documentation.builders.PathSelectors.regex;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//
///**
// * The Class SwaggerConfig.
// *
// * @author surendrane
// */
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig implements WebMvcConfigurer {
//
//	/** The Constant CLASSPATH_META_INF_RESOURCES_WEBJARS. */
//	private static final String CLASSPATH_META_INF_RESOURCES_WEBJARS = "classpath:/META-INF/resources/webjars/";
//
//	/** The Constant CLASSPATH_META_INF_RESOURCES. */
//	private static final String CLASSPATH_META_INF_RESOURCES = "classpath:/META-INF/resources/";
//
//	/** The Constant WEBJARS. */
//	private static final String WEBJARS = "/webjars/**";
//
//	/** The Constant SWAGGER_UI_HTML. */
//	private static final String SWAGGER_UI_HTML = "swagger-ui.html";
//
//	/** The Constant EMAIL */
//	private static final String EMAIL = "surendrane@xpanxion.com";
//
//	/** The Constant URL */
//	private static final String URL = "https://www.linkedin.com/in/surendran-ethiraj-a119465";
//
//	/** The Constant NAME */
//	private static final String NAME = "Surendran E";
//
//	/** The Constant _1_0. */
//	private static final String _1_0 = "1.0";
//
//	/** The Constant SERVICE_INITIALIZERS_FOR_PAGE_ANALYZER. */
//	private static final String DESCRIPTION = "\"Service Initializers for Page Analyzer\"";
//
//	/** The Constant PAGE_ANALYZER_REST_SERVICES. */
//	private static final String PAGE_ANALYZER_REST_SERVICES = "Page Analyzer REST Services";
//
//	/** The Constant API_V1. */
//	private static final String API_V1 = "/api/v1.*";
//
//	/** The Constant COM_QAINNOVATION_PAGEANALYZER_CONTROLLER. */
//	private static final String PACKAGE = "com.qainnovation.pageanalyzer.controller";
//
//	/**
//	 * Product api.
//	 *
//	 * @return the docket
//	 */
//	@Bean
//	public Docket productApi() {
//		return new Docket(DocumentationType.SWAGGER_2).select()
//				.apis(RequestHandlerSelectors.basePackage(PACKAGE))
//				.paths(regex(API_V1)).build().apiInfo(metaData());
//	}
//
//	/**
//	 * Meta data.
//	 *
//	 * @return the api info`
//	 */
//	private ApiInfo metaData() {
//		return new ApiInfoBuilder().title(PAGE_ANALYZER_REST_SERVICES)
//				.description(DESCRIPTION).version(_1_0)
//				.contact(new Contact(NAME, URL, EMAIL)).build();
//	}
//
//	/**
//	 * Adds the resource handlers.
//	 *
//	 * @param registry the registry
//	 */
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler(SWAGGER_UI_HTML).addResourceLocations(CLASSPATH_META_INF_RESOURCES);
//		registry.addResourceHandler(WEBJARS).addResourceLocations(CLASSPATH_META_INF_RESOURCES_WEBJARS);
//	}
//}