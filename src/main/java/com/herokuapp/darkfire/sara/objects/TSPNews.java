package com.herokuapp.darkfire.sara.objects;

import com.herokuapp.darkfire.sara.interfaces.Input;
import com.herokuapp.darkfire.sara.interfaces.Machine;
import com.herokuapp.darkfire.sara.objects.interfaces.ModelObjectInterface.Actions;

public class TSPNews extends ModelObject{

	private String shortDescription = "short sample discussion.";
	private String longDescription = "long sample discussion";
	private String tspName;

	public TSPNews(Machine machine) {
		super(machine);
		// TODO Auto-generated constructor stub
		

	}

	@Override
	public void onInput(Input input) {
		// TODO Auto-generated method stub
		switch(input.getInput()){
			case PRE_PROECESS:
				setOpen(true);
			case BASIC_INFO:
				setOpen(true);
				getMachine().sendInput(shortDescription);
				break;
			case KNOW_MORE: 
				setOpen(true);
				getMachine().sendInput(longDescription);
				break;
			case CLOSE:
				setOpen(false);
			default:
				break;
		}
	}
	
	@Override
	public Actions[] allowdAction(){
		Actions[] allowedActionArray = {Actions.PRE_PROECESS, Actions.BASIC_INFO, Actions.KNOW_MORE};
		return allowedActionArray;
	}
	
	@Override
	public String getDescripiton() {
		// TODO Auto-generated method stub
		return "This is TSP news";
	}
}
