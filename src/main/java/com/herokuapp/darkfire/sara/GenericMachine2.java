package com.herokuapp.darkfire.sara;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.herokuapp.darkfire.sara.adapters.ChatAdapter;
import com.herokuapp.darkfire.sara.interfaces.Input;
import com.herokuapp.darkfire.sara.interfaces.Machine;
import com.herokuapp.darkfire.sara.objects.Introduction;
import com.herokuapp.darkfire.sara.objects.ModelObject;
import com.herokuapp.darkfire.sara.objects.interfaces.ModelObjectInterface.Actions;

public class GenericMachine2 implements Machine{
	
	private ActionObject currentAction;
	private InputExpected expectedInput = InputExpected.COMMAND;
	private ChatAdapter chatAdapter;
    private ListView chatView;

	public Context getContext() {
		return context;
	}


    private Context context;

	public GenericMachine2(ChatAdapter chatAdapter, ListView listView, Context context, Activity activity) {
		// TODO Auto-generated constructor stub
		this.context = context;
        this.chatView = listView;
        this.chatAdapter = chatAdapter;
		currentAction = new ActionObject(new Introduction(this), new InputObject(Actions.PRE_PROECESS));
		currentAction.performAction();


	}

	@Override
	public void sendInput(String toUser) {
		// To be invoked by the Objects
		System.out.println("sara###" + toUser);
		sendToAdapter("sara###"+toUser);
	}

	public void sendToAdapter(String text){
		chatAdapter.addMsg(text);
        chatView.setSelection(chatAdapter.getCount()-1);
	}
	
	@Override
	public void processInput(String fromUser){
		//analyze the text...
		//System.out.println("user###"+fromUser);
		sendToAdapter("user###"+fromUser);
		switch(expectedInput){
			case COMMAND:
				currentAction = TextEngine2.getInstance().analyze(fromUser, currentAction);
				currentAction.performAction();
				break;
			case YES_NO:
				expectedInput = InputExpected.COMMAND;
				break;
			default:
				//do nothing
				break;
		}
	}
	
	private boolean makeCloseConfirm(String text){
		sendInput("Do you want to close current action? (y/n)");
		return TextEngine2.getInstance().checkConfirm(text);
	}
	
	
	public final void newObject(ModelObject modelObject){
		throw new UnsupportedOperationException();
	}
	
	public final void newObject(ActionObject action){
		currentAction = action;
		processQueue();
	}

	@Override
	public void processQueue() {
		if(currentAction.getObejct().isOpen())
			currentAction.performAction();
	}

	public ActionObject getCurrentAction() {
		return currentAction;
	}
	
	public enum InputExpected{
		YES_NO,
		COMMAND;
	}
	
}
