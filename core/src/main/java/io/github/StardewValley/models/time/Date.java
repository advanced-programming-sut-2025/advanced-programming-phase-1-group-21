package io.github.StardewValley.models.time;

import io.github.StardewValley.models.DailyUpdate;
import io.github.StardewValley.models.game.Game;

import java.io.Serializable;

public class Date implements DailyUpdate, Serializable {
    private int hour;
    private int day;
    private int season;
    private int year;

    private static final int HOURS_IN_DAY = 24;
    private static final int DAYS_IN_SEASON = 28;
    private static final int SEASONS_IN_YEAR = 4;
    private static final int DEFAULT_START_HOUR = 9; // 9 AM

    private Date(int hour, int day, int season, int year) {
        this.hour = hour;
        this.day = day;
        this.season = season;
        this.year = year;
        normalize();
    }

    public Date(Date other) {
        this(other.hour, other.day, other.season, other.year);
    }

    public static Date createBias() {
        return new Date(DEFAULT_START_HOUR, 1, 0, 1);
    }

    private void normalize() {
        if (hour >= HOURS_IN_DAY) {
            day += hour / HOURS_IN_DAY;
            hour %= HOURS_IN_DAY;
        } else if (hour < 0) {
            int daysToSubtract = (-hour + HOURS_IN_DAY - 1) / HOURS_IN_DAY;
            day -= daysToSubtract;
            hour = (hour + daysToSubtract * HOURS_IN_DAY) % HOURS_IN_DAY;
        }
        if (day > DAYS_IN_SEASON) {
            season += (day - 1) / DAYS_IN_SEASON;
            day = (day - 1) % DAYS_IN_SEASON + 1;
        } else if (day < 1) {
            int seasonsToSubtract = (-day) / DAYS_IN_SEASON + 1;
            season -= seasonsToSubtract;
            day = DAYS_IN_SEASON - (-day) % DAYS_IN_SEASON;
        }
        if (season >= SEASONS_IN_YEAR) {
            year += season / SEASONS_IN_YEAR;
            season %= SEASONS_IN_YEAR;
        } else if (season < 0) {
            int yearsToSubtract = (-season + SEASONS_IN_YEAR - 1) / SEASONS_IN_YEAR;
            year -= yearsToSubtract;
            season = (season + yearsToSubtract * SEASONS_IN_YEAR) % SEASONS_IN_YEAR;
        }
    }

    public void goToNextDay() {
        day++;
        hour = DEFAULT_START_HOUR;
        normalize();
    }

    public Season getCurrentSeason() {
        return Season.values()[season];
    }

    public String getCurrentDayOfWeek() {
        int totalDays = (season * DAYS_IN_SEASON) + day - 1;
        int dayOfWeek = totalDays % 7;

        return switch (dayOfWeek) {
            case 0 -> "Monday";
            case 1 -> "Tuesday";
            case 2 -> "Wednesday";
            case 3 -> "Thursday";
            case 4 -> "Friday";
            case 5 -> "Saturday";
            case 6 -> "Sunday";
            default -> "Unknown";
        };
    }

    public int getHour() { return hour; }
    public int getDay() { return day; }
    public int getSeason() { return season; }
    public int getYear() { return year; }
    public int getHourInDay() { return hour; }

    public void advanceHours(int hours) {
        hour += hours;
        normalize();
    }

    public void advanceDays(int days) {
        day += days;
        hour = DEFAULT_START_HOUR;
        normalize();
    }

    public void advanceSeasons(int seasons) {
        season += seasons;
        normalize();
    }

    @Override
    public String toString() {
        return String.format("Year %d, %s, Day %d, %02d:00 (%s)",
                year, getCurrentSeason(), day, hour, getCurrentDayOfWeek());
    }

    public String compactString() {
        return String.format(
                "%s:Day %02d @ %02d:00",
                getCurrentSeason(), day, hour
        );
    }

    @Override
    public boolean nextDay(Game g) {
        goToNextDay();
        return false;
    }
}
