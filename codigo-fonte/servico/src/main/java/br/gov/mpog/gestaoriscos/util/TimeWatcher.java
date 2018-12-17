package br.gov.mpog.gestaoriscos.util;

import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class TimeWatcher {

    private long start, end, elapsed;

    private TimeWatcher() {
        reset();
    }

    public static TimeWatcher start() {
        return new TimeWatcher();
    }

    private long currentTimeInMs() {
        return GregorianCalendar.getInstance().getTime().getTime();
    }

    public TimeWatcher reset() {
        start = currentTimeInMs();
        return this;
    }

    public void end() {
        end = currentTimeInMs();
        elapsed = end - start;
    }

    public String elapsedTimeStr(){
        final long l = elapsed;
        final long hr = TimeUnit.MILLISECONDS.toHours(l);
        final long min = TimeUnit.MILLISECONDS.toMinutes(l - TimeUnit.HOURS.toMillis(hr));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(l - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
        final long ms = TimeUnit.MILLISECONDS.toMillis(l - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(sec));
        return String.format("%02d:%02d:%02d.%03d", hr, min, sec, ms);
    }
}