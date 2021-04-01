package jbreakout.controller;

public class ControllerForModel implements IControllerForModel {
	
	private static ControllerForModel instance = null;

	private ControllerForModel() {
	}

	public IControllerForModel getInstance() {
		if (instance == null)
			instance = new ControllerForModel();
		return instance;
	}
	
}