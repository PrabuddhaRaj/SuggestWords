package com.activehours.model;

import java.util.ArrayList;
import java.util.List;

public class WordBean {

	private String endpoint;
	private List<String> wordList = new ArrayList<String>();
public WordBean(){
	
}
	public WordBean(String endpoint,List<String> words){
		this.endpoint = endpoint;
		this.wordList = words;
	}
	public List<String> getWordList() {
		return wordList;
	}
	public void setWordList(List<String> wordList) {
		this.wordList = wordList;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}


}
