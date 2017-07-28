package posmobile.br.com.jumpninja.game;

import java.util.ArrayList;

import posmobile.br.com.andgraph.AGGameManager;
import posmobile.br.com.andgraph.AGInputManager;
import posmobile.br.com.andgraph.AGScene;
import posmobile.br.com.andgraph.AGScreenManager;
import posmobile.br.com.andgraph.AGSprite;
import posmobile.br.com.andgraph.AGTimer;
import posmobile.br.com.jumpninja.R;

/**
 * Project JumpNinja
 * Desenvolvido por Tito Albino Evangelista da Silva Junior em 05/07/17.
 */

public class GameScreen extends AGScene {

    AGSprite ninja = null;
    AGSprite background = null;
    AGSprite[] platform = new AGSprite[9];

    AGTimer tempoNinja;


    /*******************************************
     * Name: CAGScene()
     * Description: Scene construtor
     * Parameters: CAGameManager
     * Returns: none
     *****************************************
     * @param pManager*/
    public GameScreen(AGGameManager pManager) {
        super(pManager);
    }

    @Override
    public void init() {

        setSceneBackgroundColor(1.0f, 1.0f, 1.0f);

        //Cria Sprite de background
        background = createSprite(R.drawable.background, 1, 1);
        background.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2);
        background.setScreenPercent(100, 100);

        //Cria o Sprite do Ninja
        ninja = createSprite(R.drawable.ninja, 1, 1);
        ninja.setScreenPercent(12, 20);
        ninja.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        ninja.vrPosition.setY(ninja.getSpriteHeight() / 2);

        //Cria o Sprite da plataforma
        platform[0] = createSprite(R.drawable.wood, 1, 1);
        platform[0].setScreenPercent(75, 5);
        platform[0].vrPosition.setXY(AGScreenManager.iScreenWidth,
                ninja.getSpriteHeight());

        platform[1] = createSprite(R.drawable.wood, 1, 1);
        platform[1].setScreenPercent(75, 5);
        platform[1].vrPosition.setXY(0,
                (ninja.getSpriteHeight()*2));

        platform[2] = createSprite(R.drawable.wood, 1, 1);
        platform[2].setScreenPercent(75, 5);
        platform[2].vrPosition.setXY(AGScreenManager.iScreenWidth,
                (ninja.getSpriteHeight()*3));

        platform[3] = createSprite(R.drawable.wood, 1, 1);
        platform[3].setScreenPercent(75, 5);
        platform[3].vrPosition.setXY(0,
                (ninja.getSpriteHeight()*4));

//        platform[4] = createSprite(R.drawable.wood, 1, 1);
//        platform[4].setScreenPercent(75, 5);
//        platform[4].vrPosition.setXY(AGScreenManager.iScreenWidth,
//                (ninja.getSpriteHeight()*5));
//
//        platform[5] = createSprite(R.drawable.wood, 1, 1);
//        platform[5].setScreenPercent(75, 5);
//        platform[5].vrPosition.setXY(0,
//                (ninja.getSpriteHeight()*6));
//
//        platform[6] = createSprite(R.drawable.wood, 1, 1);
//        platform[6].setScreenPercent(75, 5);
//        platform[6].vrPosition.setXY(AGScreenManager.iScreenWidth,
//                (ninja.getSpriteHeight()*7));
//
//        platform[7] = createSprite(R.drawable.wood, 1, 1);
//        platform[7].setScreenPercent(75, 5);
//        platform[7].vrPosition.setXY(0,
//                (ninja.getSpriteHeight()*8));
//
//        platform[8] = createSprite(R.drawable.wood, 1, 1);
//        platform[8].setScreenPercent(75, 5);
//        platform[8].vrPosition.setXY(AGScreenManager.iScreenWidth,
//                (ninja.getSpriteHeight()*9));



        tempoNinja = new AGTimer(25);

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {
        gravidade();
        atualizaMovimentoNinja();

        if (AGInputManager.vrTouchEvents.backButtonClicked()) {
            vrGameManager.setCurrentScene(1);
        }
    }

    private void atualizaMovimentoNinja() {
        tempoNinja.update();

        if (tempoNinja.isTimeEnded()) {
            tempoNinja.restart();

            float acelerometro = AGInputManager.vrAccelerometer.getAccelX();
            float halfWidth = ninja.getSpriteWidth() / 2;

            if (acelerometro > 2 && ninja.vrPosition.getX() <= AGScreenManager.iScreenWidth - halfWidth) {
                ninja.vrPosition.setX(ninja.vrPosition.getX() + 10);
            } else if (acelerometro < -2 && ninja.vrPosition.getX() >= halfWidth) {
                ninja.vrPosition.setX(ninja.vrPosition.getX() - 10);
            }
        }
    }

    private void gravidade() {

    }
}
