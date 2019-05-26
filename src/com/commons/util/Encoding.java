package com.commons.util;

public enum Encoding {
	UTF_8("UTF-8");

	private String encoding;

	private Encoding(String encoding) {
		this.encoding = encoding;
	}

	public String getEncoding() {
		return this.encoding;
	}
}