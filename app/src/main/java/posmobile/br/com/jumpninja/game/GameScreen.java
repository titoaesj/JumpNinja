package posmobile.br.com.jumpninja.game;

import java.util.ArrayList;
import java.util.Random;

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

    AGSprite[] placar = new AGSprite[6];
    int tempoPontuacao = 0;
    int pontuacao = 0;

    AGSprite background = null;
    AGSprite chao = null;
    AGSprite ninja = null;
    AGSprite shuriken = null;
    ArrayList<AGSprite> plataformas = null;

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

        tempoAcelerometroNinja = new AGTimer(50);
        tempoPuloNinja = new AGTimer(50);

        plataformas = new ArrayList<AGSprite>();

        //Cria Sprite de background
        background = createSprite(R.drawable.bg, 1, 1);
        background.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2);
        background.setScreenPercent(125, 100);

        //Cria o Sprinte da Shuriken
        shuriken = createSprite(R.drawable.shuriken,2,3);
        shuriken.addAnimation(8, true, 0, 4);
        shuriken.vrPosition.setXY(shuriken.getSpriteHeight() / 3,AGScreenManager.iScreenHeight - (shuriken.getSpriteHeight() / 3));
        shuriken.setScreenPercent(15,13);

        //Cria o Sprite do Ninja
        if (vrGameManager.getPersonaOption() == 1) {
            ninja = createSprite(R.drawable.ninja_male_jump, 4, 3);
        } else if (vrGameManager.getPersonaOption() == 2) {
            ninja = createSprite(R.drawable.ninja_female_jump, 4, 3);
        }
        ninja.addAnimation(10, false, 0, 9);
        ninja.setScreenPercent(20, 15);
        ninja.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        ninja.vrPosition.setY(ninja.getSpriteHeight() / 2);
        ninja.bAutoRender = false;

        /**
         * Configura os Sprites do placar
         */
        int multiplicador = 1;
        for (int pos = 0; pos < placar.length; pos++) {
            placar[pos] = createSprite(R.drawable.placar, 4, 4);
            placar[pos].setScreenPercent(10, 10);
            placar[pos].vrPosition.fY = AGScreenManager.iScreenHeight - (shuriken.getSpriteHeight() / 3);
            placar[pos].vrPosition.fX = 20 + multiplicador * placar[pos].getSpriteWidth();
            placar[pos].bAutoRender = false;
            multiplicador++;

            for (int i = 0; i < 10; i++) {
                placar[pos].addAnimation(1, false, i);
            }

        }

        Random random = new Random();

        //Cria o Sprite da plataforma Iniciais.
        for (int i = 1; i <= 8; i++) {
            AGSprite novaPlataforma = createSprite(R.drawable.plataforma, 1, 1);
            novaPlataforma.setScreenPercent(33, 5);
            if (i == 1 || i == 5) {
                novaPlataforma.vrPosition.setX(AGScreenManager.iScreenWidth - novaPlataforma.getSpriteWidth()/2 );
            } else if(i == 2 || i == 4){
                novaPlataforma.vrPosition.setX(AGScreenManager.iScreenWidth/2);
            } else if(i == 3 || i == 6){
                novaPlataforma.vrPosition.setX(0 + novaPlataforma.getSpriteWidth()/2);
            }
            novaPlataforma.vrPosition.setY(ninja.getSpriteHeight() * i);
            plataformas.add(novaPlataforma);
        }

        //Teste de plataforma no Chão
        chao = createSprite(R.drawable.plataforma, 1, 1);
        chao.setScreenPercent(110, 5);
        chao.vrPosition.setXY(chao.getSpriteWidth()/2 - 10, 0);
    }

    @Override
    public void render() {
        super.render();
        ninja.render();
        for (AGSprite digito : placar) {
            digito.render();
        }
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
        atualizaPlacar();

        if (AGInputManager.vrTouchEvents.backButtonClicked()) {
            vrGameManager.setCurrentScene(1);
        }
    }

    /**
     * Método para atualizar placar
     */
    private void atualizaPlacar() {

        if (tempoPontuacao > 0) {

            for (AGSprite digiter : placar) {
                digiter.bVisible = !digiter.bVisible;
            }

            tempoPontuacao--;
            pontuacao++;
        }

        if (placar != null && placar[5] != null) {
            placar[5].setCurrentAnimation(pontuacao % 10);
        }
        if (placar != null && placar[4] != null) {
            placar[4].setCurrentAnimation((pontuacao % 100) / 10);
        }
        if (placar != null && placar[3] != null) {
            placar[3].setCurrentAnimation((pontuacao % 1000) / 100);
        }
        if (placar != null && placar[2] != null) {
            placar[2].setCurrentAnimation((pontuacao % 10000) / 1000);
        }
        if (placar != null && placar[1] != null) {
            placar[1].setCurrentAnimation((pontuacao % 100000) / 10000);
        }
        if (placar != null && placar[0] != null) {
            placar[0].setCurrentAnimation((pontuacao % 1000000) / 100000);
        }
    }



    /**
     * Método que atualiza a posicao do heroi de acordo com o acelerômetro
     */
    private void atualizaMovimentoNinja() {
        tempoAcelerometroNinja.update();

        if (tempoAcelerometroNinja.isTimeEnded()) {
            tempoAcelerometroNinja.restart();

            float acelerometro = AGInputManager.vrAccelerometer.getAccelX();
            float halfWidth = ninja.getSpriteWidth() / 2;
            float distancia = ninja.getSpriteHeight() / 5;

            if (acelerometro > 2 && ninja.vrPosition.getX() <= AGScreenManager.iScreenWidth - halfWidth) {
                ninja.vrPosition.setX(ninja.vrPosition.getX() + distancia);
                ninja.iMirror = AGSprite.NONE;
            } else if (acelerometro < -2 && ninja.vrPosition.getX() >= halfWidth) {
                ninja.vrPosition.setX(ninja.vrPosition.getX() - distancia);
                ninja.iMirror = AGSprite.HORIZONTAL;
            }
        }
    }

    /**
     * Método que atualiza o sprit e a posição do pulo do ninja.
     */
    private void atualizaPuloNinja() {
        tempoPuloNinja.update();

        if (tempoPuloNinja.isTimeEnded()) {
            tempoPuloNinja.restart();

            //Reinicia o Pulo
            if (verificaColisaoPlataformas() || verificaColisaoChao()) {
                ninja.getCurrentAnimation().restart();
                puloNinja = 0;
            }

            Float alturaPulo = ((ninja.getSpriteHeight() / 5));

            if (puloNinja < 5) {
                ninja.vrPosition.setY(ninja.vrPosition.getY() + alturaPulo);
            } else {
                ninja.vrPosition.setY(ninja.vrPosition.getY() - alturaPulo);
            }
            if(ninja.getCurrentAnimation().getCurrentFrame() != ninja.getCurrentAnimation().getTotalFrames()){
                ninja.getCurrentAnimation().update();
            }
            puloNinja++;

        }
    }

    private boolean verificaColisaoPlataformas() {
        for (AGSprite plataforma : plataformas) {
            if (ninja.collide(plataforma) &&
                    (ninja.vrPosition.getX()) > (plataforma.vrPosition.getX() - plataforma.getSpriteWidth() / 2) &&
                    (ninja.vrPosition.getX()) < (plataforma.vrPosition.getX() + plataforma.getSpriteWidth() / 2) &&
                    (ninja.vrPosition.getY() - ninja.getSpriteHeight() / 2) > (plataforma.vrPosition.getY() - plataforma.getSpriteHeight() / 2) &&
                    (ninja.vrPosition.getY() - ninja.getSpriteHeight() / 2) < (plataforma.vrPosition.getY() + plataforma.getSpriteHeight() / 2)) {
                return true;
            }
        }
        return false;
    }

    private boolean verificaColisaoChao(){
        if (ninja.collide(chao)){
            return true;
        }
        return false;
    }
}
