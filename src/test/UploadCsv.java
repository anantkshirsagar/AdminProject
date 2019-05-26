package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.file.csv.download.CSVData;
import com.file.csv.upload.CSVUpload;

@MultipartConfig
@WebServlet("/UploadCsv")
public class UploadCsv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UploadCsv() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CSVData csvData = CSVUpload.uploadCSV(request, response, "mycsvfile");
		System.out.println(csvData.getFileData());

//		response.setContentType(ContentTypes.TEXT_CSV.getContentType());
//		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//		System.out.println(isMultipart);
//		Part filePart = request.getPart("mycsvfile"); // Retrieves <input type="file" name="file">
//		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
//		InputStream fileContent = filePart.getInputStream();
//		//String value = getValue(filePart);
//		String value = getValueFromInputStream(fileContent);
//		System.out.println(value);
	}

	private static String getValue(Part part) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
		StringBuilder value = new StringBuilder();
		char[] buffer = new char[1024];
		for (int length = 0; (length = reader.read(buffer)) > 0;) {
			value.append(buffer, 0, length);
		}
		return value.toString();
	}

	private static String getValueFromInputStream(InputStream inputStream) throws IOException {
		int data = inputStream.read();
		char ch = 0;
		String stringData = "";
		while (data != -1) {
			data = inputStream.read();
			ch = (char) data;
			stringData += String.valueOf(ch);
		}
		inputStream.close();
		return stringData;
	}
}
