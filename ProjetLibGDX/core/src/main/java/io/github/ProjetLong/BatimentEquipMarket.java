package io.github.ProjetLong;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class BatimentEquipMarket implements Batiment {

    private boolean isOpened;
    private boolean isBuying;
    private boolean isEnoughCoins;

    private int selectedItem;
    private int offsetSelectedItem;

    private int page;
    private Vector3 mouse;

    private Stage stage;

    private Texture equipementInv;

    private Texture batEquipMarketTexture;
    private Sprite batEquipMarketSprite;

    private Texture interfaceBatEquipMarketTexture;
    private Sprite interfaceOverlayBatEquipMarketSprite;

    private Texture validerAchatTexture;
    private Texture refuserAchatTexture;

    private Sprite validerAchatTextureSprite;
    private Sprite refuserAchatTextureSprite;

    private Texture flecheGaucheTexture;
    private Texture flecheDroiteTexture;
    private Sprite flecheGaucheSprite;
    private Sprite flecheDroiteSprite;

    // a supprimer quand implémente
    private ArrayList<Equipement> equipements;
    private int coinAmount;

    //son
    private static final Sound cashSfx = Gdx.audio.newSound(Gdx.files.internal("audio/CashSound.mp3"));

    // 512 par 288
    @Override
    public boolean getIsOpened() {
        return isOpened;
    }

    @Override
    public void setIsOpened(boolean value) {
        isOpened = value;
    }

    public BatimentEquipMarket() {
        this.isOpened = false;
        this.isBuying = false;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.mouse = new Vector3(0, 0, 0);

        this.batEquipMarketTexture = new Texture("bat1.png");
        this.interfaceBatEquipMarketTexture = new Texture("overlayMarche.png");
        this.validerAchatTexture = new Texture("valider.png");
        this.refuserAchatTexture = new Texture("refuser.png");

        this.batEquipMarketSprite = new Sprite(batEquipMarketTexture);
        this.interfaceOverlayBatEquipMarketSprite = new Sprite(interfaceBatEquipMarketTexture);
        this.validerAchatTextureSprite = new Sprite(validerAchatTexture);
        this.refuserAchatTextureSprite = new Sprite(refuserAchatTexture);

        this.batEquipMarketSprite.setPosition(192, 90);
        this.interfaceOverlayBatEquipMarketSprite.setPosition(130, 50);
        this.validerAchatTextureSprite.setPosition(280, 90);
        this.refuserAchatTextureSprite.setPosition(335, 90);

        this.page = 0;
        // this.selectedfish = 0;
        // this.offsetSelectedFish = 0;
        this.equipementInv = new Texture("fish_tab_fish.png");

        this.flecheGaucheTexture = new Texture("minigame2_arrow_left_fishing.png");
        this.flecheDroiteTexture = new Texture("minigame2_arrow_right_fishing.png");

        this.flecheGaucheSprite = new Sprite(flecheGaucheTexture);
        this.flecheDroiteSprite = new Sprite(flecheDroiteTexture);

        this.flecheGaucheSprite.setPosition(175, 56); // Flèche gauche
        this.flecheDroiteSprite.setPosition(215, 56); // Flèche droite
        this.flecheGaucheSprite.setSize(15, 11);
        this.flecheDroiteSprite.setSize(15, 11);

        // a supprimer later
        this.equipements = new ArrayList<>();
        this.equipements.add(new CanneAPeche());
        this.equipements.add(new CanneAPeche(2));
        this.equipements.add(new CanneAPeche(3));
        this.equipements.add(new CanneAPeche(4));
        this.equipements.add(new Filet());
        this.equipements.add(new Filet(2));
        this.equipements.add(new Filet(3));
        this.equipements.add(new Filet(4));

    }

    @Override
    public void input(VilleScreen screen) {

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            this.mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            screen.jeu.viewport.getCamera().unproject(mouse);
        } else {
            this.mouse = new Vector3(0, 0, 0);
        }
    }

    @Override
    public void logic(VilleScreen screen) {

        
        this.coinAmount = screen.jeu.data.getArgent();
        if (this.isOpened) {

            if (!this.isBuying) {
                // changer de page
                if (mouse.x >= 215 && mouse.x <= 230 && mouse.y >= 66 && mouse.y <= 77
                        && page < (((equipements.size() - 1) / 7))) {
                    page++;
                }
                if (mouse.x >= 175 && mouse.x <= 190 && mouse.y >= 66 && mouse.y <= 77 && page > 0) {
                    page--;
                }
            } else if (isEnoughCoins) {
                if (mouse.x > 280 && mouse.x < 310 && mouse.y > 90 && mouse.y < 120) {
                    // on debite le joueur
                    int prix = this.equipements.get(selectedItem).getPrix();
                    if (prix <= 0) {
                        System.out.println("Cet équipement n'est pas à vendre.");
                    } else if (this.coinAmount < prix) {
                        System.out.println("Pas assez de coins pour acheter cet équipement.");
                    } else {
                        // on ajoute l'équipement à la session
                        screen.jeu.data.ajouterModule((ModuleBateau) this.equipements.get(selectedItem).dupliquer());

                        //son vente
                        cashSfx.play();
                        // on débite le joueur
                        screen.jeu.data.retirerArgent(prix);
                    }
                    this.isBuying = false;
                    this.selectedItem = 0;
                } else if (mouse.x > 335 && mouse.x < 365 && mouse.y > 90 && mouse.y < 120) {
                    this.isBuying = false;
                    this.selectedItem = 0;
                }
            } else {
                if (mouse.x > 300 && mouse.x < 330 && mouse.y > 90 && mouse.y < 120) {
                    // on ferme la vente
                    this.isBuying = false;
                    this.selectedItem = 0;
                }
            }

            // detection de la vente (identifiez quel equipement)
            if (mouse.x >= 140 && mouse.x <= 265 && mouse.y >= 77 && mouse.y <= 192) {
                int old = this.selectedItem;
                this.offsetSelectedItem = 0;
                while (mouse.y > (77 + offsetSelectedItem * 16.5)) {
                    offsetSelectedItem++;
                }
                this.selectedItem = (7 - offsetSelectedItem) + page * 7;
                // on verifie que le item est present

                if (this.selectedItem < this.equipements.size()) {
                    if (isBuying && (old == selectedItem)) {
                        this.isBuying = false;
                        this.selectedItem = 0;
                    } else {
                        this.isBuying = true;
                        this.isEnoughCoins = this.equipements.get(selectedItem).getPrix() <= this.coinAmount;
                    }
                } else {
                    this.selectedItem = 0;
                }
            }
        }
    }

    @Override
    public void draw(VilleScreen screen, int position, int offset) {
        // alwasy draw the batiment itself

        this.batEquipMarketSprite.setPosition(position * 64 + offset, 91);
        this.batEquipMarketSprite.draw(screen.jeu.batch);
        screen.jeu.HebertBold.draw(screen.jeu.batch, "Equipement", 64 * position + offset, 180);

    }

    public void affichageInterface(VilleScreen screen) {
        if (isOpened) {
            if (this.isOpened && this.isBuying) {
                affichageVente(screen);
            }
            // Affichage de l'overlay
            this.interfaceOverlayBatEquipMarketSprite.draw(screen.jeu.batch);

            // Affichage de MAGASIN D'ÉQUIPEMENTS
            screen.jeu.HebertBold.draw(screen.jeu.batch, "MAGASIN D'EQUIPEMENTS", 140, 240);

            // Affichage des coins
            // Mis en jaune, puis re en blanc
            screen.jeu.HebertBold.setColor(1, 1, 0, 1); // couleur jaune
            screen.jeu.HebertBold.draw(screen.jeu.batch, "COINS : " + this.coinAmount, 300, 240);
            screen.jeu.HebertBold.setColor(1, 1, 1, 1);

            // Affichage des instructions
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Quel equipement souhaitez-vous acheter ?", 140, 220);
            screen.jeu.HebertBold.setColor(1, 1, 1, 1);

            // Affichage des equipements possédés
            int len = this.equipements.size();
            for (int i = (page * 7); i != (7 * (page + 1)); i++) {
                if (i < len) {
                    // Déplacement de l'élément "fishInv"
                    screen.jeu.batch.draw(equipementInv, 140, 199 - ((i % 7) * 19) - 15);

                    // Déplacement du nom de l'équipement
                    screen.jeu.HebertBold.draw(screen.jeu.batch, equipements.get(i).getCategorie(), 146f,
                            210.5f - ((i % 7) * 19) - 15);
                }
            }

            // Affichage des flèches de navigation
            // Flèche gauche (seulement si on n'est pas sur la première page)
            if (page > 0) {
                this.flecheGaucheSprite.draw(screen.jeu.batch);
            }

            // Flèche droite (seulement si il y a des pages suivantes)
            if (page < (((len - 1) / 7))) {
                this.flecheDroiteSprite.draw(screen.jeu.batch);
            }
            // Affichage du numéro de page
            screen.jeu.HebertBold.draw(screen.jeu.batch, Integer.toString(page + 1), 197.5f - (5 * (page / 9)),
                    43f + 20);
            screen.jeu.HebertBold.draw(screen.jeu.batch, "/", 201.6f, 42f + 20);
            screen.jeu.HebertBold.draw(screen.jeu.batch, Integer.toString(((len - 1) / 7) + 1), 204f, 40f + 20);
        }
        if (isOpened && isBuying) {
            affichageVente(screen);
        }
    }

    private void affichageVente(VilleScreen screen) {

        if (isEnoughCoins) {
            // affichage des carrés de validation
            this.validerAchatTextureSprite.draw(screen.jeu.batch);
            this.refuserAchatTextureSprite.draw(screen.jeu.batch);

            // affichage du texte de vente
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Confirmez l'achat de :", 270, 200);
            screen.jeu.HebertBold.setColor(0, 1, 0, 1); // vert
            Equipement equip = this.equipements.get(selectedItem);
            screen.jeu.HebertBold.draw(screen.jeu.batch, equip.getCategorie(), 270, 180);
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Niveau : " + Integer.toString(equip.getNiveau()), 270, 160);
            screen.jeu.HebertBold.setColor(1, 1, 1, 1); // blanc
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Pour " + equip.getPrix() + " coins ?", 270, 140);
        } else {
            this.refuserAchatTextureSprite.setPosition(300, 90);
            this.refuserAchatTextureSprite.draw(screen.jeu.batch);
            this.refuserAchatTextureSprite.setPosition(335, 90);

            screen.jeu.HebertBold.setColor(1, 0, 0, 1); // rouge
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Pas assez de coins !", 270, 200);
            screen.jeu.HebertBold.setColor(1, 1, 1, 1); // blanc
            Equipement equip = this.equipements.get(selectedItem);
            screen.jeu.HebertBold.draw(screen.jeu.batch, equip.getCategorie(), 270, 180);
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Niveau : " + Integer.toString(equip.getNiveau()), 270, 160);
            screen.jeu.HebertBold.setColor(1, 0, 0, 1); // rouge
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Il coute " + equip.getPrix() + " coins.", 270, 140);
            screen.jeu.HebertBold.setColor(1, 1, 1, 1); // blanc
        }
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {
        this.isBuying = false;
        this.selectedItem = 0;
    }

}
