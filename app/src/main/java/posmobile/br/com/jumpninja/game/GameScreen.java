package posmobile.br.com.jumpninja.game;

import android.util.Log;

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

    AGSprite background = null;
    AGSprite ninja = null;
    AGSprite[] platform = new AGSprite[9];

    AGTimer tempoAcelerometroNinja;
    AGTimer tempoPuloNinja;

    int puloNinja = 0;

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

        tempoAcelerometroNinja = new AGTimer(25);
        tempoPuloNinja = new AGTimer(75);

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
        atualizaMovimentoNinja();
        atualizaPuloNinja();

        if (AGInputManager.vrTouchEvents.backButtonClicked()) {
            vrGameManager.setCurrentScene(1);
        }
    }

    private void atualizaMovimentoNinja() {
        tempoAcelerometroNinja.update();

        if (tempoAcelerometroNinja.isTimeEnded()) {
            tempoAcelerometroNinja.restart();

            float acelerometro = AGInputManager.vrAccelerometer.getAccelX();
            float halfWidth = ninja.getSpriteWidth() / 2;

            if (acelerometro > 2 && ninja.vrPosition.getX() <= AGScreenManager.iScreenWidth - halfWidth) {
                ninja.vrPosition.setX(ninja.vrPosition.getX() + 10);
                ninja.iMirror = AGSprite.NONE;
            } else if (acelerometro < -2 && ninja.vrPosition.getX() >= halfWidth) {
                ninja.vrPosition.setX(ninja.vrPosition.getX() - 10);
                ninja.iMirror = AGSprite.HORIZONTAL;
            }
        }
    }

    private void atualizaPuloNinja(){
        tempoPuloNinja.update();

        if(tempoPuloNinja.isTimeEnded()){
            tempoPuloNinja.restart();

            //Reinicia o Pulo
            if(puloNinja == 10){
                puloNinja = 0;
            }

            //Faz o Pulo
            if(puloNinja < 5){
                ninja.vrPosition.setY(ninja.vrPosition.getY() + 25);
            }else{
                ninja.vrPosition.setY(ninja.vrPosition.getY() - 25);
            }

            Log.d("Pulo Ninja", "ID : " + puloNinja);
            puloNinja++;

        }
    }
}
