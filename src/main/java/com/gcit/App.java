package com.gcit;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * Hello world!
 *
 */
public class App 
{
	public Connection getConnection() throws SQLException {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection conn = (Connection) DriverManager.getConnection(
				"jdbc:mysql://testrds.c4uwpmj5ivko.us-east-1.rds.amazonaws.com:3306/library", "rootroot", "rootroot");
		return conn;
	}

	public String handler(InputStream input, Context context) throws NumberFormatException, SQLException, ParseException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(reader);
//		JSONObject params = (JSONObject) jsonObject.get("params");
//		JSONObject path = (JSONObject) params.get("path");
//		String authorId = (String) path.get("authorId");
//		//String method = (String) object.get("http-method");
//		String sql = "SELECT * FROM library.tbl_author where authorId=?";
//		Author author = null;
//		PreparedStatement prepareStatement = getConnection().prepareStatement(sql);
//		prepareStatement.setInt(1, Integer.parseInt(authorId));
//		ResultSet resultSet = prepareStatement.executeQuery();
//		author = new Author();
//		while (resultSet.next()) {
//			author.setAuthorId(resultSet.getInt("authorId"));
//			author.setAuthorName(resultSet.getString("authorName"));
//		}
//		System.out.println(author.toString());
		System.out.println("POST Method");
		JSONObject body = (JSONObject) jsonObject.get("body-json");
		String authorId = (String) body.get("authorId");
		String authorName = (String) body.get("authorName");
		String sql = "INSERT INTO `library`.`tbl_author` (`authorId`, `authorName`) VALUES (?, ?)";
		PreparedStatement prepareStatement = getConnection().prepareStatement(sql);
		prepareStatement.setInt(1, Integer.parseInt(authorId));
		prepareStatement.setString(2, authorName);
		prepareStatement.executeUpdate();
		return null;
	}
    
}
