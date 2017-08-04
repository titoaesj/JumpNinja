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



    private AGSprite backGround = null;
    private AGSprite titulo = null;

    private AGSprite btnJogar = null;
    private AGSprite btnCreditos = null;
    private AGSprite btnDoacao = null;


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

        //Cria Sprite de background
        backGround = createSprite(R.drawable.background, 1 , 1);
        backGround.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/2);
        backGround.setScreenPercent(100, 100);

        //Titulo do game
        titulo = createSprite(R.drawable.titulo_game, 1, 1);
        titulo.setScreenPercent(100, 10);
        titulo.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        titulo.vrPosition.setY(AGScreenManager.iScreenHeight - titulo.getSpriteHeight() * 2);

        //Botão de Jogar
        btnJogar = createSprite(R.drawable.btnjogar,1,1);
        btnJogar.setScreenPercent(60,10);
        btnJogar.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        btnJogar.vrPosition.setY(AGScreenManager.iScreenHeight - btnJogar.getSpriteHeight() * 4);

        //Botão de Créditos
        btnCreditos = createSprite(R.drawable.btncreditos,1,1);
        btnCreditos.setScreenPercent(60,10);
        btnCreditos.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        btnCreditos.vrPosition.setY(AGScreenManager.iScreenHeight - btnJogar.getSpriteHeight() * 6);

        //Botão de Doação
        btnDoacao = createSprite(R.drawable.btndoacao,1,1);
        btnDoacao.setScreenPercent(60,10);
        btnDoacao.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        btnDoacao.vrPosition.setY(AGScreenManager.iScreenHeight - btnJogar.getSpriteHeight() * 8);

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
            if (btnJogar.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                vrGameManager.setCurrentScene(2);
                return;
            }

            if (btnCreditos.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                vrGameManager.setCurrentScene(4);
                return;
            }

            if (btnDoacao.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                vrGameManager.vrActivity.finish();
            }

        }
    }
}
