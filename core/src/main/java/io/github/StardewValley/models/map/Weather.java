package io.github.StardewValley.models.map;

import java.io.Serializable;

public enum Weather implements Serializable {
    SUNNY,
    RAINY,
    STORM,
    SNOW;

    public static Weather getWeather(String name) {
        for (Weather weather : Weather.values())
            if (weather.name().equalsIgnoreCase(name))
                return weather;
        return null;
    }

    @Override
    public String toString() {
        return name();
    }
}
