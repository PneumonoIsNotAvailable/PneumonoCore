package net.pneumono.pneumonocore.config;

/**
 * @deprecated Use {@link net.pneumono.pneumonocore.config_api.enums.TimeUnit TimeUnit} instead.
 */
@Deprecated
@SuppressWarnings("unused")
public enum TimeUnit {
    TICKS(1),
    SECONDS(20),
    MINUTES(20 * 60),
    HOURS(20 * 60 * 60),
    DAYS(20 * 60 * 60 * 24);

    private final int division;

    TimeUnit(int division) {
        this.division = division;
    }

    public int getDivision() {
        return division;
    }

    public static TimeUnit fromValue(long value) {
        if (value == 0) {
            return TICKS;
        } else if (value % DAYS.division == 0) {
            return DAYS;
        } else if (value % HOURS.division == 0) {
            return HOURS;
        } else if (value % MINUTES.division == 0) {
            return MINUTES;
        } else if (value % SECONDS.division == 0) {
            return SECONDS;
        } else {
            return TICKS;
        }
    }
}
