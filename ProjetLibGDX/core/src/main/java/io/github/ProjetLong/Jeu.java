package io.github.ProjetLong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.ProjetLong.DataManager.DataManager;
import io.github.ProjetLong.ZonesPeche.Poisson;
import io.github.ProjetLong.ZonesPeche.SousZoneCotePort;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Jeu extends Game {
    public SpriteBatch batch;
    public FitViewport viewport;
    public BitmapFont HebertBold;
    public DataManager data;

    @Override
    public void create() {
        batch = new SpriteBatch();
        data = new DataManager();

        // a la main pour le moment
        data.ajouterModule(new Stockage(1));
        data.ajouterModule(new Stockage(1));
        data.ajouterModule(new CanneAPeche(1, new SousZoneCotePort()));
        data.ajouterBateauPort(new Barque());
        data.ajouterBateauPort(new Voilier());
        data.ajouterBateauPort(new Voilier());
        data.ajouterPoissonStockage(new Poisson(1, 0, false));
        data.ajouterPoissonStockage(new Poisson(1, 0, false));
        data.ajouterPoissonStockage(new Poisson(1, 0, false));
        data.ajouterPoissonStockage(new Poisson(3, 0, false));
        data.ajouterPoissonStockage(new Poisson(1, 0, false));
        data.ajouterPoissonStockage(new Poisson(3, 1, false));
        data.ajouterPoissonStockage(new Poisson(666, 0, false));

        HebertBold = new BitmapFont(Gdx.files.internal("HebertSansBold.fnt"));
        HebertBold.getData().setScale(0.15f);
        viewport = new FitViewport(512, 288);
        // lance l'écran
        try {
            this.setScreen(new VilleScreen(this));
        } catch (Exception e) {
            e.printStackTrace(); // Affiche l’erreur réelle
        }

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
