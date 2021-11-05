package com.timer.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.timer.util.Util;
import com.timer.watch.InvokeOnEnd;
import com.timer.watch.Watch;

public class Sound {

	private static int count = 0;
	private AudioInputStream audioIn;
	private Clip clip;
	public static Sound[] sounds = new Sound[2];

	public String soundFileName;
	// all songs
	public static final Sound creeper = new Sound("creeper_aw_man.wav");
	public static final Sound pewds_stop = new Sound("pewdiepie_stop.wav");

	public Sound(String fileName) {
		soundFileName = fileName;
		sounds[count++] = this;
		File f = new File("res/sounds/" + soundFileName);
		try {
			audioIn = AudioSystem.getAudioInputStream(f.getAbsoluteFile());
			clip = AudioSystem.getClip();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public static void playRandomSong() {
		sounds[Util.random.nextInt(sounds.length)].play();
	}

	public static void stopAll() {
		for (Sound sound : sounds) {
			sound.stop();
		}
	}

	public void play() {
		try {
			if (clip.isActive()) {
				stop();
			}
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();

			new Watch().start(clip.getMicrosecondLength() * 1000000.0, 's').addInvokeOnEnd(new InvokeOnEnd() {

				public void invoke() {
					stop();
				}
			});

		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		if (clip.isActive()) {
			clip.stop();
		}
	}
}
