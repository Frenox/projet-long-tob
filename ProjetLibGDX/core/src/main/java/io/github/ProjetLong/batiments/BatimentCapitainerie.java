package io.github.ProjetLong.batiments;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

import io.github.ProjetLong.Bateaux.Barque;
import io.github.ProjetLong.Bateaux.Bateau;
import io.github.ProjetLong.equipementetmodule.Voile;
import io.github.ProjetLong.screen.PecheActiveScreen;
import io.github.ProjetLong.screen.VilleScreen;

public class BatimentCapitainerie implements Batiment {
    // Es-ce que le batiment est ouvert
    private boolean isOpened;

    // Numéro de page
    private int page;

    // Coordonnés de la souris
    private Vector3 mouse;
    private Vector3 mouseUnclick;

    // Sprite du batiment et de l'overlay
    private Sprite batCapitainerieSprite;
    private Sprite interfaceOverlaybatCapitainerieSprite;

    // Layout pour centrer le texte
    private GlyphLayout layout = new GlyphLayout();

    // Textures nécessaires
    private Texture batCapitainerieTexture;
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
    private Texture boatCard;
    private Texture interfaceBatCapitainerieTexture;

    // Variable de position des menus
    private int Menu3pos = 0;
    private int Dispo = 0;
    private int Menu2pos = -1;

    public BatimentCapitainerie() {
        this.isOpened = false;

        // Position de la souris
        this.mouse = new Vector3(0, 0, 0);

        // Import des textures
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

        // setup des sprite
        this.batCapitainerieSprite = new Sprite(batCapitainerieTexture);
        this.interfaceOverlaybatCapitainerieSprite = new Sprite(interfaceBatCapitainerieTexture);

        this.batCapitainerieSprite.setPosition(0, 91);
        this.interfaceOverlaybatCapitainerieSprite.setPosition(130, 50);

        // Initialisation de la page
        this.page = 0;

    }

    @Override
    public void input(VilleScreen screen) {

        // Récupération de la position de la souris
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
    }

    @Override
    public void draw(VilleScreen screen, int position, int offset) {
        // alwasy draw the batiment itself
        this.batCapitainerieSprite.draw(screen.jeu.batch);
        this.batCapitainerieSprite.setPosition(64 * position + offset, 91);
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

                    // Draw infos bateau
                    screen.jeu.batch.draw(boatCard, 136, 197 - ((i % 6) * 25));
                    screen.jeu.HebertBold.draw(screen.jeu.batch, temp.get(i).getName(), 142, 212 - ((i % 6) * 25));
                    layout.setText(screen.jeu.HebertBold, temp.get(i).getState());
                    screen.jeu.HebertBold.draw(screen.jeu.batch, layout, 251 - layout.width / 2, 212 - ((i % 6) * 25));
                    screen.jeu.batch.draw(temp.get(i).getLogo(), 219, 201 - ((i % 6) * 25));

                    // boutons
                    screen.jeu.batch.draw(buttonCap1, 270, 201 - ((i % 6) * 25));

                    // bouton Temps
                    if (mouseUnclick.x >= 287 && mouseUnclick.x <= 291 + 24 && mouseUnclick.y >= 201 - ((i % 6) * 25)
                            && mouseUnclick.y <= 216 - ((i % 6) * 25)) {
                        screen.jeu.batch.draw(buttonCap2Shine, 287, 201 - ((i % 6) * 25));
                        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                            // affichage selection menu
                            Menu2pos = i;

                        }
                    } else {
                        screen.jeu.batch.draw(buttonCap2, 287, 201 - ((i % 6) * 25));

                    }

                    // draw Durée sélectionnée
                    if (temp.get(i).getDureeSelec() != 0) {
                        screen.jeu.HebertBold.draw(screen.jeu.batch,
                                String.valueOf(temp.get(i).getDureeSelec()) + " J", 290,
                                212 - ((i % 6) * 25));
                    } else {
                        screen.jeu.HebertBold.draw(screen.jeu.batch, "Non", 290,
                                212 - ((i % 6) * 25));
                    }

                    // Bouton lieu
                    if (mouseUnclick.x >= 317 && mouseUnclick.x <= 321 + 38 && mouseUnclick.y >= 201 - ((i % 6) * 25)
                            && mouseUnclick.y <= 216 - ((i % 6) * 25)) {
                        screen.jeu.batch.draw(buttonCap3Shine, 317, 201 - ((i % 6) * 25));
                        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                            List<Voile> tempVoile = temp.get(i).getVoiles();
                            Dispo = 0;
                            for (Voile voile : tempVoile) {
                                if (voile.getNiveau() > Dispo) {
                                    Dispo = voile.getNiveau() + 1;
                                }
                            }
                            if (temp.get(i) instanceof Barque && Dispo == 0) {
                                Dispo = 2;
                            }
                            // affichage selection menu
                            Menu3pos = i;

                        }
                    } else {
                        screen.jeu.batch.draw(buttonCap3, 317, 201 - ((i % 6) * 25));
                    }

                    // bouton bateau raccourcis
                    screen.jeu.batch.draw(buttonCap4, 361, 199 - ((i % 6) * 25));

                    // bouton partir
                    if (temp.get(i).getState() == "A quai") {
                        if (mouseUnclick.x >= 270 && mouseUnclick.x <= 287 && mouseUnclick.y >= 201 - ((i % 6) * 25)
                                && mouseUnclick.y <= 216 - ((i % 6) * 25)) {
                            screen.jeu.batch.draw(buttonCap11Shine, 272, 203 - ((i % 6) * 25));
                            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                                screen.jeu.setScreen(
                                        new PecheActiveScreen(screen.jeu, screen.jeu.data.getBateaux().get(i)));
                            }
                        } else {
                            screen.jeu.batch.draw(buttonCap11, 272, 203 - ((i % 6) * 25));
                        }
                    }

                }
            }

            // menu déroulant lieu
            if (Dispo != 0) {
                String[] nom = { "Aucun", "Cote", "Ocean", "Tropique", "Arctique" };

                screen.jeu.batch.draw(buttonCap31h, 317, 190 - ((Menu3pos % 6) * 25));
                for (int i = 0; i < Dispo - 1; i++) {
                    screen.jeu.batch.draw(buttonCap31, 317, 190 - ((Menu3pos % 6) * 25) - 12 * (i + 1));
                }
                screen.jeu.batch.draw(buttonCap31b, 317, 190 - ((Menu3pos % 6) * 25) - 12 * (Dispo - 1));

                for (int i = 0; i < Dispo; i++) {
                    screen.jeu.HebertBold.draw(screen.jeu.batch, nom[i], 319,
                            212 - ((Menu3pos % 6) * 25) - 12 * (i + 1));
                }

                if (mouseUnclick.x > 321 + 38 || mouseUnclick.x < 317 || mouseUnclick.y > 216 - ((Menu3pos % 6) * 25)
                        || mouseUnclick.y < 190 - ((Menu3pos % 6) * 25) - 12 * (Dispo - 1)) {
                    Dispo = 0;
                    Menu3pos = 0;
                }

                // menu déroulant temps
            } else if (Menu2pos != -1) {
                String[] nom = { "Non", "1", "2", "3", "5", "10", "20" };

                screen.jeu.batch.draw(buttonCap21h, 287, 190 - ((Menu2pos % 6) * 25));
                for (int i = 0; i < nom.length - 1; i++) {
                    screen.jeu.batch.draw(buttonCap21, 287, 190 - ((Menu2pos % 6) * 25) - 12 * (i + 1));
                }
                screen.jeu.batch.draw(buttonCap21b, 287, 190 - ((Menu2pos % 6) * 25) - 12 * (nom.length - 1));

                for (int i = 0; i < nom.length; i++) {
                    screen.jeu.HebertBold.draw(screen.jeu.batch, nom[i], 290,
                            212 - ((Menu2pos % 6) * 25) - 12 * (i + 1));
                    if (i > 4) {
                        screen.jeu.HebertBold.draw(screen.jeu.batch, "J", 299,
                                213 - ((Menu2pos % 6) * 25) - 12 * (i + 1));
                    } else if (i > 0) {
                        screen.jeu.HebertBold.draw(screen.jeu.batch, "J", 294,
                                213 - ((Menu2pos % 6) * 25) - 12 * (i + 1));
                    }
                }

                // Je sais plus ce que ça fait
                if (mouseUnclick.x > 291 + 24 || mouseUnclick.x < 287 || mouseUnclick.y > 216 - ((Menu2pos % 6) * 25)
                        || mouseUnclick.y < 190 - ((Menu2pos % 6) * 25) - 12 * (nom.length - 1)) {

                    Menu2pos = -1;
                }
            }

        }
    }

    @Override
    public boolean getIsOpened() {
        return this.isOpened;
    }

    @Override
    public void setIsOpened(boolean value) {
        this.isOpened = value;
    }

    @Override
    public void close() {
    }

    @Override
    public void open() {
    }
}
