package com.file.csv.upload;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.commons.util.ContentTypes;
import com.commons.util.Encoding;
import com.file.csv.download.CSVData;

/**
 * This class is used to Upload the csv file, collect data from request &
 * response object and return data.
 * 
 * @author Suyog Shah
 *
 */
public class CSVUpload {
	private static final Logger logger = Logger.getLogger(CSVUpload.class.getName());
	private static final int BUFFER_SIZE = 1024;

	/**
	 * This method is used to get uploaded file and return CSVData.
	 * 
	 * @param request
	 * @param response
	 * @param inputTypeName
	 * @return CSVData
	 * @throws ServletException
	 * @throws IOException
	 */
	public static CSVData uploadCSV(HttpServletRequest request, HttpServletResponse response, String inputTypeName)
			throws ServletException, IOException {
		if (request == null) {
			throw new ServletException("HttpServletRequest cannot be null");
		}
		if (response == null) {
			throw new ServletException("HttpServletResponse cannot be null");
		}

		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new ServletException("HttpServletRequest is must be a multipart request");
		}

		response.setContentType(ContentTypes.TEXT_CSV.getContentType());

		Part filePart = request.getPart(inputTypeName);
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		logger.info("Uploaded file name: " + fileName);
		InputStream fileContent = filePart.getInputStream();

		return getCsvData(fileName, filePart, fileContent, getDataFromPart(filePart));
	}

	/**
	 * This method is used to get data from <code>javax.servlet.http.Part</code>.
	 * 
	 * @param part
	 * @return
	 * @throws IOException
	 */
	public static String getDataFromPart(Part part) throws IOException {
		logger.info("Inside getDataFromPart method");
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(part.getInputStream(), Encoding.UTF_8.getEncoding()));
		StringBuilder value = new StringBuilder();
		char[] buffer = new char[BUFFER_SIZE];
		for (int length = 0; (length = reader.read(buffer)) > 0;) {
			value.append(buffer, 0, length);
		}
		return value.toString();
	}

	/**
	 * This method prepared csv data and returns CSVData object.
	 * 
	 * @param fileName
	 * @param filePart
	 * @param inputStream
	 * @param fileData
	 * @return CSVData
	 * @throws IOException
	 */
	private static CSVData getCsvData(String fileName, Part filePart, InputStream inputStream, String fileData)
			throws IOException {
		logger.info("Preparing csv data...");
		CSVData csvData = new CSVData();
		csvData.setFileName(fileName);
		csvData.setFilePart(filePart);
		csvData.setInputStream(inputStream);
		csvData.setFileData(getDataFromPart(filePart));
		logger.info("CSVData prepared");
		return csvData;
	}
}
