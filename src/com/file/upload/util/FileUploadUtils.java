package com.file.upload.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.commons.util.FileUtils;
import com.model.FileContent;

/**
 * This class contains utility functions which is used to upload file.
 * 
 * @author Anant Kshirsagar
 *
 */
public class FileUploadUtils {

	private static final Logger logger = Logger.getLogger(FileUploadUtils.class.getName());

	public static List<FileItem> getFileItems(HttpServletRequest request) throws FileUploadException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);

		List<FileItem> items = servletFileUpload.parseRequest(request);
		logger.info("File item size: " + items.size());
		return items;
	}

	/**
	 * This method is used to get single file from HttpServletRequest and it returns
	 * FileContent which contains, inputstream, byte[] and filename
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws FileUploadException
	 */
	public static FileContent getFileContents(HttpServletRequest request) throws IOException, FileUploadException {
		List<FileItem> items = getFileItems(request);
		FileContent fileContents = new FileContent();
		if (items != null && !items.isEmpty()) {
			FileItem fileItem = items.get(0);
			String fileName = fileItem.getName();
			fileContents.setFileName(fileName);
			logger.info("Filename: " + fileName);
			if (!fileItem.isFormField()) {
				fileContents.setBytes(FileUtils.toByteArray(fileItem.getInputStream()));
				InputStream inputStream = new ByteArrayInputStream(fileContents.getBytes());
				fileContents.setInputStream(inputStream);
			}
		}
		return fileContents;
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
		logger.info("File uploaded successfully! ");
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
	 * This method is used to return FileContent which contains filename and input
	 * stream
	 * 
	 * @param request
	 * @return List<FileContents>
	 * @throws FileUploadException
	 * @throws IOException
	 */
	public static List<FileContent> getMultipleFileContents(HttpServletRequest request)
			throws FileUploadException, IOException {
		List<FileItem> items = getFileItems(request);
		List<FileContent> fileContentList = new ArrayList<FileContent>();
		for (FileItem fileItem : items) {
			FileContent fileContent = new FileContent();
			String fileName = fileItem.getName();
			fileContent.setFileName(fileName);
			logger.info("Filename: " + fileName);
			if (!fileItem.isFormField()) {
				logger.info("Inside if");
				fileContent.setBytes(FileUtils.toByteArray(fileItem.getInputStream()));
				InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes());
				fileContent.setInputStream(inputStream);
			}
			fileContentList.add(fileContent);
		}
		return fileContentList;
	}
}