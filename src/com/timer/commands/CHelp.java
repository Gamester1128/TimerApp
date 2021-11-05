package com.timer.commands;

import com.timer.state.State;
import com.timer.util.Print;

public class CHelp extends Command {

	public CHelp(String[] keys) {
		super(keys);
	}

	public void doCommand(String[] inputs, State state) {
		for (Command i : state.commands)
			i.printHelp();
	}

	public void printHelp() {
		Print.longResponse("help			Displays all commands use");
	}

}
