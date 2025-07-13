package net.pneumono.pneumonocore.config;

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
}
