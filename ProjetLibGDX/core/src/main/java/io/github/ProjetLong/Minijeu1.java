package io.github.ProjetLong;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import io.github.ProjetLong.MiniJeu1.Impulsion;

public class Minijeu1 extends ApplicationAdapter implements Minijeu {

    final float POISSON_X = 50;
    final float POISSON_Y = 100;
    final float POISSON_Y_MAX = 190;

    final float POISSON_X_MIN = 50;
    final float POISSON_X_MAX = 127;

    final float MIN_REUSSITE = 20;
    final float MAX_REUSSITE = 100;

    final float IMPULSION_DURATION = 200f;
    final float IMPULSION_TIME_STEP = 1f;
    final float IMPULSION_MAX = 5f;

    final float BAR_X = 160;
    final float BAR_Y = 90;

    final float BAR_PROGRESSION_WIDTH = 13;

    private Texture poisson;
    private int State = 0;
    private Sprite fishSprite;

    private Sprite barBackgroundSprite;
    private Sprite barProgressSprite;
    private Sprite fishFishingSprite;

    private Texture loadingBarBackground;
    private Texture loadingBarProgress;
    private Texture fishFishing;

    private ArrayList<Impulsion> Impulsions;
    private float evolution;
    private float YMovement;
    private float tMovement;

    private int cooldown = 0;
    private int sens = 1;
    private float ecartTexture;

    public Minijeu1() {
        poisson = new Texture("poisson.png");

        fishSprite = new Sprite(poisson);

        fishSprite.setPosition(POISSON_X, POISSON_Y);

        loadingBarBackground = new Texture("minigame1_bar_fishing.png");
        loadingBarProgress = new Texture("minigame1_indicator_fishing.png");
        fishFishing = new Texture("minigame1_fish_fishing.png");

        ecartTexture = (loadingBarBackground.getHeight() - loadingBarProgress.getHeight());

        barBackgroundSprite = new Sprite(loadingBarBackground);
        barBackgroundSprite.setPosition(BAR_X, BAR_Y);
        barBackgroundSprite.setSize(20, MAX_REUSSITE);

        barProgressSprite = new Sprite(loadingBarProgress);
        barProgressSprite.setPosition(BAR_X + 4, BAR_Y + 3);
        barProgressSprite.setSize(BAR_PROGRESSION_WIDTH, MIN_REUSSITE - ecartTexture);

        fishFishingSprite = new Sprite(fishFishing);
        fishFishingSprite.setPosition(BAR_X + 4, BAR_Y + 22);

        Impulsions = new ArrayList<Impulsion>();

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

        if (reussite) {
            System.out.println("Poisson capturé avec : " + reussite + " % de réussite");
            this.State = 2;
        }

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
        float speed = 0.4f;
        cooldown--;
        fishSprite.translateX(speed * sens);

        if (fishSprite.getX() < POISSON_X_MIN || fishSprite.getX() > POISSON_X_MAX) {
            sens *= -1;
            fishSprite.flip(true, false);

        }

        evolution -= 0.003f;

        tMovement += 0.05;
        YMovement = (float) Math.sin(tMovement) * 5;

        fishSprite.translateY(evolution);
        if (fishSprite.getY() < POISSON_Y) {
            evolution = 0;
            fishSprite.setY(POISSON_Y);
        }
        fishFishingSprite.setY(BAR_Y + 45 + (float) Math.cos(tMovement) * 35);
        barProgressSprite.setSize(BAR_PROGRESSION_WIDTH, MIN_REUSSITE + evolution + YMovement - ecartTexture);
    }

    @Override
    public void draw(PecheActiveScreen screen) {
        barBackgroundSprite.draw(screen.jeu.batch);

        fishSprite.draw(screen.jeu.batch);
        fishFishingSprite.draw(screen.jeu.batch);

    }
}
