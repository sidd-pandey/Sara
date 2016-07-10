package com.herokuapp.darkfire.sara;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.herokuapp.darkfire.sara.interfaces.Input;
import com.herokuapp.darkfire.sara.objects.Apology;
import com.herokuapp.darkfire.sara.objects.Complaint;
import com.herokuapp.darkfire.sara.objects.ModelObject;
import com.herokuapp.darkfire.sara.objects.factories.ModelObjectFactory;
import com.herokuapp.darkfire.sara.objects.factories.ModelObjectFactory.ObjectList;
import com.herokuapp.darkfire.sara.objects.interfaces.ModelObjectInterface;
import com.herokuapp.darkfire.sara.objects.interfaces.ModelObjectInterface.Actions;
import com.herokuapp.darkfire.sara.utilities.TextSearch;

public class TextEngine2 {
	
	private static TextEngine2 INSTANCE;
	
	private TextEngine2() {
		// TODO Auto-generated constructor stub
	
	}

	public ActionObject analyze(String text, ActionObject currentAction){

		if(currentAction.getObejct() instanceof Complaint && currentAction.getObejct().isOpen()){
            System.out.println("Complaint object found");
            return processComplaint(text, currentAction);
        }

		//search for objects, subjects, actions
		List<String> objectsList = TextSearch.search(getObjectsDictionary(), text);
		System.out.println("Objects found: " + objectsList.size());
		List<String> subjectsList = TextSearch.search(getSubjectsDictionary(), text);
		System.out.println("Subject found: " + subjectsList.size());
		List<String> actionsList = TextSearch.search(getActionsDictionary(), text);
		System.out.println("Actions found: " + actionsList.size());
		
		//map the actions
		HashMap<String,Actions> mapToActions = mapToActions(actionsList);
		
		if(objectsList.size() == 0 && subjectsList.size() == 0){
			System.out.println("No object or subject found, falling to current.");
			for (String key : mapToActions.keySet()) {
				System.out.println("Current action search key:" + key);
				if(currentAction.getObejct().canMapAction(mapToActions.get(key))){
					currentAction.setInput(new InputObject(mapToActions.get(key)));
					System.out.println("Doing: " + key + " on " + currentAction.getObejct().getDescripiton());
                    InputObject temp = new InputObject(Actions.NO_ACTION);
                    temp.setBody(text);
                    currentAction.getObejct().setInputObject(temp);
					return currentAction;
				}
			}
		}
		
		InputObject cInputObject = new InputObject(Actions.NO_ACTION);

		//get action and make a input object.
		for(String key : mapToActions.keySet()){
			cInputObject = new InputObject(mapToActions.get(key));
			System.out.println("Doing " + key);
		}
		
		//more priority to subject
		HashMap<ObjectList, String> mapOfSubjects = null;
		if(subjectsList.size() > 0){
			System.out.println("Extracting from subject list");
			mapOfSubjects = mapIntoSubjects(subjectsList);

			for (ObjectList objectList : mapOfSubjects.keySet()) {
				
				ModelObject modelObject = ModelObjectFactory.createModelObject(objectList, currentAction.getObejct().getMachine());
				cInputObject.setHeader(mapOfSubjects.get(objectList));
				System.out.println(modelObject.getDescripiton());
                cInputObject.setBody(text);
                modelObject.setInputObject(cInputObject);
				return new ActionObject(modelObject, cInputObject);
			}
			
		}
		
		
		HashMap<ObjectList, String> mapOfObjects = null;
		if(objectsList.size() > 0){
			
			mapOfObjects = mapIntoObjects(objectsList);
			
			for (ObjectList objectList : mapOfObjects.keySet()) {
				
				ModelObject modelObject = ModelObjectFactory.createModelObject(objectList, currentAction.getObejct().getMachine());
				cInputObject.setHeader(mapOfObjects.get(modelObject));	
				System.out.println(modelObject.getDescripiton());
                cInputObject.setBody(text);
                modelObject.setInputObject(cInputObject);
				return new ActionObject(modelObject, cInputObject);
			}
		}
        cInputObject.setBody(text);
		return new ActionObject(new Apology(currentAction.getObejct().getMachine()), new InputObject(Actions.GENERIC_REPLY));
	}

    private ActionObject processComplaint(String text, ActionObject complaintAction) {
        System.out.println("Searching for words in reply");
        if(TextSearch.search(complaintAction.getObejct().expectedInput(), text)){
            System.out.println("Making a generic reply:" + text);
            InputObject object = new InputObject(Actions.GENERIC_REPLY);
            object.setBody(text);
            complaintAction.setInput(object);
            complaintAction.getObejct().setInputObject(object);
        }else{
            complaintAction.setInput(new InputObject(Actions.NO_ACTION));
        }
        System.out.println("Sending back the complaint action object");
        return complaintAction;
    }

    public static TextEngine2 getInstance(){
		if(INSTANCE == null){
			INSTANCE = new TextEngine2();
		}
		return INSTANCE;
	}
	
	public boolean checkConfirm(String text){
		return text.toLowerCase().contains("y");
	}
	
	private HashMap<String, ModelObjectInterface.Actions>mapToActions(List<String> actions){
		HashMap<String,  ModelObjectInterface.Actions> actionMap = new HashMap<>();
		for(String a : actions){
			switch(a){
				case "register":
					actionMap.put(a, Actions.NEW);
					break;
				case "track":
					actionMap.put(a, Actions.TRACK);
					break;
                case "deactivate":
                case "stop":
                case "remove":
					actionMap.put(a, Actions.DEACTIVATE);
					break;
				case "activate":
					actionMap.put(a, Actions.ACTIVATE);
					break;
				case "location":
					actionMap.put(a, Actions.LOCATION);
					break;
                case "detail":
				case "tell":
				case "more":
                case "about":
					actionMap.put(a, Actions.KNOW_MORE);
					break;
				case "perform":
					actionMap.put(a, Actions.NEW);
					break;
				case "update":
					actionMap.put(a, Actions.UPDATE);
					break;
				case "news":
                case "hi":
                case "hello":
                case "yo":
					actionMap.put(a, Actions.BASIC_INFO);
					break;
				case "close":
                case "exit":
					actionMap.put(a, Actions.CLOSE);
					break;
			}
		}
		return actionMap;
	}

	private HashMap<ObjectList, String> mapIntoObjects(List<String> strObjects){
		HashMap<ObjectList, String> mapToObjects = new HashMap<>();
		for(String object : strObjects){
			switch(object){
				case "complaint":
					mapToObjects.put(ObjectList.COMPLAINT, object);
					break;
				case "vas":
					mapToObjects.put(ObjectList.VAS, object);
					break;
				case "bill":
					mapToObjects.put(ObjectList.BILL, object);
					break;
				case "call center":
					mapToObjects.put(ObjectList.CUSTOMER_CARE_NUMBER, object);
					break;
				case "telephone":
					mapToObjects.put(ObjectList.CUSTOMER_CARE_NUMBER, object);
					break;
				case "offices":
					mapToObjects.put(ObjectList.CUSTOMER_CARE_NUMBER, object);
					break;
                case "speedtest":
                    mapToObjects.put(ObjectList.SPEED_TEST, object);
                    break;
			}
		}
		return mapToObjects;
	}

	private HashMap<ObjectList, String> mapIntoSubjects(List<String> strObjects){
		HashMap<ObjectList, String> mapToObjects = new HashMap<>();
		for(String object : strObjects){
			switch(object){
				case "telecom":
				case "trai":
                case "news":
					mapToObjects.put(ObjectList.TRAI_NEWS, object);
					break;
				case "airtel":
				case "bsnl":
				case "vodafone":
					mapToObjects.put(ObjectList.TSP_NEWS, object);
					break;
			}
		}
		return mapToObjects;
	}
	
	private List<String>getObjectsDictionary(){
		String[] list = {"complaint","vas","bill","survey","call center","telephone",
				"offices", "speedtest"};
		
		List<String> strList = new ArrayList<>();
		for(String s : list) strList.add(s);
		return strList;
	}
	
	private List<String>getActionsDictionary(){
		String[] list = {"hi","hello","yo","register", "track",
                "detail","deactivate","activate","location",
				"tell","more","perform","update","news","close","about"
                ,"remove","stop","exit"};

		List<String> strList = new ArrayList<>();
		for(String s : list) strList.add(s);
		return strList;
	}
	

	private List<String>getSubjectsDictionary(){
		String[] list = {"bsnl","airtel","trai","hackathon",
				"vodafone","telecom","news"};
		
		List<String> strList = new ArrayList<>();
		for(String s : list) strList.add(s);
		return strList;
	}
}
