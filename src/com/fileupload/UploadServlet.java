package com.fileupload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.filedownload.util.AbstractDownloadHandler;
import com.filedownload.util.FileDownloadHandler;
import com.filedownload.util.FileDownloadUtils;
import com.fileupload.util.FileUploadUtils;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String UPLOAD_FILE_PATH = "H:\\eclipse-14-May-2019-workspace\\AdminProject\\uploadedfiles";
	public static final String DOWNLOAD_FILE_PATH = "H:\\eclipse-14-May-2019-workspace\\AdminProject\\downloadedfiles";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// insertFile(request);
			// downloadFile(request, response);
			//downloadFileClientSide(request, response);
			downloadFile(request, response, this);
		} catch (Exception e) {
			System.out.println(" Exception: " + e);
		}
	}

	private void uploadFile(HttpServletRequest request) throws FileUploadException, IOException {
		FileUploadUtils.uploadFile(UPLOAD_FILE_PATH, request);
	}

	private void insertFile(HttpServletRequest request) throws Exception {
		DatabaseService dbService = new DatabaseService();
		Map<String, InputStream> fileInputStream = FileUploadUtils.getFileInputStream(request);
		for (Map.Entry<String, InputStream> fileIS : fileInputStream.entrySet()) {
			System.out.println(" FileName: " + fileIS.getKey());
			// FileUploadUtils.writeStream(fileIS.getValue(), new File(uploadFilePath,
			// fileIS.getKey()));
			dbService.saveFile(fileIS.getKey(), fileIS.getValue());
		}
	}

	private void downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = Long.parseLong(request.getParameter("id"));
		System.out.println(" id: " + id);
		DatabaseService dbService = new DatabaseService();
		Map<String, Blob> fileDataMap = dbService.getFile(id);
		String fileName = null;
		Blob fileContent = null;
		for (Map.Entry<String, Blob> entry : fileDataMap.entrySet()) {
			fileName = entry.getKey();
			fileContent = entry.getValue();
		}
		File file = new File(DOWNLOAD_FILE_PATH, fileName);
		InputStream inputStream = fileContent.getBinaryStream();
		FileUploadUtils.writeStream(inputStream, file);
		inputStream.close();
	}

	private void downloadFileClientSide(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = Long.parseLong(request.getParameter("id"));
		System.out.println(" id: " + id);
		DatabaseService dbService = new DatabaseService();
		Map<String, Blob> fileDataMap = dbService.getFile(id);
		String fileName = null;
		Blob fileContent = null;

		for (Map.Entry<String, Blob> entry : fileDataMap.entrySet()) {
			fileName = entry.getKey();
			fileContent = entry.getValue();
		}
		InputStream inputStream = fileContent.getBinaryStream(); 
		int fileLength = inputStream.available();

		System.out.println("fileLength = " + fileLength);

		ServletContext context = getServletContext();

		// sets MIME type for the file download
		String mimeType = context.getMimeType(fileName);
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}

		// set content properties and header attributes for the response
		response.setContentType(mimeType);
		response.setContentLength(fileLength);
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", fileName);
		response.setHeader(headerKey, headerValue);

		// writes the file to the client
		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[1024];
		int bytesRead = -1;

		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}

		inputStream.close();
		outStream.close();
	}
	
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, HttpServlet httpServlet) throws Exception {
		Long id = Long.parseLong(request.getParameter("id"));
		System.out.println(" id: " + id);
		DatabaseService dbService = new DatabaseService();
		Map<String, Blob> fileDataMap = dbService.getFile(id);
		String fileName = null;
		Blob fileContent = null;

		for (Map.Entry<String, Blob> entry : fileDataMap.entrySet()) {
			fileName = entry.getKey();
			fileContent = entry.getValue();
		}

		AbstractDownloadHandler handle = new FileDownloadHandler();
		handle.setBlob(fileContent);
		handle.setFileName(fileName);
		handle.setRequest(request);
		handle.setResponse(response);
		handle.setHttpServlet(httpServlet);
		FileDownloadUtils.downloadFile(handle);
	}
}