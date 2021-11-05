package com.timer.state;

import java.util.ArrayList;
import java.util.List;

import com.timer.commands.*;

public class MainState extends State {

	public MainState() {
		commands = getCommands();
	}

	protected Command[] getCommands() {
		List<Command> commands = new ArrayList<Command>();
		commands.add(new CHelp(new String[] { "help" }));
		commands.add(new CStart(new String[] { "start" }));
		commands.add(new CStop(new String[] { "stop" }));
		commands.add(new CThread(new String[] { "thread" }));
		commands.add(new CQuit(new String[] { "quit" }));
		commands.add(new CPause(new String[] { "pause", "unpause" }));
		commands.add(new CTime(new String[] { "time"}));
		return commands.toArray(new Command[0]);
	}

	public boolean process(String[] inputs) {
		for (int i = 0; i < commands.length; i++) {
			if (commands[i].matchesKey(inputs[0])) {
				commands[i].doCommand(inputs, this);
				return true;
			}
		}
		return false;
	}

}
