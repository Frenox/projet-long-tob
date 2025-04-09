package io.github.ProjetLong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class PecheActiveScreen implements Screen {
    final Jeu jeu;

    public Texture backgroundTexture;
    public Texture minigameBorder;
    private boolean minigameShow;
    private boolean inventaireShow;
    public Texture actualMinigameBg;
    public Texture minigameBg1;
    public Minijeu actualMinigame;
    public AffichageInventaire inventaire;
    private GlyphLayout layout = new GlyphLayout();

    public PecheActiveScreen(final Jeu jeu) {
        this.jeu = jeu;
        backgroundTexture = new Texture("bg_actif.png");
        minigameBorder = new Texture("contour_fishing.png");
        minigameBg1 = new Texture("bg_fishing_1.png");
        minigameShow = true;
        inventaireShow = true;
        actualMinigameBg = minigameBg1;
        actualMinigame = new Minijeu2();
        inventaire = new AffichageInventaire(new Barque());

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

        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            if (minigameShow) {
                minigameShow = false;
            } else {
                minigameShow = true;
            }
        }

        // Permet d'afficher les coordonnées du clic souris gauche dans la console
        // utile pour connaitre des coordonés à l'écran
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector3 mouseCoor = jeu.viewport.getCamera()
                    .unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            System.out.println(mouseCoor.x + " " + mouseCoor.y);
        }

        actualMinigame.input(this);
        inventaire.input(this);
    }

    public void logic() {
        actualMinigame.logic(this);
        inventaire.logic(this);
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
        if (inventaireShow) {

            inventaire.draw(this);
        }

        String text = Integer.toString(Gdx.graphics.getFramesPerSecond());

        layout.setText(jeu.HebertBold, text);
        jeu.HebertBold.draw(jeu.batch, text, 510f - layout.width, 287f);
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
