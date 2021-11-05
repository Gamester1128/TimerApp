package com.timer.commands;

import com.timer.state.State;

public abstract class Command {

	protected String[] keys;

	public Command(String[] keys) {
		this.keys = keys;
	}

	public abstract void doCommand(String[] inputs, State state);

	public abstract void printHelp();

	protected String[] stringOfKeys() {
		return keys;
	}

	public boolean matchesKey(String key) {
		for (String i : keys)
			if (i.equals(key)) return true;
		return false;
	}
	
}
