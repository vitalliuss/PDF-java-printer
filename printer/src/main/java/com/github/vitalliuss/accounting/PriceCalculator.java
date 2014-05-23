package com.github.vitalliuss.accounting;

public class PriceCalculator {
	
	
	public static int calculatePrice(int pagesToPrintCount, PaperFormat paperFormat)
	{
		switch (paperFormat)
		{
			case A4 : return pagesToPrintCount * Price.PRICE_A4;
			case A3 : return pagesToPrintCount * Price.PRICE_A3;
			default : return pagesToPrintCount * Price.PRICE_A3;
		}
	}

}
