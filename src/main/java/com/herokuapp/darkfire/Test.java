package com.herokuapp.darkfire;

import java.util.Scanner;

import com.herokuapp.darkfire.sara.GenericMachine2;
import com.herokuapp.darkfire.sara.adapters.ChatAdapter;
import com.herokuapp.darkfire.sara.interfaces.Machine;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GenericMachine2 machine = null;
		Scanner sc = new Scanner(System.in);
		while(true){
			String text = sc.nextLine();
			machine.processInput(text);
		}
	}

}
