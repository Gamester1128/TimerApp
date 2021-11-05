package com.timer.commands;

import com.timer.state.State;
import com.timer.util.Print;

public class CThread extends Command {

	public CThread(String[] keys) {
		super(keys);
	}

	public void doCommand(String[] inputs, State state) {
		if (inputs.length == 1) {
			Print.longResponse("Pass in more arguments >:D");
		}

		else if (inputs[1].equals("count")) {
			Thread[] n = new Thread[Thread.activeCount()];
			Thread.enumerate(n);
			for (Thread i : n)
				Print.longResponse(i.getName());
		}
	}

	@Override
	public void printHelp() {

	}

}
