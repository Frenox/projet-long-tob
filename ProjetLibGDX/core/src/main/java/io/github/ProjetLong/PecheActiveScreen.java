package io.github.ProjetLong;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.NoMinigame;
import io.github.ProjetLong.ZonesPeche.Poisson;

public class PecheActiveScreen implements Screen {
    final Jeu jeu;
    private Bateau bateau;
    public Texture backgroundTexture;
    public Texture minigameBorder;
    private boolean minigameShow;
    private boolean inventaireShow;
    private boolean priseShow;
    public boolean menuShow;
    public Texture actualMinigameBg;
    public Texture minigameBg1;
    public Minijeu actualMinigame;
    public AffichageInventaire inventaire;
    private GlyphLayout layout = new GlyphLayout();
    private AffichagePause menu = new AffichagePause();
    private double time;
    private Poisson lastPrise;
    private int timerPrise;

    public PecheActiveScreen(final Jeu jeu, Bateau bateau) {
        this.jeu = jeu;
        this.bateau = bateau;
        backgroundTexture = new Texture("bg_actif.png");
        minigameBorder = new Texture("contour_fishing.png");
        minigameBg1 = new Texture("bg_fishing_1.png");
        minigameShow = false;
        inventaireShow = true;
        menuShow = false;
        priseShow = false;
        time = 0;
        lastPrise = new Poisson(0, 0);
        actualMinigameBg = minigameBg1;
        actualMinigame = new NoMinigame();
        inventaire = new AffichageInventaire(bateau);
        timerPrise = 0;

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

        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            if (inventaireShow) {
                inventaireShow = false;
            } else {
                inventaireShow = true;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.G) && minigameShow == false && bateau.getStockageDispo() > 0) {
            lancerMiniJeu();
        }

        // Permet d'afficher les coordonnées du clic souris gauche dans la console
        // utile pour connaitre des coordonés à l'écran
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector3 mouseCoor = jeu.viewport.getCamera()
                    .unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            System.out.println(mouseCoor.x + " " + mouseCoor.y);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            menuShow = true;
        }

        actualMinigame.input(this);
        inventaire.input(this);
        if (menuShow) {
            menu.input(this);
        }

    }

    public void logic() {
        if (actualMinigame.getState() == 2 && bateau.getStockageDispo() > 0) {
            actualMinigame = new NoMinigame();
            lastPrise = bateau.getEquipedCanne().getZone().getRandPoisson();
            bateau.addPoisson(lastPrise);
            minigameShow = false;
            priseShow = true;
            timerPrise = 300;
        }
        actualMinigame.logic(this);
        inventaire.logic(this);
        if (menuShow) {
            menu.logic(this);
        }
        bateau.addSpriteY((float) (Math.sin(time) / 50));
        time += 0.02;
        if (timerPrise < 0) {
            priseShow = false;
        }
        timerPrise--;

    }

    public void lancerMiniJeu() {
        Random rn = new Random();
        switch (rn.nextInt(3) + 1) {
            case 3:
                actualMinigame = new Minijeu3();
                minigameShow = true;
                break;
            // case 2:
            //     actualMinigame = new Minijeu2();
            //     minigameShow = true;
            //     break;
            default:
                actualMinigame = new Minijeu2();
                minigameShow = true;
                break;
        }
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
        // draw Bateau
        bateau.getSprite().draw(jeu.batch);
        if (inventaireShow) {

            inventaire.draw(this);
        }

        String text = Integer.toString(Gdx.graphics.getFramesPerSecond());

        layout.setText(jeu.HebertBold, text);
        jeu.HebertBold.draw(jeu.batch, text, 510f - layout.width, 287f);

        // montrer resultat minijeu
        if (priseShow == true) {
            jeu.batch.draw(lastPrise.getFishText(), 129, 137);
            layout.setText(jeu.HebertBold, lastPrise.getNom());
            jeu.HebertBold.draw(jeu.batch, layout, 256 - (layout.width / 2), 140 + (layout.height / 2));
            layout.setText(jeu.HebertBold, Float.toString((float) ((int) (lastPrise.getTaille() * 10)) / 10) + "cm");
            jeu.HebertBold.draw(jeu.batch, layout, 256 - (layout.width / 2), 128 + (layout.height / 2));
        }

        if (menuShow) {
            menu.draw(this);
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
