package com.activehours.model;

import java.util.HashMap;
import java.util.Map;

public class Trie {
	private TrieNode root;
	private String suggestedWord;

	public Trie() {
		root = new TrieNode();
	}

	// Inserts a word into the trie.
	public void insert(String word) {
		HashMap<Character, TrieNode> children = root.children;

		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);

			TrieNode t;
			if (children.containsKey(c)) {
				t = children.get(c);
			} else {
				t = new TrieNode(c);
				children.put(c, t);
			}

			children = t.children;

			// set leaf node
			if (i == word.length() - 1)
				t.isLeaf = true;
		}
	}

	// Returns if the word is in the trie.
	/*
	 * public String search(String word) { TrieNode t = searchNode(word);
	 * 
	 * 
	 * if (t.isLeaf) { return "No typos in word"; } else { return
	 * "did you mean:"+word; }
	 * 
	 * 
	 * else return word;
	 * 
	 * }
	 */

	// Returns if there is any word in the trie
	// that starts with the given prefix.
	// public boolean startsWith(String prefix) {
	// if (searchNode(prefix) == null)
	// return false;
	// else
	// return true;
	// }

	public void searchNode(String str) {
		Map<Character, TrieNode> children = root.children;
		TrieNode t = null;
		String word = "";
		int i = 0;
		char oldc = str.charAt(i);
		for (Character c : children.keySet()) {
			if (children.containsKey(oldc)) {
				t = children.get(oldc);
				children = t.children;
				word = word + oldc;
				getSuggesTwordList(++i, t, word, oldc, str);
			} else {
				getSuggesTwordList(++i, children.get(children.entrySet().iterator().next().getKey()), word,
						children.entrySet().iterator().next().getKey(), str);
				break;
			}
			break;
			
			}
		
			if (this.suggestedWord == null) {
				this.setSuggestedWord("word not present in dictionary");
				return;
			}
		
		return;

	}

	// private void findSuggestions(String str, Map<Character, TrieNode>
	// children, TrieNode t, String word, int i) {
	// if (t != null) {
	// if (t.isLeaf) {
	// this.suggestedWord = word;
	// return;
	// } else {
	// getSuggesTwordList(i,
	// children.get(children.entrySet().iterator().next().getKey()), word,
	// children.entrySet().iterator().next().getKey(), str);
	// }
	// }
	// }

	private void getSuggesTwordList(int i, TrieNode t, String word, char newc, String str) {

		HashMap<Character, TrieNode> children = t.children;
		if (i < str.length()) {
			char oldc = str.charAt(i);

			if (children.containsKey(oldc)) {
				t = t.children.get(oldc);
				children = t.children;
				word = word + oldc;
				getSuggesTwordList(++i, t, word, oldc, str);
			} else {
				for (char sugc : children.keySet()) {
					if (children.containsKey(sugc)) {
						t = t.children.get(sugc);
						children = t.children;
						word = word + sugc;
						getSuggesTwordList(++i, t, word, oldc, str);
					} else
						continue;
				}

			}
		}
		if (this.suggestedWord == null && !t.isLeaf) {
			suggestedWord = "Word not present in dictionary";
			return;
		} else {
			if (word.length() == str.length() && t.isLeaf) {
				setSuggestedWord(word);
				return;
			}
		}

	}

	public String getSuggestedWord() {
		return suggestedWord;
	}

	public void setSuggestedWord(String suggestedWord) {
		this.suggestedWord = suggestedWord;
	}
}