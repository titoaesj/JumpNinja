package posmobile.br.com.jumpninja.game;

import android.util.Log;

import posmobile.br.com.andgraph.AGGameManager;
import posmobile.br.com.andgraph.AGInputManager;
import posmobile.br.com.andgraph.AGScene;
import posmobile.br.com.andgraph.AGScreenManager;
import posmobile.br.com.andgraph.AGSprite;
import posmobile.br.com.jumpninja.R;

/**
 * Project JumpNinja
 * Desenvolvido por Tito Albino Evangelista da Silva Junior em 04/08/17.
 */

public class MenuChoice extends AGScene {

    private static final String TAG = MenuChoice.class.getSimpleName();

    AGSprite background = null;
    AGSprite maleButton = null;
    AGSprite femaleButton = null;

    /*******************************************
     * Name: CAGScene()
     * Description: Scene construtor
     * Parameters: CAGameManager
     * Returns: none
     *****************************************
     * @param pManager*/
    public MenuChoice(AGGameManager pManager) {
        super(pManager);
    }

    @Override
    public void init() {

        //Cria Sprite de background
        background = createSprite(R.drawable.background_menu_choice, 1, 1);
        background.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2);
        background.setScreenPercent(100, 100);

        //Cria Sprite de male
        maleButton = createSprite(R.drawable.male_menu_choice, 1, 1);
        maleButton.vrPosition.setXY(AGScreenManager.iScreenWidth - (maleButton.getSpriteWidth() / 4), maleButton.getSpriteHeight() / 6);
        maleButton.setScreenPercent(40, 40);

//        //Cria Sprite de female
        femaleButton = createSprite(R.drawable.female_menu_choice, 1, 1);
        femaleButton.vrPosition.setXY(femaleButton.getSpriteWidth() / 5, AGScreenManager.iScreenWidth + (femaleButton.getSpriteWidth() / 6));
        femaleButton.setScreenPercent(40, 40);

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
            if (maleButton.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                Log.d(TAG, "loop: seta male");
                vrGameManager.setCurrentScene(1);
                return;
            }
            if (femaleButton.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                Log.d(TAG, "loop: seta female");
                personageOP
                vrGameManager.setCurrentScene(1);
                return;
            }
        }
    }
}
