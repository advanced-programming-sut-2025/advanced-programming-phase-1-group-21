package io.github.StardewValley;

import com.badlogic.gdx.Game;
import io.github.StardewValley.models.App;
import io.github.StardewValley.views.menu.AppView;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    @Override
    public void create() {
//        setScreen(new FirstScreen());
        try {
            AppView appView = new AppView();
            appView.resetStatic();
            App.reset();
            appView.run();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
