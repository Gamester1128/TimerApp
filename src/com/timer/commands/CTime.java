package com.timer.commands;

import java.time.LocalTime;

import com.timer.inputprocessor.InputProcess;
import com.timer.state.State;
import com.timer.util.Print;
import com.timer.watch.Watch;

public class CTime extends Command {

	public CTime(String[] keys) {
		super(keys);
	}

	public void doCommand(String[] inputs, State state) {
		if (inputs.length != 2) {
			Print.error("Wrong number of arguments");
			return;
		}

		if (inputs[1].equals("passed") && (InputProcess.get().watch.isOn() || InputProcess.get().watch.isPaused())) {
			Print.longResponse(Watch.getStringFormOfTime(InputProcess.get().watch.getElapsedTime()));
		}
		else if (inputs[1].equals("remaining")
				&& (InputProcess.get().watch.isOn() || InputProcess.get().watch.isPaused())) {
			Print.longResponse(Watch.getStringFormOfTime(InputProcess.get().watch.getRemainingTime()));
		}
		else if (inputs[1].equals("now")) {
			Print.longResponse(LocalTime.now().toString().substring(0, 8));
		}

	}

	public void printHelp() {

	}

}
