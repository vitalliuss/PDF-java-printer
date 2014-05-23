package com.github.vitalliuss.io;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

public class FileChooser
{
	private static final Logger logger = Logger.getLogger(FileChooser.class);
	private static String startDir = "D:\\";

	public FileChooser()
	{}

	public File openFile(Component component)
	{
		File file = new File("");
		//Create a file chooser
		final JFileChooser fc = new JFileChooser(startDir);
		fc.setFileFilter(new FileNameExtensionFilter("PDF (*.pdf)", "pdf"));
		//In response to a button click:
		int returnVal = fc.showOpenDialog(component);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			file = fc.getSelectedFile();
			logger.info("Opening '" + file.getName() + "'");
		}
		else
		{
			logger.info("Open command cancelled by user.");
		}

		return file;
	}

}
