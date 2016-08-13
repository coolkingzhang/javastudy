package org.javastudy.hadoop.kafka.test;

public class KafkaProducerDemo implements KafkaProperties {
	public static void main(String[] args) {
		StartThread(1, "mykaka", 100000);
	}

	/**
	 * @param numsOfProducer
	 *            生产者的数目
	 * @param topic
	 *            消息的主题
	 * @param numsOfMessage
	 *            每个生产者生产的消息树
	 * @return
	 */
	public static void StartThread(int numsOfProducer, String topic, int numsOfMessage) {
		for (int i = 1; i <= numsOfProducer; i++) {
			String name = "Producer" + i;
			new Producer(name, topic, numsOfMessage).start();
		}
	}
}
