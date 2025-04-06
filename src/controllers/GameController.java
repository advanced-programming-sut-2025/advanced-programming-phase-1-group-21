package controllers;

import java.util.List;

import models.game.Energy;
import models.game.Game;
import models.game.Player;
import models.map.Building;
import models.map.Cord;
import models.map.Weather;
import models.result.Result;
import models.time.Date;
import models.time.Season;
import models.user.User;

public class GameController implements MenuStarter {

    public Result<Game> createGame(List<User> users) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> selectMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Game> laodGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> exitGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> nextTurn() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> terminateGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<String> getTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public Result<String> getDate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public Result<String> getDateTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public Result<String> getDayWeek() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> advanceTimeCheat(int days, int hours) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Season> getSeason() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> struckByThorCheat(Cord cord) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Weather> getWeather() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Weather> getWeatherForecast() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Weather> setWeatherCheat(Weather weather) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Building> buildGreenHouse() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Player> walk(Cord cord) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<String> printMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Energy> showEnergy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Energy> setEnergyCheat(int energyValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Energy> setEnergyUnlimited() {
        throw new UnsupportedOperationException("Not supported yet.");
    }



    @Override
    public void start() {
    }
}
