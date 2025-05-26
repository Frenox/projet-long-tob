package io.github.ProjetLong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.ProjetLong.DataManager.DataManager;
import io.github.ProjetLong.ZonesPeche.SousZoneCotePort;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Jeu extends Game {

    // Le batch est la zone de dessin de la fenêtre, il faut qu'il soit accessible
    // directement à toute classe qui veux dessiner quelque chose
    public SpriteBatch batch;

    // Le viewport est la fenêtre du jeu mais redimensionné avec des coordonnés
    // nouvelles
    // par exemple le viemport fait 512*288 pixels mais la fenetre s'adapte à la
    // taille de l'écran
    public FitViewport viewport;

    // Police d'écriture utilisée partout
    public BitmapFont HebertBold;

    // DataManager
    // le data manager est l'endroit ou les données de la sauvegarde actuelle sont
    // stockées
    // Les classes qui en ont besoin peuvent y accéder
    public DataManager data;
    public AmbientSoundManager soundManager;

    // Temporaire tant que pas de serializer de batiment, on ajoute les batiments
    // par défaut
    private Batiment market;
    private Batiment capitainerie;
    private Batiment shop;
    private Batiment quai;
    private Batiment chantier;

    @Override
    public void create() {

        // Créé le batch
        batch = new SpriteBatch();

        // Créé le viewport
        viewport = new FitViewport(512, 288);
        data = new DataManager();
        soundManager = new AmbientSoundManager();

        // Setup des batiment par défaut
        quai = new BatimentQuai(viewport, data);
        market = new BatimentMarket(data);
        chantier = new BatimentChantierNaval();
        capitainerie = new BatimentCapitainerie();
        shop = new BatimentEquipMarket();

        // Créé la data par défaut
        data.ajouterModule(new Stockage(1));
        data.ajouterModule(new CanneAPeche(1, new SousZoneCotePort()));
        data.ajouterModule(new Stockage(1));
        data.ajouterBateauPort(new Barque());
        data.ajouterBateauPort(new Voilier());
        data.ajouterBateauPort(new Voilier());
        data.ajouterBatiment(capitainerie);
        data.ajouterBatiment(market);
        data.ajouterBatiment(quai);
        data.ajouterBatiment(chantier);
        data.ajouterBatiment(shop);

        // Setup en full screen par défaut
        Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setFullscreenMode(currentMode);

        // Setup de la police d'écriture
        HebertBold = new BitmapFont(Gdx.files.internal("HebertSansBold.fnt"));
        HebertBold.getData().setScale(0.15f);

        // lance l'écran et renvoie l'erreur sinon
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
