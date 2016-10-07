package com.example.app;

import com.example.app.multicast.MulticastListener;
import com.example.app.multicast.MulticastSender;

import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.*;

import lombok.extern.log4j.Log4j;

@Log4j
public class MulticastConcurrentHashMap<Integer, String> implements Map<Integer, String> {

	private final ConcurrentHashMap<Integer, String> internalMap = new ConcurrentHashMap<>();
	private final MulticastSender multicastSender;
	private final MulticastListener multicastListener;

	public MulticastConcurrentHashMap(InetAddress multicastAddress, int multicastPort) {
		multicastSender = new MulticastSender(multicastAddress, multicastPort);
		multicastListener = new MulticastListener(multicastAddress, multicastPort, internalMap);

		multicastListener.start();
	}

	@Override
	public void clear() {
		internalMap.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return internalMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return internalMap.containsValue(value);
	}

	@Override
	public Set<Entry<Integer, String>> entrySet() {
		return internalMap.entrySet();
	}

	@Override
	public String get(Object key) {
		return internalMap.get(key);
	}

	@Override
	public boolean isEmpty() {
		return internalMap.isEmpty();
	}

	@Override
	public Set<Integer> keySet() {
		return internalMap.keySet();
	}

	@Override
	public String put(Integer key, String value) {
		multicastSender.sendMessage(key, value);
		return internalMap.put(key, value);
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends String> m) {
		throw new UnsupportedOperationException("putAll in MulticastConcurrentHashMap is not supported (yet).");
	}

	@Override
	public String remove(Object key) {
		throw new UnsupportedOperationException("remove in MulticastConcurrentHashMap is not supported (yet).");
	}

	@Override
	public int size() {
		return internalMap.size();
	}

	@Override
	public Collection<String> values() {
		return internalMap.values();
	}
}
