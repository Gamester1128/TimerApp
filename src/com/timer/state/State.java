package com.timer.state;

import com.timer.commands.Command;

public abstract class State {

	public Command[] commands;

	//All instances of state
	public static MainState mainState;

	public static void init() {
		mainState = new MainState();
	}

	public abstract boolean process(String[] inputs);

}
