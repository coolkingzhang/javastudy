package org.javastudy.hadoop.kafka;

import java.util.Properties;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducer {

	private final Producer<String, String> producer;

	public final static String TOPIC = "helloworld";

	private KafkaProducer() {
		Properties props = new Properties();
		// 此处配置的是kafka的端口
		props.put( "metadata.broker.list", "192.168.189.200:9092,192.168.189.201:9092" );
		// 配置value的序列化类
		props.put( "serializer.class", "kafka.serializer.StringEncoder" );
		// 配置key的序列化类
		props.put( "key.serializer.class", "kafka.serializer.StringEncoder" );
		props.put( "request.required.acks", "-1" );
		
		//props.put("num.partitions", 1);
        props.put("producer.type", "sync");
		producer = new Producer<String, String>( new ProducerConfig( props ) );
	}

	void produce() {
		int messageNo = 3000;
		final int COUNT = 10000;

		while( messageNo < COUNT ) {
			String key = String.valueOf( messageNo );
			String data = "ldjflsjf中国@!#$FDSFWVqfdfw$#FF" + key;
			producer.send( new KeyedMessage<String, String>( TOPIC, key, data ) );
			System.out.println( "send:" + data );
			messageNo++;
		}
	}

	public static void main( String[] args ) {
		new KafkaProducer().produce();
	}
}