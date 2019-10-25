package com.git.simplesteph.kafka.tutorial1;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
//import com.sun.istack.internal.logging.Logger;

public class ProducerDemoKeys {
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(ProducerDemoKeys.class);

		Properties properties = new Properties();
		String bootsrapServers = "172.16.0.2:9092";
//        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		// propriedades do producer
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		System.out.println(StringSerializer.class.getName());

		// crate the producer
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

		for (int i = 0; i < 10; i++) {
			// create a producer record
			ProducerRecord<String, String> record = new ProducerRecord<String, String>("topic3",
					"Hello World " + Integer.toString(i));

			producer.send(record, new Callback() {
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					// TODO Auto-generated method stub Executa sempre que a operação e bem sucedida
					// ou uma exceção e emitida
					if (exception == null) {
						logger.info("Received new Metadata. \n" + "Topic:" + metadata.topic() + "\n" + "Partition:"
								+ metadata.partition() + "\n" + "Offset:" + metadata.offset() + "\n" + "Time:"
								+ metadata.timestamp());
					} else {
						logger.error("Error while Producing ", exception);
					}

				}
			});
		}
		producer.flush();
		producer.close();

	}

}
