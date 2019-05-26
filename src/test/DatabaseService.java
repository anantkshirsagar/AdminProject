package test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

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

	public void saveFile(String fileName, InputStream inputStream) throws Exception {
		if (inputStream != null) {
			connectionSettings.build();
			String query = "insert into store_file(file_name, file_content) values(?, ?)";
			PreparedStatement prepareStatement = connectionSettings.getConnection().prepareStatement(query);
			prepareStatement.setString(1, fileName);
			prepareStatement.setBlob(2, inputStream);
			prepareStatement.executeUpdate();
			connectionSettings.closeConnection();
		}
	}

	public Map<String, Blob> getFile(Long id) throws Exception {
		connectionSettings.build();
		String sql = "select * from store_file where id = ?";
		PreparedStatement pstm = connectionSettings.getConnection().prepareStatement(sql);
		pstm.setLong(1, id);
		ResultSet rs = pstm.executeQuery();
		Map<String, Blob> fileDataMap = null;
		if (rs.next()) {
			Blob fileData = rs.getBlob("file_content");
			String fileName = rs.getString("file_name");
			
			fileDataMap = new LinkedHashMap<String, Blob>();
			fileDataMap.put(fileName, fileData);
			connectionSettings.closeConnection();
			return fileDataMap;
		} else {
			connectionSettings.closeConnection();
		}
		return null;
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
