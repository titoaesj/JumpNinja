package posmobile.br.com.jumpninja.game;

import java.util.ArrayList;
import java.util.Random;

import posmobile.br.com.andgraph.AGGameManager;
import posmobile.br.com.andgraph.AGInputManager;
import posmobile.br.com.andgraph.AGScene;
import posmobile.br.com.andgraph.AGScreenManager;
import posmobile.br.com.andgraph.AGSoundManager;
import posmobile.br.com.andgraph.AGSprite;
import posmobile.br.com.andgraph.AGTimer;
import posmobile.br.com.jumpninja.R;

/**
 * Project JumpNinja
 * Desenvolvido por Tito Albino Evangelista da Silva Junior em 05/07/17.
 */

public class GameScreen extends AGScene {

    private static final String TAG = GameScreen.class.getSimpleName();

    AGSprite[] placar = new AGSprite[6];
    int tempoPontuacao = 0;
    int pontuacao = 0;
    Boolean paused = false;

    AGSprite background = null;
    AGSprite painel = null;
    AGSprite chao = null;
    AGSprite ninja = null;
    AGSprite mGameOverBackground = null;
    AGSprite mGameOver = null;
    AGSprite mGameGameOver = null;
    AGSprite mMenuGameOver = null;
    AGSprite mExitGameOver = null;
    ArrayList<AGSprite> plataformas = null;

    AGTimer tempoAcelerometroNinja;
    AGTimer tempoPuloNinja;

    //Variaveis de COntrole
    int puloNinja = 0; //Controla o Algoritimo para o pulo do ninja
    boolean scrollMapaAtivo;
    int velocidadeScroll;
    /*Variavel para controlar qual foi a ultima posição da Plataforma.
    * 0 - Não Começou
    * 1 - Direita
    * 2 - Meio
    * 3 - Esquerda
    * */
    int ultimaPosicaoPlataforma;

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

        //Carrega a trilha sonora do jogo usando Music que faz buffer de som
        AGSoundManager.vrMusic.loadMusic("music_game.mp3", true);
        AGSoundManager.vrMusic.play();

        setSceneBackgroundColor(1.0f, 1.0f, 1.0f);

        tempoAcelerometroNinja = new AGTimer(50);
        tempoPuloNinja = new AGTimer(50);

        plataformas = new ArrayList<>();

        scrollMapaAtivo = false;
        velocidadeScroll = 1;
        tempoPontuacao = 0;
        pontuacao = 0;
        puloNinja = 0;
        ultimaPosicaoPlataforma = 0;

        //Cria Sprite de background
        background = createSprite(R.drawable.background, 1, 1);
        background.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2);
        background.setScreenPercent(125, 100);

        //Cria o Sprite do Ninja
        if (vrGameManager.getPersonaOption() == 1) {
            ninja = createSprite(R.drawable.ninja_male_jump, 4, 3);
        } else if (vrGameManager.getPersonaOption() == 2) {
            ninja = createSprite(R.drawable.ninja_female_jump, 4, 3);
        } else {
            ninja = createSprite(R.drawable.ninja_male_jump, 4, 3);
        }
        ninja.addAnimation(10, false, 0, 9);
        ninja.setScreenPercent(20, 15);
        ninja.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        ninja.vrPosition.setY(ninja.getSpriteHeight() / 2);
        ninja.bAutoRender = false;

        //Cria o Sprinte do gameOverShow
        mGameOverBackground = createSprite(R.drawable.overlayer_mask_background, 1, 1);
        mGameOverBackground.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2);
        mGameOverBackground.setScreenPercent(100, 100);
        mGameOverBackground.bAutoRender = false;
        mGameOverBackground.bVisible = false;

        mGameOver = createSprite(R.drawable.gameover, 1, 1);
        mGameOver.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2);
        mGameOver.setScreenPercent(80, 60);
        mGameOver.bAutoRender = false;
        mGameOver.bVisible = false;

        mGameGameOver = createSprite(R.drawable.button_game, 1, 1);
        mGameGameOver.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2 + mGameGameOver.getSpriteHeight() / 3);
        mGameGameOver.setScreenPercent(40, 10);
        mGameGameOver.bAutoRender = false;
        mGameGameOver.bVisible = false;

        mMenuGameOver = createSprite(R.drawable.button_menu, 1, 1);
        mMenuGameOver.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2);
        mMenuGameOver.setScreenPercent(40, 10);
        mMenuGameOver.bAutoRender = false;
        mMenuGameOver.bVisible = false;

        mExitGameOver = createSprite(R.drawable.button_exit, 1, 1);
        mExitGameOver.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2 - mExitGameOver.getSpriteHeight() / 3);
        mExitGameOver.setScreenPercent(40, 10);
        mExitGameOver.bAutoRender = false;
        mExitGameOver.bVisible = false;

        //Cria o Sprite da plataforma Iniciais os 6 primeiros
        for (int i = 1; i <= 7; i++) {
            AGSprite novaPlataforma = createSprite(R.drawable.plataforma, 1, 1);
            novaPlataforma.setScreenPercent(33, 5);
            if (i == 1 || i == 5) {
                novaPlataforma.vrPosition.setX(AGScreenManager.iScreenWidth - novaPlataforma.getSpriteWidth() / 2);
            } else if (i == 2 || i == 4 || i == 6) {
                novaPlataforma.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
            } else if (i == 3 || i == 7) {
                novaPlataforma.vrPosition.setX(0 + novaPlataforma.getSpriteWidth() / 2);
            }
            novaPlataforma.vrPosition.setY(ninja.getSpriteHeight() * i);
            plataformas.add(novaPlataforma);
        }

        //Cria Sprits do chao
        chao = createSprite(R.drawable.chao, 1, 1);
        chao.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, 0);
        chao.setScreenPercent(100, 5);

        //CRIA O SPRITE DO PANEL DO GAME
        painel = createSprite(R.drawable.painel, 1, 1);
        painel.setScreenPercent(100, 10);
        painel.vrPosition.fX = AGScreenManager.iScreenWidth / 2;
        painel.vrPosition.fY = AGScreenManager.iScreenHeight -
                painel.getSpriteHeight() / 2;
        painel.bAutoRender = false;


        /**
         * Configura os Sprites do placar
         */
        int multiplicador = 1;
        for (int pos = 0; pos < placar.length; pos++) {
            placar[pos] = createSprite(R.drawable.placar, 4, 4);
            placar[pos].setScreenPercent(10, 10);
            placar[pos].vrPosition.fY = AGScreenManager.iScreenHeight - (painel.getSpriteHeight() / 3);
            placar[pos].vrPosition.fX = 20 + multiplicador * placar[pos].getSpriteWidth();
            placar[pos].bAutoRender = false;
            multiplicador++;

            for (int i = 0; i < 10; i++) {
                placar[pos].addAnimation(1, false, i);
            }

        }
    }

    @Override
    public void render() {
        super.render();
        ninja.render();
        mGameOverBackground.render();
        mGameOver.render();
        mGameGameOver.render();
        mMenuGameOver.render();
        mExitGameOver.render();
        painel.render();
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

        if (!paused) {
            atualizaMovimentoNinja();
            atualizaPuloNinja();
            atualizaMovimentoPlataformas();
            atualizaMovimentoChao();
            atualizaPlacar();
            verificaGameOver();
            calculaVelocidadeScrool();
        }

        if (AGInputManager.vrTouchEvents.screenClicked()) {
            if (mGameGameOver.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                gameOverHidden();
                init();
            }
            if (mMenuGameOver.collide(AGInputManager.vrTouchEvents.getLastPosition())) {

                //Carrega a trilha sonora do jogo usando Music que faz buffer de som
                AGSoundManager.vrMusic.loadMusic("music_menu.mp3", true);
                AGSoundManager.vrMusic.play();

                gameOverHidden();
                vrGameManager.setCurrentScene(1);
                return;
            }
            if (mExitGameOver.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                gameOverHidden();
                vrGameManager.vrActivity.finish();
                return;
            }

        }
        if (AGInputManager.vrTouchEvents.backButtonClicked()) {
            vrGameManager.setCurrentScene(1);
            return;
        }
    }

    private void gameOverShow() {
        paused = true;
        mGameOverBackground.bVisible = true;
        mGameOver.bVisible = true;
        mMenuGameOver.bVisible = true;
        mExitGameOver.bVisible = true;
        mGameGameOver.bVisible = true;
    }

    private void gameOverHidden() {
        paused = false;
        mGameOverBackground.bVisible = false;
        mGameOver.bVisible = false;
        mMenuGameOver.bVisible = false;
        mExitGameOver.bVisible = false;
        mGameGameOver.bVisible = false;
    }

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
                if (!ninja.collide(painel)) {
                    ninja.vrPosition.setY(ninja.vrPosition.getY() + alturaPulo);
                }
            } else {
                ninja.vrPosition.setY(ninja.vrPosition.getY() - alturaPulo);
            }
            if (ninja.getCurrentAnimation().getCurrentFrame() != ninja.getCurrentAnimation().getTotalFrames()) {
                ninja.getCurrentAnimation().update();
            }
            puloNinja++;

        }
    }

    private void criaPlataforma() {

        //10 pontos por plataforma
        pontuacao += 10;

        Random random = new Random();
        int lado = random.nextInt(2);

        //Seta qual lado vai ser criada a plataforma
        if (ultimaPosicaoPlataforma == 0) {
            ultimaPosicaoPlataforma = 2;
        } else if (ultimaPosicaoPlataforma == 1 || ultimaPosicaoPlataforma == 3) {
            ultimaPosicaoPlataforma = 2;
        } else if (ultimaPosicaoPlataforma == 2) {
            if (lado == 1) {
                ultimaPosicaoPlataforma = 1;
            } else {
                ultimaPosicaoPlataforma = 3;
            }
        }

        //Verifica se tem alguma plataforma disponivel
        for (AGSprite plataforma : plataformas) {
            if (plataforma.bRecycled) {
                plataforma.bRecycled = false;
                plataforma.bVisible = true;
                if (ultimaPosicaoPlataforma == 1) { //Esquerda
                    plataforma.vrPosition.setX(0 + plataforma.getSpriteWidth() / 2);
                } else if (ultimaPosicaoPlataforma == 2) { //Meio
                    plataforma.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
                } else if (ultimaPosicaoPlataforma == 3) { //Direita
                    plataforma.vrPosition.setX(AGScreenManager.iScreenWidth - plataforma.getSpriteWidth() / 2);
                } else { //Default é no meio
                    plataforma.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
                }
                plataforma.vrPosition.setY((AGScreenManager.iScreenHeight + plataforma.getSpriteHeight()));
                return;
            }
        }

        AGSprite novaPlataforma = createSprite(R.drawable.plataforma, 1, 1);
        novaPlataforma.setScreenPercent(33, 5);
        if (ultimaPosicaoPlataforma == 1) { //Esquerda
            novaPlataforma.vrPosition.setX(0 + novaPlataforma.getSpriteWidth() / 2);
        } else if (ultimaPosicaoPlataforma == 2) { //Meio
            novaPlataforma.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        } else if (ultimaPosicaoPlataforma == 3) { //Direita
            novaPlataforma.vrPosition.setX(AGScreenManager.iScreenWidth - novaPlataforma.getSpriteWidth() / 2);
        } else { //Default é no Meio
            novaPlataforma.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        }
        novaPlataforma.vrPosition.setY((AGScreenManager.iScreenHeight + novaPlataforma.getSpriteHeight()));
        novaPlataforma.bVisible = true;
        plataformas.add(novaPlataforma);

    }

    private void atualizaMovimentoPlataformas() {
        if (scrollMapaAtivo) {
            for (AGSprite plataforma : plataformas) {
                if (plataforma.bRecycled)
                    continue;

                plataforma.vrPosition.fY -= velocidadeScroll;

                if (plataforma.vrPosition.fY < 0 - (plataforma.getSpriteHeight() / 2)) {
                    plataforma.bRecycled = true;
                    plataforma.bVisible = false;
                    criaPlataforma();
                }
            }
        } else if (!scrollMapaAtivo) {
            if (ninja.vrPosition.getY() >= AGScreenManager.iScreenHeight / 2) {
                scrollMapaAtivo = true;
            }
        }
    }

    private void atualizaMovimentoChao() {
        if (scrollMapaAtivo) {
            chao.vrPosition.fY -= velocidadeScroll;
        }
    }

    private boolean verificaColisaoPlataformas() {
        for (AGSprite plataforma : plataformas) {
            if (ninja.collide(plataforma) &&
                    (plataforma.vrPosition.getY() > 0 - plataforma.getSpriteHeight()) &&
                    (plataforma.vrPosition.getY() < AGScreenManager.iScreenHeight - plataforma.getSpriteHeight() / 2) &&
                    (ninja.vrPosition.getX()) > (plataforma.vrPosition.getX() - plataforma.getSpriteWidth() / 2) &&
                    (ninja.vrPosition.getX()) < (plataforma.vrPosition.getX() + plataforma.getSpriteWidth() / 2) &&
                    (ninja.vrPosition.getY() - ninja.getSpriteHeight() / 2) > (plataforma.vrPosition.getY() - plataforma.getSpriteHeight() / 2) &&
                    (ninja.vrPosition.getY() - ninja.getSpriteHeight() / 2) < (plataforma.vrPosition.getY() + plataforma.getSpriteHeight() / 2)) {
                return true;
            }
        }
        return false;
    }

    private boolean verificaColisaoChao() {
        if (ninja.collide(chao)) {
            return true;
        }
        return false;
    }

    private void verificaGameOver() {
        if (ninja.vrPosition.fY < 0 - ninja.getSpriteHeight() / 2) {
            gameOverShow();
        }
    }

    private void calculaVelocidadeScrool() {
        if(pontuacao >= 50){
            velocidadeScroll = pontuacao / 50;
        }
    }
}
