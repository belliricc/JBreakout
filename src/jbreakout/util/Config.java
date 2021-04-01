package jbreakout.util;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.util.Properties;

public class Config {

	private final static boolean IS_DIST_VERSION = true; // this flag must be set to true when compiling for the dist version

	//---------------------------------------------------------------
	// STATIC ATTRIBUTE
	//---------------------------------------------------------------
	private static Config instance = null;
	
	private static String CONFIG_FILE = "conf/config.txt";

	//---------------------------------------------------------------
	// INSTANCE ATTRIBUTE
	//---------------------------------------------------------------
	private Properties properties;

	private Config() {
		try {
			BufferedReader buffRead = new BufferedReader(new InputStreamReader(new FileInputStream(CONFIG_FILE), "ISO-8859-1"));
			this.properties = new Properties();
			this.properties.load(buffRead);
		}
		catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	//---------------------------------------------------------------
	// PRIVATE INSTANCE METHODS
	//---------------------------------------------------------------
	/*private String getConfigFile() throws URISyntaxException {
		String configFile = null;
		String relPath = "\\conf\\config.txt";
		if (System.getProperty("os.name").startsWith("Linux")) {
			relPath = "/conf/config.txt";
		}
		if (IS_DIST_VERSION)
			configFile = getHomeFolderForDistVersion() + relPath;
		else
			configFile = getHomeFolderForDevVersion() + relPath;
		return configFile;
	}*/

	/*private String getHomeFolderForDistVersion() throws URISyntaxException {
		String homeDir = null;
		String jarPath = Config.class.getResource("Config.class").toURI().toString();
		int indexOfExclamationMark = jarPath.indexOf("!");
		String prefix = "jar:file:/"; // this is the prefix for Windows OS platform
		if (System.getProperty("os.name").startsWith("Linux")) {
			prefix = "jar:file:";
		}
		homeDir = jarPath.substring(prefix.length(), indexOfExclamationMark);
		int lastIndexOfSlash = homeDir.lastIndexOf("/");
		homeDir = homeDir.substring(0, lastIndexOfSlash);
		return homeDir;
	}*/

	/*private String getHomeFolderForDevVersion() throws URISyntaxException {
		File configFile = null;
		File byteCodeFileOfThisClass = new File(Config.class.getResource("Config.class").toURI());
		//System.out.println("byteCodeFileOfThisClass: " + byteCodeFileOfThisClass);
		configFile = byteCodeFileOfThisClass.getParentFile().getParentFile().getParentFile().getParentFile();
		//System.out.println("configFile: " + configFile.toString());
		return configFile.toString();
	}*/

	//---------------------------------------------------------------
	// PUBLIC INSTANCE METHODS
	//---------------------------------------------------------------
	
	public double getLevelLogicalWidth() {
		return Double.parseDouble(this.properties.getProperty("logicalLevelWidth"));
	}

	public double getLevelLogicalHeight() {
		return Double.parseDouble(this.properties.getProperty("logicalLevelHeight"));
	}

	public int getDelayTime() {
		// We store the frames per second our application should have instead of the delay time in ms.
		return (int) (1000.0 / Double.parseDouble(this.properties.getProperty("framesPerSecond")));
	}
	
	public int getMaxIdNumber() {
		return Integer.parseInt(this.properties.getProperty("maxIdNumber"));
	}
	
	public int getBaseIdNumber() {
		return Integer.parseInt(this.properties.getProperty("baseIdNumber"));
	}
	
	public double getFPS() {
		return Double.parseDouble(this.properties.getProperty("framesPerSecond"));
	}
	
	public Color getDefaultOutlineColor() {
		return MathTools.colorFromHexString(this.properties.getProperty("defaultOutlineColor"));
	}

	public int getHighScoreNumber(int num) {
		return Integer.parseInt(this.properties.getProperty("hiscore" + num));
	}
	
	public String getPlayerNameNumber(int num) {
		return this.properties.getProperty("playername" + num);
	}
	
	public boolean soundIsEnabled() {
		return Boolean.parseBoolean(this.properties.getProperty("soundIsEnabled"));
	}
	
	public Color getBackgroundColor() {
		return MathTools.colorFromHexString(this.properties.getProperty("backgroundColor"));
	}
	
	public void setSoundEnabled(boolean enabled) {
		this.properties.setProperty("soundIsEnabled", "" + enabled);
		saveToFile();
	}
	
	
	public void setHighScoreNumber(int num, int value) {
		this.properties.setProperty("hiscore" + num, "" + value);
		saveToFile();
	}
	
	public void setPlayerNameNumber(int num, String name) {
		this.properties.setProperty("playername" + num, name);
		saveToFile();
	}
	
	
	
	
	
	private void saveToFile() {
		try {
			String comments = "It is highly recommended that you do not touch this file unless\r\n";
			comments += "you know what you are doing with it, or if your name is Riccardo.\r\n";
			comments += "Then in this case it's totally fine, go ahead and mess with this stuff.";
			this.properties.store(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(CONFIG_FILE), "ISO-8859-1")), comments);
		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	//---------------------------------------------------------------
	// STATIC METHODS
	//---------------------------------------------------------------
	public static Config getInstance() {
		if (instance == null)
			instance = new Config();
		return instance;
	}

	

} // end class
