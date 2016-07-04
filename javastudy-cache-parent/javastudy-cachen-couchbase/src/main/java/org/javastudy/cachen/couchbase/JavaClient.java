package org.javastudy.cachen.couchbase;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;

public class JavaClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Create a cluster reference
		CouchbaseCluster cluster = CouchbaseCluster.create("127.0.0.1");

		// Connect to the bucket and open it
		Bucket bucket = cluster.openBucket("default");

		// Create a JSON document and store it with the ID "helloworld"
		JsonObject content = JsonObject.create().put("hello9", "world");
		JsonDocument inserted = bucket.upsert(JsonDocument.create("helloworld", content));

		// Read the document and print the "hello" field
		JsonDocument found = bucket.get("helloworld");
		System.out.println("Couchbase is the best database in the " + found.content().getString("hello9"));

		N1qlQueryResult result = bucket.query(
				N1qlQuery.simple("SELECT DISTINCT(country) FROM `travel-sample` WHERE type = 'airline' LIMIT 10"));

		for (N1qlQueryRow row : result) {
			System.out.println(row.value());
		}

		// Close all buckets and disconnect
		cluster.disconnect();
	}
}
