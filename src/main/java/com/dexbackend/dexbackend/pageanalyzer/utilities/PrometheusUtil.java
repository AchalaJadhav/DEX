package com.dexbackend.dexbackend.pageanalyzer.utilities;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.dexbackend.dexbackend.pageanalyzer.model.bestpractices.BestPracticeItems;
import com.dexbackend.dexbackend.pageanalyzer.model.bestpractices.BestPracticesPassedAudits;
import com.dexbackend.dexbackend.pageanalyzer.model.performance.OpportunityItems;
import com.dexbackend.dexbackend.pageanalyzer.parser.model.AnalyzerAudits;

import io.prometheus.client.Gauge;

@Component
public class PrometheusUtil {

	public String[] getDeclaredFields(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		List<String> fieldNames = new ArrayList<>();
		for (Field field : fields) {
			fieldNames.add(field.getName());
		}
		fieldNames.add("userId");
		return fieldNames.toArray(new String[0]);
	}

	public void ingestAuditsToPrometheus(Gauge gauge, Object audits, String[] fieldNames, String userId) {
		String[] labelValues = getLabelValuesFromfieldNames(audits.getClass(), audits, fieldNames, userId);
		gauge.labels(labelValues);
	}

	private String[] getLabelValuesFromfieldNames(Class<?> clazz, Object audits, String[] fieldNames, String userId) {
		List<String> labelValues = new ArrayList<>();
		List<String> fieldNameList = Arrays.asList(fieldNames);
		try {
			String labelValue;
			Method method;
			for (String fieldName : fieldNameList) {
				fieldName = StringUtils.capitalize(fieldName);
				if(!fieldName.equalsIgnoreCase("userId")) {
					try {
						method = clazz.getDeclaredMethod("get" + fieldName);
					} catch (NoSuchMethodException e) {
						method = clazz.getDeclaredMethod("is" + fieldName);
					}
					labelValue = String.valueOf(method.invoke(audits));
					labelValues.add(labelValue.equalsIgnoreCase("null") ? "" : labelValue);
				}
			}
			//userId is not from light house Json, It is from the API request
			labelValues.add(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return labelValues.toArray(new String[0]);
	}

//	public void ingestAnalyzerAudits(Gauge analyzeAuditsGauge, AnalyzerAudits analyzerAudits) {
//		analyzeAuditsGauge.labels(Optional.ofNullable(String.valueOf(analyzerAudits.getPerformanceScore())).orElse(""),
//				Optional.ofNullable(String.valueOf(analyzerAudits.getAccessibilityScore())).orElse(""),
//				Optional.ofNullable(String.valueOf(analyzerAudits.getSeoscore())).orElse(""),
//				Optional.ofNullable(String.valueOf(analyzerAudits.getPwascore())).orElse(""),
//				Optional.ofNullable(String.valueOf(analyzerAudits.getBestpracticesScore())).orElse(""),
//				Optional.ofNullable(String.valueOf(analyzerAudits.getBuildId())).orElse(""),
//				Optional.ofNullable(String.valueOf(analyzerAudits.getRequestedUrl())).orElse(""),
//				Optional.ofNullable(String.valueOf(analyzerAudits.getFetchTime())).orElse(""),
//				Optional.ofNullable(String.valueOf(analyzerAudits.getPageName())).orElse(""),
//				Optional.ofNullable(String.valueOf(analyzerAudits.getApplicationName())).orElse(""),
//				Optional.ofNullable(String.valueOf(analyzerAudits.getScans())).orElse(""));
//	}

//	public void ingestAnalyzerbpPassAudits(Gauge analyzerBppassAuditGauge,
//			BestPracticesPassedAudits bestPracticesPassItem) {
//		analyzerBppassAuditGauge.labels(Optional.ofNullable(bestPracticesPassItem.getBuildId()).orElse(""),
//				Optional.ofNullable(bestPracticesPassItem.getRequestedUrl()).orElse(""),
//				Optional.ofNullable(bestPracticesPassItem.getFetchTime()).orElse(""),
//				Optional.ofNullable(bestPracticesPassItem.getPageName()).orElse(""),
//				Optional.ofNullable(bestPracticesPassItem.getApplicationName()).orElse(""),
//				Optional.ofNullable(bestPracticesPassItem.getTitle()).orElse(""),
//				Optional.ofNullable(bestPracticesPassItem.getDescription()).orElse(""),
//				Optional.ofNullable(bestPracticesPassItem.getGroup()).orElse(""));
//	}
//
//	public void ingestAnalyzerbpFailAudits(Gauge analyzerBpFailAuditGauge, BestPracticeItems bestPracticeItem) {
//		analyzerBpFailAuditGauge.labels(Optional.ofNullable(bestPracticeItem.getUrl()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getResolution()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getHightestSeverity()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getVulnCount()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getDectectedLibText()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getDectectedLibUrl()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getAuditId()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getGDescription()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getGUrl()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getScriptUrl()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getSourceMapUrl()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getError()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getIUrl()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getIssueType()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getNoType()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getNoUrl()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getPath()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getValue()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getSeverity()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getDescription2()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getTsurl()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getExpextedSize()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getDisplayedSize()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getRUrl()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getDisplayedAspectRatio()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getActualAspectRatio()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getBuildId()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getRequestedUrl()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getFetchTime()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getPageName()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getApplicationName()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getTitle()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getDescription()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getGroup()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getScore()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getTarget()).orElse(""),
//				Optional.ofNullable(bestPracticeItem.getSummary()).orElse(""));
//	}

}
