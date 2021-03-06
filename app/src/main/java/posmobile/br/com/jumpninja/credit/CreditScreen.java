package posmobile.br.com.jumpninja.credit;

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

public class CreditScreen extends AGScene {

    private AGSprite background = null;
    private AGSprite mascara = null;
    private AGSprite credit = null;
    AGTimer tempoCredit = null;

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
        //Cria Sprite de BackGround
        background = createSprite(R.drawable.background, 1 , 1);
        background.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/2);
        background.setScreenPercent(100, 100);

        mascara = createSprite(R.drawable.overlayer_mask_background, 1 , 1);
        mascara.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/2);
        mascara.setScreenPercent(100, 100);

        credit = createSprite(R.drawable.credit, 1 , 1);
        credit.vrPosition.setXY(AGScreenManager.iScreenWidth/2,AGScreenManager.iScreenHeight/2);
        credit.setScreenPercent(80, 80);

        tempoCredit = new AGTimer(100);

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

//        moveCredit();
    }

    private void moveCredit() {
        tempoCredit.update();
        if (tempoCredit.isTimeEnded()) {
            tempoCredit.restart();
            if (credit.vrPosition.getY() <= AGScreenManager.iScreenHeight / 1.8) {
                credit.vrPosition.fY = credit.vrPosition.getY() + 10;
            }
        }
    }
}
