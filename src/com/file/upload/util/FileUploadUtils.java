package com.file.upload.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * This class contains utility functions which is used to upload file.
 * 
 * @author Anant Kshirsagar
 *
 */
public class FileUploadUtils {
	private static final Logger logger = Logger.getLogger("FileUploadUtils");

	/**
	 * This is method is used to upload file.
	 * 
	 * @param uploadPath
	 * @param request
	 * @return
	 * @throws FileUploadException
	 * @throws IOException
	 */
	public static void uploadFile(String uploadPath, HttpServletRequest request)
			throws FileUploadException, IOException {
		File fileSaveDir = new File(uploadPath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdirs();
			logger.info("Directory created! Path: " + uploadPath);
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		List<FileItem> items = upload.parseRequest(request);
		logger.info("File item size: " + items.size());
		for (FileItem fileItem : items) {
			String fileName = fileItem.getName();
			logger.info("Filename: " + fileName);
			if (!fileItem.isFormField()) {
				InputStream inputStream = fileItem.getInputStream();
				writeStream(inputStream, fileName, uploadPath);
			}
		}
	}

	/**
	 * This method takes inputStream, fileName, uploadPath and it upload the file in
	 * the desired location.
	 * 
	 * @param inputStream
	 * @param fileName
	 * @param uploadPath
	 * @throws IOException
	 */
	public static void writeStream(InputStream inputStream, String fileName, String uploadPath) throws IOException {
		if (inputStream == null) {
			throw new NullPointerException("Input stream is empty!");
		}

		byte[] buffer = new byte[inputStream.available()];
		inputStream.read(buffer);
		File targetFile = new File(uploadPath, fileName);
		logger.info("Target filename: " + targetFile.getAbsolutePath());
		OutputStream outStream = new FileOutputStream(targetFile);
		outStream.write(buffer);
		inputStream.close();
		outStream.flush();
		outStream.close();
		logger.info("File upload/download successfully! ");
	}

	/**
	 * This method is used to write stream into file. File must contains upload path
	 * + filename.<br>
	 * <code>File file = new File(uploadPath, fileName);</code>
	 * 
	 * @param inputStream
	 * @param file
	 * @throws IOException
	 */
	public static void writeStream(InputStream inputStream, File file) throws IOException {
		if (inputStream == null) {
			throw new NullPointerException("Input stream is empty!");
		}

		byte[] buffer = new byte[inputStream.available()];
		inputStream.read(buffer);
		logger.info("Target filename: " + file.getAbsolutePath());
		OutputStream outStream = new FileOutputStream(file);
		outStream.write(buffer);
		inputStream.close();
		outStream.flush();
		outStream.close();
		logger.info("File upload/download successfully! ");
	}

	/**
	 * This method is used to get HttpServletRequest and it returns the Map contains
	 * file name and input streams.
	 * 
	 * @param request
	 * @return
	 * @throws FileUploadException
	 * @throws IOException
	 */
	public static Map<String, InputStream> getFileInputStream(HttpServletRequest request)
			throws FileUploadException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		List<FileItem> items = upload.parseRequest(request);
		Map<String, InputStream> inputStreamList = new LinkedHashMap<String, InputStream>();
		for (FileItem fileItem : items) {
			String fileName = fileItem.getName();
			logger.info("Filename: " + fileName);
			if (!fileItem.isFormField()) {
				InputStream inputStream = fileItem.getInputStream();
				inputStreamList.put(fileName, inputStream);
			}
		}
		logger.info("Input stream size: " + inputStreamList.size());
		return inputStreamList;
	}
}