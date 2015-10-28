package com.activehours.words;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.junit.runner.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.activehours.Dao.CustomerDAO;
import com.activehours.Dao.WordsDao;
import com.activehours.model.Customer;
import com.activehours.model.Trie;
import com.activehours.model.UserData;
import com.activehours.model.WordBean;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private WordBean words;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {

		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);
		return "home";

	}

	
	@RequestMapping(value = "/suggestWord", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<WordBean> suggestWord( @ModelAttribute UserData user
			) {

		try {
			
			File input = new File(getClass().getResource("/wordsEn.txt").getPath());
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
			String[] splitwords = user.getWord().split(" ");
			List<String> newList = new ArrayList<String>();
			for (String string : splitwords) {
				T.searchNode(string);
				newList.add(T.getSuggestedWord());
			}
			words = new WordBean("/suggestWord/{word}", newList);
			return new ResponseEntity<WordBean>(words,HttpStatus.OK);

		} catch (IOException e) // Error reading in nextToken()
		{
			System.out.println(e); // Output the error
			System.exit(1); // End the program
		}
		
		return null;
	}

	@RequestMapping(value = "/suggestWord/{word}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getWordDetails(@PathVariable("word") String word) {
		
		return new ResponseEntity<String>(words.getWordList().get(words.getWordList().indexOf(word)), HttpStatus.OK);
	}
}
