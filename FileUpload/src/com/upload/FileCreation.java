package com.upload;

import java.io.File;
import java.io.FileWriter;

import com.opencsv.CSVWriter;

public class FileCreation {

	public static void main(String[] args) {
		File file = new File("C:\\Documents\\Files\\UserInfoReport.csv");
		FileWriter outputfile=null;
		
		try   
		{  
			outputfile = new FileWriter(file);
	        CSVWriter writer = new CSVWriter(outputfile);
	  
	        String[] header = { "Name", "Class", "Marks" };
	        writer.writeNext(header);
	  
	       
	        String[] data1 = { "Aman", "10", "620" };
	        writer.writeNext(data1);
	        String[] data2 = { "Suraj", "10", "630" };
	        writer.writeNext(data2);
	  
	        writer.close();
	  
			System.out.println("Excel file has been generated successfully.");  
		}   
		catch (Exception e)   
		{  
		e.printStackTrace();  
		}  

	}

}
