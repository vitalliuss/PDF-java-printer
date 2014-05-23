package com.github.vitalliuss;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.github.vitalliuss.ui.MainFrame;

public class Main
{
	private static final Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args)
	{
		initLogger();
		initGUI();
	}

	private static void initGUI()
	{
		MainFrame.run(null);
	}

	private static void initLogger()
	{
		BasicConfigurator.configure();
		logger.info("Logger initialized");
	}
}
