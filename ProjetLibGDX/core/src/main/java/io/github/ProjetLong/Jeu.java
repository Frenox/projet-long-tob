package io.github.ProjetLong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Jeu extends Game {
    public SpriteBatch batch;
    public FitViewport viewport;
    public BitmapFont HebertBold;

    @Override
    public void create() {

        batch = new SpriteBatch();
        HebertBold = new BitmapFont(Gdx.files.internal("HebertSansBold.fnt"));
        HebertBold.getData().setScale(0.15f);
        viewport = new FitViewport(512, 288);
        // lance l'écran de peche active
        this.setScreen(new VilleScreen(this));
        // this.setScreen(new PecheActiveScreen(this, new Barque()));
    }

    @Override
    public void render() {
        super.render();

    }

    @Override
    public void dispose() {
        batch.dispose();

    }
}
