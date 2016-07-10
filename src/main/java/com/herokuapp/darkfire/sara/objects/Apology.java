package com.herokuapp.darkfire.sara.objects;

import com.herokuapp.darkfire.sara.interfaces.Input;
import com.herokuapp.darkfire.sara.interfaces.Machine;

public class Apology extends ModelObject{

	public Apology(Machine machine) {
		super(machine);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onInput(Input input) {
		// TODO Auto-generated method stub
		switch (input.getInput()) {
		case PRE_PROECESS:
		case GENERIC_REPLY:
			setOpen(true);
			getMachine().sendInput("Sorry could not understand you!");
			setOpen(false);
			break;
		case CLOSE:
			setOpen(false);
		default:
			break;
		}
	}
	@Override
	public String getDescripiton() {
		// TODO Auto-generated method stub
		return "Apology object.";
	}

}
