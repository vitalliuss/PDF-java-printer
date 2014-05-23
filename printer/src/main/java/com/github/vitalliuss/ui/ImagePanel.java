package com.github.vitalliuss.ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel
{

	private final Image image;

	public Image getImage()
	{
		return image;
	}

	public ImagePanel(Image image)
	{
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
	}

}
