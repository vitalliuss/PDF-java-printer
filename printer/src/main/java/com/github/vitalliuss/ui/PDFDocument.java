package com.github.vitalliuss.ui;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDFDocument
{
	private static final Logger logger = Logger.getLogger(PDFDocument.class);
	private final File pdfFile;

	private final int PREVIEW_WIDTH = 560;
	private final int PREVIEW_HIGHT = 800;

	private final double ASPECT_RATIO = Math.sqrt(2);

	public PDFDocument(File pdfFile)
	{
		this.pdfFile = pdfFile;
	}

	public Image getPDFPageImage(int pageNumber) throws IOException
	{
		PDDocument pdfDocument = PDDocument.load(pdfFile);
		@SuppressWarnings("unchecked")
		List<PDPage>pages =  pdfDocument.getDocumentCatalog().getAllPages();
		PDPage page = pages.get(pageNumber - 1);
		Rectangle cropSize = new Rectangle(PREVIEW_WIDTH, (int) (PREVIEW_WIDTH * ASPECT_RATIO));
		Image image = getScaledImage(page.convertToImage(), cropSize);
		pdfDocument.close();
		return image;
	}

	private Image getScaledImage(BufferedImage src, Rectangle rect)
	{
		Image dest = src.getScaledInstance(rect.width, rect.height, Image.SCALE_SMOOTH);
		return dest;
	}
	
	public static int getPDFDocumentPagesCount(File pdfFile)
	{
		int count = 0;
		try {
			PDDocument pdfDocument = PDDocument.load(pdfFile);
			count = pdfDocument.getNumberOfPages();
		} catch (IOException e) {
			logger.error("Can't read PDF document: " + e.getMessage());
		}
		return count;
		
	}

}
