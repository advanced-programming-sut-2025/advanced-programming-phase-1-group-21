import models.data.AnimalHouseData;
import models.data.DataLoader;
import models.data.StardropSaloonRecipeData;
import views.menu.AppView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        AppView appView = new AppView();
//        appView.run();
        DataLoader.load();
    }

}