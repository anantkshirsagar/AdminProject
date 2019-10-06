package com.commons.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class FileUtils {

	private static final Logger logger = Logger.getLogger(FileUtils.class.getName());

	private FileUtils() {

	}

	public static byte[] toByteArray(InputStream inputStream) throws IOException {
		logger.info("Converting inputstream to byte array...");
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[16384];
		while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}
		return buffer.toByteArray();
	}

	public static InputStream toInputStream(byte[] byteArray) {
		logger.info("Converting byte array to inputstream");
		return new ByteArrayInputStream(byteArray);
	}
}
