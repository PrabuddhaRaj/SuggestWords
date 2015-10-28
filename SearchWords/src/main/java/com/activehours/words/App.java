package com.activehours.words;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.activehours.Dao.CustomerDAO;
import com.activehours.model.Trie;

public class App {

	public static void main(String args[]) {

		// Wiktionary wiktionary = new Wiktionary(DB_PATH, Language.GERMAN);
		// wiktionary.addWiktionary(DB_PATH, Language.English);
		// wiktionary.setWordLanguage(Language.German);
		// List<WiktionaryWord> wordList = wiktionary.getWords("bank");
		try {
			String fileName = "C:\\Users\\Prabuddha\\Downloads\\wordsEn.txt"; // file
																											// line
			File input = new File(fileName);
			StreamTokenizer tokenizer = new StreamTokenizer(new BufferedReader(new FileReader(input)));
			tokenizer.lowerCaseMode(true); // make it all lower case

			Trie T = new Trie(); // create top level trie

			while (tokenizer.nextToken() != tokenizer.TT_EOF) {
				if (tokenizer.ttype == tokenizer.TT_WORD) {
					String word = tokenizer.sval; // get the next word
					T.insert(word);
					;// insert it starting at letter position 0
				}

			}
			//T.insert("robust");
			T.searchNode("procastinate");
			System.out.println(T.getSuggestedWord());

		} catch (IOException e) // Error reading in nextToken()
		{
			System.out.println(e); // Output the error
			System.exit(1); // End the program
		}
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("Spring-Module.xml");
		//
		// CustomerDAO customerDAO = (CustomerDAO)
		// context.getBean("CustomerDAO");
		// List<String> list =
		// customerDAO.findSuggestions(customerDAO.findWordByValue("robhusd"));
		//

		// System.out.println(list.get(0));
	}

}
