package com.timer.inputprocessor;

import com.timer.state.State;
import com.timer.tracker.TaskTracker;
import com.timer.ui.TimerWarning;
import com.timer.util.Input;
import com.timer.util.Print;
import com.timer.watch.Watch;

public class InputProcess {

	private static InputProcess inputProcess;
	public Watch watch;
	public TimerData data;
	public boolean running = false;
	public TimerWarning timerUI;
	private TaskTracker tk;

	public static final String BUILD_VERSION = "1.101";
	private static final String INTRO_MESSAGE = "| Timer App | Build: " + BUILD_VERSION + " |";

	public State currentState;

	private InputProcess() {
		tk = new TaskTracker();
		watch = new Watch();
		data = new TimerData();
		data.startImmediately = true;
		data.unit = 'm';
		currentState = State.mainState;
		timerUI = new TimerWarning();
	}

	public static InputProcess get() {
		if (inputProcess == null) return inputProcess = new InputProcess();
		return inputProcess;
	}

	public void sleep(long milli) {
		try {
			Thread.sleep(milli);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		System.out.println(INTRO_MESSAGE);
		running = true;
		while (running) {
			String[] inputs = Input.string("Please enter a command: ").trim().split("\\s+");
			if (inputs[0].length() == 0) continue;
			inputs[0] = inputs[0].toLowerCase();
			boolean successful = currentState.process(inputs);
			if (!successful) Print.error("Command not found");
		}
	}

	public TaskTracker getTK() {
		return tk;
	}
}
