package com.timer.watch;

public class Watch implements Runnable {

	private long startTime;
	private boolean isOn = false, isPaused = false;
	private long elapsedTime = 0;
	private long duration;
	private Thread thread;
	private InvokeOnEnd listener;

	private void start(long duration, long elapsedTime, long start) {
		this.startTime = start;
		this.isPaused = false;
		this.isOn = true;
		this.elapsedTime = elapsedTime;
		this.duration = duration;
		thread = new Thread(this, "Watch");
		thread.start();
	}

	/**
	 * @param unit - must be either 's', 'm' or 'h'
	 */
	public Watch start(double duration, char unit) {
		if (!(unit == 'h' || unit == 'm' || unit == 's')) throw new RuntimeException("Wrong units to start timer");
		start((long) (duration * getUnitMultiplier(unit) * 1000000), 0, System.nanoTime());
		return this;
	}

	private int getUnitMultiplier(char unit) {
		if (unit == 'h') return 60 * 60 * 1000;
		if (unit == 'm') return 60 * 1000;
		if (unit == 's') return 1000;
		else return 0;
	}

	public void stop() {
		thread.interrupt();
		if (listener != null) listener.invoke();
	}

	public void pause() {
		if (isPaused) throw new RuntimeException("Attempting to pause when paused");
		elapsedTime += System.nanoTime() - startTime;
		isPaused = true;
		isOn = false;
		thread.interrupt();
	}

	public void unPause() {
		if (isPaused) start(duration, elapsedTime, System.nanoTime());
		else throw new RuntimeException("Attempting to unpause when isn't paused");
	}

	public void run() {
		boolean shouldInvoke = true;
		try {
			long time = (duration - elapsedTime <= 0) ? 1 : duration - elapsedTime;
			Thread.sleep(time / 1000000);
		} catch (InterruptedException e) {
			shouldInvoke = false;
		}
		if (!isPaused) isOn = false;
		if (shouldInvoke && listener != null) {
			listener.invoke();
		}
	}

	public long getElapsedTime() {
		// if (!isOn && !isPaused) throw new RuntimeException("Timer has not started and
		// attempting to notify time");
		if (isPaused) return elapsedTime;
		else return elapsedTime + System.nanoTime() - startTime;
	}

	public long getRemainingTime() {
		if (!isOn && !isPaused) throw new RuntimeException("Timer has not started and attempting to notify time");
		if (isPaused) return duration - elapsedTime;
		else return duration - (elapsedTime + System.nanoTime() - startTime);
	}

	public Watch addInvokeOnEnd(InvokeOnEnd invoke) {
		this.listener = invoke;
		return this;
	}

	public static String getStringFormOfTime(long nanoTimeElapsed) {
		double time = nanoTimeElapsed / 1000000000.0;
		if (time < 60) return Math.round(time * 10.0) / 10.0 + "s";
		if ((time /= 60) < 60) return Math.round(time * 10.0) / 10.0 + "m";
		if ((time /= 60) < 24) return Math.round(time * 10.0) / 10.0 + "h";
		else return "Too big";
	}

	public boolean isPaused() {
		return isPaused;
	}

	public boolean isOn() {
		return isOn;
	}

	public long getDuration() {
		return duration;
	}

}
