package com.services;

import java.util.List;

public class Folder {
	private int id;
	  private String name;
	  private List<String> fileList;
	  
	  public List<String> getFileList() {
		return fileList;
	}

	public void setFileList(List<String> fileList) {
		this.fileList = fileList;
	}

	public Folder(){
	    
	  }
	  
	  public Folder(int id, String name,List<String> fileList){
	    this.id = id;
	    this.name = name;
	    this.fileList=fileList;
	  }
	 
	  public int getId() {
	    return id;
	  }
	 
	  public void setId(int id) {
	    this.id = id;
	  }
	 
	  public String getName() {
	    return name;
	  }
	 
	  public void setName(String name) {
	    this.name = name;
	  }
	 
	  @Override
	  public String toString() {
	    String info = String.format("Customer Info: id = %d, name = %s, fileList = %s", id, name, fileList);
	    return info;
	  }
}
