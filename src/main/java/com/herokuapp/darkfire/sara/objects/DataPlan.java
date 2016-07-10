/**
 * 
 */
package com.herokuapp.darkfire.sara.objects;

import com.herokuapp.darkfire.sara.interfaces.Machine;
import com.herokuapp.darkfire.sara.objects.interfaces.ModelObjectInterface.Actions;

/**
 * @author Siddharth
 *
 */
public class DataPlan extends ModelObject {

	public DataPlan(Machine machine) {
		super(machine);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getDescripiton() {
		// TODO Auto-generated method stub
		return "Contains information of new data plans and can activate";
	}
	
	@Override
	public Actions[] allowdAction(){
		Actions[] allowedActionArray = {Actions.PRE_PROECESS, Actions.BASIC_INFO, Actions.KNOW_MORE,
				Actions.ACTIVATE,Actions.DEACTIVATE};
		return allowedActionArray;
	}

}
