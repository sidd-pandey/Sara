package com.herokuapp.darkfire.sara.objects;

import com.herokuapp.darkfire.sara.interfaces.Input;
import com.herokuapp.darkfire.sara.interfaces.Machine;
import com.herokuapp.darkfire.sara.objects.interfaces.ModelObjectInterface.Actions;

public class Introduction extends ModelObject{

	
	public Introduction(Machine machine) {
		// TODO Auto-generated constructor stub
		super(machine);
	}

	@Override
	public void onInput(Input input) {
		// TODO Auto-generated method stub
		switch (input.getInput()) {
			case PRE_PROECESS:
			setOpen(true);
			getMachine().sendInput("Hi! this is SARA.");
			break;
			case BASIC_INFO:
				setOpen(true);
				getMachine().sendInput("Nice meeting you");
				break;
			case KNOW_MORE:
				setOpen(true);
				getMachine().sendInput("I can help you with lot of stuff, and also keep you updated.");
				getMachine().sendInput("You can track/register/search complaints, perform speed test" +
						", tell about VAS, Bills and much more.");
				break;
			case CLOSE:
			setOpen(false);
			default:
			break;
		}
	}

	@Override
	public void postProcess() {
		// TODO Auto-generated method stub
		//nothing to process
	}

	@Override
	public String expectedInput() {
        //return "hi hello nice meeting yo";
        return null;
	}

	@Override
	public String getDescripiton() {
		// TODO Auto-generated method stub
		return "Introduces SARA to you.";
	}
	
	@Override
	public Actions[] allowdAction(){
		Actions[] allowedActionArray = {Actions.PRE_PROECESS, Actions.BASIC_INFO, Actions.KNOW_MORE};
		return allowedActionArray;
	}


}
