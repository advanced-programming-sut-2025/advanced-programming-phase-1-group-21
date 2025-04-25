import views.menu.AppView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AppView appView = new AppView();
        Scanner scanner = new Scanner(System.in);
        String test = scanner.next();
        System.out.println("this is test: " + test);
//        appView.run();
    }

}

// merge sortx