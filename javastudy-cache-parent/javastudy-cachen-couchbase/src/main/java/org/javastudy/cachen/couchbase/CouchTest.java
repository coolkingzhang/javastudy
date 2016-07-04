package org.javastudy.cachen.couchbase;

import java.util.Arrays;
import java.util.List;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

/**
 * Hello world!
 *
 */
public class CouchTest {
	public static void main(String[] args) {
		// Connect to localhost
		List<String> nodes = Arrays.asList("127.0.0.1", "127.0.0.1");
		Cluster cluster = CouchbaseCluster.create(nodes);
		// Cluster cluster = CouchbaseCluster.create();

		// Open the default bucket and the "beer-sample" one
		Bucket defaultBucket = cluster.openBucket();
		Bucket beerSampleBucket = cluster.openBucket("default");
		// Bucket bucket = cluster.openBucket("myapp", "password");

		JsonObject user = JsonObject.empty().put("firstname", "Walter").put("lastname", "White")
				.put("job", "chemistry teacher").put("age", 50);
		JsonDocument stored = beerSampleBucket.upsert(JsonDocument.create("walter", user));

		JsonDocument walter = beerSampleBucket.get("walter");
		System.out.println("Found: " + walter.content().getString("firstname"));

		
		// Disconnect and clear all allocated resources
		cluster.disconnect();
	}
}
