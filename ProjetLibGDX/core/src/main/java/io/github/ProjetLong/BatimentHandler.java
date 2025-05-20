package io.github.ProjetLong;

import java.util.LinkedHashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

//512 par 288

public class BatimentHandler {

    private Jeu jeu;

    private boolean isOpened;
    private boolean modifiedIsOpened;
    private boolean spriteFacingRight;

    private int offset;
    private int maxvalueOffset;
    private int positionJoueur;
    private Map<Batiment, Boolean> batimentList; // Associe a chaque bat un show ou pas booleen

    private Texture joueur;
    private Sprite joueurSprite;

    public BatimentHandler(Jeu jeu) {
        this.jeu = jeu;
        this.isOpened = false;
        this.spriteFacingRight = true;
        this.modifiedIsOpened = false;
        this.offset = 0;
        this.maxvalueOffset = Integer.MAX_VALUE;
        this.positionJoueur = 248;
        this.joueur = new Texture("jouervillescreen.png");
        this.joueurSprite = new Sprite(joueur);

        loadBatimentList();
        updateBatimentVisibility();
    }

    public void input(VilleScreen screen) {

        // gestion barre espace
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            this.isOpened = !this.isOpened;
            this.modifiedIsOpened = !this.modifiedIsOpened;
        }

        // gestion fleche droite
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !isOpened) {
            if (this.positionJoueur >= 492 && offset < maxvalueOffset) {
                offsetIncrementer();
            } else if (this.positionJoueur <= 492) {
                joueurIncrementer();
            }
            // flip du sprite si besoin
            if (!spriteFacingRight) {
                joueurSprite.flip(true, false);
                spriteFacingRight = true;
            }

            // gestion fleche gauche
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && !isOpened) {
            if (this.positionJoueur <= 4 && offset >= 0) {
                offsetDecrementer();
            } else if (this.positionJoueur >= 4) {
                joueurDecrementer();
            }
            if (spriteFacingRight) {
                joueurSprite.flip(true, false);
                spriteFacingRight = false;
            }
        }

        // input de tous les batiments
        for (Map.Entry<Batiment, Boolean> entree : this.batimentList.entrySet()) {
            entree.getKey().input(screen);
        }
    }

    public void logic(VilleScreen screen) {

        Batiment openedBat;
        System.out.println(offset);
        // load des batiments (a faire quand datamanager ok)
        loadBatimentList();

        // gestion ouverture/fermeture batiment
        if (this.modifiedIsOpened) {
            openedBat = getActualBat();
            if (this.isOpened) {
                openedBat.open();
                openedBat.setIsOpened(true);
            } else {
                openedBat.close();
                openedBat.setIsOpened(false);
            }
            this.modifiedIsOpened = false;
        }
        updateBatimentVisibility();
        this.joueurSprite.setPosition(positionJoueur, 80);

        // appel de la logique des batiments
        for (Map.Entry<Batiment, Boolean> entree : this.batimentList.entrySet()) {
            entree.getKey().logic(screen);
        }

        // update de la valeur max du offset
        this.maxvalueOffset = Math.max((batimentList.size() * 64 - 512), 0);

    }

    public void draw(VilleScreen screen) {
        // affichage des batiments
        int currentBat = 0;
        for (Map.Entry<Batiment, Boolean> entree : this.batimentList.entrySet()) {
            if (entree.getValue()) {
                entree.getKey().draw(screen, currentBat, -(offset % 64));
                currentBat += 1;
            }
        }

        // affichage du joueur
        this.joueurSprite.draw(screen.jeu.batch);
    }

    public void affichageInterface(VilleScreen screen) {
        Batiment openedBat;
        if (this.isOpened) {
            openedBat = getActualBat();
            openedBat.affichageInterface(screen);
        }
    }

    private void offsetIncrementer() {
        this.offset = this.offset + 2;
    }

    private void offsetDecrementer() {
        this.offset = this.offset - 2;
    }

    private void joueurIncrementer() {
        this.positionJoueur = this.positionJoueur + 2;
    }

    private void joueurDecrementer() {
        this.positionJoueur = this.positionJoueur - 2;
    }

    // load la liste des bâtiments depuis le data manager
    private void loadBatimentList() {
        batimentList = jeu.data.getBatimentsMap();
        updateBatimentVisibility(); //met à jour la visibilité après avoir load les
                                    // bats
    }

    // updates the status of the visibility of each bat depending on the offset
    private void updateBatimentVisibility() {
        int currentBat = 0;
        for (Map.Entry<Batiment, Boolean> entree : this.batimentList.entrySet()) {
            if ((64 * currentBat - offset) > -64 && (64 * currentBat - offset) <= 512) {
                entree.setValue(true);
            } else {
                entree.setValue(false);
            }
            currentBat += 1;
        }
    }

    // permet de recup le batiment se trouvant devant le joueur
    private Batiment getActualBat() {
        Batiment foundBat = new BatimentChantierNaval(); // pas propre mais pas de solutions
        int numBat;
        int compteur = 0;
        numBat = Math.min((positionJoueur - 8 + offset) / 64, this.batimentList.size() - 1);
        for (Map.Entry<Batiment, Boolean> entree : this.batimentList.entrySet()) {
            if (numBat == compteur) {
                foundBat = entree.getKey();
            }
            compteur += 1;
        }
        return foundBat;
    }

    public int getOffset() {
        return this.offset;
    }
}
