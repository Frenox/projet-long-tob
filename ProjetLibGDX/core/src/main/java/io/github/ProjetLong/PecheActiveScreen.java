package io.github.ProjetLong;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import io.github.ProjetLong.ZonesPeche.Poisson;

public class PecheActiveScreen implements Screen {
    final Jeu jeu;
    private Sprite sun;
    private Texture sunTexture;
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
    private ShaderProgram shader;
    private ShaderProgram shaderEau;

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
        sunTexture = new Texture("sun.png");
        sun = new Sprite(sunTexture);
        sun.setPosition(256 - 25, 200 - 25);
        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("shaders/vertex.vert"),
                Gdx.files.internal("shaders/shader1.frag"));
        shaderEau = new ShaderProgram(Gdx.files.internal("shaders/vertex.vert"),
                Gdx.files.internal("shaders/shaderEau.frag"));

        jeu.soundManager.startMinigameMusic();

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
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            // avant de retourner à la ville
            while (jeu.data.reste1place() && this.bateau.getContenu().size() != 0) {
                jeu.data.ajouterPoissonStockage(this.bateau.remFirstPoisson());
                System.out.println(bateau.getContenu().size());

            }
            jeu.soundManager.stopMinigameMusic();
            jeu.setScreen(new VilleScreen(jeu));
        }
        // Permet d'afficher les coordonnées du clic souris gauche dans la console
        // utile pour connaitre des coordonés à l'écran
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector3 mouseCoor = jeu.viewport.getCamera()
                    .unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            System.out.println(mouseCoor.x + " " + mouseCoor.y);
        }
        if (menuShow) {
            menu.input(this);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            menuShow = true;
            jeu.soundManager.pauseAudio();
        }

        actualMinigame.input(this);
        inventaire.input(this);

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
        if (bateau.getEquipedCanne() != null) {
            Random rn = new Random();
            switch (rn.nextInt(4)) {
                case 0:
                    actualMinigame = new Minijeu1();
                    minigameShow = true;// Minijeu1();
                    // Récupère la fin du mini jeu (succes ou non)

                    break;
                case 1:
                    // Minijeu2();
                    // lancer le mini jeu 2 et récupère la fin
                    actualMinigame = new Minijeu2();
                    minigameShow = true;
                    break;
                case 2:
                    // Minijeu3();
                    // lancer le mini jeu 3 et récupère la fin
                    actualMinigame = new Minijeu3();
                    minigameShow = true;
                    break;
                default:
                    actualMinigame = new Minijeu4();
                    minigameShow = true;
                    break;
            }
        }
    }

    public void draw() {
        ScreenUtils.clear(Color.BLACK);
        jeu.viewport.apply();
        jeu.batch.setProjectionMatrix(jeu.viewport.getCamera().combined);
        float worldWidth = jeu.viewport.getWorldWidth();
        float worldHeight = jeu.viewport.getWorldHeight();

        jeu.batch.setShader(shader);
        jeu.batch.begin();

        // affiche le fond d'écran
        jeu.batch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        sun.draw(jeu.batch);

        // PARTIE EAU
        jeu.batch.end();

        jeu.batch.setShader(shaderEau);

        shaderEau.setUniformf("u_time", TimeUtils.millis() / 1000f);
        shaderEau.setUniformf("u_strength", 0.01f); // tuning
        shaderEau.setUniformf("u_opacity", 0.4f); // tuning
        shaderEau.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        jeu.batch.setShader(null);
        jeu.batch.begin();
        bateau.getSprite().draw(jeu.batch);
        // affiche la partie du minijeu si il le faut
        if (minigameShow) {
            jeu.batch.draw(minigameBorder, 21, 77);
            jeu.batch.draw(actualMinigameBg, 25, 81);
            actualMinigame.draw(this);
        }

        // draw Bateau
        if (inventaireShow) {

            inventaire.draw(this);
        }
        // montrer resultat minijeu
        if (priseShow == true) {
            jeu.batch.draw(lastPrise.getFishText(), 129, 137);
            layout.setText(jeu.HebertBold, lastPrise.getNom());
            jeu.HebertBold.draw(jeu.batch, layout, 256 - (layout.width / 2), 140 + (layout.height / 2));
            layout.setText(jeu.HebertBold, Float.toString((float) ((int) (lastPrise.getTaille() * 10)) / 10) + "cm");
            jeu.HebertBold.draw(jeu.batch, layout, 256 - (layout.width / 2), 128 + (layout.height / 2));
        }
        jeu.batch.end();
        jeu.batch.setShader(shader);
        jeu.batch.begin();

        String text = Integer.toString(Gdx.graphics.getFramesPerSecond());

        layout.setText(jeu.HebertBold, text);
        jeu.HebertBold.draw(jeu.batch, text, 510f - layout.width, 287f);

        if (menuShow) {
            menu.draw(this);
        }

        // SHADERS INFORMATIONS

        // shader soleil
        Vector3 tempProj = new Vector3(sun.getX() + 25, sun.getY() + 25, 0);
        jeu.viewport.getCamera().project(tempProj);
        shader.setUniformf("u_sunLocation", tempProj.x, tempProj.y);
        shader.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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