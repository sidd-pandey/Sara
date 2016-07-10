package com.herokuapp.darkfire.sara.objects;

import android.content.res.AssetFileDescriptor;
import android.os.AsyncTask;

import com.example.arpan.sara.CBR;
import com.example.arpan.sara.TweetConfig;
import com.herokuapp.darkfire.sara.Action;
import com.herokuapp.darkfire.sara.ActionObject;
import com.herokuapp.darkfire.sara.ComplaintDB;
import com.herokuapp.darkfire.sara.InputObject;
import com.herokuapp.darkfire.sara.interfaces.Input;
import com.herokuapp.darkfire.sara.interfaces.Machine;
import com.herokuapp.darkfire.sara.query.ComplaintQueryObj;

import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Complaint extends ModelObject{

    String expected;
    Procedure procedure = new Procedure();
    boolean isClosing = false;
    private boolean open = true;

    public Complaint(Machine machine) {
		super(machine);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getDescripiton() {
		// TODO Auto-generated method stub
		return "This is the complaint object.";
	}

    @Override
    public void onInput(Input input) {
        switch (input.getInput()) {
            case PRE_PROECESS:
                setOpen(true);
                getMachine().sendInput(getDescripiton());
            case BASIC_INFO:
                setOpen(true);
                getMachine().sendInput("I can help you with your problems.");
                break;
            case KNOW_MORE:
                break;
            case NEW:
                setOpen(true);
                procedure = new Procedure();
                getMachine().sendInput("Did you know that I tweet about registered complaints to get help from world!");
                getMachine().sendInput(procedure.sendIssueList());
                break;
            case GENERIC_REPLY:
                if(isClosing){
                    //process for yes or no
                    if("yes yup yeah haan".contains(getInputObject().getBody().get(InputObject.InputStruct.BODY))){
                        setInputObject(new InputObject(Actions.CLOSE));
                        new ActionObject(this, getInputObject()).performAction();
                    }
                    else {
                        isClosing = false;
                        getMachine().sendInput("Cool! Lets do it again");
                        setInputObject(new InputObject(Actions.NEW));
                        new ActionObject(this, getInputObject()).performAction();
                    }
                    break;
                }
                System.out.println("Generic reply found!");
                procedure.processStep();
                break;
            case TRACK:
                setOpen(true);
                getMachine().sendInput("Total number of complaints registered under " +
                        "you is " + ComplaintDB.getInstance().getNumber());
                Set<String> stringSet = ComplaintDB.getInstance().getDb().keySet();
                String text = getInputObject().getBody().get(InputObject.InputStruct.BODY);
                boolean containsNumber = false;
                for (String keys : stringSet) {
                    if(text.contains(keys)){
                        containsNumber = true;
                        boolean status = ComplaintDB.getInstance().getDb().get(keys).isOpen();
                        if(isOpen()){
                            getMachine().sendInput("Complaint with id #"+keys
                            +" is still open");
                        }else{
                            getMachine().sendInput("Complaint with id #"+keys
                                    +" has been closed");
                        }
                        getMachine().sendInput("Let me check if someone tweeted to your complaint");
                        new TweetConfig.CheckTwitter(getMachine()).execute(keys);
                        setOpen(false);
                        break;
                    }
                }
                if(ComplaintDB.getInstance().getNumber() > 0 && !containsNumber){
                    StringBuilder builder = new StringBuilder();
                    builder.append("Tracking IDs are ");
                    for (String key : ComplaintDB.getInstance().getDb().keySet()) {
                        builder.append("#"+key+"\n");
                    }
                    getMachine().sendInput(builder.toString());
                }
                setOpen(false);
                break;
            case DEACTIVATE:
                break;
            case NO_ACTION:
                getMachine().sendInput("Could not understand you.");
                getMachine().sendInput("Do you want me to close this complaint ?");
                expected = "yes no yeah nope haan na close open";
                isClosing = true;
                break;
            case CLOSE:
                getMachine().sendInput("Complaint closed.");
                setOpen(false);
                expected = null;
                getMachine().sendInput("What's next?");
                break;
            default:
                getMachine().sendInput(getDescripiton());
                break;
        }
    }


    public Actions[] allowdAction(){
		Actions[] allowedActionArray = {Actions.PRE_PROECESS, Actions.BASIC_INFO, Actions.KNOW_MORE,
				Actions.NEW, Actions.TRACK, Actions.DEACTIVATE, Actions.GENERIC_REPLY};
		return allowedActionArray;
	}

    @Override
    public String expectedInput() {
        return expected;
    }

    public class Procedure{
		public String domain ="Some Domain";
		public String subDomain = "Other";
		public String location;
		public String issue = "Some Issue";
		int stepNo = 1;

		public void processStep(){

			switch (stepNo){
				case 1:
					processInput(getInputObject());
                    System.out.println("Choice selected: " + domain);
                    getMachine().sendInput("You selected " + domain);
                    if(domain.equalsIgnoreCase("Billing")||domain.equalsIgnoreCase("Value Added Service")
                            ||domain.equalsIgnoreCase("Internet/Data")) {
                        stepNo++;
                        getMachine().sendInput(selectSubDomain(domain));
                        return;
                    }else{
                        stepNo = stepNo + 2;
                        getMachine().sendInput("What is the issue ? (Short description, around 30 words)");
                        expected = null;
                        return;
                    }
				case 2:
                    processInput(getInputObject());
                    System.out.println("Choice selected: " + subDomain);
                    getMachine().sendInput("You selected " + subDomain);
                    getMachine().sendInput("What is the issue ? (Short description, around 30 words)");
                    expected = null;
                    stepNo++;
					break;
				case 3:
                    processInput(getInputObject());
                    System.out.println("Issue: " + issue);
                    getMachine().sendInput("Got your complaint.");
                    getMachine().sendInput("Let me check, if I can help you");
                    CBR cbr = new CBR(getMachine().getContext());
                    cbr.loadCBR(this);
                    getMachine().sendInput("You can check status of complaints anytime.");
                    stepNo++;
                    ActionObject actionObject = new ActionObject(Complaint.this, new InputObject(Actions.CLOSE));
                    actionObject.performAction();
					break;
				case 4:
					//finalize;
			}
		}
        public void processInput(InputObject inputObject){

            String text = inputObject.getBody().get(InputObject.InputStruct.BODY);
            System.out.println("Unwrapped the input object: " + text);
            System.out.println("Current step is: " + stepNo);
            switch (stepNo){
                case 1:
                    //this is domain selection step search for the domain
                    if("1 Billing".contains(text)) domain = "Billing";
                    else if ("2 mobile number portability mnp".contains(text)) domain = "Mobile Number Portability";
                    else if ("3 unsolicited commercial communication ucc".contains(text)) domain = "Unsolicited Commercial Communication";
                    else if ("4 value added service vas".contains(text)) domain = "Value Added Service";
                    else if ("5 fault network".contains(text)) domain = "Fault Network";
                    else if ("6 internet/data".contains(text)) domain = "Internet/Data";
                    else if ("7 customer service".contains(text)) domain = "Customer Service";
                    break;
                case 2:
                    //this is sub domain selection process
                    System.out.println("Inside sub domain selection validation.");
                    switch (domain){
                        case "Billing":
                            System.out.println("Trying to search for subdomains in billing.");
                            if("1 invoice related".contains(text)) subDomain = "Invoice Related";
                            else  if("2 charging validity problems".contains(text)) subDomain = "Charging Validaity Problem";
                            else if("3 others".contains(text)) subDomain = "Other";
                            break;
                        case "Value Added Service":
                            if("1 renewal related".contains(text)) subDomain = "Renewal Related";
                            else  if("2 activation without consent".contains(text)) subDomain = "Activation Without Consent";
                            else if("3 others".contains(text)) subDomain = "Other";
                            break;
                        case "Internet/Data":
                            if("1 charging related".contains(text)) subDomain = "Charging Related";
                            else  if("2 data usage related".contains(text)) subDomain = "Data Usage Related";
                            else if("3 others".contains(text)) subDomain = "Other";
                            break;
                    }
                    break;
                case 3:
                    issue = text;
                    break;
            }
        }

        public String sendIssueList() {
            StringBuilder builder = new StringBuilder();
            builder.append("In which domain you have an issue ? \n");
            builder.append("1. Billing\n");
            builder.append("2. Mobile Number Portability\n");
            builder.append("3. Unsolicited Commercial Communication\n");
            builder.append("4. Value Added Service\n");
            builder.append("5. Fault Network\n");
            builder.append("6. Internet/Data\n");
            builder.append("7. Customer Service");
            expected = builder.toString();
            return builder.toString();
        }

        public String selectSubDomain(String domain){
            StringBuilder builder = new StringBuilder();
            getMachine().sendInput("Can you tell me the sub domain ?\n");
            if(domain.equals("Billing")) {
                builder.append("1. Invoice Related\n");
                builder.append("2. Charging Validity Problems\n");
                builder.append("3. Others");
                expected = builder.toString();
            } else if(domain.equals("Value Added Service")) {
                builder.append("1. Renewal Related\n");
                builder.append("2. Activation Without Consen\n");
                builder.append("3. Others");
                expected = builder.toString();
            }
            else if(domain.equals("Internet/Data")) {
                builder.append("1. Charging Related\n");
                builder.append("2. Data Usage Related\n");
                builder.append("3. Others");
                expected = builder.toString();
            }
            return builder.toString();
        }
	}

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public void setOpen(boolean open) {
        this.open = open;
    }
}
