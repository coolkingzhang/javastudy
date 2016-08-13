package org.javastudy.hadoop.kafka.test;

public interface KafkaProperties {
	final static String zkConnect = "zookeeper:2181";
	final static String groupId = "group1";
	final static String topic = "mykaka";
	final static String kafkaServerURL = "zookeeper";
	final static int kafkaServerPort = 9092;
	final static int kafkaProducerBufferSize = 64 * 1024;
	final static int connectionTimeOut = 100000;
	final static int reconnectInterval = 10000;
	final static String clientId = "SimpleConsumerDemoClient";
}