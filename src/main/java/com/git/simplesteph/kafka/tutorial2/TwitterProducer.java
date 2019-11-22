package com.git.simplesteph.kafka.tutorial2;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

public class TwitterProducer {

	static Logger logger = LoggerFactory.getLogger(TwitterProducer.class.getName());
	String consumerKey = "QvUQTGwYFT08qvUW53wt27L5r";
	String consumerSecret = "eoe0qsPYYXasg4HfAFuUxj0pyvCAK47vfee6cpxqcVGrt1zw9P";
	String token = "3397480510-mur2rOBK6TgUoZ9a2qg0UiKJCIFaGOMQlqmhNzD";
	String secret = "EAC8LCgNwR99VQMsedJ4qb2mfvjtHUxHX27gXkl0kgtRE";

	public static void main(String[] args) {
		logger.info("Start da Aplicacao");

		new TwitterProducer().run();
	}

	public TwitterProducer() {
		// TODO Auto-generated constructor stub
	}

	public void run() {
		BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(1000);

		logger.info("inicio da Aplicacao");

		// create a twitter client
		Client client = createTwitterClient(msgQueue);
		

		// attempts to establish a connection.
		client.connect();
		logger.info("inicio da Aplicacao22");
		// create a kafka producer

		// loop to see twitts

		while (!client.isDone()) {
			String msg = null;
			try {
				msg = msgQueue.poll(5, TimeUnit.SECONDS);

			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
				client.stop();
			}
			
			if (msg != null) {
				logger.info(msg);
			}
		}
		
		logger.info("Fim da Aplicacao");
	}

	public Client createTwitterClient(BlockingQueue<String> msgQueue) {
		Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
		StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

		List<String> terms = Lists.newArrayList("bitcoin");

		//		hosebirdEndpoint.followings(followings);
		hosebirdEndpoint.trackTerms(terms);

		// These secrets should be read from a config file
		Authentication hosebirdAuth = new OAuth1(consumerKey, consumerSecret, token, secret);
		logger.info("chegamos!!!!");

		ClientBuilder builder = new ClientBuilder()// optional: mainly for the logs
				.hosts(hosebirdHosts).authentication(hosebirdAuth).endpoint(hosebirdEndpoint)
				.processor(new StringDelimitedProcessor(msgQueue));
		// .eventMessageQueue(eventQueue); // optional: use this if you want to process
		// client events

		Client hosebirdClient = builder.build();
		// Attempts to establish a connection.
//				hosebirdClient.connect();
		return hosebirdClient;
	}

}
