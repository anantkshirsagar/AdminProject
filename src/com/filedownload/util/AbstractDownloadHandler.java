package com.filedownload.util;

import java.sql.Blob;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractDownloadHandler implements IDownloadHandler {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpServlet httpServlet;
	protected String fileName;
	protected Blob blob;
}
