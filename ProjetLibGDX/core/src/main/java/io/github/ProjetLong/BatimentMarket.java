package io.github.ProjetLong;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.ProjetLong.ZonesPeche.Poisson;

public class BatimentMarket implements Batiment {

    private boolean isOpened;
    private boolean isSelling;

    private int coinAmount;
    private int selectedfish;
    private int offsetSelectedFish;

    private int page;
    private Vector3 mouse;

    private Stage stage;

    private Texture fishInv;

    private Texture batMarketTexture;
    private Sprite batMarketSprite;

    private Texture interfaceBatMarketTexture;
    private Sprite interfaceOverlayBatMarketSprite;

    private Texture validerVenteTexture;
    private Texture refuserVenteTexture;

    private Sprite validerVenteTextureSprite;
    private Sprite refuserVenteTextureSprite;

    // a supprimer quand implémente
    private ArrayList<Poisson> poissons;

    // 512 par 288

    public BatimentMarket() {
        this.isOpened = false;
        this.isSelling = false;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.mouse = new Vector3(0, 0, 0);

        this.batMarketTexture = new Texture("bat1.png");
        this.interfaceBatMarketTexture = new Texture("overlayMarche.png");
        this.validerVenteTexture = new Texture("valider.png");
        this.refuserVenteTexture = new Texture("refuser.png");

        this.batMarketSprite = new Sprite(batMarketTexture);
        this.interfaceOverlayBatMarketSprite = new Sprite(interfaceBatMarketTexture);
        this.validerVenteTextureSprite = new Sprite(validerVenteTexture);
        this.refuserVenteTextureSprite = new Sprite(refuserVenteTexture);

        this.batMarketSprite.setPosition(0, 90);
        this.interfaceOverlayBatMarketSprite.setPosition(130, 50);
        this.validerVenteTextureSprite.setPosition(280, 90);
        this.refuserVenteTextureSprite.setPosition(335, 90);

        this.page = 0;
        this.selectedfish = 0;
        this.offsetSelectedFish = 0;
        this.fishInv = new Texture("fish_tab_fish.png");

        // a supprimer later
        this.poissons = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            this.poissons.add(new Poisson(1, 1));
        }

    }

    @Override
    public void input(VilleScreen screen) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (this.isOpened == true) {
                this.isOpened = false;
                this.isSelling = false;
                this.selectedfish = 0;
                this.offsetSelectedFish = 0;
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

        // à décommenter quand ca sera implémenté
        // this.coinAmount = screen.jeu.DataManagerJeu.getArgent();

        if (this.isOpened && this.isSelling == false) {
            // changer de page
            if (mouse.x >= 216 && mouse.x <= 265 && mouse.y >= 56 && mouse.y <= 67
                    && page < (((poissons.size() - 1) / 7))) {
                page++;
            }
            if (mouse.x >= 139 && mouse.x <= 190 && mouse.y >= 56 && mouse.y <= 67 && page > 0) {
                page--;
            }

            // detection de la vente (identifiez quel poisson)
            if (mouse.x >= 140 && mouse.x <= 265 && mouse.y >= 70 && mouse.y <= 200) {
                while (mouse.y > (70 + offsetSelectedFish * 18.5)) {
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

        if (this.isOpened && this.isSelling == true) {
            if (mouse.x > 280 && mouse.x < 310 && mouse.y > 90 && mouse.y < 120) {
                // on crédite le joueur
                // this.coinAmount += this.poissons.get(selectedfish).getValeur();
                this.coinAmount += 10;

                // on enlève le poisson dans la session ET dans le data manager
                this.poissons.remove(selectedfish);

                this.isSelling = false;
                this.selectedfish = 0;
                this.offsetSelectedFish = 0;
            }
            if (mouse.x > 335 && mouse.x < 365 && mouse.y > 90 && mouse.y < 120) {
                this.isSelling = false;
                this.selectedfish = 0;
                this.offsetSelectedFish = 0;
            }
        }
    }

    @Override
    public void draw(VilleScreen screen, int position) {
        // alwasy draw the batiment itself
        this.batMarketSprite.draw(screen.jeu.batch);

        if (this.isOpened && this.isSelling) {
            affichageVente(screen);
        }
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
        // screen.jeu.HebertBold.draw(screen.jeu.batch, "pour " +
        // this.poissons.get(selectedfish).getValeur() + " coins ?", 140, 220);
        screen.jeu.HebertBold.draw(screen.jeu.batch, "pour " + "100" + " coins ?", 270, 160);

    }

}
