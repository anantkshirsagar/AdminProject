package com.filedownload.util;

import java.sql.Blob;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownloadHandler extends AbstractDownloadHandler {

	@Override
	public HttpServletRequest getRequest() {
		return this.request;
	}

	@Override
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public HttpServletResponse getResponse() {
		return this.response;
	}

	@Override
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public HttpServlet getHttpServlet() {
		return this.httpServlet;
	}

	@Override
	public void setHttpServlet(HttpServlet httpServlet) {
		this.httpServlet = httpServlet;
	}

	@Override
	public String getFileName() {
		return this.fileName;
	}

	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public Blob getBlob() {
		return this.blob;
	}

	@Override
	public void setBlob(Blob blob) {
		this.blob = blob;
	}
}
