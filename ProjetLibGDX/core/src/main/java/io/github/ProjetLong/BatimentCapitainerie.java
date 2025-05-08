package io.github.ProjetLong;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.ProjetLong.ZonesPeche.Poisson;

public class BatimentCapitainerie implements Batiment {
    private boolean isOpened;

    private int page;

    private Vector3 mouse;

    private Texture batCapitainerieTexture;
    private Sprite batCapitainerieSprite;

    private GlyphLayout layout = new GlyphLayout();
    private Texture NomBat;
    private Texture buttonCap1;
    private Texture buttonCap2;
    private Texture buttonCap3;
    private Texture buttonCap4;
    private Texture boatCard;
    private Texture interfaceBatCapitainerieTexture;
    private Sprite interfaceOverlaybatCapitainerieSprite;

    // A SUPPRIMER
    private DataManager data;

    public BatimentCapitainerie() {
        this.isOpened = false;

        this.mouse = new Vector3(0, 0, 0);

        this.batCapitainerieTexture = new Texture("bat1.png");
        this.interfaceBatCapitainerieTexture = new Texture("overlayMarche.png");
        this.NomBat = new Texture("nom_cap.png");
        this.boatCard = new Texture("boat_card.png");
        this.buttonCap1 = new Texture("button_cap1.png");
        this.buttonCap2 = new Texture("button_cap2.png");
        this.buttonCap3 = new Texture("button_cap3.png");
        this.buttonCap4 = new Texture("button_cap4.png");

        this.batCapitainerieSprite = new Sprite(batCapitainerieTexture);
        this.interfaceOverlaybatCapitainerieSprite = new Sprite(interfaceBatCapitainerieTexture);

        this.batCapitainerieSprite.setPosition(0, 90);
        this.interfaceOverlaybatCapitainerieSprite.setPosition(130, 50);

        this.page = 0;

        // A Supprimer
        data = new DataManager();
        data.ajouterBateauPort(new Barque());
        data.ajouterBateauPort(new Voilier());

    }

    @Override
    public void input(VilleScreen screen) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            if (this.isOpened == true) {
                this.isOpened = false;
            } else {
                this.isOpened = true;
            }
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            this.mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            screen.jeu.viewport.getCamera().unproject(mouse);
        } else {
            this.mouse = new Vector3(0, 0, 0);
        }
    }

    @Override
    public void logic(VilleScreen screen) {

        if (this.isOpened) {

        }
    }

    @Override
    public void draw(VilleScreen screen, int position) {
        // alwasy draw the batiment itself
        this.batCapitainerieSprite.draw(screen.jeu.batch);
        this.batCapitainerieSprite.setPosition(64 * position, 90);

    }

    @Override
    public void affichageInterface(VilleScreen screen) {
        if (isOpened) {
            // Affichage de l'overlay
            this.interfaceOverlaybatCapitainerieSprite.draw(screen.jeu.batch);
            screen.jeu.batch.draw(NomBat, 130, 231);
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Nom", 142, 229);
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Modele", 180, 229);
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Etat", 180, 229);
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Temps", 260, 229);
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Lieu", 142, 229);
            // Draw bateau
            List<Bateau> temp = data.getBateaux();
            int len = temp.size();
            for (int i = (page * 6); i != (6 * (page + 1)); i++) {
                if (i < len) {
                    screen.jeu.batch.draw(boatCard, 136, 197 - ((i % 6) * 25));
                    screen.jeu.HebertBold.draw(screen.jeu.batch, temp.get(i).getName(), 142, 212 - ((i % 6) * 25));
                    layout.setText(screen.jeu.HebertBold, temp.get(i).getState());
                    screen.jeu.HebertBold.draw(screen.jeu.batch, layout, 251 - layout.width / 2, 212 - ((i % 6) * 25));
                    screen.jeu.batch.draw(temp.get(i).getLogo(), 219, 201 - ((i % 6) * 25));

                    // boutons
                    screen.jeu.batch.draw(buttonCap1, 269, 201 - ((i % 6) * 25));
                    screen.jeu.batch.draw(buttonCap2, 286, 201 - ((i % 6) * 25));
                    screen.jeu.batch.draw(buttonCap3, 316, 201 - ((i % 6) * 25));
                    screen.jeu.batch.draw(buttonCap4, 360, 199 - ((i % 6) * 25));
                }
            }
        }
    }

}
