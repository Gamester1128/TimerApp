package com.timer;

import com.timer.inputprocessor.InputProcess;
import com.timer.state.State;

public class Launcher {

	public static void main(String[] args) {
		State.init();
		InputProcess ip = InputProcess.get();
		ip.start();

	}

}
