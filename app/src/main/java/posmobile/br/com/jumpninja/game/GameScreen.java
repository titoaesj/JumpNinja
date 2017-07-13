package posmobile.br.com.jumpninja.game;

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

public class GameScreen extends AGScene {

    AGSprite cenarySprint = null;
    AGSprite ninjaJump = null;

    Boolean direction = false;

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

        cenarySprint = createSprite(R.drawable.ic_cenary,1,1);
        cenarySprint.setScreenPercent(100, 100);
        cenarySprint.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        cenarySprint.vrPosition.setY(AGScreenManager.iScreenHeight / 2);

        ninjaJump = createSprite(R.drawable.ic_ninja_jump, 4, 3);
        ninjaJump.setScreenPercent(23, 19);
        ninjaJump.vrPosition.fX = AGScreenManager.iScreenWidth / 2;
        ninjaJump.vrPosition.fY = AGScreenManager.iScreenHeight / 2;



    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {
        if (AGInputManager.vrTouchEvents.backButtonClicked()) {
            vrGameManager.setCurrentScene(1);
        }

        if (AGInputManager.vrTouchEvents.screenClicked()) {
            if (!direction) {
                ninjaJump.iMirror = AGSprite.HORIZONTAL;
            } else {
                ninjaJump.iMirror = AGSprite.NONE;
            }

            direction = !direction;
        }
        ninjaJump.addAnimation(7, true, 0, 9);
    }
}