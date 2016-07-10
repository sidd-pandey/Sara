package com.herokuapp.darkfire.sara.objects;

import com.herokuapp.darkfire.sara.interfaces.Machine;
import com.herokuapp.darkfire.sara.objects.interfaces.ModelObjectInterface.Actions;

public class Bill extends ModelObject{

	public Bill(Machine machine) {
		super(machine);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getDescripiton() {
		// TODO Auto-generated method stub
		return "This is a bill object.";
	}

	@Override
	public Actions[] allowdAction(){
		Actions[] allowedActionArray = {Actions.PRE_PROECESS, Actions.BASIC_INFO, Actions.KNOW_MORE};
		return allowedActionArray;
	}
}
