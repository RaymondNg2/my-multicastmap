package com.example.app;

import java.io.*;
import java.util.zip.*;

import lombok.extern.log4j.Log4j;

@Log4j
public class StringGZipper {

	public static byte[] compress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException("str should not be null or empty");
		}
		log.info("String length : " + str.length());
		ByteArrayOutputStream obj = new ByteArrayOutputStream();
		try (GZIPOutputStream gzip = new GZIPOutputStream(obj)) {
			gzip.write(str.getBytes("UTF-8"));
		}
		String outStr = obj.toString("UTF-8");
		byte[] bytes = outStr.getBytes();
		log.info("Output byte[] length : " + bytes.length);
		return bytes;
	}

	public static String decompress(byte[] byteArray) throws IOException {
		if (byteArray == null || byteArray.length == 0) {
			throw new IllegalArgumentException("byteArray should not be null or empty");
		}
		log.info("Input String length : " + byteArray.length);
		GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(byteArray));
		BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
		String outStr = "";
		String line;
		while ((line = bf.readLine()) != null) {
			outStr += line;
		}
		log.info("Output String lenght : " + outStr.length());
		return outStr;
	}
}
