package com.herokuapp.darkfire.sara.objects;

import com.herokuapp.darkfire.sara.interfaces.Machine;
import com.herokuapp.darkfire.sara.objects.interfaces.ModelObjectInterface.Actions;

public class Grievance extends ModelObject{

	public Grievance(Machine machine) {
		super(machine);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getDescripiton() {
		// TODO Auto-generated method stub
		return "This is grievance object. Uses CBR.";
	}
	
	@Override
	public Actions[] allowdAction(){
		Actions[] allowedActionArray = {Actions.PRE_PROECESS, Actions.BASIC_INFO, Actions.KNOW_MORE,
				Actions.NEW, Actions.GENERIC_REPLY, Actions.FIND};
		return allowedActionArray;
	}

}
