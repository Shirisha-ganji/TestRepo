package com.upload;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Insert {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
			try
			{
			String jdbcURL = "jdbc:mysql://localhost:3306/testing?zeroDateTimeBehavior=convertToNull";
			String username = "root";
	     	String password = "Siri@123";
	     	Connection con=null;
	     	PreparedStatement p3=null;
	     	Class.forName("com.mysql.jdbc.Driver");
    	 	con = DriverManager.getConnection(jdbcURL, username, password);
    	 	String path="C:\\Documents\\Files\\UserInfo.csv";
    	 	BufferedReader lineReader = new BufferedReader(new FileReader(path));
    	 	String lineText = null;
    	 	lineReader.readLine();
    	 	
    	 	while ((lineText = lineReader.readLine()) != null) 
         		{
        	 
        	 	String[] data = lineText.split(",");
	     	String sql3 = "INSERT INTO FileData (username, emailaddress, Phonenumber) VALUES (?, ?, ?)";
			p3= con.prepareStatement(sql3);
			p3.setString(1, data[0]); 
			p3.setString(2, data[1]);
			p3.setString(3, data[2]);
			p3.execute();
         		}
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			finally {
				
			}
	}

}
