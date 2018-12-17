package br.gov.mpog.gestaoriscos.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil{

    private DateUtil() {
        throw new IllegalAccessError("Classe Utiliataria");
    }

    public static Date getDateMinimizedHours(Date date){
        return getCalendarMinimizedHours(date).getTime();
    }

    public static Date getDateMaximizedHours(Date dateNow){
        return getCalendarMaximizedHours(dateNow).getTime();
    }

    public static Calendar getCalendarMinimizedHours(Date date){
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        return calendar;
    }

    public static Calendar getCalendarMaximizedHours(Date date){
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));

        return calendar;
    }

    public static String getDBTimestamp(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("pt", "BR"));
        format.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

        return format.format(date);
    }
}
