package com.file.csv.parser;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import com.commons.util.ContentTypes;

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
	
	/** TODO
	 * Implement method which takes string contains comma separated rows and enter for new line and parse the string and download csv file
	 * Method params: String filename, String data, HttpServletResponse response
	 */
}
