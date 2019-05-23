package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.file.csv.parser.CSVDownload;

@WebServlet("/TestCsv")
public class TestCsv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TestCsv() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		CSVDownload.downloadCSV(getData(), response, "StudentInfo.csv");
//		response.setContentType(ContentTypes.TEXT_CSV.getContentType());
//		response.setHeader("Content-Disposition", "attachment; filename=\"userDirectory.csv\"");
//		try {
//			OutputStream outputStream = response.getOutputStream();
//			String outputResult = "xxxx, yyyy, zzzz, aaaa, bbbb, ccccc, dddd, eeee, ffff, gggg\nxxxx, yyyy, zzzz, aaaa, bbbb, ccccc, dddd, eeee, ffff, gggg\n";
//			outputStream.write(outputResult.getBytes());
//			outputStream.flush();
//			outputStream.close();
//		} catch (Exception e) {
//			System.out.println(e.toString());
//		}
	}
	
	public List<List<String>> getData(){
		List<List<String>> data = new  ArrayList<List<String>>();
		List<String> headerRow = new ArrayList<String>();
		headerRow.add("Name");
		headerRow.add("Phone No");
		headerRow.add("City");
		
		data.add(headerRow);
		List<String> dataRow1 = new ArrayList<String>();
		dataRow1.add("Anant Kshirsagar");
		dataRow1.add("9090909090");
		dataRow1.add("Pune");
		data.add(dataRow1);
		
		List<String> dataRow2 = new ArrayList<String>();
		dataRow2.add("Suyog Shah");
		dataRow2.add("8080808080");
		dataRow2.add("Mumbai");
		data.add(dataRow2);
		return data;
	}
}
