package org.javastudy.rpc.avro;

import org.apache.avro.Protocol;
import org.apache.avro.Protocol.Message;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.ipc.HttpServer;
import org.apache.avro.ipc.generic.GenericResponder;

// 例子 http://blog.csdn.net/zhu_tianwei/article/details/44042955
public class Server extends GenericResponder {

	private Protocol protocol = null;
	private int port;

	public Server(Protocol protocol, int port) {
		super(protocol);
		this.protocol = protocol;
		this.port = port;
	}

	@Override
	public Object respond(Message message, Object request) throws Exception {
		GenericRecord req = (GenericRecord) request;
		GenericRecord reMessage = null;
		if (message.getName().equals("sendMessage")) {
			GenericRecord msg = (GenericRecord) req.get("message");
			System.out.print("接收到数据：");
			System.out.println(msg);
			// 取得返回值的类型
			reMessage = new GenericData.Record(protocol.getType("message"));
			// 直接构造回复
			reMessage.put("name", "苹果");
			reMessage.put("type", 100);
			reMessage.put("price", 4.6);
			reMessage.put("valid", true);
			reMessage.put("content", "最新上架货物");
		}
		return reMessage;
	}

	public void run() {
		try {
			HttpServer server = new HttpServer(this, port);
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Server(Utils.getProtocol(), 9090).run();
	}
}