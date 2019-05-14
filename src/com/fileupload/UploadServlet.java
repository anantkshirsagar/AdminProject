package com.fileupload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.fileupload.util.FileUploadUtils;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String uploadFilePath = "H:\\eclipse-14-May-2019-workspace\\AdminProject\\uploadedfiles";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			//FileUploadUtils.uploadFile(uploadFilePath, request);
			Map<String, InputStream> fileInputStream = FileUploadUtils.getFileInputStream(request);
			for(Map.Entry<String, InputStream> fileIS : fileInputStream.entrySet()) {
				System.out.println(" FileName: " +fileIS.getKey());
				FileUploadUtils.writeStream(fileIS.getValue(), new File(uploadFilePath, fileIS.getKey()));
			}
		} catch (FileUploadException e) {
			System.out.println(" Fileupload exception: " +e);
		}
	}
}