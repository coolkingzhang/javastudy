package org.javastudy.cachen.couchbase;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.couchbase.client.CouchbaseClient;

public class ClientGet {
	public static void main(String[] args) {

		List<URI> uris = new LinkedList<URI>();

		uris.add(URI.create("http://127.0.0.1:8091/pools"));
		CouchbaseClient client = null;
		try {
			client = new CouchbaseClient(uris, "default", "");
		} catch (IOException e) {
			System.err.println("IOException connecting to Couchbase: " + e.getMessage());
			System.exit(1);
		}
		Object o = client.get("testkey1");
		System.out.println(o);

		client.shutdown(3, TimeUnit.SECONDS);
		System.exit(0);
	}
}