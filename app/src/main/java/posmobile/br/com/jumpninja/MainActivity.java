package posmobile.br.com.jumpninja;

import posmobile.br.com.andgraph.AGActivityGame;
import posmobile.br.com.jumpninja.menu.MenuScreen;
import posmobile.br.com.jumpninja.splash.SplashScreen;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AGActivityGame {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /**
         * Activity e booleano habilitar o acelerômetro.
         */
        init(this, false);

        SplashScreen splashScreen = new SplashScreen(this.vrManager);
//        GameScreen gameScreen = new GameScreen(this.vrManager);
        MenuScreen menuScreen = new MenuScreen(this.vrManager);
//        CreditScreen creditScreen = new CreditScreen(this.vrManager);
//
        vrManager.addScene(splashScreen);
        vrManager.addScene(menuScreen);
//        vrManager.addScene(gameScreen);
//        vrManager.addScene(creditScreen);

    }
}
