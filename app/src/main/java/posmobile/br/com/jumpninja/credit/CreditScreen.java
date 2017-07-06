package posmobile.br.com.jumpninja.credit;

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

public class CreditScreen extends AGScene {


    AGSprite ninjaJump = null;
    AGSprite ninjaDead = null;
    AGSprite ninjaGlide = null;

    Boolean direction = false;

    /*******************************************
     * Name: CAGScene()
     * Description: Scene construtor
     * Parameters: CAGameManager
     * Returns: none
     *****************************************
     * @param pManager*/
    public CreditScreen(AGGameManager pManager) {
        super(pManager);
    }

    @Override
    public void init() {
//        setSceneBackgroundColor(0.0f,0.0f,1.0f);

        ninjaJump = createSprite(R.drawable.ninja_jump,4,4);
        ninjaJump.setScreenPercent(23,19);
        ninjaJump.vrPosition.fX = AGScreenManager.iScreenWidth/2;
        ninjaJump.vrPosition.fY = AGScreenManager.iScreenHeight/2;

        ninjaGlide = createSprite(R.drawable.ninja_glide,4,3);
        ninjaGlide.setScreenPercent(23,19);
        ninjaGlide.vrPosition.fX = AGScreenManager.iScreenWidth/2;
        ninjaGlide.vrPosition.fY = AGScreenManager.iScreenHeight/2 + ninjaGlide.getSpriteHeight();

        ninjaDead = createSprite(R.drawable.ninja_dead,4,3);
        ninjaDead.setScreenPercent(23,19);
        ninjaDead.vrPosition.fX = AGScreenManager.iScreenWidth/2;
        ninjaDead.vrPosition.fY = AGScreenManager.iScreenHeight/2 + ninjaGlide.getSpriteHeight() +  ninjaDead.getSpriteHeight();
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

//        if (AGInputManager.vrTouchEvents.screenClicked()) {
//            if ( ! direction) {
//
//                ninjaDead.iMirror = AGSprite.HORIZONTAL;
//                ninjaJump.iMirror = AGSprite.HORIZONTAL;
//                ninjaGlide.iMirror = AGSprite.HORIZONTAL;
//
//            } else {
//                ninjaDead.iMirror = AGSprite.NONE;
//                ninjaJump.iMirror = AGSprite.NONE;
//                ninjaGlide.iMirror = AGSprite.NONE;
//            }
//
//            direction = ! direction;
//        }
//
//        ninjaJump.addAnimation(7,true,0,9);
//        ninjaGlide.addAnimation(10,true,0,9);
//        ninjaDead.addAnimation(10,true,0,9);
    }
}
