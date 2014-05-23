package com.github.vitalliuss.ui;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFImageWriter;

public class PDFViewer
{
	private static final Logger logger = Logger.getLogger(PDFViewer.class);
	private final File pdfFile;

	private final int PREVIEW_WIDTH = 560;
	private final int PREVIEW_HIGHT = 800;

	private final double ASPECT_RATIO = Math.sqrt(2);

	public PDFViewer(File pdfFile)
	{
		this.pdfFile = pdfFile;
	}

	public ImagePanel previewFile(int startPage, int endPage, String outputName) throws IOException
	{
		logger.info("Opening file in a preview pane: '" + pdfFile.getName() + "'");
		PDDocument pdfDocument = PDDocument.load(pdfFile);
		logger.info("Found PDF with pages count: " + pdfDocument.getNumberOfPages());

		PDFImageWriter imageWriter = new PDFImageWriter();
		String imageFormat = "jpg";
		String password = "";
		int imageType = BufferedImage.TYPE_INT_RGB;
		int resolution = 96;
		boolean result =
				imageWriter.writeImage(pdfDocument, imageFormat, password, startPage, endPage, outputName, imageType,
						resolution);

		BufferedImage myPicture = ImageIO.read(new File("test1.jpg"));
		//		Image cropedImage = cropImage(myPicture, new Rectangle(240, 320)); works
		Image cropedImage =
				getScaledImage(myPicture, new Rectangle(PREVIEW_WIDTH, (int) (PREVIEW_WIDTH * ASPECT_RATIO)));
		ImagePanel picLabel = new ImagePanel(cropedImage);

		pdfDocument.close();

		return picLabel;
	}

	public Image getPDFPageImage(int pageNumber) throws IOException
	{
		logger.info("Opening file: '" + pdfFile.getName() + "'");
		PDDocument pdfDocument = PDDocument.load(pdfFile);
		logger.info("Found PDF with pages count: " + pdfDocument.getNumberOfPages());

		PDFImageWriter imageWriter = new PDFImageWriter();
		String imageFormat = "jpg";
		String password = "";
		int imageType = BufferedImage.TYPE_INT_RGB;
		int resolution = 96;

		String tempImageName = "tempImage";
		String postfix = String.valueOf(pageNumber);
		String outputName = tempImageName.concat(postfix);
		boolean result =
				imageWriter.writeImage(pdfDocument, imageFormat, password, pageNumber, pageNumber, tempImageName,
						imageType, resolution);

		BufferedImage pagePicture = ImageIO.read(new File(outputName.concat(".").concat(imageFormat)));
		Image cropedImage =
				getScaledImage(pagePicture, new Rectangle(PREVIEW_WIDTH, (int) (PREVIEW_WIDTH * ASPECT_RATIO)));
		pdfDocument.close();
		return cropedImage;
	}

	private Image getScaledImage(BufferedImage src, Rectangle rect)
	{
		Image dest = src.getScaledInstance(rect.width, rect.height, Image.SCALE_SMOOTH);
		return dest;
	}

}
