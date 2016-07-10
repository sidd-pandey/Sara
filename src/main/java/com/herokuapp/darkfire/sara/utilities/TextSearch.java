package com.herokuapp.darkfire.sara.utilities;

import java.util.ArrayList;
import java.util.List;

public class TextSearch {

	public static List<String> search(List<String> dictionary, String text){
		List<String> foundWords = new ArrayList<String>();
		text = text.toLowerCase();
		for(String word : dictionary){
			if(text.contains(word.toLowerCase())){
				//word found
				foundWords.add(word);
			}
		}
		return foundWords;
	}
	public static boolean search(String source, String textToFind){
        if(source == null) return true;
        return source.toLowerCase().contains(textToFind.toLowerCase());
    }
}
