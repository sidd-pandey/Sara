package com.herokuapp.darkfire;

import com.herokuapp.darkfire.sara.TextEngine;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TextEngine.getInstance().analyze("Register a complaint for me", null);
		TextEngine.getInstance().analyze("Tell me about vas", null);
		TextEngine.getInstance().analyze("Track my complaint", null);
		TextEngine.getInstance().analyze("Perform speedtest", null);
		TextEngine.getInstance().analyze("Tell me about vas", null);
		TextEngine.getInstance().analyze("any updates on telecom", null);
		TextEngine.getInstance().analyze("news on bsnl", null);
	}

}
