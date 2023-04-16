package com.upload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.opencsv.CSVWriter;

public class CsvFileUpload {

	public static void main(String[] args) throws SQLException, IOException {
		 String jdbcURL = "jdbc:mysql://localhost:3306/testing?zeroDateTimeBehavior=convertToNull";
	     String username = "root";
	     String password = "Siri@123";
	     String path="C:\\Documents\\Files\\UserInfo.csv";
	     
	     Connection con=null;
	     ResultSet rs=null;
		 PreparedStatement p1=null;
		 String sql=null;
		 
	     String name =null;
	     String email=null;
	     long phonenumber=0;
	     int id=0;
	     
	     int validationErorr=0;
 	 	 int duplicateError=0;
 	 	 int successCount=0;
 	 	 
 	 	 boolean flag=false;
 	 	 boolean duplicateflag=false;
 	 	
 	 	 BufferedReader lineReader=null;
 	 	 String lineText = null;
 	 	 String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
 	 	 String nameregex = "^[A-Za-z]\\w{2,29}$";
 	 	 
	     try {
	    	 	Class.forName("com.mysql.cj.jdbc.Driver");
	    	 	con = DriverManager.getConnection(jdbcURL, username, password);
	         
	    	 	lineReader = new BufferedReader(new FileReader(path));
	    	 	
	    	 	lineReader.readLine();
	         
	    	 	while ((lineText = lineReader.readLine()) != null) 
	         		{
	        	 
	        	 	String[] data = lineText.split(",");
	        	 	
	        	 	if(data[0]==null || data[0].isEmpty()|| !data[0].matches(nameregex))
	        	 	{
	        	 		validationErorr++;
	        	 	}
	        	 	if(data[1]==null ||data[1].isEmpty() || !data[1].matches(regex))
	        	 	{
	        	 		validationErorr++;
	        	 	}
	        	 	if(data[2]==null ||data[2].isEmpty()|| !(data[2].length()==10))
	        	 	{
	        	 		validationErorr++;
	        	 	}
	        	 	
	        	 	if(validationErorr==0)
	    	 		{
	        	 		
	        	 		sql="select * from FileData";
	    	    	 	p1 =con.prepareStatement(sql);
	    	    	 	rs =p1.executeQuery();
	    	    	 	while (rs.next()) {
	    	    	 		
	    	    	 		  name = rs.getString("username"); 
	    					  email = rs.getString("emailaddress");
	    					  phonenumber=rs.getLong("Phonenumber");
	    					  id=rs.getInt("id");
	    					  
	    	    	 		if(data[0].equals(name)&& data[1].equals(email)&& phonenumber==Long.parseLong(data[2])) 
		        	 		{
		        	 			System.out.println("match"); 
		        	 			duplicateError++;
		        	 			duplicateflag=true;
		        	 			break;
		        	 		} 
		        	 		else
		        	 		{
		        	 		 if(data[0].equals(name) || data[0].equals(email) || phonenumber==Long.parseLong((data[2])))
		       				  {
		        	 			 flag=true;
		        	 			 duplicateflag=false;
		        	 			 break;
		       				  }
		        	 		 else
		        	 		 {
		        	 			flag=false;
		        	 			duplicateflag=false;
		        	 			
		        	 		 }
		        	 		}
	    	    	 	}
	    	    	 	
	    	    	 	 if(flag)
	        	 		 {
	    	    	 		 p1.close();
	    	    	 		 sql="update FileData set username=?,emailaddress=?,Phonenumber=? where id=? ";
					         p1=con.prepareStatement(sql);
					         p1.setString(1,data[0]);
					         p1.setString(2,data[1]);
					         p1.setString(3,data[2]);
					         p1.setInt(4,id);
					         p1.executeUpdate();
					         
	        	 		 }
	        	 		 if(!flag && !duplicateflag)
	        	 		 {
	        	 			p1.close();
	        	 			sql = "INSERT INTO FileData (username, emailaddress, Phonenumber) VALUES (?, ?, ?)";
	        	 			p1= con.prepareStatement(sql);
	        	 			p1.setString(1, data[0]); 
	        	 			p1.setString(2, data[1]);
	        	 			p1.setString(3, data[2]);
	        	 			p1.execute();
	        	 			successCount++;
	        	 		 }
	    	 		}
	        	 	
	         		}
	    	 	
	    	 	System.out.println("DuplicateCount::"+duplicateError +"ValidationErrorCount::"+validationErorr+"SuccessCount::"+successCount);
	    	 	
	    	 	createCSVfile(validationErorr,duplicateError,successCount);
	    	 	
	    	 	System.out.println("csv file has been generated successfully.");  
		} catch (Exception e) {
			e.printStackTrace();
		}
	     finally {
	    	 	p1.close();
	    	 	con.close();
	    	 	rs.close();
	    	 	lineReader.close();
		}
	}

	public static void createCSVfile(int validationcount,int duplicatecount,int successcount) throws IOException
	{
		File file = new File("C:\\Documents\\Files\\UserInfoReport.csv");
 	 	FileWriter outputfile=null;
		try {
			outputfile = new FileWriter(file);
	        CSVWriter writer = new CSVWriter(outputfile);
	        String[] header = { "Sr.No.", "ValidationError Count", "Duplicate Count","Success Count" };
	        writer.writeNext(header);
	        String[] data1 = { "1", validationcount+"", duplicatecount+"",successcount+"" };
	        writer.writeNext(data1);
	        writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			outputfile.close();
		}
	}
}
