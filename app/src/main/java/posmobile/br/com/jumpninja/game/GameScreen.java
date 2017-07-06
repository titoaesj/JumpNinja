package posmobile.br.com.jumpninja.game;

import posmobile.br.com.andgraph.AGGameManager;
import posmobile.br.com.andgraph.AGInputManager;
import posmobile.br.com.andgraph.AGScene;
import posmobile.br.com.andgraph.AGScreenManager;
import posmobile.br.com.andgraph.AGSoundManager;
import posmobile.br.com.andgraph.AGSprite;
import posmobile.br.com.andgraph.AGTimer;

import java.util.ArrayList;

import posmobile.br.com.jumpninja.R;

/**
 * Project JumpNinja
 * Desenvolvido por Tito Albino Evangelista da Silva Junior em 05/07/17.
 */

public class GameScreen extends AGScene {

    AGSprite[] placar = new AGSprite[6];

    ArrayList<AGSprite> vetorTiros = null;
    ArrayList<AGSprite> vectorExplosao = null;

    AGSprite[] vectorNavios = new AGSprite[2];

    AGTimer tempoCanhao = null;
    AGTimer tempoBala = null;

    int efeitoCatracaCod = 0;
    int efeitoExplosaoCod = 0;
    int pontuacao = 0;
    int tempoPontuacao = 0;

    boolean pauseGAME = false;

    AGSprite planoFundo = null;
    AGSprite canhao = null;
    AGSprite barraSuperior = null;

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

        // Carega item na memória
        createSprite(R.drawable.explosao, 4, 2).bVisible = false;
        createSprite(R.drawable.bala, 1, 1).bVisible = false;

        vetorTiros = new ArrayList<>();
        vectorExplosao = new ArrayList<>();

        /**
         * Senário do jogo
         */
        planoFundo = createSprite(R.drawable.textmar, 1, 1);
        planoFundo.setScreenPercent(100, 100);
        planoFundo.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        planoFundo.vrPosition.setY(AGScreenManager.iScreenHeight / 2);

        /**
         * Criar o Sprite da Barra superior
         */
        barraSuperior = createSprite(R.drawable.barrasuperior, 1, 1);
        barraSuperior.setScreenPercent(100, 10);
        barraSuperior.vrPosition.fX = AGScreenManager.iScreenWidth / 2;
        barraSuperior.vrPosition.fY
                = AGScreenManager.iScreenHeight - barraSuperior.getSpriteHeight() / 2;
        barraSuperior.bAutoRender = false;

        /**
         * Criar o Sprite do Canhão
         */
        canhao = createSprite(R.drawable.canhao, 1, 1);
        canhao.setScreenPercent(12, 20);
        canhao.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        canhao.vrPosition.setY(canhao.iFrameHeight / 2);

        /**
         * Configura os Sprites do placar
         */
        int multiplicador = 1;
        for (int pos = 0; pos < placar.length; pos++) {
            placar[pos] = createSprite(R.drawable.fonte, 4, 4);
            placar[pos].setScreenPercent(10, 10);
            placar[pos].vrPosition.fY = barraSuperior.vrPosition.getY();
            placar[pos].vrPosition.fX = 20 + multiplicador * placar[pos].getSpriteWidth();
            placar[pos].bAutoRender = false;
            multiplicador++;

            for (int i = 0; i < 10; i++) {
                placar[pos].addAnimation(1, false, i);
            }

        }

        tempoCanhao = new AGTimer(100);
        tempoBala = new AGTimer(300);
        efeitoCatracaCod = AGSoundManager.vrSoundEffects.loadSoundEffect("toc.wav");
        efeitoExplosaoCod = AGSoundManager.vrSoundEffects.loadSoundEffect("explodenavio.wav");

        vectorNavios[0] = createSprite(R.drawable.navio, 1, 1);
        vectorNavios[0].setScreenPercent(20, 12);
        vectorNavios[0].iMirror = AGSprite.HORIZONTAL;
        vectorNavios[0].vrDirection.fX = 1.0f;
        vectorNavios[0].vrPosition.fX = -vectorNavios[0].getSpriteWidth() / 2;
        vectorNavios[0].vrPosition.fY = AGScreenManager.iScreenHeight - vectorNavios[0].getSpriteHeight() / 2 - barraSuperior.getSpriteHeight();

        vectorNavios[1] = createSprite(R.drawable.navio, 1, 1);
        vectorNavios[1].setScreenPercent(20, 12);
        vectorNavios[1].vrDirection.fX = -1.0f;
        vectorNavios[1].vrPosition.fX = AGScreenManager.iScreenWidth + vectorNavios[1].getSpriteWidth() / 2;
        vectorNavios[1].vrPosition.fY = vectorNavios[0].vrPosition.fY - vectorNavios[1].getSpriteHeight();


    }

    @Override
    public void render() {
        super.render();
        barraSuperior.render();
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

        if (AGInputManager.vrTouchEvents.backButtonClicked()) {
            vrGameManager.setCurrentScene(1);
        }

//        if (AGInputManager.vrTouchEvents.screenClicked()) {
//            pauseGAME = ! pauseGAME;
//        }
//
//        if ( ! pauseGAME) {
        atualizaMovimentoCanhao();
        atualizaBalas();
        criaTiro();
        atualizaNavios();

        verificaColisaoBalasNavios();
        atualizaExplosoes();
        atualizaPlacar();
//        }

    }

    /**
     * Método que movimenta o canhão
     */
    private void atualizaMovimentoCanhao() {

        tempoCanhao.update();

        if (tempoCanhao.isTimeEnded()) {

            tempoCanhao.restart();

            if (AGInputManager.vrAccelerometer.getAccelX() > 2.0f
                    && canhao.vrPosition.getX() <= AGScreenManager.iScreenWidth - (canhao.getSpriteWidth() / 2)) {

                canhao.vrPosition.setX(canhao.vrPosition.getX() + 10);
                AGSoundManager.vrSoundEffects.play(efeitoCatracaCod);

            }

            if (AGInputManager.vrAccelerometer.getAccelX() < -2.0f
                    && canhao.vrPosition.getX() >= (canhao.getSpriteWidth() / 2)) {

                canhao.vrPosition.setX(canhao.vrPosition.getX() - 10);
                AGSoundManager.vrSoundEffects.play(efeitoCatracaCod);

            }

        }
    }

    /**
     * Método para atualizar o movimento
     * das balas
     */
    private void atualizaBalas() {
        for (AGSprite bala : vetorTiros) {
            if (bala != null) {
                bala.vrPosition.fY += 10;

                if (bala.vrPosition.fY >
                        AGScreenManager.iScreenHeight + bala.getSpriteHeight() / 2) {
                    bala.bRecycled = true;
                    bala.bVisible = false;
                }
            }
        }
    }

    /**
     * Coloca uma bala no vetor de tiros
     */
    private void criaTiro() {

        tempoBala.update();

        if (AGInputManager.vrTouchEvents.screenClicked()) {

            AGSoundManager.vrSoundEffects.play(efeitoExplosaoCod);

            if (!tempoBala.isTimeEnded()) {
                return;
            }

            tempoBala.restart();

            for (AGSprite bala : vetorTiros) {

                // Tenta reciclar uma bala criada anteriormente
                if (bala.bRecycled) {
                    bala.bRecycled = false;
                    bala.bVisible = true;
                    bala.vrPosition.fX = canhao.vrPosition.fX;
                    bala.vrPosition.fY = canhao.getSpriteHeight() + bala.getSpriteHeight() / 2;

                    return;
                }
            }

            AGSprite novaBala = createSprite(R.drawable.bala, 1, 1);
            novaBala.setScreenPercent(8, 5);
            novaBala.vrPosition.fX = canhao.vrPosition.fX;
            novaBala.vrPosition.fY = canhao.getSpriteHeight() + novaBala.getSpriteHeight() / 2;
            vetorTiros.add(novaBala);
        }
    }

    /**
     * Método que atuliza os vectorNavios
     */
    private void atualizaNavios() {
        for (AGSprite navio : vectorNavios) {
            if (navio.vrPosition != null) {
                navio.vrPosition.fX += 5 * navio.vrDirection.fX;

                if (navio.vrDirection.fX == 1) {
                    if (navio.vrPosition.fX
                            >= AGScreenManager.iScreenWidth + navio.getSpriteWidth() / 2) {
                        navio.iMirror = AGSprite.NONE;
                        navio.vrDirection.fX = -1;
                    }
                } else {
                    if (navio.vrPosition.fX
                            <= -navio.getSpriteWidth() / 2) {
                        navio.iMirror = AGSprite.HORIZONTAL;
                        navio.vrDirection.fX = 1;
                    }
                }
            }
        }
    }

    /**
     * Método utilizado para reciclar as explosões
     */
    private void atualizaExplosoes() {
        for (AGSprite explosao : vectorExplosao) {
            if (explosao.getCurrentAnimation().isAnimationEnded()) {
                explosao.bRecycled = true;
            }
        }
    }

    /**
     * Método para colisão da bala com o navio
     */
    private void verificaColisaoBalasNavios() {
        for (AGSprite bala : vetorTiros) {
            if (bala.bRecycled)
                continue;

            for (AGSprite navio : vectorNavios) {
                if (bala.collide(navio)) {

                    tempoPontuacao += 50;

                    criaExplosao(navio.vrPosition.fX, navio.vrPosition.fY);

                    bala.bRecycled = true;
                    bala.bVisible = false;

                    AGSoundManager.vrSoundEffects.play(efeitoExplosaoCod);

                    if (navio.vrDirection.fX == 1) {
                        navio.vrDirection.fX = -1;
                        navio.iMirror = AGSprite.NONE;
                        navio.vrPosition.fX = AGScreenManager.iScreenWidth + navio.getSpriteWidth() / 2;
                    } else {
                        navio.vrDirection.fX = 1;
                        navio.iMirror = AGSprite.HORIZONTAL;
                        navio.vrPosition.fX = -navio.getSpriteWidth();
                    }
                    break;
                }
            }
        }
    }

    /**
     * Método utilizado para ciar uma explosão
     */
    public void criaExplosao(float x, float y) {

        for (AGSprite explosao : vectorExplosao) {
            if (explosao.bRecycled) {
                explosao.bRecycled = false;
                explosao.getCurrentAnimation().restart();
                explosao.vrPosition.fX = x;
                explosao.vrPosition.fY = y;
                return;
            }
        }

        AGSprite novaExplosao = createSprite(R.drawable.explosao, 4, 2);
        novaExplosao.setScreenPercent(20, 12);
        novaExplosao.addAnimation(10, false, 0, 7);
        novaExplosao.vrPosition.fX = x;
        novaExplosao.vrPosition.fY = y;
        vectorExplosao.add(novaExplosao);
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
}
