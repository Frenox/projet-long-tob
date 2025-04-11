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
import java.util.Random;

public class PecheActiveScreen implements Screen {
    final Jeu jeu;
    private Bateau bateau;
    public Texture backgroundTexture;
    public Texture minigameBorder;
    private boolean minigameShow;
    private boolean inventaireShow;
    public boolean menuShow;
    public Texture actualMinigameBg;
    public Texture minigameBg1;
    public Minijeu actualMinigame;
    public AffichageInventaire inventaire;
    private GlyphLayout layout = new GlyphLayout();
    private AffichagePause menu = new AffichagePause();

    public PecheActiveScreen(final Jeu jeu, Bateau bateau) {
        this.jeu = jeu;
        this.bateau = bateau;
        backgroundTexture = new Texture("bg_actif.png");
        minigameBorder = new Texture("contour_fishing.png");
        minigameBg1 = new Texture("bg_fishing_1.png");
        minigameShow = true;
        inventaireShow = true;
        menuShow = false;
        actualMinigameBg = minigameBg1;
        actualMinigame = new Minijeu2();
        inventaire = new AffichageInventaire(bateau);

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
        actualMinigame.logic(this);
        inventaire.logic(this);
        if (menuShow) {
            menu.logic(this);
        }
    }

    public void attente() {
        Random randomNumbers = new Random();
        //Initialisation
        boolean attente=True;
        int n=3; // Nombre de minijeu
        int NumeroMiniJeu;
        CanneAPeche CanneAPecheActuel;
        Stockage StockageActuel
        while (attente) {
            //Ouvrir l'inventaire quand on clique
            Stockage.getTailleDisponible();
            Stockage.getContenu();
            Stockage.getNiveau();
            Stockage.getTailleMax();
            Stockage.getTailleDisponible();
            //Change les statistiques de la canne à pêche
            //CanneAPecheActuel=
            if (commencerLaPeche) { //Clique sur le bouton pour commencer la peche
                attente=False;
            }
        }
    }
    public void lancerMiniJeu() {
        NumeroMiniJeu=randomNumbers.nextInt(n)+1; //Choisit un mini jeu random entre 1 et n
        switch (NumeroMiniJeu) {
            case 1:
                //Minijeu1();
                //Récupère la fin du mini jeu (succes ou non)
                break;
            case 2:
                //Minijeu2();
                //lancer le mini jeu 2 et récupère la fin
                break;
            default:
                // lancer le mini jeu 3 et récupère la fin
                break;
        }
        if (succes) {
            //getpoisson
            Stockage.addPoisson(Poisson poisson);
        } else {
            null
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
        if (inventaireShow) {

            inventaire.draw(this);
        }
        if (menuShow) {
            menu.draw(this);
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
