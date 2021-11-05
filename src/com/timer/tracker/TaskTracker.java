package com.timer.tracker;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//Class handles score.json file storing how I have been doing the objectives
public class TaskTracker {

	private static final String path = "res/score.json"; // path to json file
	public static final String PASSED = "passed", FAILED = "failed", PARTIAL = "partial"; // enum
	public static final String BESTSCORE = "bestScore";
	public static final String BESTDATE = "bestDate";
	public static final String WORSTSCORE = "worstScore";
	public static final String WORSTDATE = "worstDate";
	public static final int SCORE_DATE = 0, SCORE_INDEX = 1;

	public TaskTracker() {
		addToday();

		// make sure best and worst score exists
		JSONObject json = getJSONfromFile(path);
		if (!(json.containsKey(BESTSCORE) && json.containsKey(BESTDATE) && json.containsKey(WORSTSCORE)
				&& json.containsKey(WORSTDATE))) {

			recalculateWorstScore(json);
			recalculateBestScore(json);
		}
	}

	public Object[] getBestScore(JSONObject json) {
		return new Object[] { json.get(BESTDATE), json.get(BESTSCORE) };
	}

	public Object[] getWorstScore(JSONObject json) {
		return new Object[] { json.get(WORSTDATE), json.get(WORSTSCORE) };
	}

	public int getBestScoreToday() {
		return Math.toIntExact((Long) getBestScore(getJSONfromFile(path))[SCORE_INDEX]);
	}

	public int getWorstScoreToday() {
		return Math.toIntExact((Long) getWorstScore(getJSONfromFile(path))[SCORE_INDEX]);
	}

	@SuppressWarnings("unchecked")
	public Object[] recalculateWorstScore(JSONObject json) {
		int worstScore = 99999999;
		String date = null;
		Object[] keys = json.keySet().toArray();

		for (int i = 0; i < keys.length; i++) {
			if (keys[i].equals(BESTSCORE) || keys[i].equals(BESTDATE) || keys[i].equals(WORSTDATE)
					|| keys[i].equals(WORSTSCORE))
				continue;
			int scoreOfDate = scoreOfDate(json, (String) keys[i]);

			if (scoreOfDate < worstScore) {
				worstScore = scoreOfDate;
				date = (String) keys[i];
			}
		}
		json.put(WORSTSCORE, worstScore);
		json.put(WORSTDATE, date);

		save(json);

		return new Object[] { date, worstScore };
	}

	@SuppressWarnings("unchecked")
	public Object[] recalculateBestScore(JSONObject json) {
		int highestScore = -99999999;
		String date = null;
		Object[] keys = json.keySet().toArray();

		for (int i = 0; i < keys.length; i++) {
			if (keys[i].equals(BESTSCORE) || keys[i].equals(BESTDATE) || keys[i].equals(WORSTDATE)
					|| keys[i].equals(WORSTSCORE))
				continue;
			int scoreOfDate = scoreOfDate(json, (String) keys[i]);

			if (scoreOfDate > highestScore) {
				highestScore = scoreOfDate;
				date = (String) keys[i];
			}
		}
		json.put(BESTSCORE, highestScore);
		json.put(BESTDATE, date);

		save(json);

		return new Object[] { date, highestScore };
	}

	// adds num on specified typeScore on the current date
	@SuppressWarnings("unchecked")
	private void addDataToDate(String typeOfScore) {
		// initialise entry if not there
		addToday();

		JSONObject json = getJSONfromFile(path);
		String date = getDateNow();
		// add entry
		JSONObject currentEntry = (JSONObject) (json.get(date));
		currentEntry.put(typeOfScore, (Long) (currentEntry.get(typeOfScore)) + 1);

		// write to file
		save(json);
	}

	private void save(JSONObject json) {
		try {
			FileWriter writer = new FileWriter(path);
			writer.write(json.toJSONString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// returns jsonobject from jsonfile
	private synchronized JSONObject getJSONfromFile(String path) {
		try {
			return (JSONObject) new JSONParser().parse(new FileReader(path));
		} catch (IOException | ParseException e) {
			return getJSONfromFile(path);
		}
	}

	@SuppressWarnings("unchecked")
	public void addBestScore(JSONObject json, int score, String date) {
		int best = Math.toIntExact((Long) json.get(BESTSCORE));

		if (score > best) {
			json.put(BESTSCORE, score);
			json.put(BESTDATE, date);
			save(json);
		}

	}

	@SuppressWarnings("unchecked")
	public void addWorstScore(JSONObject json, int score, String date) {
		int worst = Math.toIntExact((Long) json.get(WORSTSCORE));

		if (score < worst) {
			json.put(WORSTSCORE, score);
			json.put(WORSTDATE, date);
			save(json);
		}

	}

	// following three methods updates data on current date
	public void addPassed() {
		addDataToDate(PASSED);
		JSONObject json = getJSONfromFile(path);
		addBestScore(getJSONfromFile(path), scoreOfDate(json, getDateNow()), getDateNow());
	}

	public void addFailed() {
		addDataToDate(FAILED);
		JSONObject json = getJSONfromFile(path);
		addWorstScore(getJSONfromFile(path), scoreOfDate(json, getDateNow()), getDateNow());
	}

	public void addPartial() {
		addDataToDate(PARTIAL);
		JSONObject json = getJSONfromFile(path);
		addWorstScore(getJSONfromFile(path), scoreOfDate(json, getDateNow()), getDateNow());
	}

	// returns todays date in the format dd\mm\yy
	private String getDateNow() {
		return DateTimeFormatter.ofPattern("dd/MM/yy").format(LocalDateTime.now());
	}

	public int getPassed(JSONObject json, String date) {
		return Math.toIntExact((long) ((JSONObject) json.get(date)).get(PASSED));
	}

	public int getFailed(JSONObject json, String date) {
		return Math.toIntExact((Long) ((JSONObject) json.get(date)).get(FAILED));
	}

	public int getPartial(JSONObject json, String date) {
		return Math.toIntExact((Long) ((JSONObject) json.get(date)).get(PARTIAL));
	}
	// returns current date in string

	public int getPassed() {
		return getPassed(getJSONfromFile(path), getDateNow());
	}

	public int getFailed() {
		return getFailed(getJSONfromFile(path), getDateNow());
	}

	public int getPartial() {
		return getPartial(getJSONfromFile(path), getDateNow());
	}

	// returns total score of today
	// returns null if none found
	public Integer scoreOfToday() {
		return scoreOfDate(getDateNow());
	}

	public Integer scoreOfToday(JSONObject json) {
		return scoreOfDate(json, getDateNow());
	}

	// gets score of a specific date
	// returns null if none found
	public Integer scoreOfDate(String date) {
		JSONObject json = getJSONfromFile(path);
		if (json.containsKey(date))
			return calculateScore(getPassed(json, date), getFailed(json, date), getPartial(json, date));
		return null;
	}

	public Integer scoreOfDate(JSONObject json, String date) {
		if (json.containsKey(date))
			return calculateScore(getPassed(json, date), getFailed(json, date), getPartial(json, date));
		return null;
	}

	// formula for calculating score
	public int calculateScore(int passed, int failed, int partial) {
		return passed - ((int) Math.pow(2.0, failed) - 1) - partial;
	}

	@SuppressWarnings("unchecked")
	public void addToday() {
		JSONObject json = getJSONfromFile(path);
		// initialise entry if not there
		if (!json.containsKey(getDateNow())) {
			JSONObject newEntry = new JSONObject();
			newEntry.put(PASSED, 0);
			newEntry.put(FAILED, 0);
			newEntry.put(PARTIAL, 0);
			json.put(getDateNow(), newEntry);

			// write to file
			save(json);
		}

	}

}
