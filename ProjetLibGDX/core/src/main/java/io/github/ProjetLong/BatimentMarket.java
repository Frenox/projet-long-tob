package io.github.ProjetLong;

import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.ProjetLong.DataManager.DataManager;
import io.github.ProjetLong.ZonesPeche.Poisson;

public class BatimentMarket implements Batiment {

    // etat d'ouverture du batiment
    private boolean isOpened;
    // etat de vente ou non
    private boolean isSelling;
    // argent joueur
    private int coinAmount;
    // poisson selectionné en vente
    private int selectedfish;
    private int offsetSelectedFish;
    // page de poisson actuelle
    private int page;
    // position curseur
    private Vector3 mouse;

    private Stage stage;

    // texture et sprite pour tout le batiment
    private Texture fishInv;
    private Texture batMarketTexture;
    private Sprite batMarketSprite;
    private Texture interfaceBatMarketTexture;
    private Sprite interfaceOverlayBatMarketSprite;
    private Texture validerVenteTexture;
    private Texture refuserVenteTexture;
    private Sprite validerVenteTextureSprite;
    private Sprite refuserVenteTextureSprite;
    private Texture flecheGaucheTexture;
    private Texture flecheDroiteTexture;
    private Sprite flecheGaucheSprite;
    private Sprite flecheDroiteSprite;

    // liste des poissons du marché
    private List<Poisson> poissons;

    // son
    private static final Sound cashSfx = Gdx.audio.newSound(Gdx.files.internal("audio/CashSound.mp3"));

    public BatimentMarket(DataManager data) {

        // batiment ferme et pas en vente
        this.isOpened = false;
        this.isSelling = false;

        // ecran du port
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // initialisation
        this.mouse = new Vector3(0, 0, 0);

        // texture
        this.batMarketTexture = new Texture("bat1.png");
        this.interfaceBatMarketTexture = new Texture("overlayMarche.png");
        this.validerVenteTexture = new Texture("valider.png");
        this.refuserVenteTexture = new Texture("refuser.png");
        this.flecheGaucheTexture = new Texture("minigame2_arrow_left_fishing.png");
        this.flecheDroiteTexture = new Texture("minigame2_arrow_right_fishing.png");
        this.fishInv = new Texture("fish_tab_fish.png");

        // sprite
        this.batMarketSprite = new Sprite(batMarketTexture);
        this.interfaceOverlayBatMarketSprite = new Sprite(interfaceBatMarketTexture);
        this.validerVenteTextureSprite = new Sprite(validerVenteTexture);
        this.refuserVenteTextureSprite = new Sprite(refuserVenteTexture);
        this.flecheGaucheSprite = new Sprite(flecheGaucheTexture);
        this.flecheDroiteSprite = new Sprite(flecheDroiteTexture);

        // position initiale des sprites
        this.interfaceOverlayBatMarketSprite.setPosition(130, 50);
        this.validerVenteTextureSprite.setPosition(280, 90);
        this.refuserVenteTextureSprite.setPosition(335, 90);
        this.flecheGaucheSprite.setPosition(175, 56); // Flèche gauche
        this.flecheDroiteSprite.setPosition(215, 56); // Flèche droite
        this.flecheGaucheSprite.setSize(15, 11);
        this.flecheDroiteSprite.setSize(15, 11);

        // initialisation de plusieurs variables
        this.page = 0;
        this.selectedfish = 0;
        this.offsetSelectedFish = 0;

        // liste des poissons
        this.poissons = data.getStockage();

    }

    @Override
    public void input(VilleScreen screen) {
        // recupere les coordonnees du curseur si clic gauche
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            this.mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            screen.jeu.viewport.getCamera().unproject(mouse);
        } else {
            this.mouse = new Vector3(0, 0, 0);
        }
    }

    @Override
    public void logic(VilleScreen screen) {
        // recupere les poissons et l'argent du joueur
        this.poissons = screen.jeu.data.getStockage();
        this.coinAmount = screen.jeu.data.getArgent();

        // si ouvert mais pas en vente
        if (this.isOpened && this.isSelling == false) {
            // changer de page
            if (mouse.x >= 215 && mouse.x <= 230 && mouse.y >= 56 && mouse.y <= 67
                    && page < (((poissons.size() - 1) / 7))) {
                page++;
            }
            if (mouse.x >= 175 && mouse.x <= 190 && mouse.y >= 56 && mouse.y <= 67 && page > 0) {
                page--;
            }

            // detection de la vente (identifiez quel poisson)
            if (mouse.x >= 140 && mouse.x <= 265 && mouse.y >= 70 && mouse.y < 200) {
                while (mouse.y > (70 + offsetSelectedFish * 18.75)) {
                    offsetSelectedFish++;
                    System.out.println(offsetSelectedFish);
                }
                this.selectedfish = (7 - offsetSelectedFish) + page * 7;
                // on verifie que le poisson est present
                if (this.selectedfish < this.poissons.size()) {
                    this.isSelling = true;
                } else {
                    this.selectedfish = 0;
                }
            }
        }

        // si ouvert et en vente
        if (this.isOpened && this.isSelling == true) {
            // validation vente
            if (mouse.x > 280 && mouse.x < 310 && mouse.y > 90 && mouse.y < 120) {
                // on crédite le joueur
                screen.jeu.data.ajouterArgent(this.poissons.get(selectedfish).getPrix());
                // son vente
                cashSfx.play();

                // on enlève le poisson dans la session ET dans le data manager
                this.poissons.remove(selectedfish);

                // reset des variables
                this.isSelling = false;
                this.selectedfish = 0;
                this.offsetSelectedFish = 0;
            }
            // refus vente
            if (mouse.x > 335 && mouse.x < 365 && mouse.y > 90 && mouse.y < 120) {
                this.isSelling = false;
                this.selectedfish = 0;
                this.offsetSelectedFish = 0;
            }
        }
    }

    @Override
    public void draw(VilleScreen screen, int position, int offset) {
        // dessine le batiment
        this.batMarketSprite.setPosition(position * 64 + offset, 91);
        this.batMarketSprite.draw(screen.jeu.batch);
        screen.jeu.HebertBold.draw(screen.jeu.batch, "Market", 64 * position + offset, 180);
    }

    public void affichageInterface(VilleScreen screen) {
        if (isOpened) {
            // Affichage de l'overlay
            this.interfaceOverlayBatMarketSprite.draw(screen.jeu.batch);

            // Affichage de MARKET
            screen.jeu.HebertBold.draw(screen.jeu.batch, "MARCHE AUX POISSONS", 140, 240);

            // Affichage des coins
            // Mis en jaune, puis re en blanc
            screen.jeu.HebertBold.setColor(1, 1, 0, 1); // couleur jaune
            screen.jeu.HebertBold.draw(screen.jeu.batch, "COINS : " + this.coinAmount, 300, 240);
            screen.jeu.HebertBold.setColor(1, 1, 1, 1);

            // Affichage des instructions
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Quel poisson souhaitez-vous vendre ?", 140, 220);
            screen.jeu.HebertBold.setColor(1, 1, 1, 1);

            // Affichage des poissons possédés
            int len = this.poissons.size();
            for (int i = (page * 7); i != (7 * (page + 1)); i++) {
                if (i < len) {
                    // Déplacement de l'élément "fishInv"
                    screen.jeu.batch.draw(fishInv, 140, 199 - ((i % 7) * 19) - 15);

                    // Déplacement du nom du poisson
                    screen.jeu.HebertBold.draw(screen.jeu.batch, poissons.get(i).getNom(), 146f,
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
        if (isOpened && isSelling) {
            affichageVente(screen);
        }
    }

    private void affichageVente(VilleScreen screen) {

        // affichage des carrés de validation
        this.validerVenteTextureSprite.draw(screen.jeu.batch);
        this.refuserVenteTextureSprite.draw(screen.jeu.batch);

        // affichage du texte de vente
        screen.jeu.HebertBold.draw(screen.jeu.batch, "Confirmez la vente de :", 270, 200);
        screen.jeu.HebertBold.setColor(1, 0, 0, 1); // rouge
        screen.jeu.HebertBold.draw(screen.jeu.batch, this.poissons.get(selectedfish).getNom(), 270, 180);
        screen.jeu.HebertBold.setColor(1, 1, 1, 1); // blanc
        screen.jeu.HebertBold.draw(screen.jeu.batch, "pour " + this.poissons.get(selectedfish).getPrix() + " coins ?",
                270, 160);

    }

    public boolean getIsOpened() {
        return this.isOpened;
    }

    public void setIsOpened(boolean value) {
        this.isOpened = value;
    }

    // fermeture interface
    public void close() {
        this.isSelling = false;
        this.selectedfish = 0;
        this.offsetSelectedFish = 0;
    }

    // ouverture interface
    public void open() {
        System.out.println("ouverture market");
    }

}
