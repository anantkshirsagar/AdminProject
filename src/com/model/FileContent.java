package com.model;

import java.io.InputStream;
import java.util.Arrays;

public class FileContent {
	private InputStream inputStream;
	private byte[] bytes;
	private String fileName;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileContents [inputStream=");
		builder.append(inputStream);
		builder.append(", bytes=");
		builder.append(Arrays.toString(bytes));
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append("]");
		return builder.toString();
	}

}
