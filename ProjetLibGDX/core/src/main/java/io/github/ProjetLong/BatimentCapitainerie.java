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

import io.github.ProjetLong.DataManager.DataManager;
import io.github.ProjetLong.ZonesPeche.Poisson;
import io.github.ProjetLong.ZonesPeche.Zone;

public class BatimentCapitainerie implements Batiment {
    private boolean isOpened;

    private int page;

    private Vector3 mouse;
    private Vector3 mouseUnclick;

    private Texture batCapitainerieTexture;
    private Sprite batCapitainerieSprite;

    private GlyphLayout layout = new GlyphLayout();
    private Texture NomBat;
    private Texture buttonCap1;
    private Texture buttonCap11;
    private Texture buttonCap11Shine;
    private Texture buttonCap2;
    private Texture buttonCap21;
    private Texture buttonCap21b;
    private Texture buttonCap21h;
    private Texture buttonCap31;
    private Texture buttonCap31b;
    private Texture buttonCap31h;
    private Texture buttonCap3;
    private Texture buttonCap3Shine;
    private Texture buttonCap2Shine;
    private Texture buttonCap4;
    private int Dispo = 0;
    private Texture boatCard;
    private Texture interfaceBatCapitainerieTexture;
    private Sprite interfaceOverlaybatCapitainerieSprite;
    private int Menu3pos = 0;
    private int Menu2pos = -1;

    public BatimentCapitainerie() {
        this.isOpened = false;

        this.mouse = new Vector3(0, 0, 0);

        this.batCapitainerieTexture = new Texture("bat1.png");
        this.interfaceBatCapitainerieTexture = new Texture("overlayMarche.png");
        this.NomBat = new Texture("nom_cap.png");
        this.boatCard = new Texture("boat_card.png");
        this.buttonCap1 = new Texture("button_cap1.png");
        this.buttonCap11 = new Texture("button_cap1_1.png");
        this.buttonCap11Shine = new Texture("button_cap1_1shine.png");
        this.buttonCap2 = new Texture("button_cap2.png");
        this.buttonCap31 = new Texture("button_cap2_1.png");
        this.buttonCap31b = new Texture("button_cap2_1b.png");
        this.buttonCap31h = new Texture("button_cap2_1h.png");
        this.buttonCap21 = new Texture("button_cap3_1.png");
        this.buttonCap21b = new Texture("button_cap3_1b.png");
        this.buttonCap21h = new Texture("button_cap3_1h.png");
        this.buttonCap3 = new Texture("button_cap3.png");
        this.buttonCap3Shine = new Texture("button_cap3shine.png");
        this.buttonCap2Shine = new Texture("button_cap2shine.png");
        this.buttonCap4 = new Texture("button_cap4.png");

        this.batCapitainerieSprite = new Sprite(batCapitainerieTexture);
        this.interfaceOverlaybatCapitainerieSprite = new Sprite(interfaceBatCapitainerieTexture);

        this.batCapitainerieSprite.setPosition(0, 90);
        this.interfaceOverlaybatCapitainerieSprite.setPosition(130, 50);

        this.page = 0;

        // A Supprimer

    }

    @Override
    public void input(VilleScreen screen) {
        this.mouseUnclick = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        screen.jeu.viewport.getCamera().unproject(mouseUnclick);
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
    public void draw(VilleScreen screen, int position, int offset) {
        // alwasy draw the batiment itself
        this.batCapitainerieSprite.draw(screen.jeu.batch);
        this.batCapitainerieSprite.setPosition(64 * position + offset, 90);
        screen.jeu.HebertBold.draw(screen.jeu.batch, "Capitainerie", 64 * position + offset, 180);
    }

    @Override
    public void affichageInterface(VilleScreen screen) {
        if (isOpened) {
            // Affichage de l'overlay
            this.interfaceOverlaybatCapitainerieSprite.draw(screen.jeu.batch);
            screen.jeu.batch.draw(NomBat, 130, 231);
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Nom", 172, 229);
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Etat", 243, 229);
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Passif", 286, 229);
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Lieu", 327, 229);
            // Draw bateau
            List<Bateau> temp = screen.jeu.data.getBateaux();
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

                    // bouton Temps
                    if (mouseUnclick.x >= 286 && mouseUnclick.x <= 290 + 24 && mouseUnclick.y >= 201 - ((i % 6) * 25)
                            && mouseUnclick.y <= 216 - ((i % 6) * 25)) {
                        screen.jeu.batch.draw(buttonCap2Shine, 286, 201 - ((i % 6) * 25));
                        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                            // affichage selection menu
                            Menu2pos = i;

                        }
                    } else {
                        screen.jeu.batch.draw(buttonCap2, 286, 201 - ((i % 6) * 25));
                    }
                    // bouton lieu
                    if (mouseUnclick.x >= 316 && mouseUnclick.x <= 320 + 38 && mouseUnclick.y >= 201 - ((i % 6) * 25)
                            && mouseUnclick.y <= 216 - ((i % 6) * 25)) {
                        screen.jeu.batch.draw(buttonCap3Shine, 316, 201 - ((i % 6) * 25));
                        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                            List<Voile> tempVoile = temp.get(i).getVoiles();
                            Dispo = 0;
                            for (Voile voile : tempVoile) {
                                if (voile.getNiveau() > Dispo) {
                                    Dispo = voile.getNiveau();
                                }
                            }
                            if (temp.get(i) instanceof Barque && Dispo == 0) {
                                Dispo = 1;
                            }
                            // affichage selection menu
                            Menu3pos = i;

                        }
                    } else {
                        screen.jeu.batch.draw(buttonCap3, 316, 201 - ((i % 6) * 25));
                    }
                    // bouton bateau raccourcis
                    screen.jeu.batch.draw(buttonCap4, 360, 199 - ((i % 6) * 25));
                    // bouton partir
                    if (temp.get(i).getState() == "A quai") {
                        if (mouseUnclick.x >= 269 && mouseUnclick.x <= 286 && mouseUnclick.y >= 201 - ((i % 6) * 25)
                                && mouseUnclick.y <= 216 - ((i % 6) * 25)) {
                            screen.jeu.batch.draw(buttonCap11Shine, 271, 203 - ((i % 6) * 25));
                            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                                screen.jeu.setScreen(
                                        new PecheActiveScreen(screen.jeu, screen.jeu.data.getBateaux().get(i)));
                            }
                        } else {
                            screen.jeu.batch.draw(buttonCap11, 271, 203 - ((i % 6) * 25));
                        }
                    }

                }
            }
            // menu déroulant lieu
            if (Dispo != 0) {
                String[] nom = { "Cote", "Ocean", "Tropique", "Arctique" };

                screen.jeu.batch.draw(buttonCap31h, 316, 190 - ((Menu3pos % 6) * 25));
                for (int i = 0; i < Dispo - 1; i++) {
                    screen.jeu.batch.draw(buttonCap31, 316, 190 - ((Menu3pos % 6) * 25) - 12 * (i + 1));
                }
                screen.jeu.batch.draw(buttonCap31b, 316, 190 - ((Menu3pos % 6) * 25) - 12 * (Dispo - 1));

                for (int i = 0; i < Dispo; i++) {
                    screen.jeu.HebertBold.draw(screen.jeu.batch, nom[i], 318,
                            212 - ((Menu3pos % 6) * 25) - 12 * (i + 1));
                }

                if (mouseUnclick.x > 320 + 38 || mouseUnclick.x < 316 || mouseUnclick.y > 216 - ((Menu3pos % 6) * 25)
                        || mouseUnclick.y < 190 - ((Menu3pos % 6) * 25) - 12 * (Dispo - 1)) {
                    Dispo = 0;
                    Menu3pos = 0;
                }
                // menu déroulant temps
            } else if (Menu2pos != -1) {
                String[] nom = { "Non", "1", "2", "3", "5", "10", "20" };

                screen.jeu.batch.draw(buttonCap21h, 286, 190 - ((Menu2pos % 6) * 25));
                for (int i = 0; i < nom.length - 1; i++) {
                    screen.jeu.batch.draw(buttonCap21, 286, 190 - ((Menu2pos % 6) * 25) - 12 * (i + 1));
                }
                screen.jeu.batch.draw(buttonCap21b, 286, 190 - ((Menu2pos % 6) * 25) - 12 * (nom.length - 1));

                for (int i = 0; i < nom.length; i++) {
                    screen.jeu.HebertBold.draw(screen.jeu.batch, nom[i], 289,
                            212 - ((Menu2pos % 6) * 25) - 12 * (i + 1));
                    if (i > 4) {
                        screen.jeu.HebertBold.draw(screen.jeu.batch, "J", 298,
                                213 - ((Menu2pos % 6) * 25) - 12 * (i + 1));
                    } else if (i > 0) {
                        screen.jeu.HebertBold.draw(screen.jeu.batch, "J", 293,
                                213 - ((Menu2pos % 6) * 25) - 12 * (i + 1));
                    }
                }

                if (mouseUnclick.x > 290 + 24 || mouseUnclick.x < 286 || mouseUnclick.y > 216 - ((Menu2pos % 6) * 25)
                        || mouseUnclick.y < 190 - ((Menu2pos % 6) * 25) - 12 * (nom.length - 1)) {

                    Menu2pos = -1;
                }
            }

        }
    }

    public boolean getIsOpened() {
        return this.isOpened;
    }

    public void setIsOpened(boolean value) {
        this.isOpened = value;
    }    

    public void close() {
    }

    public void open(){
    }
}
