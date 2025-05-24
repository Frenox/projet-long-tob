package io.github.ProjetLong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class VilleScreen implements Screen {
    final Jeu jeu;
    private Sprite sun;
    private Texture sunTexture;
    public Texture backgroundTexture;
    public Texture backgroundTexture2;
    public Texture backgroundTexture3;
    public Texture backgroundTexture4;
    private ShaderProgram shader;
    public boolean menuShow;
    private AffichagePause menu = new AffichagePause();
    private Batiment market = new BatimentMarket();
    private Batiment capitainerie = new BatimentCapitainerie();
    private Batiment marcheEquipement = new BatimentEquipMarket();
    private Batiment quai;

    public VilleScreen(final Jeu jeu) {
        this.jeu = jeu;
        menuShow = false;
        backgroundTexture = new Texture("bg_port.png");
        backgroundTexture2 = new Texture("bg_port_2.png");
        backgroundTexture3 = new Texture("bg_port_3.png");
        backgroundTexture4 = new Texture("bg_port_4.png");
        sunTexture = new Texture("sun.png");
        sun = new Sprite(sunTexture);
        sun.setPosition(256 - 25, 200 - 25);
        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("shaders/vertex.vert"),
                Gdx.files.internal("shaders/shaderCielPort.frag"));

        quai = new BatimentQuai(jeu.viewport);
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
        market.input(this);
        capitainerie.input(this);
        quai.input(this);
        marcheEquipement.input(this);
    }

    public void logic() {
        market.logic(this);
        capitainerie.logic(this);
        quai.logic(this);
        marcheEquipement.logic(this);
    }

    public void draw() {
        ScreenUtils.clear(Color.BLACK);
        jeu.viewport.apply();
        jeu.batch.setProjectionMatrix(jeu.viewport.getCamera().combined);
        float worldWidth = jeu.viewport.getWorldWidth();
        float worldHeight = jeu.viewport.getWorldHeight();

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
        this.jeu.batch.draw(backgroundTexture2, 0, 0);
        this.jeu.batch.draw(backgroundTexture3, 0, 0);
        // DRAW DU BAT
        this.market.draw(this, 0);
        this.capitainerie.draw(this, 1);
        this.quai.draw(this, 2);
        this.marcheEquipement.draw(this, 3);
        // avant plan bat
        this.jeu.batch.draw(backgroundTexture4, 0, 0);
        // overlays
        market.affichageInterface(this);
        capitainerie.affichageInterface(this);
        quai.affichageInterface(this);
        marcheEquipement.affichageInterface(this);

        // menu pause
        if (menuShow) {
            menu.draw(this);
        }

        jeu.batch.end();
        jeu.batch.setShader(shader);
        jeu.batch.begin();

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