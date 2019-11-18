package com.services;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController 
@RequestMapping("/rest")
public class ModelController {	
	public static String FILE_PATH = "";
	
	private static void loadConfiguration(){
		InputStream input = ModelController.class.getClassLoader().getResourceAsStream("config.properties");
		Properties properties = new Properties();		
		try{
			properties.load(input);			
			input.close();
			FILE_PATH = properties.getProperty("FILE_PATH");			
		} catch(Exception ex) {ex.printStackTrace(); 
	   }				
	}
 
		 @GetMapping("/folders")
		  public ResponseEntity<List<String>> getFolders() throws IOException {
			 loadConfiguration();
			   	File directory = new File(FILE_PATH);     		       
			   	File[] fList = directory.listFiles();
			   	List<String> list=new ArrayList<String>();
			    for (File file : fList){
			        if (file.isDirectory()){
			            list.add(file.getName());
			        }
			    }
			    return new ResponseEntity<List<String>>(list, HttpStatus.OK);
		   }	
	  
		 @GetMapping("/folders/{folderName:.+}")
		  public ResponseEntity<Folder> getFolder(@PathVariable String folderName) throws IOException {
			 loadConfiguration();
			 File folderDirectory = new File(FILE_PATH + File.separatorChar + folderName);  
			 int fileCount = folderDirectory.listFiles().length;
			 File[] files = folderDirectory.listFiles();
			 List<String> list=new ArrayList<String>();
			 for (int i = 0; i < files.length; i++) {
				  if (files[i].isFile()) {
				    list.add(files[i].getName());
				  } 
			 }
			 //TODO: return unique folder id
			 Folder folder=new Folder(1,folderName,list);
			 return new ResponseEntity<Folder>(folder, HttpStatus.OK);
		   }

		 @GetMapping(value="/folders/{folderName:.+}/files")
		  public ResponseEntity<List<String>> getFiles(@PathVariable String folderName) throws IOException {	    	
			 loadConfiguration();
			 File file = new File(FILE_PATH + File.separatorChar + folderName );  
		      String[] files=null;
		      if(file.isDirectory()){
		    	  files = file.list();
			   }
		      return new ResponseEntity<List<String>>(Arrays.asList(files), HttpStatus.OK);
		   }
  
		 @GetMapping("/folders/{folderName:.+}/files/{fileName:.+}")
		  public void getFile(HttpServletResponse response, @PathVariable String folderName, @PathVariable String fileName) throws IOException {
			 loadConfiguration();
			 File file = new File(FILE_PATH+ File.separatorChar +folderName+ File.separatorChar + fileName);

	       response.setContentType("application/octet-stream");
	       response.setHeader("Content-Disposition", "attachment; filename=\""+fileName);
	       InputStream inputStream = new FileInputStream(file);
	           int nRead;
	           while ((nRead = inputStream.read()) != -1) {
	               response.getWriter().write(nRead);
	           }
		   }    
}
