package io.github.ProjetLong.batiments;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiModele;
import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiVue;
import io.github.ProjetLong.DataManager.DataManager;
import io.github.ProjetLong.screen.VilleScreen;

public class BatimentQuai implements Batiment {
    public static final Texture batQuaiTexture = new Texture("bat1.png");
    private BitmapFont HebertBold;

    DataManager data;

    private boolean isOpened;

    Stage stage;
    Table root;

    boolean poissonDessin;
    Texture poisson_texture_hover;

    Texture pixmaptex;
    private Texture NomBat;

    private int width;
    private int height;

    public BatimentQuai(Viewport viewport, DataManager data) {
        HebertBold = new BitmapFont(Gdx.files.internal("HebertSansBold.fnt"));
        HebertBold.getData().setScale(0.15f);

        this.isOpened = false;
        this.data = data;

        height = BatimentQuaiVue.fishInv.getHeight() * 7 + 82;
        width = 370;

        pixmaptex = new Texture("overlayQuai.png");
        NomBat = new Texture("nom_quais.png");

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        BatimentQuaiModele modele = new BatimentQuaiModele(this.data);
        root = new BatimentQuaiVue(this, modele);

        stage.addActor(root);

        root.setFillParent(true);
    }

    @Override
    public void input(VilleScreen screen) {

    }

    @Override
    public void logic(VilleScreen screen) {
        this.data = screen.jeu.data;
    }

    @Override
    public void draw(VilleScreen screen, int position, int offset) {
        screen.jeu.batch.draw(batQuaiTexture, 0f + position * batQuaiTexture.getWidth() + offset, 91);
        screen.jeu.HebertBold.draw(screen.jeu.batch, "Quai", 64 * position + offset, 180);

    }

    public void addActor(Actor actor) {
        this.stage.addActor(actor);
        ;
    }

    @Override
    public void affichageInterface(VilleScreen screen) {
        if (this.isOpened) {
            int epsilon = 8;
            screen.jeu.batch.draw(this.pixmaptex, screen.jeu.viewport.getWorldWidth() / 2 - width / 2,
                    screen.jeu.viewport.getWorldHeight() / 2 - height / 2 + epsilon);
            screen.jeu.batch.draw(this.NomBat, 71, 233);
        }

        screen.jeu.batch.end();

        Gdx.input.setInputProcessor(stage);

        stage.getViewport().apply();
        stage.act();
        stage.draw();

        screen.jeu.batch.begin();
    }

    public boolean getIsOpened() {
        return this.isOpened;
    }

    public void setIsOpened(boolean value) {
        this.isOpened = value;
    }

    public void close() {
        root.setVisible(false);
    }

    public void open() {
        root.setVisible(true);
    }
}
