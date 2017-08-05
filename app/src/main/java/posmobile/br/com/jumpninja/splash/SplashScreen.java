package posmobile.br.com.jumpninja.splash;

import  posmobile.br.com.andgraph.AGGameManager;
import posmobile.br.com.andgraph.AGScene;
import  posmobile.br.com.andgraph.AGScreenManager;
import  posmobile.br.com.andgraph.AGSoundManager;
import  posmobile.br.com.andgraph.AGSprite;
import  posmobile.br.com.andgraph.AGTimer;

import posmobile.br.com.jumpninja.R;


/**
 * Project JumpNinja
 * Desenvolvido por Tito Albino Evangelista da Silva Junior em 05/07/17.
 */

public class SplashScreen extends AGScene {

    AGTimer timer;
    AGSprite title = null;

    /*******************************************
     * Name: CAGScene()
     * Description: Scene construtor
     * Parameters: CAGameManager
     * Returns: none
     *****************************************
     * @param pManager*/
    public SplashScreen(AGGameManager pManager) {
        super(pManager);
    }

    @Override
    public void init() {
        timer = new AGTimer(5000);
        setSceneBackgroundColor(1.0f,1.0f,1.0f);
        title = createSprite(R.drawable.splash,1,1);
        title.setScreenPercent(80,80);
        title.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        title.vrPosition.setY(AGScreenManager.iScreenHeight / 2);

        //Carrega a trilha sonora do jogo usando Music que faz buffer de som
        AGSoundManager.vrMusic.loadMusic("music_menu.mp3", true);
        AGSoundManager.vrMusic.play();
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {
        timer.update();
        if (timer.isTimeEnded()) {
            vrGameManager.setCurrentScene(1);
        }
    }
}
