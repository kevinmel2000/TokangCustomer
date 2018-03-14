package com.tokang.customer.utils;

import android.content.Context;

import com.tokang.customer.R;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by royli on 2/16/2018.
 */

public class FormatUtils {
    public static String currencyFormat(BigInteger number){
        DecimalFormat currencyIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatIDR = new DecimalFormatSymbols();

        formatIDR.setCurrencySymbol("Rp. ");
        formatIDR.setMonetaryDecimalSeparator(',');
        formatIDR.setGroupingSeparator('.');

        currencyIndonesia.setDecimalFormatSymbols(formatIDR);

        return currencyIndonesia.format(number).replace(",00","");
    }

    public static String currencyFormat(BigDecimal number){
        DecimalFormat currencyIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatIDR = new DecimalFormatSymbols();

        formatIDR.setCurrencySymbol("Rp. ");
        formatIDR.setMonetaryDecimalSeparator(',');
        formatIDR.setGroupingSeparator('.');

        currencyIndonesia.setDecimalFormatSymbols(formatIDR);

        return currencyIndonesia.format(number);
    }

    public static String truncateFormat(BigDecimal number, Context context) {
        BigDecimal thousand = new BigDecimal("1000");
        BigDecimal million = new BigDecimal("1000000");
        BigDecimal billion = new BigDecimal("1000000000");
        BigDecimal trillion = new BigDecimal("1000000000000");

        String output, unit;
        BigDecimal num2;
        if(number.compareTo(thousand)== -1){
            output = currencyFormat(number);
        } else {
            if(number.compareTo(million) == -1){
                num2 = thousand;
                unit = " "+ context.getResources().getString(R.string.unit_thousand);
            } else if(number.compareTo(billion) == -1){
                num2 = million;
                unit = " "+ context.getResources().getString(R.string.unit_million);
            } else if(number.compareTo(trillion) ==  -1){
                num2 = billion;
                unit = " "+ context.getResources().getString(R.string.unit_billion);
            } else {
                num2 = trillion;
                unit = " "+ context.getResources().getString(R.string.unit_trillion);
            }

            BigDecimal res = number.divide(num2);
            output = currencyFormat(res)+unit;
        }

        return output;
    }
}
