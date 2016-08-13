package org.javastudy.search.elasticsearch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import static org.elasticsearch.common.xcontent.XContentFactory.*;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class EsClient {

	public static void main(String[] args) {
		Client client = null;
		try {
			client = TransportClient.builder().build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.189.201"), 9300))
					.addTransportAddress(
							new InetSocketTransportAddress(InetAddress.getByName("192.168.189.201"), 9300));
			System.out.println(client);

			try {
				// 写索引
				IndexResponse responseIndex = client.prepareIndex("shop", "fulltext", "19")
						.setSource(jsonBuilder().startObject().field("content", "北京欢迎您").endObject()).get();

				CountResponse response = client.prepareCount("shop").setQuery(termQuery("_type", "fulltext")).execute()
						.actionGet();
				System.out.print(response.getCount());

				// 搜索
				SearchResponse response2 = client.prepareSearch("shop").setTypes("fulltext")
						.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(QueryBuilders.termQuery("multi", "美国"))
						.setFrom(0).setSize(60).setExplain(true).execute().actionGet();

				System.out.print(response2.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} finally {
			client.close();
		}
	}

	private static QueryBuilder termQuery(String string, String string2) {
		return null;
	}
}
