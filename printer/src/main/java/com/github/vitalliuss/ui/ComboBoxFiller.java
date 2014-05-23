package com.github.vitalliuss.ui;

import javax.swing.JComboBox;

public class ComboBoxFiller {
	
	public static JComboBox addComboBoxItems(JComboBox combobox, int maxItem)
	{
		for (int i =0; i<maxItem;i++){
			combobox.addItem(String.valueOf(i+1));
		}
		return combobox;
	}
	public static JComboBox initComboBox(JComboBox combobox, String firstItemText, int maxItem)
	{
		combobox.addItem(firstItemText);
		addComboBoxItems(combobox, maxItem);
		return combobox;
	}
	
	public static int getComboBoxIntegerValue(String stringValue, int maxValue)
	{
		if (stringValue == null )
		{
			return 0;
		}
		if (stringValue.equalsIgnoreCase("First page")) 
		{
			return 1;
		}
		if (stringValue.equalsIgnoreCase("Last page"))
		{
			return maxValue;
		}
		else return Integer.valueOf(stringValue);
	}

}
