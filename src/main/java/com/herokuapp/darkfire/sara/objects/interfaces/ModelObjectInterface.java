package com.herokuapp.darkfire.sara.objects.interfaces;

import android.content.Context;

import com.herokuapp.darkfire.sara.interfaces.Input;

public interface ModelObjectInterface {
	
	enum Actions{
		PRE_PROECESS,
		BASIC_INFO,
		KNOW_MORE,
		CALL,
		MAIL,
		ACTIVATE,
		DEACTIVATE,
		TRACK,
		NEW,
		FIND,
		GENERIC_REPLY,
		CLOSE,
		UPDATE,
		LOCATION,
		NO_ACTION;
	}

	void onInput(Input input);
	void postProcess();
	String getDescripiton();
	boolean isOpen();
}
