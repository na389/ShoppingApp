package com.cnmc.shoppingbuddy.db;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ListItemsPriority implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2009816911537555061L;

	private List<String> listItems;
	private List<String> typeItems;
	private String priority;
	private String listItemString;
	private String listTypeString;
	private String listName;	
	private String status;
	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public List<String> getListItems() {
		String[] items = this.listItemString.split(":");
		listItems = Arrays.asList(items);
		return listItems;
	}

	public void setListItems(List<String> listItems) {
		this.listItems = listItems;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public void listItemsString(){
		StringBuilder arrayListItems = new StringBuilder();
		for(String items: listItems){
			arrayListItems.append(items.toString()+":");
		}
		
		listItemString = arrayListItems.toString();
	}
	
	
	public String getListItemString() {
		return listItemString;
	}

	public void setListItemString(String listItemString) {
		this.listItemString = listItemString;
	}

	
	
	public void listTypeString(){
		StringBuilder arrayListItems = new StringBuilder();
		for(String items: typeItems){
			arrayListItems.append(items+"|");
		}
		
		listTypeString = arrayListItems.toString();
	}
	
	
	public List<String> getTypeItems() {
		String[] items = this.listTypeString.split("|");
		
		typeItems = Arrays.asList(items);
		return typeItems;
	}

	public void setTypeItems(List<String> typeItems) {
		this.typeItems = typeItems;
	}
	
	
	public String getTypeItemString() {
		return listTypeString;
	}

	public void setTypeItemString(String listTypeString) {
		this.listTypeString = listTypeString;
	}
	
	
	
}


