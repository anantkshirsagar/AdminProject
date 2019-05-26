package com.file.download.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import com.commons.util.ContentTypes;
import com.file.upload.util.FileUploadUtils;

/**
 * This class is used to download the files at client side.
 * 
 * @author Anant Kshirsagar
 *
 */
public class FileDownloadUtils {
	private static final Logger logger = Logger.getLogger(FileDownloadUtils.class.getName());
	private static final String CONTENT_DISPOSITION = "Content-Disposition";
	private static final String ATTACHMENT = "attachment; filename=";

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
	public static void saveFileToLocation(final String savePath, String fileName, Blob fileContents)
			throws SQLException, IOException {
		File file = new File(savePath, fileName);
		logger.info("File: " + file.getName());
		logger.info("Location: " + file.getAbsolutePath());
		InputStream inputStream = fileContents.getBinaryStream();
		FileUploadUtils.writeStream(inputStream, file);
		logger.info("File saved successfully!");
	}

	/**
	 * This method is used to download file at client side.
	 * 
	 * @param downloadHandler
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void downloadFile(AbstractDownloadHandler downloadHandler) throws IOException, SQLException {
		String fileName = downloadHandler.getFileName();
		logger.info("Filename: " + fileName);
		Blob blob = downloadHandler.getBlob();
		InputStream inputStream = blob.getBinaryStream();
		ServletContext context = downloadHandler.getHttpServlet().getServletContext();

		// sets MIME type for the file download
		String mimeType = context.getMimeType(downloadHandler.getFileName());
		if (mimeType == null) {
			mimeType = ContentTypes.APPLICATION_OCTET_STREAM.getContentType();
		}

		int fileLength = inputStream.available();
		logger.info("File length: " + fileLength);

		downloadHandler.getResponse().setContentType(mimeType);
		downloadHandler.getResponse().setContentLength(fileLength);
		String headerKey = CONTENT_DISPOSITION;
		String headerValue = ATTACHMENT + fileName;

		logger.info("Header key: " + headerKey);
		logger.info("Header value: " + headerValue);
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
