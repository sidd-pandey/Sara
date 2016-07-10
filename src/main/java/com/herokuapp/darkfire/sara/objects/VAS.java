package com.herokuapp.darkfire.sara.objects;

import com.herokuapp.darkfire.sara.ComplaintDB;
import com.herokuapp.darkfire.sara.InputObject;
import com.herokuapp.darkfire.sara.interfaces.Input;
import com.herokuapp.darkfire.sara.interfaces.Machine;
import com.herokuapp.darkfire.sara.objects.interfaces.ModelObjectInterface.Actions;

public class VAS extends ModelObject {

    boolean open = true;
    String expected = null;
    String vasToStop = null;

	public VAS(Machine machine) {
		super(machine);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getDescripiton() {
		// TODO Auto-generated method stub
		return "Contains VAS details, can be de//activated.";
	}

    @Override
    public void onInput(Input input) {
        switch (input.getInput()){
            case BASIC_INFO:
            case KNOW_MORE:
                setOpen(true);
                System.out.println("VAS reached 1");
                getMachine().sendInput("VAS activated inside 24 hrs are: ");
                StringBuilder builder = new StringBuilder();
                int count = 0;
                for (String key : ComplaintDB.getInstance().getVasDb().keySet()) {
                    count++;
                    builder.append(count + ". " + ComplaintDB.getInstance().getVasDb().get(key).name + "\n");
                }
                getMachine().sendInput(builder.toString());
                getMachine().sendInput("You can get a refund for these services.");
                builder = null;
                break;
            case DEACTIVATE:
                setOpen(true);
                String text = getInputObject().getBody().get(InputObject.InputStruct.BODY);
                System.out.println(text);
                for (String key : ComplaintDB.getInstance().getVasDb().keySet()) {
                    String serviceName = ComplaintDB.getInstance().getVasDb().get(key).name;
                    if(serviceName.toLowerCase().contains(text.toLowerCase()) ||
                            text.toLowerCase().contains(serviceName.toLowerCase())){
                        getMachine().sendInput(serviceName + "will be deactivated. You will receive a update soon.");
                        ComplaintDB.getInstance().getVasDb().get(key).active = false;
                        getMachine().sendInput("Done! What's next ?");
                        vasToStop = key;
                        break;
                    }
                }
                break;
            case CLOSE:
                getMachine().sendInput("Find about VAS anytime.");
                setOpen(false);

        }
    }

    @Override
	public Actions[] allowdAction(){
		Actions[] allowedActionArray = {Actions.PRE_PROECESS, Actions.BASIC_INFO, Actions.KNOW_MORE
                , Actions.DEACTIVATE, Actions.CLOSE};
		return allowedActionArray;
	}

    @Override
    public boolean isOpen() {
        return super.isOpen();
    }

    @Override
    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public String expectedInput() {
        expected = "yes yup yeah haan";
        return expected;
    }
}
