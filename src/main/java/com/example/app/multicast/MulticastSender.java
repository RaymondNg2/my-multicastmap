package com.example.app.multicast;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j @AllArgsConstructor
public class MulticastSender {

	private final static String MESSAGE_DELIMINATOR = ":";
	private final InetAddress multicastAddress;
	private final int multicastPort;

	public void sendMessage(Object key, Object value) {
		String msg = new StringJoiner(MESSAGE_DELIMINATOR).add(key.toString()).add(value.toString()).toString();

		// Open a new DatagramSocket, which will be used to send the data.
		try (DatagramSocket serverSocket = new DatagramSocket()) {
			// Create a packet that will contain the data
			// (in the form of bytes) and send it.
			DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
														  msg.length(),
														  multicastAddress,
														  multicastPort);
			serverSocket.send(msgPacket);
			serverSocket.close();
			log.info("Server sent packet with msg: " + msg);
		} catch (IOException ex) {
			// what should be done when this is catched?
			// resend? throw?
			log.error(ex, ex);
		}
	}
}
