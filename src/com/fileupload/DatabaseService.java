package com.fileupload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;

import com.dbmanager.connection.setting.AbstractConnectionSettings;
import com.dbmanager.connection.setting.ConnectionSettings;
import com.dbmanager.objectify.Objectify;
import com.dbmanager.property.util.PropertyReader;

public class DatabaseService {
	private AbstractConnectionSettings connectionSettings;
	public static final String PROPERTIES = "H:\\eclipse-14-May-2019-workspace\\AdminProject\\resources\\mysql.properties";

	public DatabaseService() throws IOException {
		connectionSettings = new ConnectionSettings(new PropertyReader(new File(PROPERTIES)).readProperties());
	}

	public void saveFile(InputStream inputStream) throws Exception {
		connectionSettings.build();
		String query = "insert into store_file(file_content) values(?)";
		PreparedStatement prepareStatement = connectionSettings.getConnection().prepareStatement(query);
		prepareStatement.setObject(1, Objectify.deserialize(toByteArray(inputStream)));
		prepareStatement.executeUpdate();
		connectionSettings.closeConnection();
	}	

	public static byte[] toByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;

		// read bytes from the input stream and store them in buffer
		while ((len = in.read(buffer)) != -1) {
			// write bytes from the buffer into output stream
			os.write(buffer, 0, len);
		}
		return os.toByteArray();
	}
}
