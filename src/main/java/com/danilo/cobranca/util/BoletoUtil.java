package com.danilo.cobranca.util;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class BoletoUtil {
	
	public static String formatarDataPadraoLocalDate(String data) {
		String[] dataSplit = data.split("/");
		String dataFormatada = "";
		for (int i = dataSplit.length - 1; i >= 0; i--)
			dataFormatada += dataSplit[i] + "-";
        return dataFormatada.replaceFirst(".$", "");
	}
	
	public static String formatarDataPadraoNormal(LocalDate date) {
		String[] dataSplit = date.toString().split("-");
		return dataSplit[2] + "/" + dataSplit[1] + "/" + dataSplit[0];
	}
	
	public static String formatarValorParaReal(String valor) {
		double valorDouble = Double.valueOf(valor);
		DecimalFormat df = new DecimalFormat("#,##0.00");
		return df.format(valorDouble).toString();
	}

}
