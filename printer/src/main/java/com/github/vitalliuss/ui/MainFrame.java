package com.github.vitalliuss.ui;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import com.github.vitalliuss.accounting.PaperFormat;
import com.github.vitalliuss.accounting.PriceCalculator;
import com.github.vitalliuss.io.FileChooser;
import com.github.vitalliuss.io.Printer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;

public class MainFrame extends JFrame
{
	private static final Logger logger = Logger.getLogger(MainFrame.class);
	private static File file;

	private boolean showWindowButtons = false;
	private int currentPageNumber = -1;
	private int totalPagesInPDFFile = 0;
	private int pagesToPrintCount = 0;
	private int totalPrice = 0;
	private int printStartPage = 0;
	private int printEndPage = 0;
	private PaperFormat printPaperFormat = PaperFormat.A4;

	public static File getFile()
	{
		return file;
	}

	public static void setFile(File file)
	{
		MainFrame.file = file;
	}

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JButton btnBrowseFiles = new JButton("Browse files");
	private final JRadioButton rdbtnA3 = new JRadioButton("A3");
	private final JRadioButton rdbtnA4 = new JRadioButton("A4");
	private final JButton btnPrint = new JButton("Print!");
	private final JLabel lblSelectFormat = new JLabel("Select format");
	private final static JLabel picture1 = new JLabel("");
	private final static JLabel picture2 = new JLabel("");
	private final JButton buttonPreviousPage = new JButton("<- Previous");
	private final JButton buttonNextPage = new JButton("Next ->");
	private final JComboBox comboBoxStartPage = new JComboBox();
	private final JLabel lblPrintFromPage = new JLabel("Print from page");
	private final JLabel lblPrintToPage = new JLabel("Print to page");
	private final JComboBox comboBoxEndPage = new JComboBox();
	private final JLabel lblTotalHint = new JLabel("Total:");
	private final JLabel labelTotalAmount = new JLabel("");
	private final JLabel lblInsertedHint = new JLabel("Inserted:");
	private final JLabel labelInsertedAmount = new JLabel("");
	private final JLabel lblLeftHint = new JLabel("Left:");
	private final JLabel labelLeftAmount = new JLabel("");
	private final JLabel lblFileName = new JLabel("");
	private final JLabel lblPagesSelectedHint = new JLabel("Pages selected:");
	private final JLabel lblPagesSelected = new JLabel("");

	/**
	 * Launch the application.
	 */
	public static void run(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
					logger.info("GUI started");
				}
				catch (Exception e)
				{
					logger.error("GUI failed: " + e.getMessage());
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame()
	{
		initGUI();
	}

	private void initGUI()
	{
		setTitle("Printer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(showWindowButtons);
		setBounds(100, 100, 1280, 1024);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		btnBrowseFiles.setBounds(10, 11, 124, 23);
		btnBrowseFiles.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				FileChooser fc = new FileChooser();
				File file = fc.openFile(btnBrowseFiles);
				setFile(file);
				String filePath = file.getAbsolutePath();
				totalPagesInPDFFile = PDFDocument.getPDFDocumentPagesCount(file);
				lblFileName.setText(filePath);
				previewLoadedFile(file);
				initPrintRangeComboBoxes(totalPagesInPDFFile);
				calculatePagesToPrint(totalPagesInPDFFile);
				
			}
		});
		contentPane.setLayout(null);

		contentPane.add(btnBrowseFiles);
		lblSelectFormat.setBounds(10, 323, 112, 14);

		contentPane.add(lblSelectFormat);
		rdbtnA4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnA3.setSelected(false);
				totalPrice = PriceCalculator.calculatePrice(pagesToPrintCount, PaperFormat.A4);
				setPriceLabelsValues(totalPrice);
			}
		});
		rdbtnA4.setBounds(10, 348, 57, 23);

		contentPane.add(rdbtnA4);
		rdbtnA3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnA4.setSelected(false);
				totalPrice = PriceCalculator.calculatePrice(pagesToPrintCount, PaperFormat.A3);
				setPriceLabelsValues(totalPrice);
			}
		});
		rdbtnA3.setBounds(69, 348, 53, 23);

		contentPane.add(rdbtnA3);
		btnPrint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Printer printer = new Printer(getFile());
				try {
					printer.print(printPaperFormat, printStartPage, printEndPage);
				} catch (PrinterException e) {
					logger.error("Printer error, can't print file: " + e.getMessage());
				} catch (IOException e) {
					logger.error("I/O error, can't print file: " + e.getMessage());
				}
			}
		});
		btnPrint.setBounds(10, 547, 124, 23);

		contentPane.add(btnPrint);
		picture1.setBounds(169, 58, 540, 876);

		contentPane.add(picture1);
		picture2.setBounds(714, 58, 540, 876);

		contentPane.add(picture2);
		buttonPreviousPage.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				try
				{
					currentPageNumber--;
					PDFDocument pdfViewer = new PDFDocument(getFile());
					MainFrame.picture1.setIcon(new ImageIcon(pdfViewer.getPDFPageImage(currentPageNumber)));
					MainFrame.picture2.setIcon(new ImageIcon(pdfViewer.getPDFPageImage(currentPageNumber + 1)));
					logger.info("Prevoius page is opened");
				}
				catch (IOException e1)
				{
					logger.error("Can't go to prevoius page: " + e1.getMessage());
				}

			}
		});
		buttonPreviousPage.setBounds(615, 945, 89, 23);

		contentPane.add(buttonPreviousPage);
		buttonNextPage.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				try
				{
					currentPageNumber++;
					PDFDocument pdfViewer = new PDFDocument(getFile());
					MainFrame.picture1.setIcon(new ImageIcon(pdfViewer.getPDFPageImage(currentPageNumber)));
					MainFrame.picture2.setIcon(new ImageIcon(pdfViewer.getPDFPageImage(currentPageNumber + 1)));
					logger.info("Next page is opened");
				}
				catch (IOException e1)
				{
					logger.error("Can't go to prevoius page: " + e1.getMessage());
				}
			}
		});
		buttonNextPage.setBounds(724, 945, 89, 23);

		contentPane.add(buttonNextPage);
		
		comboBoxStartPage.setToolTipText("Start page");
		comboBoxStartPage.setBounds(10, 158, 124, 20);
		contentPane.add(comboBoxStartPage);
		comboBoxStartPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				calculatePagesToPrint(totalPagesInPDFFile);
			}
		});

		
		lblPrintFromPage.setBounds(10, 133, 112, 14);

		contentPane.add(lblPrintFromPage);
		lblPrintToPage.setBounds(10, 197, 112, 14);

		contentPane.add(lblPrintToPage);
		comboBoxEndPage.setToolTipText("Last page");
		comboBoxEndPage.setBounds(10, 222, 124, 20);
		contentPane.add(comboBoxEndPage);
		comboBoxEndPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				calculatePagesToPrint(totalPagesInPDFFile);
			}
		});

		
		lblTotalHint.setBounds(10, 442, 68, 14);

		contentPane.add(lblTotalHint);
		labelTotalAmount.setBounds(88, 442, 46, 14);

		contentPane.add(labelTotalAmount);
		lblInsertedHint.setBounds(10, 467, 68, 14);

		contentPane.add(lblInsertedHint);
		labelInsertedAmount.setBounds(88, 467, 46, 14);

		contentPane.add(labelInsertedAmount);
		lblLeftHint.setBounds(10, 492, 68, 14);

		contentPane.add(lblLeftHint);
		labelLeftAmount.setBounds(88, 492, 46, 14);

		contentPane.add(labelLeftAmount);
		lblFileName.setBounds(146, 15, 339, 14);
		
		contentPane.add(lblFileName);
		lblPagesSelectedHint.setBounds(10, 253, 112, 14);
		
		contentPane.add(lblPagesSelectedHint);
		lblPagesSelected.setBounds(10, 275, 46, 14);
		
		contentPane.add(lblPagesSelected);
	}
	
	protected void setPriceLabelsValues(int totalPrice2) {
		labelTotalAmount.setText(String.valueOf(totalPrice));
	}

	protected void calculatePagesToPrint(int pages) {
        String comboBoxStartPageText = (String)comboBoxStartPage.getSelectedItem();
        String comboBoxEndPageText = (String)comboBoxEndPage.getSelectedItem();
        int firstPageNumber = ComboBoxFiller.getComboBoxIntegerValue(comboBoxStartPageText, pages);
		int lastPageNumber = ComboBoxFiller.getComboBoxIntegerValue(comboBoxEndPageText, pages);
		int difference = 0;
		if (firstPageNumber <= lastPageNumber)
		{
			difference = lastPageNumber - firstPageNumber + 1;
			printStartPage = firstPageNumber;
			printEndPage = lastPageNumber;
		}
		else
		{
			difference = firstPageNumber - lastPageNumber + 1;
			printStartPage = lastPageNumber;
			printEndPage = firstPageNumber;
		}
		
		pagesToPrintCount = difference;
		lblPagesSelected.setText(String.valueOf(difference));
        
		
	}

	protected void initPrintRangeComboBoxes(int pages) {
		logger.info(String.valueOf(pages));
		ComboBoxFiller.initComboBox(comboBoxStartPage, "First page", pages);
		ComboBoxFiller.initComboBox(comboBoxEndPage, "Last page", pages);
	}

	private void previewLoadedFile(File file)
	{
		try
		{
			PDFDocument pdfViewer = new PDFDocument(file);

			MainFrame.picture1.setIcon(new ImageIcon(pdfViewer.getPDFPageImage(1)));
			MainFrame.picture2.setIcon(new ImageIcon(pdfViewer.getPDFPageImage(2)));
			currentPageNumber = 1;

			setVisible(true);
		}
		catch (IOException e1)
		{
			logger.error("Exception while previewing file: " + e1.getMessage());
		}
	}
	
}
