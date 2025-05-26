package io.github.ProjetLong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
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

    // temporaire tant que pas de serializer de bat
    private Batiment market;
    private Batiment capitainerie;
    private Batiment shop;
    private Batiment quai;
    private Batiment chantier;

    @Override
    public void create() {
        batch = new SpriteBatch();
        data = new DataManager();

        viewport = new FitViewport(512, 288);
        Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setFullscreenMode(currentMode);
        // a la main pour le moment
        data.ajouterModule(new Stockage(1));
        data.ajouterModule(new CanneAPeche(1, new SousZoneCotePort()));
        data.ajouterModule(new Stockage(1));
        data.ajouterBateauPort(new Barque());
        data.ajouterBateauPort(new Voilier());
        data.ajouterBateauPort(new Voilier());

        quai = new BatimentQuai(viewport, data);
        market = new BatimentMarket(data);
        chantier = new BatimentChantierNaval();
        capitainerie = new BatimentCapitainerie();
        shop = new BatimentEquipMarket();

        data.ajouterBatiment(capitainerie);
        data.ajouterBatiment(market);
        data.ajouterBatiment(quai);
        data.ajouterBatiment(chantier);
        data.ajouterBatiment(shop);

        HebertBold = new BitmapFont(Gdx.files.internal("HebertSansBold.fnt"));
        HebertBold.getData().setScale(0.15f);

        // lance l'écran
        try {
            this.setScreen(new mainMenuScreen(this));
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
