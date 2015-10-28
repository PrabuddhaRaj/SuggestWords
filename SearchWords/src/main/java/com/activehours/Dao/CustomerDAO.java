package com.activehours.Dao;

import java.util.List;

import com.activehours.model.Customer;

public interface CustomerDAO {
	public void insert(Customer customer);

	public String findWordByValue(String word);

	public List<String> findSuggestions(String word);
}