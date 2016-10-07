package com.example.app.multicast;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.*;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j @AllArgsConstructor
public class MulticastListener<K, V> extends Thread {

	private final static String MESSAGE_DELIMINATOR = ":";
	private final InetAddress multicastAddress;
	private final int multicastPort;
	private final ConcurrentHashMap<Integer, String> internalMap;

	@Override
	public void run() {

		// Create a buffer of bytes, which will be used to store
		// the incoming bytes containing the information from the server.
		// Since the message is small here, 256 bytes should be enough.
		// Need to define the size of the byte array
		byte[] buf = new byte[256];

		try (MulticastSocket clientSocket = new MulticastSocket(multicastPort)) {
			clientSocket.joinGroup(multicastAddress);
			log.info("MulticastListener started to listen on " + multicastAddress.getHostAddress() + ":" + multicastPort);

			while (!isInterrupted()) {
				DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
				// clientSocket.receive is blocking
				clientSocket.receive(msgPacket);

				// Ignore messages from itself
				if (!InetAddress.getLocalHost().getHostAddress().equals(msgPacket.getAddress().getHostAddress())) {
					String msg = new String(buf, 0, buf.length);
					log.info("Socket 1 received msg: " + msg);

					String[] split = msg.split(MESSAGE_DELIMINATOR);
					internalMap.put(Integer.valueOf(split[0]), split[1]);
				}
			}

			clientSocket.leaveGroup(multicastAddress);
			clientSocket.close();
		} catch (IOException ex) {
			log.error(ex, ex);
		}
	}
}
