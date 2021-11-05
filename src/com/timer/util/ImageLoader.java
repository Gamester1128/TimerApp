package com.timer.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {

	public static BufferedImage load(String path) {
		try {
			//System.out.println(">> File \"" + path + "\" found!!!");
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			System.err.println(">> File \"" + path + "\" not found!!!");
		}
		return null;
	}

}
