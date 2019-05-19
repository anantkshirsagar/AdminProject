package com.filedownload.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import com.fileupload.util.FileUploadUtils;

/**
 * This class is used to download the files at client side
 * 
 * @author Anant Kshirsagar
 *
 */
public class FileDownloadUtils {
	private static final Logger logger = Logger.getLogger("FileDownloadUtils");

	/**
	 * This method is takes two arguments savePath and fileDataMap savePath is the
	 * location to save file and fileDataMap is the contains String - fileName and
	 * Blob - fileContent
	 * 
	 * @param savePath
	 * @param fileDataMap
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void saveFileToLocation(final String savePath, Map<String, Blob> fileDataMap)
			throws SQLException, IOException {
		String fileName = null;
		Blob fileContent = null;
		for (Map.Entry<String, Blob> entry : fileDataMap.entrySet()) {
			fileName = entry.getKey();
			fileContent = entry.getValue();
		}
		File file = new File(savePath, fileName);
		logger.info("File: " + file.getName());
		InputStream inputStream = fileContent.getBinaryStream();
		FileUploadUtils.writeStream(inputStream, file);
		logger.info("File saved successfully!");
	}

	/**
	 * This method is used to download file at client side
	 * @param downloadHandler
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void downloadFile(AbstractDownloadHandler downloadHandler) throws IOException, SQLException {
		String fileName = downloadHandler.getFileName();
		logger.info("Filename: " +fileName);
		Blob blob = downloadHandler.getBlob();
		InputStream inputStream = blob.getBinaryStream();
		ServletContext context = downloadHandler.getHttpServlet().getServletContext();

		// sets MIME type for the file download
		String mimeType = context.getMimeType(downloadHandler.getFileName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		
		int fileLength = inputStream.available();
		logger.info("File length: " +fileLength);
		
		downloadHandler.getResponse().setContentType(mimeType);
		downloadHandler.getResponse().setContentLength(fileLength);
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", fileName);
		
		logger.info("Header key: " +headerKey);
		logger.info("Header value: " +headerValue);
		downloadHandler.getResponse().setHeader(headerKey, headerValue);

		OutputStream outStream = downloadHandler.getResponse().getOutputStream();

		byte[] buffer = new byte[1024];
		int bytesRead = -1;

		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		inputStream.close();
		outStream.close();
	}
}
