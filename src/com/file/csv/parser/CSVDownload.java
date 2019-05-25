package com.file.csv.parser;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.commons.util.ContentTypes;

/**
 * This class provides methods to download csv.
 * 
 * @author Anant Kshirsagar
 *
 */
public class CSVDownload {
	private static final Logger logger = Logger.getLogger("CSVDownload");
	private static final String NEW_LINE = "\n";
	private static final String COMMA = ",";

	private enum Header {
		CONTENT_DISPOSITION("Content-Disposition"), ATTACHMENT("attachment; filename=");
		private final String metadata;

		private Header(String metadata) {
			this.metadata = metadata;
		}

		public String getMetadata() {
			return this.metadata;
		}
	}

	/**
	 * This method is used to build data string and download csv file with specified
	 * filename
	 * 
	 * @param data
	 * @param response
	 * @param fileName
	 * @throws IOException
	 */
	public static void downloadCSV(List<List<String>> data, HttpServletResponse response, String fileName)
			throws IOException {
		if (data == null) {
			logger.info("Data is null");
			throw new NullPointerException("Null data found");
		}
		response.setContentType(ContentTypes.TEXT_CSV.getContentType());
		response.setHeader(Header.CONTENT_DISPOSITION.getMetadata(), Header.ATTACHMENT.getMetadata().concat(fileName));
		OutputStream outputStream = response.getOutputStream();
		String output = parseDataAndMakeString(data);
		outputStream.write(output.getBytes());
		outputStream.flush();
		outputStream.close();
		logger.info("CSV download successfully!");
	}

	/**
	 * This method is responsible to convert List<List<String>> data into a string.
	 * 
	 * @param data
	 * @return
	 */
	private static String parseDataAndMakeString(List<List<String>> data) {
		logger.info("Inside parseDataAndMakeString method");
		StringBuilder content = new StringBuilder();
		for (List<String> rows : data) {
			for (String column : rows) {
				content.append(column.concat(COMMA));
			}
			content.append(NEW_LINE);
		}
		return content.toString();
	}

	/**
	 * 
	 * This method takes filename, data in the comma delimited format then parse
	 * data and download csv with provided filename
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void downloadCSV(String fileName, String data, HttpServletResponse response)
			throws ServletException, IOException {
		if (data == "" || data == null) {
			throw new NullPointerException("Data cannot be empty");
		}

		if (fileName == "" || fileName == null) {
			throw new NullPointerException("Filename cannot be empty");
		}

		if (response == null) {
			throw new ServletException("HttpServletResponse cannot be null");
		}

		logger.info("Preparing data...");
		List<List<String>> dataList = parse(data);
		downloadCSV(dataList, response, fileName);
	}

	/**
	 * This method is responsible to parse data which is given in comma separated
	 * format and convert it into List<List<String>>
	 * 
	 * @param data
	 * @return List<List<String>>
	 */
	private static List<List<String>> parse(String data) {
		logger.info("Inside parse method");
		List<List<String>> dataList = new ArrayList<>();
		StringTokenizer rowTokens = new StringTokenizer(data, NEW_LINE);
		List<String> rows = new ArrayList<>();
		while (rowTokens.hasMoreElements()) {
			rows.add(rowTokens.nextToken().trim());
		}

		if (rows != null) {
			for (String row : rows) {
				StringTokenizer columnTokens = new StringTokenizer(row, COMMA);
				List<String> columnData = new ArrayList<>();
				while (columnTokens.hasMoreElements()) {
					columnData.add(columnTokens.nextToken().trim());
				}
				dataList.add(columnData);
			}
		}
		logger.info("Data prepared successfully");
		return dataList;
	}
}
