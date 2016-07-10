package com.herokuapp.darkfire.sara;

import com.herokuapp.darkfire.sara.interfaces.Input;
import com.herokuapp.darkfire.sara.objects.ModelObject;

public abstract class Action {

	private ModelObject obejct;
	private Input input;
	
	Action(ModelObject object, Input input){
		this.obejct = object;
		this.input = input;
	}
	
	
	public ModelObject getObejct() {
		return obejct;
	}


	public void setObejct(ModelObject obejct) {
		this.obejct = obejct;
	}


	public Input getInput() {
		return input;
	}


	public void setInput(Input input) {
		this.input = input;
	}


	public abstract void performAction();
}
