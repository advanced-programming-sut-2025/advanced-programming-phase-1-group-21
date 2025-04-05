package models.time;

public class Date {
    private long hour;
    
    private static final int HOURS_IN_DAY = 24;
    private static final int DAYS_IN_SEASON = 90;
    private static final int SEASONS_IN_YEAR = 4;
    private static final int DAYS_IN_YEAR = DAYS_IN_SEASON * SEASONS_IN_YEAR;
    
    public Date(long hour) {
        this.hour = hour;
    }

    public static Date createBias() {
        return new Date(9);
    }

    public Date nextHour() {
        return new Date(hour + 1);
    }
    
    public long getCurrentDay() {
        return (hour / HOURS_IN_DAY) % DAYS_IN_YEAR + 1;
    }
    
    public int getCurrentMonth() {
        return (int)(getCurrentDay() / (DAYS_IN_SEASON / 3)) % 12 + 1;
    }
    
    public Season getCurrentSeason() {
        long day = getCurrentDay();
        if (day < DAYS_IN_SEASON) {
            return Season.SPRING;
        } else if (day < DAYS_IN_SEASON * 2) {
            return Season.SUMMER;
        } else if (day < DAYS_IN_SEASON * 3) {
            return Season.AUTUMN;
        } else {
            return Season.WINTER;
        }
    }
    
    public long getHour() {
        return hour;
    }

    public void advance(long hours) {
        hour += hours;
    }

    public void advance(long days, long hours) {
        hour += days * HOURS_IN_DAY + hours;
    }
    
    public String getCurrentDayOfWeek() {
        long dayOfWeek = ((hour / HOURS_IN_DAY) % 7) + 1;
        switch ((int)dayOfWeek) {
            case 1: return "Monday";
            case 2: return "Tuesday";
            case 3: return "Wednesday";
            case 4: return "Thursday";
            case 5: return "Friday";
            case 6: return "Saturday";
            case 7: return "Sunday";
            default: return "Unknown";
        }
    }
}