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

import com.github.vitalliuss.io.FileChooser;

public class MainFrame extends JFrame
{
	private static final Logger logger = Logger.getLogger(MainFrame.class);
	private static File file;

	private boolean isPreviewModeOn = false;
	private int currentPageNumber = -1;

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
	private final JButton btnPreview = new JButton("Preview");
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
	private final JButton btnCalculatePrice = new JButton("Calculate Price");
	private final JLabel lblInsertedHint = new JLabel("Inserted:");
	private final JLabel labelInsertedAmount = new JLabel("");
	private final JLabel lblLeftHint = new JLabel("Left:");
	private final JLabel labelLeftAmount = new JLabel("");

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
		setBounds(100, 100, 1280, 1024);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		btnBrowseFiles.setBounds(10, 11, 112, 23);
		btnBrowseFiles.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				FileChooser fc = new FileChooser();
				setFile(fc.openFile(btnBrowseFiles));
			}
		});
		contentPane.setLayout(null);

		contentPane.add(btnBrowseFiles);
		lblSelectFormat.setBounds(10, 260, 112, 14);

		contentPane.add(lblSelectFormat);
		rdbtnA4.setBounds(10, 285, 57, 23);

		contentPane.add(rdbtnA4);
		btnPreview.setBounds(10, 99, 112, 23);
		btnPreview.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (getFile() != null)
				{
					try
					{
						isPreviewModeOn = true;
						PDFViewer pdfViewer = new PDFViewer(getFile());

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
				else
				{
					logger.error("Please select file first!");
				}
			}
		});
		rdbtnA3.setBounds(69, 285, 53, 23);

		contentPane.add(rdbtnA3);

		contentPane.add(btnPreview);
		btnPrint.setBounds(10, 484, 112, 23);

		contentPane.add(btnPrint);
		picture1.setBounds(146, 11, 540, 923);

		contentPane.add(picture1);
		picture2.setBounds(714, 11, 540, 923);

		contentPane.add(picture2);
		buttonPreviousPage.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				try
				{
					currentPageNumber--;
					PDFViewer pdfViewer = new PDFViewer(getFile());
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
					PDFViewer pdfViewer = new PDFViewer(getFile());
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
		comboBoxStartPage.setBounds(10, 158, 112, 20);
		comboBoxStartPage.addItem("First page");
		comboBoxStartPage.addItem("1");
		comboBoxStartPage.addItem("2");

		contentPane.add(comboBoxStartPage);
		lblPrintFromPage.setBounds(10, 133, 112, 14);

		contentPane.add(lblPrintFromPage);
		lblPrintToPage.setBounds(10, 197, 112, 14);

		contentPane.add(lblPrintToPage);
		comboBoxEndPage.setToolTipText("Last page");
		comboBoxEndPage.setBounds(10, 222, 112, 20);
		comboBoxEndPage.addItem("Last page");
		comboBoxEndPage.addItem("1");
		comboBoxEndPage.addItem("2");

		contentPane.add(comboBoxEndPage);
		lblTotalHint.setBounds(10, 379, 46, 14);

		contentPane.add(lblTotalHint);
		labelTotalAmount.setBounds(76, 379, 46, 14);

		contentPane.add(labelTotalAmount);
		btnCalculatePrice.setBounds(10, 334, 112, 23);

		contentPane.add(btnCalculatePrice);
		lblInsertedHint.setBounds(10, 404, 46, 14);

		contentPane.add(lblInsertedHint);
		labelInsertedAmount.setBounds(76, 404, 46, 14);

		contentPane.add(labelInsertedAmount);
		lblLeftHint.setBounds(10, 429, 46, 14);

		contentPane.add(lblLeftHint);
		labelLeftAmount.setBounds(76, 429, 46, 14);

		contentPane.add(labelLeftAmount);
	}
}
