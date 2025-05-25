package io.github.ProjetLong;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class VilleScreen implements Screen {
    final Jeu jeu;
    private Sprite sun;
    private Texture sunTexture;
    public Texture backgroundTexture;
    private ShaderProgram shader;
    public boolean menuShow;
    private AffichagePause menu = new AffichagePause();
    private Batiment market = new BatimentMarket();
    private Batiment quai;

    private ArrayList<Batiment> batiments = new ArrayList<>();
    private ArrayList<ButtonBatiment> batimentsButtons = new ArrayList<>();
    private int nb_batiments = 3;

    private Texture batQuaiTexture = new Texture("bat1.png");
    Stage stage;

    public VilleScreen(final Jeu jeu) {
        this.jeu = jeu;
        menuShow = false;
        backgroundTexture = new Texture("bg_port.png");
        sunTexture = new Texture("sun.png");
        sun = new Sprite(sunTexture);
        sun.setPosition(256 - 25, 200 - 25);
        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("shaders/vertex.vert"),
                Gdx.files.internal("shaders/shader1.frag"));

        quai = new BatimentQuai(jeu.viewport);
        batiments.add(market);
        batiments.add(capitainerie);
        batiments.add(quai);
        
        Skin skin = new Skin();
        skin.add("Moulin", batQuaiTexture);
        ButtonStyle style = new ButtonStyle(skin.getDrawable("Moulin"), skin.getDrawable("Moulin"),skin.getDrawable("Moulin"));
    
        stage = new Stage(jeu.viewport);

        for(int i = 0;i < nb_batiments;i++) {
            batimentsButtons.add(new ButtonBatiment(style, i, jeu.HebertBold));
            stage.addActor(batimentsButtons.get(i));
        }
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
<<<<<<< Updated upstream
        market.input(this);
        quai.input(this);
    }

    public void logic() {
        market.logic(this);
        quai.logic(this);
=======

        for (int i = 0;i < nb_batiments;i++) {
            batiments.get(i).input(this);
        }
    }

    public void logic() {
        for (int i = 0;i < nb_batiments;i++) {
            batiments.get(i).logic(this);
        }
>>>>>>> Stashed changes
    }

    public void draw() {
        ScreenUtils.clear(Color.BLACK);
        jeu.viewport.apply();
        jeu.batch.setProjectionMatrix(jeu.viewport.getCamera().combined);
        float worldWidth = jeu.viewport.getWorldWidth();
        float worldHeight = jeu.viewport.getWorldHeight();

        jeu.batch.setShader(null);
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
<<<<<<< Updated upstream
=======
        this.jeu.batch.draw(backgroundTexture2, 0, 0);
        this.jeu.batch.draw(backgroundTexture3, 0, 0);
        // avant plan bat
        this.jeu.batch.draw(backgroundTexture4, 0, 0);
        // overlays
        
        
        Gdx.input.setInputProcessor(stage);

        stage.getViewport().apply();
        stage.act();
        stage.draw();

        for (int i = 0;i < nb_batiments;i++) {
            batiments.get(i).affichageInterface(this);
            batiments.get(i).draw(this, i);
        }
>>>>>>> Stashed changes

        if (menuShow) {
            menu.draw(this);
        }

        // DRAW DU BAT
        this.market.draw(this, 0);

        // DRAW DU QUAI/BATEAUX
        this.quai.draw(this, 1);
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

    public class ButtonBatiment extends Button {
        private int i;
        private Label textAbove;

        public ButtonBatiment(ButtonStyle buttonStyle, int i, BitmapFont font) {
            super(buttonStyle);

            this.i = i;
            this.setPosition(64 * i, 90);
            this.setVisible(true);

            LabelStyle label_style = new LabelStyle(font, Color.WHITE);
            textAbove = new Label(batiments.get(i).getNom(), label_style);
            textAbove.setPosition(64 * i + 16, 200);
            textAbove.setVisible(false);
            stage.addActor(textAbove);

            this.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    batiments.get(i).agir();
                }
                
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
                    textAbove.setVisible(true);
                }
                
                @Override
                public void exit(InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
                    textAbove.setVisible(false);
                }
            });
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
        }
    }
}