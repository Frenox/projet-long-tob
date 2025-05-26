package io.github.ProjetLong.batiments;

import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.github.ProjetLong.Jeu;
import io.github.ProjetLong.screen.VilleScreen;

public class BatimentHandler {

    private Jeu jeu;

    // etat d'ouverture
    private boolean isOpened;
    private boolean modifiedIsOpened;

    // sprite a droite ou gauche
    private boolean spriteFacingRight;

    private float tempsAnim;
    private int offset;
    private int maxvalueOffset;
    private int positionJoueur;

    // associe à chaque bâtiment sa visibilité à l'écran
    private Map<Batiment, Boolean> batimentList;

    // texture et sprite du jouer
    private Texture joueur;
    private Sprite joueurSprite;

    // animation joueur
    private TextureRegion[] runFrames;
    private TextureRegion[] idleFrames;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> idleAnimation;

    public BatimentHandler(Jeu jeu) {
        this.jeu = jeu;
        // batiment pas open au depart
        this.isOpened = false;
        this.modifiedIsOpened = false;

        // perso regarde à droite
        this.spriteFacingRight = true;

        // valeur de l'offset (décalage par rapport à la position initiale)
        this.offset = 0;
        // valeur max de l'offset
        this.maxvalueOffset = Integer.MAX_VALUE;

        // position initiale du joueur
        this.positionJoueur = 230;

        // texture et sprite du personnage
        this.joueur = new Texture("jouervillescreen.png");
        this.joueurSprite = new Sprite(joueur);

        // pour animation
        tempsAnim = 0;

        // permet de load les bâtiments
        loadBatimentList();
        // permet de mettre à jour la visibilité de chaque bâtiment
        updateBatimentVisibility();

        // animations
        runFrames = new TextureRegion[6];
        idleFrames = new TextureRegion[12];

        // load run
        for (int i = 0; i < 6; i++) {
            String fileName = "animations/joueur_run/frame_" + (i) + ".png";
            runFrames[i] = new TextureRegion(new Texture(Gdx.files.internal(fileName)));

        }
        // load idle
        for (int i = 0; i < 12; i++) {
            String fileName = "animations/joueur_idle/frame_" + (i) + ".png";
            idleFrames[i] = new TextureRegion(new Texture(Gdx.files.internal(fileName)));
        }
        // animations
        runAnimation = new Animation<TextureRegion>(0.2f, runFrames);
        idleAnimation = new Animation<TextureRegion>(0.2f, idleFrames);
    }

    public void input(VilleScreen screen) {

        // gestion de l'input barre espace (ouvre le batiment devant lequel le joueur se
        // trouve, sauf si c'est un bâtiment fictif)
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !(getActualBat() instanceof noBat)) {
            this.isOpened = !this.isOpened;
            this.modifiedIsOpened = !this.modifiedIsOpened;
        }

        // animation texture
        TextureRegion currentFramerun = runAnimation.getKeyFrame(tempsAnim, true);
        TextureRegion currentFrameidle = idleAnimation.getKeyFrame(tempsAnim, true);
        boolean flipX = joueurSprite.isFlipX();
        boolean flipY = joueurSprite.isFlipY();

        // changement du sens du personnage si une seule des deux flêches
        // directionnelles est pressée
        if (!Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            joueurSprite.setRegion(currentFrameidle);
            joueurSprite.setFlip(flipX, flipY);
        }

        // gestion de l'input fleche droite
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !isOpened) {

            // animation
            joueurSprite.setRegion(currentFramerun);
            joueurSprite.setFlip(flipX, flipY);

            // augmenter l'offset
            if (this.positionJoueur >= 492 && offset < maxvalueOffset) {
                offsetIncrementer();

                // augmenter position joueur
            } else if (this.positionJoueur <= 492) {
                joueurIncrementer();
            }

            // flip du sprite si besoin
            if (!spriteFacingRight) {
                joueurSprite.flip(true, false);
                spriteFacingRight = true;
            }

            // gestion de l'input fleche gauche
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && !isOpened) {

            // animation
            joueurSprite.setRegion(currentFramerun);
            joueurSprite.setFlip(flipX, flipY);

            // décremente l'offset
            if (this.positionJoueur <= 4 && offset >= 2) {
                offsetDecrementer();

                // décrementer la position du joueur
            } else if (this.positionJoueur >= 4) {
                joueurDecrementer();
            }

            // flip le sprite si besoin
            if (spriteFacingRight) {
                joueurSprite.flip(true, false);
                spriteFacingRight = false;
            }
        }

        // appelle la méthode input() pour chaque bâtiment
        for (Map.Entry<Batiment, Boolean> entree : this.batimentList.entrySet()) {
            entree.getKey().input(screen);
        }
    }

    public void logic(VilleScreen screen) {

        // variable stockant le bâtiment ouvert
        Batiment openedBat;

        // variable d'animation
        tempsAnim += 1f / 60f;

        // chargement des bâtiments stocké dans le data manager (fait à chaque itération
        // si déblocage de bâtiment)
        loadBatimentList();

        // gestion de l'ouverture/fermeture du batiment
        // vérifie si on vient d'ouvrir/fermer un batiment
        if (this.modifiedIsOpened) {

            // recupere le batiment devant lequel le joueur se trouve
            openedBat = getActualBat();

            // si on ouvre un bat
            if (this.isOpened) {
                // on appelle open() du batiment actuel et on le met en mode "ouvert"
                openedBat.open();
                openedBat.setIsOpened(true);
                // si on ferme un bat
            } else {
                // on appelle close() du batiment actuel et on le met en mode "fermé"
                openedBat.close();
                openedBat.setIsOpened(false);
            }
            this.modifiedIsOpened = false;
        }

        // mise à jour de la visibilité des batiments
        updateBatimentVisibility();

        //
        this.joueurSprite.setPosition(positionJoueur, 85);

        // appel de la logique des batiments
        for (Map.Entry<Batiment, Boolean> entree : this.batimentList.entrySet()) {
            entree.getKey().logic(screen);
        }

        // update de la valeur max du offset

        this.maxvalueOffset = 512 * 3;

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

    // gère l'affichage de l'interface du batiment ouvert
    public void affichageInterface(VilleScreen screen) {
        Batiment openedBat;
        // si un batiment est ouvert, on appelle son affichageInterface()
        if (this.isOpened) {
            openedBat = getActualBat();
            openedBat.affichageInterface(screen);
        }
    }

    // incremente l'offset
    private void offsetIncrementer() {
        this.offset = this.offset + 2;
    }

    // decremente l'offset
    private void offsetDecrementer() {
        this.offset = this.offset - 2;
    }

    // incremente la position joueur
    private void joueurIncrementer() {
        this.positionJoueur = this.positionJoueur + 2;
    }

    // decremente la position joueur
    private void joueurDecrementer() {
        this.positionJoueur = this.positionJoueur - 2;
    }

    // load la liste des bâtiments depuis le data manager
    private void loadBatimentList() {
        batimentList = jeu.data.getBatimentsMap();
        // met à jour la visibilité après avoir load les batiments
        updateBatimentVisibility();
    }

    // met à jour la visibilité des batiments en fonction de l'offset
    private void updateBatimentVisibility() {
        int currentBat = 0;
        for (Map.Entry<Batiment, Boolean> entree : this.batimentList.entrySet()) {
            // batiment visible
            if ((64 * currentBat - offset) > -64 && (64 * currentBat - offset) <= 512) {
                entree.setValue(true);
                // batiment pas visible
            } else {
                entree.setValue(false);
            }
            currentBat += 1;
        }
    }

    // permet de recuperer le batiment se trouvant devant le joueur
    private Batiment getActualBat() {
        Batiment foundBat = new noBat(); // initialise le batiment à un batiment fictif
        int numBat;
        int compteur = 0;
        numBat = (positionJoueur + 8 + offset) / 64;
        for (Map.Entry<Batiment, Boolean> entree : this.batimentList.entrySet()) {
            if (numBat == compteur) {
                foundBat = entree.getKey();
            }
            compteur += 1;
        }
        return foundBat;
    }

    // renvoie l'offset
    public int getOffset() {
        return this.offset;
    }
}
