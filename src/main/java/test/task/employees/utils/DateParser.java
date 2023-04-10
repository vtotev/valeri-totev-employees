package test.task.employees.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateParser {

    private static final String[] dateFormats =
            {
                    "yyyy-MM-dd",
                    "yyyy/MM/dd",
                    "yyyyMMdd",
                    "dd/MM/yy",
                    "dd/MM/yyyy",
                    "dd-MM-yyyy",
                    "MM-dd-yyyy",
                    "dd MMMM yyyy",
                    "dd MMMM, yyyy",
                    "MMMM dd yyyy",
                    "MMMM dd, yyyy",

                    "dd-MM-yyyy HH:mm:ssZ",
                    "dd-MM-yyyy HH:mm:ss.SSSzzzz",
                    "dd-MM-yyyy HH:mm:sszzzz",
                    "dd-MM-yyyy HH:mm:ss z",
                    "dd-MM-yyyy HH:mm:ssz",
                    "dd-MM-yyyy HH:mm:ss",
                    "dd-MM-yyyy HHmmss.SSSz",
                    "dd/MM/yyyy HH:mm:ssZ",
                    "dd/MM/yyyy HH:mm:ss.SSSzzzz",
                    "dd/MM/yyyy HH:mm:sszzzz",
                    "dd/MM/yyyy HH:mm:ss z",
                    "dd/MM/yyyy HH:mm:ssz",
                    "dd/MM/yyyy HH:mm:ss",
                    "dd/MM/yyyy HHmmss.SSSz",

                    "MM-dd-yyyy HH:mm:ssZ",
                    "MM-dd-yyyy HH:mm:ss.SSSzzzz",
                    "MM-dd-yyyy HH:mm:sszzzz",
                    "MM-dd-yyyy HH:mm:ss z",
                    "MM-dd-yyyy HH:mm:ssz",
                    "MM-dd-yyyy HH:mm:ss",
                    "MM-dd-yyyy HHmmss.SSSz",
                    "MM/dd/yyyy HH:mm:ssZ",
                    "MM/dd/yyyy HH:mm:ss.SSSzzzz",
                    "MM/dd/yyyy HH:mm:sszzzz",
                    "MM/dd/yyyy HH:mm:ss z",
                    "MM/dd/yyyy HH:mm:ssz",
                    "MM/dd/yyyy HH:mm:ss",
                    "MM/dd/yyyy HHmmss.SSSz",

                    "yyyy-MM-dd HH:mm:ssZ",
                    "yyyy-MM-dd HH:mm:ss.SSSzzzz",
                    "yyyy-MM-dd HH:mm:sszzzz",
                    "yyyy-MM-dd HH:mm:ss z",
                    "yyyy-MM-dd HH:mm:ssz",
                    "yyyy-MM-dd HH:mm:ss",
                    "yyyy-MM-dd HHmmss.SSSz",
                    "yyyy/MM/dd HH:mm:ssZ",
                    "yyyy/MM/dd HH:mm:ss.SSSzzzz",
                    "yyyy/MM/dd HH:mm:sszzzz",
                    "yyyy/MM/dd HH:mm:ss z",
                    "yyyy/MM/dd HH:mm:ssz",
                    "yyyy/MM/dd HH:mm:ss",
                    "yyyy/MM/dd HHmmss.SSSz",

                    "MMMM-dd-yyyy HH:mm:ssZ",
                    "MMMM-dd-yyyy HH:mm:ss.SSSzzzz",
                    "MMMM-dd-yyyy HH:mm:sszzzz",
                    "MMMM-dd-yyyy HH:mm:ss z",
                    "MMMM-dd-yyyy HH:mm:ssz",
                    "MMMM-dd-yyyy HH:mm:ss",
                    "MMMM-dd-yyyy HHmmss.SSSz",
                    "MMMM/dd/yyyy HH:mm:ssZ",
                    "MMMM/dd/yyyy HH:mm:ss.SSSzzzz",
                    "MMMM/dd/yyyy HH:mm:sszzzz",
                    "MMMM/dd/yyyy HH:mm:ss z",
                    "MMMM/dd/yyyy HH:mm:ssz",
                    "MMMM/dd/yyyy HH:mm:ss",
                    "MMMM/dd/yyyy HHmmss.SSSz",

                    "yyyy-MMMM-dd HH:mm:ssZ",
                    "yyyy-MMMM-dd HH:mm:ss.SSSzzzz",
                    "yyyy-MMMM-dd HH:mm:sszzzz",
                    "yyyy-MMMM-dd HH:mm:ss z",
                    "yyyy-MMMM-dd HH:mm:ssz",
                    "yyyy-MMMM-dd HH:mm:ss",
                    "yyyy-MMMM-dd HHmmss.SSSz",
                    "yyyy/MMMM/dd HH:mm:ssZ",
                    "yyyy/MMMM/dd HH:mm:ss.SSSzzzz",
                    "yyyy/MMMM/dd HH:mm:sszzzz",
                    "yyyy/MMMM/dd HH:mm:ss z",
                    "yyyy/MMMM/dd HH:mm:ssz",
                    "yyyy/MMMM/dd HH:mm:ss",
                    "yyyy/MMMM/dd HHmmss.SSSz",

                    "MMMM dd yyyy HH:mm:ssZ",
                    "MMMM dd yyyy HH:mm:ss.SSSzzzz",
                    "MMMM dd yyyy HH:mm:sszzzz",
                    "MMMM dd yyyy HH:mm:ss z",
                    "MMMM dd yyyy HH:mm:ssz",
                    "MMMM dd yyyy HH:mm:ss",
                    "MMMM dd yyyy HHmmss.SSSz",
                    "MMMM dd yyyy HH:mm:ssZ",
                    "MMMM dd yyyy HH:mm:ss.SSSzzzz",
                    "MMMM dd yyyy HH:mm:sszzzz",
                    "MMMM dd yyyy HH:mm:ss z",
                    "MMMM dd yyyy HH:mm:ssz",
                    "MMMM dd yyyy HH:mm:ss",
                    "MMMM dd yyyy HHmmss.SSSz",

                    "yyyy MMMM dd HH:mm:ssZ",
                    "yyyy MMMM dd HH:mm:ss.SSSzzzz",
                    "yyyy MMMM dd HH:mm:sszzzz",
                    "yyyy MMMM dd HH:mm:ss z",
                    "yyyy MMMM dd HH:mm:ssz",
                    "yyyy MMMM dd HH:mm:ss",
                    "yyyy MMMM dd HHmmss.SSSz",
                    "yyyy MMMM dd HH:mm:ssZ",
                    "yyyy MMMM dd HH:mm:ss.SSSzzzz",
                    "yyyy MMMM dd HH:mm:sszzzz",
                    "yyyy MMMM dd HH:mm:ss z",
                    "yyyy MMMM dd HH:mm:ssz",
                    "yyyy MMMM dd HH:mm:ss",
                    "yyyy MMMM dd HHmmss.SSSz",

                    "MMMM dd, yyyy HH:mm:ssZ",
                    "MMMM dd, yyyy HH:mm:ss.SSSzzzz",
                    "MMMM dd, yyyy HH:mm:sszzzz",
                    "MMMM dd, yyyy HH:mm:ss z",
                    "MMMM dd, yyyy HH:mm:ssz",
                    "MMMM dd, yyyy HH:mm:ss",
                    "MMMM dd, yyyy HHmmss.SSSz",
                    "MMMM dd, yyyy HH:mm:ssZ",
                    "MMMM dd, yyyy HH:mm:ss.SSSzzzz",
                    "MMMM dd, yyyy HH:mm:sszzzz",
                    "MMMM dd, yyyy HH:mm:ss z",
                    "MMMM dd, yyyy HH:mm:ssz",
                    "MMMM dd, yyyy HH:mm:ss",
                    "MMMM dd, yyyy HHmmss.SSSz",

                    "yyyy MMMM dd, HH:mm:ssZ",
                    "yyyy MMMM dd, HH:mm:ss.SSSzzzz",
                    "yyyy MMMM dd, HH:mm:sszzzz",
                    "yyyy MMMM dd, HH:mm:ss z",
                    "yyyy MMMM dd, HH:mm:ssz",
                    "yyyy MMMM dd, HH:mm:ss",
                    "yyyy MMMM dd, HHmmss.SSSz",
                    "yyyy MMMM dd, HH:mm:ssZ",
                    "yyyy MMMM dd, HH:mm:ss.SSSzzzz",
                    "yyyy MMMM dd, HH:mm:sszzzz",
                    "yyyy MMMM dd, HH:mm:ss z",
                    "yyyy MMMM dd, HH:mm:ssz",
                    "yyyy MMMM dd, HH:mm:ss",
                    "yyyy MMMM dd, HHmmss.SSSz",

                    "dd MMMM yyyy HH:mm:ssZ",
                    "dd MMMM yyyy HH:mm:ss.SSSzzzz",
                    "dd MMMM yyyy HH:mm:sszzzz",
                    "dd MMMM yyyy HH:mm:ss z",
                    "dd MMMM yyyy HH:mm:ssz",
                    "dd MMMM yyyy HH:mm:ss",
                    "dd MMMM yyyy HHmmss.SSSz",
                    "dd MMMM yyyy HH:mm:ssZ",
                    "dd MMMM yyyy HH:mm:ss.SSSzzzz",
                    "dd MMMM yyyy HH:mm:sszzzz",
                    "dd MMMM yyyy HH:mm:ss z",
                    "dd MMMM yyyy HH:mm:ssz",
                    "dd MMMM yyyy HH:mm:ss",
                    "dd MMMM yyyy HHmmss.SSSz"
            };

    public static LocalDate parseDate(String inputDate) {

        for (String dateFormat : dateFormats) {
            try {
                // parse any of the above date formats and parse it to yyyy-MM-dd output format
                LocalDate date = LocalDate.from(DateTimeFormatter.ofPattern(dateFormat).withLocale(Locale.ENGLISH).parse(inputDate));
                date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                return date;
            } catch (Exception ignored) {
            }
        }

        return null;
    }

}
