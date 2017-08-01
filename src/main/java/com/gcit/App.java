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
	    JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject)jsonParser.parse(new InputStreamReader(input, "UTF-8"));
		
		//System.out.println(input.toString());
		//System.out.println(jsonObject.toJSONString());
		
		String sqlCommand = ""; String result = "";
		//String method = (String) jsonObject.get("http-method");
		String method = "GET";
		//JSONObject body = (JSONObject) jsonObject.get("body-json");
		
		if ("GET".equals(method)) {
			System.out.println("GET Method");
			Long authorId = (Long) jsonObject.get("authorId");
			sqlCommand = "SELECT * FROM `library`.`tbl_author` WHERE `authorId` = ?";
			PreparedStatement prepareStatement1 = getConnection().prepareStatement(sqlCommand);
			prepareStatement1.setLong(1, authorId);
			ResultSet rs = prepareStatement1.executeQuery();
			Author author = new Author();
			while(rs.next()){
				author.setAuthorId(rs.getInt("authorId"));
				author.setAuthorName(rs.getString("authorName"));
			}
			result = author.toString();
			System.out.println(result);
			return result;
			//return (JSONObject)jsonParser.parse(result);
		}
		else if ("POST".equals(method)){
			System.out.println("POST Method");
			String authorName = (String) jsonObject.get("authorName");
			sqlCommand = "INSERT INTO `library`.`tbl_author` (`authorName`) VALUES (?)";
			PreparedStatement prepareStatement2 = getConnection().prepareStatement(sqlCommand);
			prepareStatement2.setString(1, authorName);
			prepareStatement2.executeUpdate();
		}
		
		return "okay";
		//return (JSONObject)jsonParser.parse("null");
	}
    
}
