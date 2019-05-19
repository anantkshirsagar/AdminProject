package com.filedownload.util;

import java.sql.Blob;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IDownloadHandler {
	public HttpServletRequest getRequest();

	public void setRequest(HttpServletRequest request);

	public HttpServletResponse getResponse();

	public void setResponse(HttpServletResponse response);

	public HttpServlet getHttpServlet();

	public void setHttpServlet(HttpServlet httpServlet);

	public String getFileName();

	public void setFileName(String fileName);

	public Blob getBlob();

	public void setBlob(Blob blob);
}
