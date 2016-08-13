package org.javastudy.rpc.avro;

import java.net.URL;

import org.apache.avro.Protocol;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.ipc.HttpTransceiver;
import org.apache.avro.ipc.Transceiver;
import org.apache.avro.ipc.generic.GenericRequestor;

public class Client {

	private Protocol protocol = null;
	private String host = null;
	private int port = 0;
	private int count = 0;

	public Client(Protocol protocol, String host, int port, int count) {
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.count = count;
	}

	public long sendMessage() throws Exception {
		GenericRecord requestData = new GenericData.Record(protocol.getType("message"));
		requestData.put("name", "香梨");
		requestData.put("type", 36);
		requestData.put("price", 5.6);
		requestData.put("valid", true);
		requestData.put("content", "价钱便宜");

		// 初始化请求数据
		GenericRecord request = new GenericData.Record(protocol.getMessages().get("sendMessage").getRequest());
		request.put("message", requestData);

		Transceiver t = new HttpTransceiver(new URL("http://" + host + ":" + port));
		GenericRequestor requestor = new GenericRequestor(protocol, t);

		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			Object result = requestor.request("sendMessage", request);
			if (result instanceof GenericData.Record) {
				GenericData.Record record = (GenericData.Record) result;
				System.out.println(record);
			}
		}
		long end = System.currentTimeMillis();
		System.out.println((end - start) + "ms");
		return end - start;
	}

	public long run() {
		long res = 0;
		try {
			res = sendMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public static void main(String[] args) throws Exception {
		new Client(Utils.getProtocol(), "127.0.0.1", 9090, 5).run();
	}
}