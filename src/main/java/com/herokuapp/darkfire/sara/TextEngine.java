package com.herokuapp.darkfire.sara;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.herokuapp.darkfire.sara.interfaces.Input;
import com.herokuapp.darkfire.sara.objects.ModelObject;
import com.herokuapp.darkfire.sara.utilities.TextSearch;

public class TextEngine {
	
	private static TextEngine INSTANCE;
	
	private TextEngine() {
		// TODO Auto-generated constructor stub
	
	}

	public HashMap<ModelObject, Input> analyze(String text, List<ModelObject> objList){
		
		//search for objects, subjects, actions
		List<String> objectsList = TextSearch.search(getObjectsDictionary(), text);
		List<String> subjectsList = TextSearch.search(getSubjectsDictionary(), text);
		List<String> actionsList = TextSearch.search(getActionsDictionary(), text);
		
		//create object-action pair
		HashMap<String, List<String>> objectActionMap = new HashMap<>();
		for(String object : objectsList)
			objectActionMap.put(object, actionsList);
		
		//create subject-action pair
		HashMap<String, List<String>> subjectActionMap = new HashMap<>();
		for(String subject : subjectsList)
			subjectActionMap.put(subject, actionsList);
		
		//null the not needed list
		objectsList = subjectsList = actionsList = null;

		for (String string : objectActionMap.keySet()) {
			List<String> list = objectActionMap.get(string);
			for(String action : list){
				System.out.println("Perform " + action + " on " + string);
			}
		}
		
		for (String string : subjectActionMap.keySet()) {
			List<String> list = subjectActionMap.get(string);
			for(String action : list){
				System.out.println("Perform " + action + " on " + string);
			}
		}
		
		
		//map pairs to ModelObjects and Inputs, use factories to create ModelObjects
		//check for objList and close or add action to them
		//add to hash map and return
		return null;
	}
	
	public static TextEngine getInstance(){
		if(INSTANCE == null){
			INSTANCE = new TextEngine();
		}
		return INSTANCE;
	}
	
	private List<String>getObjectsDictionary(){
		String[] list = {"complaint","vas","bill","survey","call center","telephone",
				"offices", "speedtest", "speed test"};
		
		List<String> strList = new ArrayList<>();
		for(String s : list) strList.add(s);
		return strList;
	}
	
	private List<String>getActionsDictionary(){
		String[] list = {"register", "track","detail","deactivate","activate","location","details",
				"tell","more","perform","update","news"};

		List<String> strList = new ArrayList<>();
		for(String s : list) strList.add(s);
		return strList;
	}
	

	private List<String>getSubjectsDictionary(){
		String[] list = {"bsnl","airtel","trai","hackathon",
				"vodafone","telecom"};
		
		List<String> strList = new ArrayList<>();
		for(String s : list) strList.add(s);
		return strList;
	}
}
