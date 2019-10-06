package test;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.fileupload.FileUploadException;

import com.file.download.util.AbstractDownloadHandler;
import com.file.download.util.FileDownloadHandler;
import com.file.download.util.FileDownloadUtils;
import com.file.upload.util.FileUploadUtils;
import com.model.FileContent;

/**
 * Servlet implementation class FileDownloadTest
 */
@WebServlet("/FileDownloadTest")
public class FileDownloadTest extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(FileDownloadTest.class.getName());
	private static final long serialVersionUID = 1L;
	public static final String UPLOAD_FILE_PATH = "C:\\Users\\ANANT\\Desktop\\MyFiles";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AbstractDownloadHandler downloadHandler = new FileDownloadHandler();
		downloadHandler.setFileName("abcd.txt");
		downloadHandler.setHttpServlet(this);
		downloadHandler.setRequest(request);
		downloadHandler.setResponse(response);

		String fileContent = "Download file test successful!";
		try {
			Blob blob = new SerialBlob(fileContent.getBytes());
			downloadHandler.setBlob(blob);
			FileDownloadUtils.downloadFile(downloadHandler);
		} catch (SQLException e) {
			LOG.info("SQLException: " + e);
		}

	}

	public static void fileSaveToPath(HttpServletRequest request) throws IOException {
		try {
			LOG.info("File download...");
			FileContent fileContents = FileUploadUtils.getFileContents(request);
			LOG.info("File name: " + fileContents.getFileName());
			byte[] bytes = fileContents.getBytes();
			Blob blob = new SerialBlob(bytes);
			FileDownloadUtils.saveFileToLocation(UPLOAD_FILE_PATH, fileContents.getFileName(), blob);
		} catch (FileUploadException e) {
			LOG.info("file upload exception " + e);
		} catch (SerialException e) {
			LOG.info("serial exception " + e);
		} catch (SQLException e) {
			LOG.info("SQL exception " + e);
		}
	}

}
