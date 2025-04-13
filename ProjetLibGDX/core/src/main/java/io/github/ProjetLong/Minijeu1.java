package io.github.ProjetLong;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture3D;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.ProjetLong.MiniJeu1.Impulsion;

public class Minijeu1 extends ApplicationAdapter implements Minijeu {

    final float POISSON_X = 50;
    final float POISSON_Y = 100;

    final float POISSON_X_MIN = 50;
    final float POISSON_X_MAX = 150;

    final float MIN_REUSSITE = 20;
    final float MAX_REUSSITE = 100;

    final float IMPULSION_DURATION = 100f;
    final float IMPULSION_TIME_STEP = 1f;
    final float IMPULSION_MAX = 20f;

    final float BAR_X = 160;
    final float BAR_Y = 90;

    private Texture poisson;
    private int State = 0;
    private Sprite fishSprite;
    private ProgressBar jauge;

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

    private int sens = 1;

    public Minijeu1() {
        poisson = new Texture("poisson.png");

        fishSprite = new Sprite(poisson);

        fishSprite.setPosition(POISSON_X, POISSON_Y);

        loadingBarBackground = new Texture("minigame1_bar_fishing.png");
        loadingBarProgress = new Texture("minigame1_indicator_fishing.png");
        fishFishing = new Texture("minigame1_fish_fishing.png");
        
        barBackgroundSprite = new Sprite(loadingBarBackground);
        barBackgroundSprite.setPosition(BAR_X, BAR_Y);
        barBackgroundSprite.setSize(MIN_REUSSITE, MAX_REUSSITE);

        barProgressSprite = new Sprite(loadingBarProgress);
        barProgressSprite.setPosition(BAR_X, BAR_Y);
        barProgressSprite.setSize(MIN_REUSSITE, MAX_REUSSITE);

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
        if (Gdx.input.isKeyJustPressed(Keys.P)) {
            float reussite = 20 + evolution;

            if (reussite > 90) {
                System.out.println("Poisson capturé avec : " + reussite + " % de réussite");
                this.State = 2;
            } else {
                System.out.println("Poisson non capturé.");
            }
        }
        if (Gdx.input.isKeyJustPressed(Keys.O)) {
            if (Impulsions.size() < 4) {
                Impulsion impulsion = new Impulsion(IMPULSION_DURATION, IMPULSION_TIME_STEP, IMPULSION_MAX);
                Impulsions.add(impulsion);
            }
        }
    }

    @Override
    public void logic(PecheActiveScreen screen) {
        float speed = 0.2f;

        fishSprite.translateX(speed * sens);

        if (fishSprite.getX() < POISSON_X_MIN || fishSprite.getX() > POISSON_X_MAX) {
            sens *= -1;
            fishSprite.flip(true, false);

        }

        evolution = 0;
        Iterator<Impulsion> iterator = this.Impulsions.listIterator();
        while (iterator.hasNext()) {
            Impulsion impulsion = iterator.next();
            impulsion.evolve();
            if (!impulsion.isFinished()) {
                evolution += impulsion.getImpulsionValue();
            } else {
                iterator.remove();
            }
        }

        tMovement += 0.05;
        YMovement = (float) Math.sin(tMovement) * 5;

        fishSprite.setY(POISSON_Y + evolution + YMovement);
        fishFishingSprite.setY(BAR_Y + 22 + evolution + YMovement);
    }

    @Override
    public void draw(PecheActiveScreen screen) {
        screen.jeu.batch.draw(barProgressSprite, BAR_X, BAR_Y, 20, MIN_REUSSITE + evolution + YMovement);
        barBackgroundSprite.draw(screen.jeu.batch);
        fishSprite.draw(screen.jeu.batch);
        fishFishingSprite.draw(screen.jeu.batch);
        
    }
}
