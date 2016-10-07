package com.example.app;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import lombok.extern.log4j.Log4j;

/**
 * Hello world!
 * <p>
 */
@Log4j
public class App {

	public static void main(String[] args) throws UnknownHostException {
		log.info("Hello World!");

		Map<Integer, String> map = new MulticastConcurrentHashMap<>(InetAddress.getByName("232.57.108.73"), 24625);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			log.warn(ex, ex);
		}

//		map.put(1, "test1");
//		log.info(map.size());
//		map.put(2, "test2");
//		log.info(map.size());
//		map.put(3, "test3");
//		log.info(map.size());
//		map.put(4, "test4");
//		log.info(map.size());
//		map.put(1, "test1");
//		log.info(map.size());
		while (true) {
			log.info(map.size());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				log.warn(ex, ex);
			}
		}
	}
}
