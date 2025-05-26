package io.github.ProjetLong.minijeu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import io.github.ProjetLong.screen.PecheActiveScreen;

public class Minijeu1 extends ApplicationAdapter implements Minijeu {

    // constante de position
    final float POISSON_X = 50;
    final float POISSON_Y = 100;
    final float POISSON_Y_MAX = 190;

    final float POISSON_X_MIN = 50;
    final float POISSON_X_MAX = 127;

    // Constante du niveau du poisson pour réussir
    final float MIN_REUSSITE = 20;
    final float MAX_REUSSITE = 100;

    // Position de la barre
    final float BAR_X = 160;
    final float BAR_Y = 90;

    final float BAR_PROGRESSION_WIDTH = 13;

    // Affichage du poisson
    private Texture poisson;
    private int State = 0;
    private Sprite fishSprite;

    // Sprite
    private Sprite barBackgroundSprite;
    private Sprite barProgressSprite;
    private Sprite fishFishingSprite;

    // Texture
    private Texture loadingBarBackground;
    private Texture loadingBarProgress;
    private Texture fishFishing;

    // Récupérer l'état actuel
    private float evolution;
    private float YMovement;
    private float tMovement;

    // Variables d'actions
    private int cooldown = 0;
    private int sens = 1;
    private float ecartTexture;

    public Minijeu1() {

        // Texture du poisson
        poisson = new Texture("poisson.png");

        // Sprite du poisson
        fishSprite = new Sprite(poisson);
        fishSprite.setPosition(POISSON_X, POISSON_Y);

        // Barre setup
        loadingBarBackground = new Texture("minigame1_bar_fishing.png");
        loadingBarProgress = new Texture("minigame1_indicator_fishing.png");
        fishFishing = new Texture("minigame1_fish_fishing.png");

        fishFishingSprite = new Sprite(fishFishing);
        fishFishingSprite.setPosition(BAR_X + 4, BAR_Y + 22);
        ecartTexture = (loadingBarBackground.getHeight() - loadingBarProgress.getHeight());

        // sprites de la barre
        barBackgroundSprite = new Sprite(loadingBarBackground);
        barBackgroundSprite.setPosition(BAR_X, BAR_Y);
        barBackgroundSprite.setSize(20, MAX_REUSSITE);

        barProgressSprite = new Sprite(loadingBarProgress);
        barProgressSprite.setPosition(BAR_X + 4, BAR_Y + 3);
        barProgressSprite.setSize(BAR_PROGRESSION_WIDTH, MIN_REUSSITE - ecartTexture);

        // Setup des variables d'actions
        evolution = 0;
        tMovement = 0;
        YMovement = 0;
    }

    @Override
    public int getState() {

        return State;
    }

    @Override
    public void input(PecheActiveScreen screen) {

        boolean reussite = fishSprite.getY() > POISSON_Y_MAX;

        // Fin du minijeu si assez de réussite
        if (reussite) {
            this.State = 2;
        }

        // Recupère la touche d'ation du minijeu
        if (Gdx.input.isKeyJustPressed(Keys.SPACE) && cooldown < 0) {
            if ((float) Math.abs(Math.sin(tMovement)) > 0.95) {
                evolution += 0.3;
                System.out.println((float) Math.abs(Math.sin(tMovement)));
            }

            cooldown = 40;
        }
    }

    @Override
    public void logic(PecheActiveScreen screen) {
        // Gère le cooldown et la vitesse
        float speed = 0.4f;
        cooldown--;
        fishSprite.translateX(speed * sens);

        // Si le poisson est trop loin il se retourne
        if (fishSprite.getX() < POISSON_X_MIN || fishSprite.getX() > POISSON_X_MAX) {
            sens *= -1;
            fishSprite.flip(true, false);

        }

        // Le poisson redescend au fur et à mesure
        evolution -= 0.003f;

        // Variable de temps faisant le déplacement de poisson
        tMovement += 0.05;
        YMovement = (float) Math.sin(tMovement) * 5;

        fishSprite.translateY(evolution);
        if (fishSprite.getY() < POISSON_Y) {
            evolution = 0;
            fishSprite.setY(POISSON_Y);
        }
        fishFishingSprite.setY(BAR_Y + 45 + (float) Math.cos(tMovement) * 40);
        barProgressSprite.setSize(BAR_PROGRESSION_WIDTH, MIN_REUSSITE + evolution + YMovement - ecartTexture);
    }

    @Override
    public void draw(PecheActiveScreen screen) {
        barBackgroundSprite.draw(screen.jeu.batch);

        fishSprite.draw(screen.jeu.batch);
        fishFishingSprite.draw(screen.jeu.batch);

    }
}
