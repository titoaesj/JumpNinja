package posmobile.br.com.jumpninja.menu;

import posmobile.br.com.andgraph.AGGameManager;
import posmobile.br.com.andgraph.AGInputManager;
import posmobile.br.com.andgraph.AGScene;
import posmobile.br.com.andgraph.AGScreenManager;
import posmobile.br.com.andgraph.AGSprite;

import posmobile.br.com.jumpninja.R;


/**
 * Project JumpNinja
 * Desenvolvido por Tito Albino Evangelista da Silva Junior em 05/07/17.
 */

public class MenuScreen extends AGScene {


    AGSprite gameButton = null;
    AGSprite creditButton = null;
    AGSprite exitButton = null;


    /*******************************************
     * Name: CAGScene()
     * Description: Scene construtor
     * Parameters: CAGameManager
     * Returns: none
     *****************************************
     * @param pManager*/
    public MenuScreen(AGGameManager pManager) {
        super(pManager);
    }

    @Override
    public void init() {
        setSceneBackgroundColor(1.0f,1.0f,1.0f);
        gameButton = createSprite(R.drawable.button1,1,1);
        creditButton = createSprite(R.drawable.button2,1,1);
        exitButton = createSprite(R.drawable.button3,1,1);

        gameButton.setScreenPercent(60,10);
        gameButton.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        gameButton.vrPosition.setY(AGScreenManager.iScreenHeight - gameButton.getSpriteHeight() * 4);

        creditButton.setScreenPercent(60,10);
        creditButton.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        creditButton.vrPosition.setY(AGScreenManager.iScreenHeight - gameButton.getSpriteHeight() * 6);

        exitButton.setScreenPercent(60,10);
        exitButton.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        exitButton.vrPosition.setY(AGScreenManager.iScreenHeight - gameButton.getSpriteHeight() * 8);

    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {
        if (AGInputManager.vrTouchEvents.screenClicked()) {
            if (gameButton.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                vrGameManager.setCurrentScene(2);
                return;
            }

            if (creditButton.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                vrGameManager.setCurrentScene(3);
                return;
            }

            if (exitButton.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                vrGameManager.vrActivity.finish();
            }

        }
    }
}
