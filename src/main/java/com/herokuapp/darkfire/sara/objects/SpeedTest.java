package com.herokuapp.darkfire.sara.objects;

import com.example.arpan.sara.CheckSpeedTest;
import com.herokuapp.darkfire.sara.interfaces.Input;
import com.herokuapp.darkfire.sara.interfaces.Machine;
import com.herokuapp.darkfire.sara.objects.interfaces.ModelObjectInterface;

/**
 * Created by Siddharth on 7/10/2016.
 */
public class SpeedTest extends ModelObject {

    boolean open = true;

    public SpeedTest(Machine machine) {
        super(machine);
    }

    @Override
    public void onInput(Input input) {
        switch (input.getInput()){
            case PRE_PROECESS:
            case BASIC_INFO:
            case KNOW_MORE:
                setOpen(true);
                getMachine().sendInput("I can do speed test for you!");
                break;
            case NEW:
                setOpen(true);
                getMachine().sendInput("Performing speed test");
                new CheckSpeedTest(getMachine()).execute();
                setOpen(false);
                break;
            case CLOSE:
                setOpen(false);
        }
    }

    @Override
    public Actions[] allowdAction() {
        Actions[] allowedActionArray = {Actions.PRE_PROECESS, Actions.BASIC_INFO, Actions.KNOW_MORE,
                Actions.NEW};
        return allowedActionArray;
    }

    @Override
    public String getDescripiton() {
        return "This is a speed test object";
    }

    @Override
    public void setOpen(boolean open) {
        this.open = open;
    }
}
