package test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.file.download.util.AbstractDownloadHandler;
import com.file.download.util.FileDownloadHandler;
import com.file.download.util.FileDownloadUtils;
import com.file.upload.util.FileUploadUtils;
import com.model.FileContent;

@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String UPLOAD_FILE_PATH = "C:\\Users\\ANANT\\Desktop\\MyFiles";
	public static final String DOWNLOAD_FILE_PATH = "H:\\eclipse-14-May-2019-workspace\\AdminProject\\downloadedfiles";

	private static final Logger logger = Logger.getLogger(UploadServlet.class.getName());

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			FileContent fileContents = FileUploadUtils.getFileContents(request);
			FileUploadUtils.writeStream(fileContents.getInputStream(), fileContents.getFileName(), UPLOAD_FILE_PATH);

			// Multiple file uploads
			// Call to getFileContents and pass request, it will return FileContents which
			// contains
			// filename, inputstream and byte[] of file, whatever you required you can use
			// it.
			List<FileContent> fileContentsList = FileUploadUtils.getMultipleFileContents(request);
			for(FileContent fileContent : fileContentsList) {
				FileUploadUtils.writeStream(fileContent.getInputStream(), fileContent.getFileName(), UPLOAD_FILE_PATH);
				//For retrieving byte[] from file content
				//fileContent.getBytes()
			}
			
			

			// insertFile(request);
			// downloadFile(request, response);
			// downloadFileClientSide(request, response);
			// downloadFile(request, response, this);

//			DatabaseService dbService = new DatabaseService();
//			Map.Entry<String, Blob> fileDataMap = dbService.getFile(2L).entrySet().iterator().next();
//			String fileName = fileDataMap.getKey();
//			Blob fileContents = fileDataMap.getValue();
//			FileDownloadUtils.saveFileToLocation("C:\\Users\\ANANT\\Desktop\\Kachara", fileName, fileContents);			
		} catch (Exception e) {
			System.out.println(" Exception: " + e);
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

	public void downloadFile(HttpServletRequest request, HttpServletResponse response, HttpServlet httpServlet)
			throws Exception {
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