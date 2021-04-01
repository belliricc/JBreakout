package jbreakout.util;

import java.awt.Color;

public class MathTools {
	
	public static double min(double a, double b) {
		return (a < b) ? a : b;
	}
	
	public static double max(double a, double b) {
		return (a > b) ? a : b;
	}
	
	// See: https://learnopengl.com/In-Practice/2D-Game/Collisions/Collision-detection
	public static double clamp(double value, double min, double max) {
		return max(min, min(max, value));
	}

	public static int[] hexStringToRgbConverter(String hex) {
		
		int[] rgb = new int[3];
		
		rgb[0] = Integer.parseInt(hex.substring(0, 2), 16);
		rgb[1] = Integer.parseInt(hex.substring(2, 4), 16);
		rgb[2] = Integer.parseInt(hex.substring(4, 6), 16);
	
		return rgb;
	}
	
	public static Color colorFromHexString(String hexString) {
		int[] rgb = hexStringToRgbConverter(hexString);
		
		return new Color(rgb[0], rgb[1], rgb[2]);
	}
	
	public static Color randomColor() {
		return new Color(randomNumberInclusive(0, 255),randomNumberInclusive(0, 255),randomNumberInclusive(0, 255));
	}
	
	public static Color randomColorDifferentFrom(Color thisColor) {
		int[] rgb = new int[3];
		do {
			rgb[0] = randomNumberInclusive(0, 255);
			rgb[1] = randomNumberInclusive(0, 255);
			rgb[2] = randomNumberInclusive(0, 255);
		} while (rgb[0] == thisColor.getRed() ||
				 rgb[1] == thisColor.getBlue() || 
				 rgb[2] == thisColor.getGreen());
		
		return new Color(rgb[0], rgb[1], rgb[2]);
	}
	
	
	public static String toHexString(Color colour) {
		String hexColour = Integer.toHexString(colour.getRGB() & 0xffffff);
		if (hexColour.length() < 6) {
			hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
		}
		return hexColour;	
	}
	
	// Returns a random integer number in the interval [min,max].
	public static int randomNumberInclusive(int min, int max) {
		return (min + (int)(Math.random() * ((max - min) + 1)));
	}

	// -----------------------------------------------
	// -------------- CONVERSION METHODS -------------
	// -----------------------------------------------

	public static double radToDeg(double rad){
		return Math.toDegrees(rad);    // Return value is DEGREES
	}

	public static double degToRad(double deg){
		return Math.toRadians(deg);    // Return value is RADIANS
	}
}
