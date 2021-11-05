package com.timer.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.timer.inputprocessor.InputProcess;
import com.timer.sound.Sound;
import com.timer.util.ImageLoader;
import com.timer.util.Util;
import com.timer.watch.Watch;

public class TimerWarning extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private boolean runnable = false;
	private BufferedImage image;
	private String message;
	private String[] objs;
	private int fixedHeight = 500;

	public TimerWarning() {

	}

	public void run() {
		int x = 0;
		message = "It has been " + Watch.getStringFormOfTime(InputProcess.get().watch.getElapsedTime()) + " (>.<)";

		// initialise display
		List<File> imageFiles = new ArrayList<File>();

		File[] files = new File("res").listFiles();
		for (int i = 0; i < files.length; i++)
			if (files[i].getName().endsWith(".png")) imageFiles.add(files[i]);

		initDisplay(ImageLoader.load(imageFiles.get(Util.random.nextInt(imageFiles.size())).getAbsolutePath()));

		this.createBufferStrategy(3);
		frame.setVisible(true);
		runnable = true;
		while (runnable) {
			toFront();
			render();
			frame.setLocationRelativeTo(null);
			if (x == 0) x = 2000;
			try {
				Thread.sleep(x);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		g.setFont(new Font("TimesRoman", Font.BOLD, 40));
		renderStringWithShadow(g, 20, 40, 2, 2, message, Color.white, Color.black);

		// handle drawing score of today
		g.setFont(new Font("TimesRoman", Font.BOLD, 30));
		renderStringWithShadow(g, 20, 130, 1, 1, "Score:", Color.white, Color.black);
		g.setFont(new Font("TimesRoman", Font.BOLD, 19));
		renderStringWithShadow(g, 30, 150 + 24 * 0, 1, 1, "Passed: " + InputProcess.get().getTK().getPassed(),
				Color.white, Color.black);
		renderStringWithShadow(g, 30, 150 + 24 * 1, 1, 1, "Failed: " + InputProcess.get().getTK().getFailed(),
				Color.white, Color.black);
		renderStringWithShadow(g, 30, 150 + 24 * 2, 1, 1, "Partial: " + InputProcess.get().getTK().getPartial(),
				Color.white, Color.black);
		renderStringWithShadow(g, 30, 150 + 24 * 3, 1, 1, "Overall: " + InputProcess.get().getTK().scoreOfToday(),
				Color.white, Color.black);
		renderStringWithShadow(g, 30, 150 + 24 * 4, 1, 1, "Best: " + InputProcess.get().getTK().getBestScoreToday(),
				Color.white, Color.black);
		renderStringWithShadow(g, 30, 150 + 24 * 5, 1, 1, "Worst: " + InputProcess.get().getTK().getWorstScoreToday(),
				Color.white, Color.black);

		// handle drawing objective
		if (objs != null) {
			int yOffset = 300;
			g.setColor(Color.CYAN);
			g.setFont(new Font("TimesRoman", Font.BOLD, 20));

			renderStringWithShadow(g, 20, yOffset, 1, 1, "If you have not done these you die (in game): ", Color.white,
					Color.RED);
			for (int i = 0; i < objs.length; i++)
				renderStringWithShadow(g, 30, yOffset + 20 + i * 20, 1, 1, "- " + objs[i], Color.white, Color.RED);
		}
		g.dispose();
		bs.show();
	}

	public void renderStringWithShadow(Graphics g, int x, int y, int xoffset, int yoffset, String string,
			Color stringcol, Color shadowcol) {
		g.setColor(shadowcol);
		g.drawString(string, x + xoffset, y + yoffset);
		g.setColor(stringcol);
		g.drawString(string, x, y);
	}

	public synchronized void start(String objective) {
		if (objective == null) this.objs = null;
		else {
			this.objs = objective.split(";");
			for (int i = 0; i < objs.length; i++) {
				objs[i] = objs[i].replace('-', ' ');
			}
		}
		if (runnable == true) throw new RuntimeException("Attempting to start timer UI when on");
		new Thread(this, "TimerEndingUI").start();
	}

	public synchronized void stop() {
		runnable = false;
		frame.setVisible(false);
		message = null;
	}

	public void initDisplay(BufferedImage image) {
		// init frame
		this.image = image;
		Dimension size = new Dimension(fixedHeight * image.getWidth() / image.getHeight(), fixedHeight);
		frame = new JFrame("Time's Up!!!!");
		frame.setSize(size);
		frame.addWindowListener(new ListensToWindow());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.add(this);
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		this.setBackground(Color.BLACK);
		this.setMaximumSize(size);
		frame.setVisible(false);
		frame.setLocationRelativeTo(null);

		// initialise buttons
		JButton passed = new JButton("Passed");
		passed.setBounds(30, 55, 100, 40);
		JButton failed = new JButton("Failed");
		failed.setBounds(30 + 55 + 10, 55, 100, 40);
		JButton partial = new JButton("Partial");
		partial.setBounds(30 + (55 + 10) * 2, 55, 100, 40);
		setStyle(passed, new Color(28, 178, 28));
		setStyle(failed, new Color(178, 28, 28));
		setStyle(partial, new Color(28, 28, 178));

		// add listeners
		passed.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				InputProcess.get().getTK().addPassed();
				render();
			}

		});
		failed.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				InputProcess.get().getTK().addFailed();
				render();
			}

		});
		partial.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				InputProcess.get().getTK().addPartial();
				render();
			}

		});

		// add components to frame
		frame.add(passed);
		frame.add(failed);
		frame.add(partial);
		frame.add(panel);
		frame.pack();

		toFront();
	}

	private void setStyle(JButton button, Color col) {
		button.setBackground(new Color(col.getRed(), col.getGreen(), col.getBlue(), (int) (0.46 * 255)));
		button.setBorder(new EmptyBorder(0, 0, 0, 0));
		button.setFocusPainted(false);
	}

	private void toFront() {
		int sta = frame.getExtendedState() & ~JFrame.ICONIFIED & JFrame.NORMAL;
		frame.setExtendedState(sta);
		frame.setAlwaysOnTop(true);
		frame.toFront();
		frame.requestFocus();
		frame.setAlwaysOnTop(false);
	}

	private class ListensToWindow extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			stop();
			Sound.stopAll();
		}
	}
}