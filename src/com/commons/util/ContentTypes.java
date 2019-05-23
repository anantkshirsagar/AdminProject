package com.commons.util;

public enum ContentTypes {
	TEXT_CSV("text/csv"),
	TEXT_HTML("text/html"),
	TEXT_PLAIN("text/plain"),
	APPLICATION_MSWORD("application/msword"),
	APPLICATION_VND_MS_EXCEL("application/vnd.ms-excel"),
	APPLICATION_JAR("application/jar"),
	APPLICATION_PDF("application/pdf"),
	APPLICATION_OCTET_STREAM("application/octet-stream"),
	APPLICATION_X_ZIP("application/x-zip"),
	IMAGES_JPEG("images/jpeg"),
	IMAGES_PNG("images/png"),
	IMAGES_GIF("images/gif"),
	AUDIO_MP4("audio/mp3"),
	VIDEO_MP4("video/mp4"),
	VIDEO_QUICKTIME("video/quicktime");
	
	private final String contentType;
	private ContentTypes(String contentType) {
		this.contentType = contentType;
	}
	
	public String getContentType() {
		return this.contentType;
	}
}
