package com.git.simplesteph.kafka.tutorial1;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProducerDemo {
	public static void main(String[] args) {
		System.out.println("Hello World -------!");
		Properties properties = new Properties();
		String bootsrapServers = "172.16.0.2:9092";
//        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		//propriedades do producer
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		System.out.println(StringSerializer.class.getName());
		
		//crate the producer
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
		
		//create a producer record
		ProducerRecord<String,String> record = new ProducerRecord<String, String>("teste","Hello World");
		
		producer.send(record);
		producer.flush();
		producer.close();
	
	}

}
