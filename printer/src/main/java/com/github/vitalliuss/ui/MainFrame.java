package com.github.vitalliuss;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class MainFrame extends JFrame
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JButton btnBrowseFiles = new JButton("Browse files");
	private final JRadioButton rdbtnA = new JRadioButton("A3");
	private final JRadioButton rdbtnA_1 = new JRadioButton("A4");
	private final JButton btnPrint = new JButton("Print!");
	private final JButton btnPreview = new JButton("Preview");
	private final JLabel lblSelectFormat = new JLabel("Select format");

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
				}
				catch (Exception e)
				{
					e.printStackTrace();
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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("89px"), ColumnSpec.decode("59px"), FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { RowSpec.decode("114px"), RowSpec.decode("23px"), FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		contentPane.add(btnBrowseFiles, "2, 2, left, center");

		contentPane.add(lblSelectFormat, "2, 4");

		contentPane.add(rdbtnA_1, "3, 4");

		contentPane.add(rdbtnA, "3, 6");

		contentPane.add(btnPreview, "2, 8");

		contentPane.add(btnPrint, "2, 10");
	}
}
