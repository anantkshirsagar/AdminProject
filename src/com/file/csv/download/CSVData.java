package com.file.csv.download;

import java.io.InputStream;

import javax.servlet.http.Part;

/**
 * This class is used to store the files data.
 * 
 * @author Suyog Shah
 *
 */
public class CSVData {
	private String fileName;
	private Part filePart;
	private InputStream inputStream;
	private String fileData;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Part getFilePart() {
		return filePart;
	}

	public void setFilePart(Part filePart) {
		this.filePart = filePart;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileData() {
		return fileData;
	}

	public void setFileData(String fileData) {
		this.fileData = fileData;
	}
}
