package com.servicemanagement.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("service-management")
public class ApiProperty {

	private String allowedOrigin;

	private final Security security = new Security();
	
	public Security getSecurity() {
		return security;
	}
	
	public String getAllowedOrigin() {
		return allowedOrigin;
	}
	
	public void setAllowedOrigin(String allowedOrigin) {
		this.allowedOrigin = allowedOrigin;
	}

	
	public static class Security {

		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}

	}
	
	

}