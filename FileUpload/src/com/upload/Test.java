package com.upload;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
			
		 String jdbcURL = "jdbc:mysql://localhost:3306/testing?zeroDateTimeBehavior=convertToNull";
	     String username = "root";
	     String password = "Siri@123";
	     Connection con=null;
	     String path="C:\\Documents\\Files\\UserInfo.csv";
	     List<String> dbData = new ArrayList<>();
	     List<String> csvData = new ArrayList<>();
	     
	     try {
	    	 	Class.forName("com.mysql.jdbc.Driver");
	    	 	con = DriverManager.getConnection(jdbcURL, username, password);
	    	 	ResultSet rs=null;
	    	 	PreparedStatement p1=null;
	    	 	
	    	 	String sql1="select username,emailaddress,Phonenumber from FileData";
	    	 	p1 =con.prepareStatement(sql1);
	    	 	rs =p1.executeQuery();
	    	 	while (rs.next()) {
	    	 	
	    	 		String row = String.join("\t", rs.getString(1), rs.getString(2), rs.getString(3));
	    	 		dbData.add(row);

	    	 	}
	    	 	BufferedReader lineReader = new BufferedReader(new FileReader(path));
	    	 	String lineText = null;
	    	 	lineReader.readLine();
	    	 	while ((lineText = lineReader.readLine()) != null) 
         		{
        	 
        	 	String[] data = lineText.split(",");
        	 	String row = String.join("\t", data[0], data[1], data[2]);
        	 	csvData.add(row);
         		}
	    	 	
	    	 	for(int i=0;i<csvData.size();i++)
	    	 	{
	    	 	for(int j=0;j<dbData.size();j++)
	    	 	{
	    	 	System.out.println(csvData.get(i)+"-->"+dbData.get(j));
	    	 	System.out.println(csvData.get(i).equals(dbData.get(j)));
	    	 	
	    	 	}
	    	 	}
	    	 	
	    	 	
	    	 	
	    	 	
	     	} 
	     catch (Exception e) {
			e.printStackTrace();
		}
	}

}
