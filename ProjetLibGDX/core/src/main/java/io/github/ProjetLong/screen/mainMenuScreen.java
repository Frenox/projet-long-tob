package io.github.ProjetLong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.ProjetLong.Jeu;
import io.github.ProjetLong.Affichage.AffichageMainMenu;

public class mainMenuScreen implements Screen {

    // Récupère le jeu (Avec le batch et la data)
    final public Jeu jeu;

    // Affichage du menu
    private AffichageMainMenu aff;

    public mainMenuScreen(final Jeu jeu) {

        this.jeu = jeu;
        aff = new AffichageMainMenu();

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

        // Permet d'afficher les coordonnées du clic souris gauche dans la console
        // utile pour connaitre des coordonés à l'écran
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector3 mouseCoor = jeu.viewport.getCamera()
                    .unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            System.out.println(mouseCoor.x + " " + mouseCoor.y);
        }
        aff.input(this);

    }

    public void logic() {
        aff.logic(this);
    }

    public void draw() {
        jeu.batch.setShader(null);
        ScreenUtils.clear(Color.BLACK);
        jeu.viewport.apply();
        jeu.batch.setProjectionMatrix(jeu.viewport.getCamera().combined);

        jeu.batch.begin();

        // Draw l'affichage
        aff.draw(this);
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