/**
 * 
 */
package com.dexbackend.dexbackend.pageanalyzer.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.core.TimeValue;
//import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.dexbackend.dexbackend.pageanalyzer.model.accessibility.AccessibilityIssues;
import com.dexbackend.dexbackend.pageanalyzer.model.accessibility.AccessibilityPassedAudits;
import com.dexbackend.dexbackend.pageanalyzer.model.bestpractices.BestPracticeItems;
import com.dexbackend.dexbackend.pageanalyzer.model.bestpractices.BestPracticesAllAudits;
import com.dexbackend.dexbackend.pageanalyzer.model.bestpractices.BestPracticesPassedAudits;
import com.dexbackend.dexbackend.pageanalyzer.model.diagnostics.DiagnosticsDetails;
import com.dexbackend.dexbackend.pageanalyzer.model.metrics.MetricsDetails;
import com.dexbackend.dexbackend.pageanalyzer.model.networkrequests.NetworkRequestItems;
import com.dexbackend.dexbackend.pageanalyzer.model.networkrtt.NetworkRttItems;
import com.dexbackend.dexbackend.pageanalyzer.model.performance.OpportunityItems;
import com.dexbackend.dexbackend.pageanalyzer.model.performance.PerformanceCategories;
import com.dexbackend.dexbackend.pageanalyzer.model.performance.PerformanceMiscellaneous;
import com.dexbackend.dexbackend.pageanalyzer.model.performance.PerformancePassedAudits;
import com.dexbackend.dexbackend.pageanalyzer.model.seo.SEOFailedAudits;
import com.dexbackend.dexbackend.pageanalyzer.model.seo.SEOPassedAudits;
import com.dexbackend.dexbackend.pageanalyzer.networkserverlatency.NetworkRequestLatencyItems;
import com.google.gson.Gson;


/**
 * The Class ElasticSearchUtils.
 *
 * @author surendrane
 */
@SuppressWarnings("deprecation")
@Component
public class ElasticSearchUtils {

	/** The Constant SERVER_PROTOCOL. */
	private static final String SERVER_PROTOCOL = "server.protocol";

	/** The Constant INDEX_NUMBER_OF_REPLICAS. */
	private static final String INDEX_NUMBER_OF_REPLICAS = "index.number_of_replicas";

	/** The Constant INDEX_MAPPING_TOTAL_FIELDS_LIMIT. */
	private static final String INDEX_MAPPING_TOTAL_FIELDS_LIMIT = "index.mapping.total_fields.limit";

	/** The Constant INDEX_NUMBER_OF_SHARDS. */
	private static final String INDEX_NUMBER_OF_SHARDS = "index.number_of_shards";

	/** The Constant TIMESTAMP. */
	private static final String TIMESTAMP = "@timestamp";

	/** The Constant LOGSTASH. */
	private static final String LOGSTASH = "logstash-*";

	/** The Constant ELASTIC_HOST. */
	private static final String ELASTIC_HOST = "elastic.host";

	/** The env. */
	@Autowired
	private Environment env;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(ElasticSearchUtils.class);

	/**
	 * Gets the source docs throughterms query.
	 *
	 * @param term       the term
	 * @param filterData the filter data
	 * @param threadSize the thread size
	 * @return the source docs throughterms query
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public List<Map<String, Object>> getSourceDocsThroughtermsQuery(final String term, final String filterData,
			final int threadSize) throws IOException {
		List<Map<String, Object>> listOfSources = new ArrayList<Map<String, Object>>();
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
						new HttpHost(appElasticHost(), 9201, appServerProtocol())));

		SearchRequest searchRequest = new SearchRequest(LOGSTASH).source(SearchSourceBuilder.searchSource()
				.size(threadSize).query(QueryBuilders.termsQuery(term, filterData)).sort(TIMESTAMP, SortOrder.DESC));
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

		SearchHit[] searchHits = response.getHits().getHits();
		for (int i = 0; i < searchHits.length; i++) {
			listOfSources.add(searchHits[i].getSourceAsMap());
		}
		client.close();
		return listOfSources;
	}

	/**
	 * Gets the source logs throughterms query.
	 *
	 * @param term       the term
	 * @param filterData the filter data
	 * @param threadSize the thread size
	 * @return the source logs throughterms query
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public List<String> getSourceLogsThroughtermsQuery(final String term, final String filterData, final int threadSize)
			throws IOException {
		List<String> listOfSources = new ArrayList<String>();
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
						new HttpHost(appElasticHost(), 9201, appServerProtocol())));

		SearchRequest searchRequest = new SearchRequest(LOGSTASH).source(
				SearchSourceBuilder.searchSource().size(threadSize).query(QueryBuilders.termsQuery(term, filterData))
						.sort(new FieldSortBuilder(TIMESTAMP).order(SortOrder.DESC)));
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

		SearchHit[] searchHits = response.getHits().getHits();
		for (int i = 0; i < searchHits.length; i++) {
			listOfSources.add(searchHits[i].getSourceAsString());
		}
		client.close();
		return listOfSources;
	}

	/**
	 * Creates the index.
	 *
	 * @param indexName the index name
	 */
	@SuppressWarnings("deprecation")
	public void createIndex(final String indexName) {
		try {
			RestHighLevelClient client = new RestHighLevelClient(
					RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
							new HttpHost(appElasticHost(), 9201, appServerProtocol())));
			GetIndexRequest request = new GetIndexRequest();
			request.indices(indexName);
			boolean exists;

			exists = client.indices().exists(request, RequestOptions.DEFAULT);
			if (!exists) {
				createIndexWithMapping(indexName, client);
			} else {
				LOGGER.info("Index of name : " + indexName + " Exists");
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

	/**
	 * Creates the index with mapping.
	 *
	 * @param indexName the index name
	 * @param client    the client
	 */
	private void createIndexWithMapping(final String indexName, final RestHighLevelClient client) {
		try {
			CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
			createIndexRequest.settings(Settings.builder().put(INDEX_NUMBER_OF_SHARDS, 10)
					.put(INDEX_NUMBER_OF_REPLICAS, 5).put(INDEX_MAPPING_TOTAL_FIELDS_LIMIT, "100000"));
			CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest,
					RequestOptions.DEFAULT);
			if (createIndexResponse.isAcknowledged()) {
				LOGGER.info("New Index Created of name:" + indexName);
			} else {
				LOGGER.error("Failed to Create New Index Created of name:" + indexName);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

	/**
	 * Ingest data to index.
	 *
	 * @param indexName  the index name
	 * @param jsonString the json string
	 * @throws Exception the exception
	 */
	public void ingestDataToIndex(final String indexName, final String jsonString) throws Exception {
		try {
			RestHighLevelClient client = new RestHighLevelClient(
					RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
							new HttpHost(appElasticHost(), 9201, appServerProtocol())));
			IndexRequest request = new IndexRequest(indexName);
			request.source(jsonString, XContentType.JSON);
			IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
			client.close();
			if (indexResponse.status() == RestStatus.OK || indexResponse.status() == RestStatus.ACCEPTED
					|| indexResponse.status() == RestStatus.CREATED) {
				LOGGER.info("Data Ingested to index :" + indexName);
			} else {
				LOGGER.error("Data Ingestion failed to index :" + indexName);
				throw new Exception("Data Ingestion failed to index :" + indexName);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Ingest oppotunity items to index.
	 *
	 * @param indexName the index name
	 * @param data      the data
	 * @throws Exception the exception
	 */
	public void ingestOppotunityItemsToIndex(final String indexName, final List<OpportunityItems> data)
			throws Exception {
		try {
			BulkResponse bulkResponse = null;
			RestHighLevelClient client = new RestHighLevelClient(
					RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
							new HttpHost(appElasticHost(), 9201, appServerProtocol())));
			BulkRequest request = new BulkRequest(indexName);
			for (OpportunityItems opportunityItems : data) {
				request.add(new IndexRequest().source(new Gson().toJson(opportunityItems), XContentType.JSON));
			}
			request.timeout(TimeValue.timeValueMinutes(3));
			request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
			if (request.numberOfActions() > 0) {
				bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
			}
			client.close();
			if (bulkResponse.status() == RestStatus.OK) {
				LOGGER.info("Data Ingested to index :" + indexName);
			} else {
				LOGGER.error("Data Ingestion failed to index :" + indexName);
				throw new Exception("Ingestion failed to index :" + indexName);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Ingest metrics items to index.
	 *
	 * @param indexName the index name
	 * @param data      the data
	 * @throws Exception the exception
	 */
	public void ingestMetricsItemsToIndex(final String indexName, final List<PerformanceCategories> data)
			throws Exception {
		try {
			RestHighLevelClient client = new RestHighLevelClient(
					RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
							new HttpHost(appElasticHost(), 9201, appServerProtocol())));
			BulkRequest request = new BulkRequest(indexName);
			for (PerformanceCategories performanceCategories : data) {
				request.add(new IndexRequest().source(new Gson().toJson(performanceCategories), XContentType.JSON));
			}
			request.timeout(TimeValue.timeValueMinutes(3));
			request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
			BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
			client.close();
			if (bulkResponse.status() == RestStatus.OK) {
				LOGGER.info("Data Ingested to index :" + indexName);
			} else {
				LOGGER.error("Data Ingestion failed to index :" + indexName);
				throw new Exception("Ingestion failed to index :" + indexName);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public void ingestPerformanceMiscellaneous(final String indexName, final List<PerformanceMiscellaneous> data)
			throws Exception {
		try {
			RestHighLevelClient client = new RestHighLevelClient(
					RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
							new HttpHost(appElasticHost(), 9201, appServerProtocol())));
			BulkRequest request = new BulkRequest(indexName);
			for (PerformanceMiscellaneous performanceMiscellaneous : data) {
				request.add(new IndexRequest().source(new Gson().toJson(performanceMiscellaneous), XContentType.JSON));
			}
			request.timeout(TimeValue.timeValueMinutes(3));
			request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
			BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
			client.close();
			if (bulkResponse.status() == RestStatus.OK) {
				LOGGER.info("Data Ingested to index :" + indexName);
			} else {
				LOGGER.error("Data Ingestion failed to index :" + indexName);
				throw new Exception("Ingestion failed to index :" + indexName);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	/**
	 * Ingest performance passed audits items to index.
	 *
	 * @param indexName the index name
	 * @param data      the data
	 * @throws Exception the exception
	 */
	public void ingestPerformancePassedAuditsItemsToIndex(final String indexName,
			final List<PerformancePassedAudits> data) throws Exception {
		try {
			RestHighLevelClient client = new RestHighLevelClient(
					RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
							new HttpHost(appElasticHost(), 9201, appServerProtocol())));
			BulkRequest request = new BulkRequest(indexName);
			for (PerformancePassedAudits performancePassedAudits : data) {
				request.add(new IndexRequest().source(new Gson().toJson(performancePassedAudits), XContentType.JSON));
			}
			request.timeout(TimeValue.timeValueMinutes(3));
			request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
			BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
			client.close();
			if (bulkResponse.status() == RestStatus.OK) {
				LOGGER.info("Data Ingested to index :" + indexName);
			} else {
				LOGGER.error("Data Ingestion failed to index :" + indexName);
				throw new Exception("Ingestion failed to index :" + indexName);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Ingest accessibility passed audits items to index.
	 *
	 * @param indexName the index name
	 * @param data      the data
	 * @throws Exception the exception
	 */
	public void ingestAccessibilityPassedAuditsItemsToIndex(final String indexName,
			final List<AccessibilityPassedAudits> data) throws Exception {
		try {
			RestHighLevelClient client = new RestHighLevelClient(
					RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
							new HttpHost(appElasticHost(), 9201, appServerProtocol())));
			BulkRequest request = new BulkRequest(indexName);
			for (AccessibilityPassedAudits accessibilityPassedAudits : data) {
				request.add(new IndexRequest().source(new Gson().toJson(accessibilityPassedAudits), XContentType.JSON));
			}
			request.timeout(TimeValue.timeValueMinutes(3));
			request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
			BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
			client.close();
			if (bulkResponse.status() == RestStatus.OK) {
				LOGGER.info("Data Ingested to index :" + indexName);
			} else {
				LOGGER.error("Data Ingestion failed to index :" + indexName);
				throw new Exception("Ingestion failed to index :" + indexName);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Ingest accessibility issues to index.
	 *
	 * @param indexName the index name
	 * @param data      the data
	 * @throws Exception the exception
	 */
	public void ingestAccessibilityIssuesToIndex(final String indexName, final List<AccessibilityIssues> data)
			throws Exception {
		try {
			RestHighLevelClient client = new RestHighLevelClient(
					RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
							new HttpHost(appElasticHost(), 9201, appServerProtocol())));
			BulkRequest request = new BulkRequest(indexName);
			for (AccessibilityIssues accessibilityIssues : data) {
				request.add(new IndexRequest().source(new Gson().toJson(accessibilityIssues), XContentType.JSON));
			}
			request.timeout(TimeValue.timeValueMinutes(3));
			request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
			BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
			client.close();
			if (bulkResponse.status() == RestStatus.OK) {
				LOGGER.info("Data Ingested to index :" + indexName);
			} else {
				LOGGER.error("Data Ingestion failed to index :" + indexName);
				throw new Exception("Data Ingestion failed to index :" + indexName);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Ingest SEO pass audits to index.
	 *
	 * @param indexName the index name
	 * @param data      the data
	 * @throws Exception the exception
	 */
	public void ingestSEOPassAuditsToIndex(final String indexName, final List<SEOPassedAudits> data) throws Exception {
		try {
			RestHighLevelClient client = new RestHighLevelClient(
					RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
							new HttpHost(appElasticHost(), 9201, appServerProtocol())));
			BulkRequest request = new BulkRequest(indexName);
			for (SEOPassedAudits seoPassedAudits : data) {
				request.add(new IndexRequest().source(new Gson().toJson(seoPassedAudits), XContentType.JSON));
			}
			request.timeout(TimeValue.timeValueMinutes(3));
			request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
			BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
			client.close();
			if (bulkResponse.status() == RestStatus.OK) {
				LOGGER.info("Data Ingested to index :" + indexName);
			} else {
				LOGGER.error("Data Ingestion failed to index :" + indexName);
				throw new Exception("Ingestion failed to index :" + indexName);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Ingest SEO failures to index.
	 *
	 * @param indexName the index name
	 * @param data      the data
	 * @throws Exception the exception
	 */
	public void ingestSEOFailuresToIndex(final String indexName, final List<SEOFailedAudits> data) throws Exception {
		try {
			RestHighLevelClient client = new RestHighLevelClient(
					RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
							new HttpHost(appElasticHost(), 9201, appServerProtocol())));
			BulkRequest request = new BulkRequest(indexName);
			for (SEOFailedAudits seoFailedAudits : data) {
				request.add(new IndexRequest().source(new Gson().toJson(seoFailedAudits), XContentType.JSON));
			}
			request.timeout(TimeValue.timeValueMinutes(3));
			request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
			BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
			client.close();
			if (bulkResponse.status() == RestStatus.OK) {
				LOGGER.info("Data Ingested to index :" + indexName);
			} else {
				LOGGER.error("Data Ingestion failed to index :" + indexName);
				throw new Exception("Ingestion failed to index :" + indexName);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Ingest Network Request Items to index.
	 *
	 * @param indexName the index name
	 * @param data      the data
	 * @throws Exception the exception
	 */
	public void ingestNetworkRequestItemsToIndex(final String indexName, final List<NetworkRequestItems> data)
			throws Exception {

		try {
			BulkResponse bulkResponse = null;
			RestHighLevelClient client = new RestHighLevelClient(
					RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
							new HttpHost(appElasticHost(), 9201, appServerProtocol())));
			BulkRequest request = new BulkRequest(indexName);
			for (NetworkRequestItems networkRequestItems : data) {
				request.add(new IndexRequest().source(new Gson().toJson(networkRequestItems), XContentType.JSON));
			}
			request.timeout(TimeValue.timeValueMinutes(3));
			request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
			if (request.numberOfActions() > 0) {
				bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
			}
			client.close();
			if (bulkResponse.status() == RestStatus.OK) {
				LOGGER.info("Data Ingested to index :" + indexName);
			} else {
				LOGGER.error("Data Ingestion failed to index :" + indexName);
				throw new Exception("Ingestion failed to index :" + indexName);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Ingest network rtt items to index.
	 *
	 * @param indexName the index name
	 * @param data      the data
	 * @throws Exception the exception
	 */
	public void ingestNetworkRttItemsToIndex(final String indexName, final List<NetworkRttItems> data)
			throws Exception {
		try {
			BulkResponse bulkResponse = null;
			RestHighLevelClient client = new RestHighLevelClient(
					RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
							new HttpHost(appElasticHost(), 9201, appServerProtocol())));
			BulkRequest request = new BulkRequest(indexName);
			for (NetworkRttItems networkRttItems : data) {
				request.add(new IndexRequest().source(new Gson().toJson(networkRttItems), XContentType.JSON));
			}
			request.timeout(TimeValue.timeValueMinutes(3));
			request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
			if (request.numberOfActions() > 0) {
				bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
			}
			client.close();
			if (bulkResponse.status() == RestStatus.OK) {
				LOGGER.info("Data Ingested to index :" + indexName);
			} else {
				LOGGER.error("Data Ingestion failed to index :" + indexName);
				throw new Exception("Ingestion failed to index :" + indexName);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Ingest diagnostics.
	 *
	 * @param indexName the index name
	 * @param data      the data
	 * @throws Exception the exception
	 */
	public void ingestDiagnostics(final String indexName, final List<DiagnosticsDetails> data) throws Exception {
		try {
			RestHighLevelClient client = new RestHighLevelClient(
					RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
							new HttpHost(appElasticHost(), 9201, appServerProtocol())));
			BulkRequest request = new BulkRequest(indexName);
			for (DiagnosticsDetails diagnosticsList : data) {
				request.add(new IndexRequest().source(new Gson().toJson(diagnosticsList), XContentType.JSON));
			}
			request.timeout(TimeValue.timeValueMinutes(3));
			request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
			BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
			client.close();
			if (bulkResponse.status() == RestStatus.OK) {
				LOGGER.info("Data Ingested to index :" + indexName);
			} else {
				LOGGER.error("Data Ingestion failed to index :" + indexName);
				throw new Exception("Ingestion failed to index :" + indexName);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Ingest Network Request Latency to index.
	 *
	 * @param indexName the index name
	 * @param data      the data
	 * @throws Exception the exception
	 */
	public void ingestNetworkRequestLatencyItemsToIndex(final String indexName,
			final List<NetworkRequestLatencyItems> data) throws Exception {
		try {
			BulkResponse bulkResponse = null;
			RestHighLevelClient client = new RestHighLevelClient(
					RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
							new HttpHost(appElasticHost(), 9201, appServerProtocol())));
			BulkRequest request = new BulkRequest(indexName);
			for (NetworkRequestLatencyItems networkRequestLatencyItems : data) {
				request.add(
						new IndexRequest().source(new Gson().toJson(networkRequestLatencyItems), XContentType.JSON));
			}
			request.timeout(TimeValue.timeValueMinutes(3));
			request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
			if (request.numberOfActions() > 0) {
				bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
			}
			client.close();
			if (bulkResponse.status() == RestStatus.OK) {
				LOGGER.info("Data Ingested to index :" + indexName);
			} else {
				LOGGER.error("Data Ingestion failed to index :" + indexName);
				throw new Exception("Ingestion failed to index :" + indexName);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Ingest observed metrics details to index.
	 *
	 * @param indexName the index name
	 * @param data      the data
	 * @throws Exception the exception
	 */
	public void ingestObservedMetricsDetailsToIndex(final String indexName, final List<MetricsDetails> data)
			throws Exception {
		try {
			BulkResponse bulkResponse = null;
			RestHighLevelClient client = new RestHighLevelClient(
					RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
							new HttpHost(appElasticHost(), 9201, appServerProtocol())));
			BulkRequest request = new BulkRequest(indexName);
			for (MetricsDetails metricsDetails : data) {
				request.add(new IndexRequest().source(new Gson().toJson(metricsDetails), XContentType.JSON));
			}
			request.timeout(TimeValue.timeValueMinutes(3));
			request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
			if (request.numberOfActions() > 0) {
				bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
			}
			client.close();
			if (bulkResponse.status() == RestStatus.OK) {
				LOGGER.info("Data Ingested to index :" + indexName);
			} else {
				LOGGER.error("Data Ingestion failed to index :" + indexName);
				throw new Exception("Ingestion failed to index :" + indexName);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	/**
	 * Ingest BestPractices all audits to index.
	 *
	 * @param indexName the index name
	 * @param data      the data
	 * @throws Exception the exception
	 */

	public void ingestBestPracticesAllAuditsToIndex(final String indexName, final List<BestPracticesAllAudits> data) throws Exception {
		try {
			RestHighLevelClient client = new RestHighLevelClient(
					RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
							new HttpHost(appElasticHost(), 9201, appServerProtocol())));
			BulkRequest request = new BulkRequest(indexName);
			for (BestPracticesAllAudits bestPracticesAllAudits : data) {
				request.add(new IndexRequest().source(new Gson().toJson(bestPracticesAllAudits), XContentType.JSON));
			}
			request.timeout(TimeValue.timeValueMinutes(3));
			request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
			BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
			client.close();
			if (bulkResponse.status() == RestStatus.OK) {
				LOGGER.info("Data Ingested to index :" + indexName);
			} else {
				LOGGER.error("Data Ingestion failed to index :" + indexName);
				throw new Exception("Ingestion failed to index :" + indexName);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	/**
	 * Ingest BestPractices pass audits to index.
	 *
	 * @param indexName the index name
	 * @param data      the data
	 * @throws Exception the exception
	 */

	public void ingestBestPracticesPassAuditsToIndex(final String indexName, final List<BestPracticesPassedAudits> data) throws Exception {
		try {
			RestHighLevelClient client = new RestHighLevelClient(
					RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
							new HttpHost(appElasticHost(), 9201, appServerProtocol())));
			BulkRequest request = new BulkRequest(indexName);
			for (BestPracticesPassedAudits bestPracticesPassedAudits : data) {
				request.add(new IndexRequest().source(new Gson().toJson(bestPracticesPassedAudits), XContentType.JSON));
			}
			request.timeout(TimeValue.timeValueMinutes(3));
			request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
			BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
			client.close();
			if (bulkResponse.status() == RestStatus.OK) {
				LOGGER.info("Data Ingested to index :" + indexName);
			} else {
				LOGGER.error("Data Ingestion failed to index :" + indexName);
				throw new Exception("Ingestion failed to index :" + indexName);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Ingest BestPractices failures to index.
	 *
	 * @param indexName the index name
	 * @param data      the data
	 * @throws Exception the exception
	 */
	public void ingestBestPracticesFailuresToIndex(final String indexName, final List<BestPracticeItems> data) throws Exception {
		try {
			RestHighLevelClient client = new RestHighLevelClient(
					RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
							new HttpHost(appElasticHost(), 9201, appServerProtocol())));
			BulkRequest request = new BulkRequest(indexName);
			for (BestPracticeItems bestPracticesFailedAudits : data) {
				request.add(new IndexRequest().source(new Gson().toJson(bestPracticesFailedAudits), XContentType.JSON));
			}
			request.timeout(TimeValue.timeValueMinutes(3));
			request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
			BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
			client.close();
			if (bulkResponse.status() == RestStatus.OK) {
				LOGGER.info("Data Ingested to index :" + indexName);
			} else {
				LOGGER.error("Data Ingestion failed to index :" + indexName);
				throw new Exception("Ingestion failed to index :" + indexName);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}


	/**
	 * Ingest PerfConf data to index.
	 *
	 * @param indexName  the index name
	 * @param jsonString the json string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void ingestPerformanceConfigDataToIndex(final String indexName, final String jsonString) throws IOException {
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost(appElasticHost(), 9200, appServerProtocol()),
						new HttpHost(appElasticHost(), 9201, appServerProtocol())));
		IndexRequest request = new IndexRequest(indexName);
		request.source(jsonString, XContentType.JSON);
		IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
		client.close();
	}

	/**
	 * App elastic host.
	 *
	 * @return the string
	 */
	private String appElasticHost() {
		String ElasticHost = env.getProperty(ELASTIC_HOST);
		return ElasticHost;
	}

	/**
	 * App server protocol.
	 *
	 * @return the string
	 */
	private String appServerProtocol() {
		String ServerProtocol = env.getProperty(SERVER_PROTOCOL);
		return ServerProtocol;
	}

}
