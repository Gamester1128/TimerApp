package com.timer.commands;

import java.time.LocalTime;

import com.timer.inputprocessor.InputProcess;
import com.timer.sound.Sound;
import com.timer.state.State;
import com.timer.util.Print;
import com.timer.util.Util;
import com.timer.watch.InvokeOnEnd;

public class CStart extends Command {

	public CStart(String[] keys) {
		super(keys);
	}

	public void doCommand(String[] inputs, State state) {
		if (!(inputs.length == 3 || inputs.length == 2)) {
			Print.error("Please pass in start arguments");
			return;
		}

		if (inputs[1].contains(":")) {
			String[] timeInput = inputs[1].split(":");

			if (timeInput.length < 2) {
				Print.error("Time must be in the format \"HH:MM\" or \"HH:MM:SS\"");
				return;
			}

			int hr = Util.parseToInt(timeInput[0]);
			int min = Util.parseToInt(timeInput[1]);
			int sec = (timeInput.length == 3) ? Util.parseToInt(timeInput[2]) : 0;

			if (hr < 0 || min < 0 || sec < 0) {
				Print.error("I don't understand time which are not numerical ");
				return;
			}

			LocalTime time = LocalTime.now();
			int localTimeNumber = (time.getHour() * 60 + time.getMinute()) * 60 + time.getSecond();
			int inputTimeNumber = (hr * 60 + min) * 60 + sec;
			int timeToStart = inputTimeNumber - localTimeNumber;

			if (timeToStart < 0) {
				Print.error("Timer does not support next day through \"XX:XX\" format");
				return;
			}

			InputProcess.get().watch.start(timeToStart, 's').addInvokeOnEnd(new InvokeOnEnd() {
				public void invoke() {
					Sound.pewds_stop.play();
					InputProcess.get().timerUI.start((inputs.length == 3) ? inputs[2] : null);
				}
			});
		}

		else {
			char unit = inputs[1].charAt(inputs[1].length() - 1);
			if (!(unit == 'm' || unit == 'h' || unit == 's')) {
				Print.error("Please insert correct units");
				return;
			}

			if (InputProcess.get().watch.isOn()) {
				Print.error("Timer is already started");
				return;
			}

			String num = inputs[1].substring(0, inputs[1].length() - 1);
			if (!Util.isParseableToDouble(num)) {
				Print.error("number not found");
				return;
			}

			InputProcess.get().watch.start(Util.parseToDouble(num), unit).addInvokeOnEnd(new InvokeOnEnd() {
				public void invoke() {
					//Sound.pewds_stop.play();
					Sound.playRandomSong();
					InputProcess.get().timerUI.start((inputs.length == 3) ? inputs[2] : null);
				}
			});
		}
	}

	@Override
	public void printHelp() {
		Print.longResponse("start [num][unit]		Starts timer based on arguments");
	}
}
