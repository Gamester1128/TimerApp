package com.timer.commands;

import com.timer.inputprocessor.InputProcess;
import com.timer.state.State;
import com.timer.util.Print;

public class CStop extends Command {

	public CStop(String[] keys) {
		super(keys);
	}

	public void doCommand(String[] inputs, State state) {
		if (inputs.length != 1) Print.error("stop command does not taken any arguments");
		else if (!InputProcess.get().watch.isOn()) Print.error("Timer is not on");
		else InputProcess.get().watch.stop();
	}

	public void printHelp() {
		Print.longResponse("stop			Stops the timer when timer is already active");
	}

}
