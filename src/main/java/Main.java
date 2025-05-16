import models.App;
import views.menu.AppView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        AppView appView = new AppView();
        appView.resetStatic();
        appView.run();
    }

}


