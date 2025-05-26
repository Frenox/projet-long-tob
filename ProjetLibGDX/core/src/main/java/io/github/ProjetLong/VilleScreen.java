package io.github.ProjetLong;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class VilleScreen implements Screen {
    final Jeu jeu;

    // Sprite du soleil
    private Sprite sun;

    // Textures utilisées
    private Texture sunTexture;
    private Texture backgroundTexture;
    private Texture backgroundTexture2;
    private Texture backgroundTexture3;
    private Texture backgroundTexture4;

    // Shader pour le rayonnement du soleil
    private ShaderProgram shader;

    // Affichage du menu
    public boolean menuShow;

    private AffichagePause menu = new AffichagePause();

    // Gestionnaire de l'affichage des batiment
    private BatimentHandler handler;

    // Son et musique d'ambience
    private static final Sound wavesSfx = Gdx.audio.newSound(Gdx.files.internal("audio/AmbientWater.mp3"));
    private static final Sound seagullSfx = Gdx.audio.newSound(Gdx.files.internal("audio/Seagull.mp3"));
    private ScheduledExecutorService seagullScheduler;

    public VilleScreen(final Jeu jeu) {
        this.jeu = jeu;
        handler = new BatimentHandler(jeu);
        menuShow = false;

        // Texture setup
        backgroundTexture = new Texture("bg_port.png");
        backgroundTexture2 = new Texture("bg_port_2.png");
        backgroundTexture3 = new Texture("bg_port_3.png");
        backgroundTexture4 = new Texture("bg_port_4.png");
        sunTexture = new Texture("sun.png");

        // setup du sprite
        sun = new Sprite(sunTexture);
        sun.setPosition(256 - 25, 200 - 25);

        // Setup du shader
        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("shaders/vertex.vert"),
                Gdx.files.internal("shaders/shaderCielPort.frag"));

        // Setup de l'audio
        wavesSfx.loop(); // Lance l'audio de vagues
        startSeagullLoop(); // Lance la boucle de sfx de mouette

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

        // Permet d'afficher les coordonnées du clic souris gauche dans la console
        // utile pour connaitre des coordonés à l'écran
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector3 mouseCoor = jeu.viewport.getCamera()
                    .unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            System.out.println(mouseCoor.x + " " + mouseCoor.y);
        }

        // INPUT MENU
        if (menuShow) {
            menu.input(this);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            menuShow = true;
        }
        // input affichage des bâtiments
        handler.input(this);
    }

    public void logic() {
        // Logique des bâtiments
        handler.logic(this);
    }

    public void draw() {
        ScreenUtils.clear(Color.BLACK);
        jeu.viewport.apply();
        jeu.batch.setProjectionMatrix(jeu.viewport.getCamera().combined);
        float worldWidth = jeu.viewport.getWorldWidth();
        float worldHeight = jeu.viewport.getWorldHeight();

        // Met le shader
        jeu.batch.setShader(shader);

        // MAJ SHADER
        Vector3 tempProj = new Vector3(sun.getX() + 25, sun.getY() + 25, 0);
        jeu.viewport.getCamera().project(tempProj);
        shader.setUniformf("u_sunLocation", tempProj.x, tempProj.y);
        shader.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        jeu.batch.begin();

        // affiche le fond d'écran
        jeu.batch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        sun.draw(jeu.batch);

        jeu.batch.end();
        jeu.batch.setShader(null);
        jeu.batch.begin();

        // AFFICHAGE DU RESTE
        for (int i = 0; i < 2; i++) {
            this.jeu.batch.draw(backgroundTexture2, 0 - handler.getOffset() / 4 + 1024 * i, 0);
        }
        for (int i = 0; i < 4; i++) {
            this.jeu.batch.draw(backgroundTexture3, 0 - handler.getOffset() + 512 * i, 0);
        }
        // DRAW DU BAT
        this.handler.draw(this);
        // avant plan bat
        for (int i = 0; i < 4; i++) {
            this.jeu.batch.draw(backgroundTexture4, 0 - handler.getOffset() + 512 * i, 0);
        }
        // overlays
        this.handler.affichageInterface(this);
        // menu pause
        if (menuShow) {
            menu.draw(this);
        }

        jeu.batch.end();

        // Re set du shader (Marche pas sinon, sais pas pourquoi)
        jeu.batch.setShader(shader);
        jeu.batch.begin();
        jeu.batch.end();

    }

    @Override
    public void dispose() {

    }

    @Override
    public void hide() {
        wavesSfx.stop(); // Stoppe l'audio de vagues
        seagullSfx.stop(); // Stoppe l'audio de mouette
        seagullScheduler.shutdownNow(); // Stope la boucle de sfx de mouette
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

    /* Initialise la boucle d'audio pour le sfx de mouette */
    private void startSeagullLoop() {
        seagullScheduler = Executors.newSingleThreadScheduledExecutor();
        scheduleSeagullSound();
    }

    private void scheduleSeagullSound() {
        int delay = 20 + (int) (Math.random() * 35);

        // Joue l'audio apres [delay] secondes
        seagullScheduler.schedule(() -> {
            seagullSfx.play();
            scheduleSeagullSound();
        }, delay, TimeUnit.SECONDS);
    }
}