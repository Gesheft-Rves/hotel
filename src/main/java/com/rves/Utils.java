package com.rves;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Utils {
    public static Timestamp parseDateFromStringOrNow(String arg){

        if (arg == null) {
            return new Timestamp(System.currentTimeMillis());
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            return new Timestamp(dateFormat.parse(arg.replace("T"," ")).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
