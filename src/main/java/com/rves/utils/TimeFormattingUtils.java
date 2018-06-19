package com.rves.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeFormattingUtils {

    public static final SimpleDateFormat INPUT_TYPE_DATE_TIME_LOCAL_FORMATTER =  new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static Timestamp parseTimestamp(SimpleDateFormat formatter, String stringDate){
        try {
            return new Timestamp(formatter.parse(stringDate).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Formats date from html <input type="datetime-local"/>, which returns String date in format "yyyy-MM-ddTHH:mm"
     * @param dateFromWeb
     * @return parsedTimestamp
     */
    public static Timestamp parseTimestampFromWeb(String dateFromWeb) {
        return parseTimestamp(
                INPUT_TYPE_DATE_TIME_LOCAL_FORMATTER,
                dateFromWeb.replace("T", " ")
        );
    }

}
