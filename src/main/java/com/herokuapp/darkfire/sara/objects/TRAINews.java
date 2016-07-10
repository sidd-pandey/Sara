package com.herokuapp.darkfire.sara.objects;

import com.herokuapp.darkfire.sara.interfaces.Input;
import com.herokuapp.darkfire.sara.interfaces.Machine;
import com.herokuapp.darkfire.sara.objects.interfaces.ModelObjectInterface.Actions;
import com.herokuapp.darkfire.sara.tasks.LoadNewsTask;

public class TRAINews extends ModelObject{

	public TRAINews(Machine machine) {
		super(machine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onInput(Input input) {
		switch (input.getInput()){
			case BASIC_INFO:
			case PRE_PROECESS:
			case KNOW_MORE:
				setOpen(true);
				new LoadNewsTask(getMachine()).execute();
				setOpen(false);
			case CLOSE:
				setOpen(false);
		}
	}

	@Override
	public String getDescripiton() {
		// TODO Auto-generated method stub
		return "This is TRAI News";
	}
	
	@Override
	public Actions[] allowdAction(){
		Actions[] allowedActionArray = {Actions.PRE_PROECESS, Actions.BASIC_INFO, Actions.KNOW_MORE, Actions.CLOSE};
		return allowedActionArray;
	}
}
