package com.timer.commands;

import com.timer.inputprocessor.InputProcess;
import com.timer.state.State;
import com.timer.util.Print;

public class CPause extends Command {

	public CPause(String[] keys) {
		super(keys);
	}

	public void doCommand(String[] inputs, State state) {
		InputProcess ip = InputProcess.get();
		if (inputs.length != 1) {
			Print.error("Pause/Unpause does not take in arguments");
		}
		else if (!ip.watch.isOn() && !ip.watch.isPaused()) {
			Print.error("Timer has not started");
		}
		else if (!ip.watch.isPaused() && ip.watch.isOn() && inputs[0].equals(keys[0])) {
			ip.watch.pause();
			Print.longResponse("Goodnight (-,-)..zzZZ");
		}
		else if (ip.watch.isPaused() && !ip.watch.isOn() && inputs[0].equals(keys[1])) {
			ip.watch.unPause();
			Print.longResponse("Top of morning (^o^)/..");
		}
		else if (ip.watch.isPaused()) Print.error("Timer is already paused");
		else Print.longResponse("Timer is not sleeping");
	}

	public void printHelp() {
		Print.longResponse("pause			pauses the timer when started");
		Print.longResponse("unpause			unpauses the timer when started");
	}
}
