package com.dexbackend.dexbackend.pageanalyzer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "configtoggles")
@Component
public class ConfigProperties
{
	private String chromedriver;

	public String getChromedriver() {
		return chromedriver;
	}

	public void setChromedriver(String chromedriver) {
		this.chromedriver = chromedriver;
	}

	
}

