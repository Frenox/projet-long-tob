package io.github.ProjetLong;

import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class PecheActiveScreen implements Screen {
    final Jeu jeu;

    public Texture backgroundTexture;
    public Texture minigameBorder;
    public boolean minigameShow;
    public Texture actualMinigameBg;
    public Texture minigameBg1;
    public Minijeu actualMinigame;

    public PecheActiveScreen(final Jeu jeu) {
        this.jeu = jeu;
        backgroundTexture = new Texture("bg_actif.png");
        minigameBorder = new Texture("contour_fishing.png");
        minigameBg1 = new Texture("bg_fishing_1.png");
        minigameShow = true;
        actualMinigameBg = minigameBg1;
        actualMinigame = new Minijeu1();
    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();
    }

    public void input() {

        // FULLSCREEN
        // met en plein écran quand on appuie sur tab utile pour voir le jeu en plus
        // grand car le resize de la fenetre est vérouillé pour avoir le même ratio
        // constament;
        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            Boolean fullScreen = Gdx.graphics.isFullscreen();
            Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
            if (fullScreen == true)
                Gdx.graphics.setWindowedMode(512 * 2, 288 * 2);
            else
                Gdx.graphics.setFullscreenMode(currentMode);
        }

        actualMinigame.input(this);
    }

    public void logic() {
        actualMinigame.logic(this);
    }

    public void draw() {
        ScreenUtils.clear(Color.BLACK);
        jeu.viewport.apply();
        jeu.batch.setProjectionMatrix(jeu.viewport.getCamera().combined);
        float worldWidth = jeu.viewport.getWorldWidth();
        float worldHeight = jeu.viewport.getWorldHeight();

        jeu.batch.begin();

        // affiche le fond d'écran
        jeu.batch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);

        // affiche la partie du minijeu si il le faut
        if (minigameShow) {
            jeu.batch.draw(minigameBorder, 21, 77);
            jeu.batch.draw(actualMinigameBg, 25, 81);
            actualMinigame.draw(this);
        }

        jeu.batch.end();

    }

    @Override
    public void dispose() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        jeu.viewport.update(width, height, true);
    }
}
