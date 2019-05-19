package com.email;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

import com.dbmanager.connection.setting.AbstractConnectionSettings;
import com.dbmanager.connection.setting.ConnectionSettings;
import com.dbmanager.property.util.PropertyReader;

public class DatabaseService {
	private static final Logger logger = Logger.getLogger("DatabaseService.class");
	private AbstractConnectionSettings connectionSettings;
	public static final String PROPERTIES = "C://Users//Avengers//workspace//Module//src//system.properties";

	public DatabaseService() throws IOException {
		connectionSettings = new ConnectionSettings(new PropertyReader(new File(PROPERTIES)).readProperties());
	}

	public String getSendGridApiKey() throws Exception {
		String string = "";
		connectionSettings.build();
		PreparedStatement prepareStatement = connectionSettings.getConnection().prepareStatement("select * from api");
		ResultSet executeQuery = prepareStatement.executeQuery();
		if (executeQuery.next()) {
			string = executeQuery.getString("sendgrid");
		}
		return string;
	}

}