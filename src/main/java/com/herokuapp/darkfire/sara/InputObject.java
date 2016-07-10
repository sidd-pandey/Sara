package com.herokuapp.darkfire.sara;

import java.util.HashMap;

import com.herokuapp.darkfire.sara.interfaces.Input;
import com.herokuapp.darkfire.sara.objects.interfaces.ModelObjectInterface.Actions;

public class InputObject implements Input {

	private Actions input;
	private HashMap<InputStruct, String> inputStruct = new HashMap<>();
	
	public InputObject(Actions input) {
		// TODO Auto-generated constructor stub
		this.input = input;
	}
	
	@Override
	public Actions getInput() {
		// TODO Auto-generated method stub
		return input;
	}
	
	public void setHeader(String header){
		inputStruct.put(InputStruct.HEADER, header);
	}
	
	public void setBody(String body){
		inputStruct.put(InputStruct.BODY, body);
	}
	
	public void setFooter(String footer){
		inputStruct.put(InputStruct.FOOTER, footer);
	}
	
	public enum InputStruct{
		HEADER,
		FOOTER,
		BODY;
	}

	public void setInput(Actions action){
		this.input = action;
	}

	public HashMap<InputStruct, String> getBody() {
		return inputStruct;
	}

	public void setBody(HashMap<InputStruct, String> body) {
		this.inputStruct = body;
	}
}
