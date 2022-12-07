package org.softwareengine.utils.service;

import java.text.DecimalFormat;

public class ArabicNumberToWords {

    private String s ;
    public ArabicNumberToWords(String s) {
//        double d = 56421.123;

       this.s = s ;

//        System.out.println(ArabicNumberToWords.convert(i) + " " + currency + " " + " و " + newS2 + "/1000" + " " + fCurrency);



        /*

        System.out.println("*** " + EnglishNumberToWords.convert(123456789));
        System.out.println("*** " + EnglishNumberToWords.convert(2147483647));
        System.out.println("*** " + EnglishNumberToWords.convert(3000000010L));

        /*

         *
         */


    }
    public String get() {
        long i;
        //String s = Double.toString(d);
        String newS;

        int mid = s.lastIndexOf(".");
        newS = s.substring(0, mid);
        String newS2 = s.substring(mid + 1, s.length());
        s = s.substring(0, newS.length());
        i = Long.parseLong(s);

        System.out.println(s);
        String currency = "دينار ";
        String fCurrency = "درهم";
        return ArabicNumberToWords.convert(i) + " " + currency + " " + " و " + newS2 + "/1000" + " " + fCurrency ;
    }
    private static final String[] tensNames = {
            "",
            " عشر",
            " عشرون",
            " ثلاثون",
            " أربعون",
            " خمسون",
            " ستون",
            " سبعون",
            " ثمانون",
            " تسعون"
    };
    private static final String[] numNames = {
            "",
            " واحد",
            " اثنان",
            " ثلاث",
            " أربع",
            " خمس",
            " ست",
            " سبع",
            " ثمان",
            " تسع",
            " عشر",
            " إحدى عشر",
            " إثنا عشر",
            " ثلاثة عشر",
            " أربعة عشر",
            " خمسة عشر",
            " ستة عشر",
            " سبعة عشر",
            " ثمانية عشر",
            " تسعة عشر"
    };

    private static String convertLessThanOneThousand(int number) {
        String soFar;

        if (number % 100 < 20) {
            soFar = numNames[number % 100];
            number /= 100;
        } else {
            soFar = numNames[number % 10];
            number /= 10;

            soFar = soFar + " و " + tensNames[number % 10]; //
            number /= 10;
        }
        if (number == 0) {
            return soFar;
        }
        return numNames[number] + "مائة" + soFar;
    }

    public static String convert(long number) {
        // 0 to 999 999 999 999
        if (number == 0) {
            return "صفر";
        }

        String snumber = Long.toString(number);

        // pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0, 3));
        // nnnXXXnnnnnn
        int millions = Integer.parseInt(snumber.substring(3, 6));
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9, 12));

        String tradBillions;
        switch (billions) {
            case 0:
                tradBillions = "";
                break;
            case 1:
                tradBillions = convertLessThanOneThousand(billions)
                        + " بليون ";
                break;
            default:
                tradBillions = convertLessThanOneThousand(billions)
                        + " بليون ";
        }
        String result = tradBillions;

        String tradMillions;
        switch (millions) {
            case 0:
                tradMillions = "";
                break;
            case 1:
                tradMillions = convertLessThanOneThousand(millions)
                        + " مليون ";
                break;
            default:
                tradMillions = convertLessThanOneThousand(millions)
                        + " مليون ";
        }
        result = result + tradMillions;

        String tradHundredThousands;
        switch (hundredThousands) {
            case 0:
                tradHundredThousands = "";
                break;
            case 1:
                tradHundredThousands = "الف ";
                break;
            default:
                tradHundredThousands = convertLessThanOneThousand(hundredThousands)
                        + " الف ";
        }
        result = result + tradHundredThousands;

        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result = result + tradThousand;

        // remove extra spaces!
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }

    /**
     * testing
     * @param      */
    long convertFromDouble(Double d) {
        long i;
        String s = Double.toString(d);
        String newS;

        int mid = s.lastIndexOf(".");
        newS = s.substring(0, mid);

        i = Long.parseLong(s);

        return i;

    }


}