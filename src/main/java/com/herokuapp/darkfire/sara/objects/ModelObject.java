package com.herokuapp.darkfire.sara.objects;

import com.herokuapp.darkfire.sara.InputObject;
import com.herokuapp.darkfire.sara.interfaces.Input;
import com.herokuapp.darkfire.sara.interfaces.Machine;
import com.herokuapp.darkfire.sara.objects.interfaces.ModelObjectInterface;

public class ModelObject implements ModelObjectInterface{

	private Machine machine;
	private boolean open = true;
	private InputObject inputObject;
	
	public ModelObject(Machine machine) {
		// TODO Auto-generated constructor stub
		this.machine = machine;
	}
	
	@Override
	public void onInput(Input input) {
		// TODO Auto-generated method stub
		switch(input.getInput()){
		case PRE_PROECESS: 
			open = true;
			machine.sendInput(getDescripiton());
			open = false;
		case CLOSE:
			machine.sendInput("Closing: " + getClass().getSimpleName());
			open = false;
		default:
			machine.sendInput(getDescripiton());
			break;
			
		}
	}

	@Override
	public void postProcess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDescripiton() {
		// TODO Auto-generated method stub
		return "This is a generic model object";
	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return open;
	}
	
	public void setOpen(boolean open){
		this.open = open;
	}
	
	public Machine getMachine(){
		return machine;
		
	}
	
	public Actions[] allowdAction(){
		Actions[] allowedActionArray = {Actions.PRE_PROECESS, Actions.CLOSE};
		return allowedActionArray;
	}
	
	public boolean canMapAction(Actions action){
		for(Actions a : allowdAction()){
			if(a == action)
				return true;
		}
		return false;
	}

	public String expectedInput(){
		String strings = null;
		return strings;
	}

	public InputObject getInputObject() {
		return inputObject;
	}

	public void setInputObject(InputObject inputObject) {
		this.inputObject = inputObject;
	}
	
	
	
}
