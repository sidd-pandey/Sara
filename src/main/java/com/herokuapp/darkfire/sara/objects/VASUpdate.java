package com.herokuapp.darkfire.sara.objects;

import com.herokuapp.darkfire.sara.interfaces.Machine;
import com.herokuapp.darkfire.sara.objects.interfaces.ModelObjectInterface.Actions;

public class VASUpdate extends ModelObject {

	public VASUpdate(Machine machine) {
		super(machine);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getDescripiton() {
		// TODO Auto-generated method stub
		return "This is VAS update news.";
	}
	
	@Override
	public Actions[] allowdAction(){
		Actions[] allowedActionArray = {Actions.PRE_PROECESS, Actions.BASIC_INFO, Actions.KNOW_MORE};
		return allowedActionArray;
	}

}
