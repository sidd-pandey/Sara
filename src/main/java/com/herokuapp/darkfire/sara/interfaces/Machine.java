package com.herokuapp.darkfire.sara.interfaces;

import android.app.Activity;
import android.content.Context;

import com.herokuapp.darkfire.sara.objects.ModelObject;

public interface Machine {
	void sendInput(String toUser);
	void processInput(String fromUser);
	void newObject(ModelObject modelObject);
	void processQueue();
	Context getContext();
}
