package com.github.vitalliuss.io;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PageExtractor;

import com.github.vitalliuss.accounting.PaperFormat;

public class Printer {
	
	private static final Logger logger = Logger.getLogger(Printer.class);
	private boolean isDebugModeOn = false;
	private File pdfDocument;

	public Printer (File pdfDocument)
	{
		this.pdfDocument = pdfDocument;
	}
	
	public void print(PaperFormat paperFormat, int startPage, int endPage) throws PrinterException, IOException
	{
		PDDocument document = PDDocument.load(pdfDocument);
		PDDocument documentToPrint = new PDDocument();
		documentToPrint = extractPages(document, startPage, endPage);
		document.close();
		if (isDebugModeOn) {
			try {
				documentToPrint.save("documentToPrint.pdf");
				documentToPrint.close();
				
			} catch (COSVisitorException e) {
				logger.error("Can't save file " + pdfDocument.getAbsolutePath() + " : " + e.getMessage());
			}
			
		}
		else {
//			documentToPrint.silentPrint();
			documentToPrint.close();
		}
	}
		
	
	public PDDocument extractPages(PDDocument document, int startPage, int endPage) throws IOException
	{
		PageExtractor extractor = new PageExtractor(document, startPage, endPage);
		PDDocument printableDocument = extractor.extract();
		return printableDocument;
	}

}
