package com.timer.commands;

import com.timer.inputprocessor.InputProcess;
import com.timer.state.State;
import com.timer.util.Print;
import com.timer.watch.InvokeOnEnd;
import com.timer.watch.Watch;

public class CQuit extends Command {

	public CQuit(String[] keys) {
		super(keys);
	}

	public void doCommand(String[] inputs, State state) {
		if (inputs.length == 1) {
			Print.longResponse("(^.^)/");
			Watch watch = new Watch();
			watch.start(2, 's');
			watch.addInvokeOnEnd(new InvokeOnEnd() {
				public void invoke() {
					System.exit(0);
				}
			});
			InputProcess.get().sleep(3000);
		}
	}

	public void printHelp() {
		Print.longResponse("quit			Closes the timer app in 2 seconds");
	}

}
