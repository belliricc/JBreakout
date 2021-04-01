package jbreakout.controller;

import jbreakout.util.Config;

public class Main {

	public static void main(String[] args) {
		Config.getInstance();
		ControllerForView.getInstance().openStartWindow();
	}

}