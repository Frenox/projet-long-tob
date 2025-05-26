package io.github.ProjetLong.minijeu;

//import
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.ProjetLong.screen.PecheActiveScreen;

public class Minijeu2 implements Minijeu {

    private Stage stage;

    private Texture fish;
    private Texture bubble;

    private Sprite fishSprite;
    private Sprite bubbleSprite;

    private int directionX;
    private int directionY;
    private int cooldown = 60;
    private final static double proba = 0.982;
    private int State; // 0 si inactif, 1 si actif, 2 si finit

    public Minijeu2() {
        // stage
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // chargement des textures
        this.fish = new Texture("poisson.png");
        this.bubble = new Texture("CercleMinijeu3.png");

        // mise en place des sprites
        this.fishSprite = new Sprite(fish);
        this.bubbleSprite = new Sprite(bubble);

        // generateur random
        Random r = new Random();

        // initialisation de la position de depart du poisson et de la bulle
        fishSprite.setPosition(r.nextInt(100) + 50, 100);
        bubbleSprite.setPosition(Gdx.input.getX(), Gdx.input.getY());

        // definit le sens de deplacement sur x initial de manière aléatoire
        switch (r.nextInt(2)) {
            case 1:
                this.directionX = 1;
                break;
            default:
                this.directionX = -1;
                fishSprite.flip(true, false);
                break;
        }

        // definit l'état "en fonctionnement" et le déplacement sur Y de départ
        this.directionY = 0;
        this.State = 1;

    }

    // gère les inputs pour le mini-jeu
    @Override
    public void input(PecheActiveScreen screen) {
        // recupère les coordonnées du curseur
        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        screen.jeu.viewport.getCamera().unproject(mouse);

        // déplace la bulle à la position du curseur
        bubbleSprite.setPosition(
                mouse.x - bubbleSprite.getWidth() / 2f,
                mouse.y - bubbleSprite.getHeight() / 2f);

    }

    // effectue la logique du mini-jeu
    @Override
    public void logic(PecheActiveScreen screen) {
        float x_limit_min = 50;
        float x_limit_max = 150;
        float y_limit_min = 100;
        float y_limit_max = 190;
        float speed = 0.75f;
        float speedy = 0.40f;

        // déplacement du poisson sur l'axe X
        fishSprite.translateX(speed * this.directionX);
        if (fishSprite.getX() < x_limit_min || fishSprite.getX() > x_limit_max) {
            this.directionX *= -1;
            fishSprite.flip(true, false);

            // changement de direction potentiel si cooldown < 0
            // augmenter proba si on veut changer de direction moins souvent
        } else {
            if (new Random().nextFloat() > Minijeu2.proba && cooldown < 0) {
                this.directionX *= -1;
                fishSprite.flip(true, false);
                cooldown = 45;
            }
        }

        cooldown -= 1;

        // calculer les centres du poisson et de la bulle
        float fishCenterX = fishSprite.getX() + fishSprite.getWidth() / 2f;
        float fishCenterY = fishSprite.getY() + fishSprite.getHeight() / 2f;
        float bubbleCenterX = bubbleSprite.getX() + bubbleSprite.getWidth() / 2f;
        float bubbleCenterY = bubbleSprite.getY() + bubbleSprite.getHeight() / 2f;

        // distance entre les centres du poisson et de la bulle
        float dx = fishCenterX - bubbleCenterX;
        float dy = fishCenterY - bubbleCenterY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        // calcul du rayon de la bulle
        float bubbleRadius = bubbleSprite.getWidth() / 2f;

        // déplace le poisson uniquement si le jeu a commencé
        if (this.State == 1) {
            if (distance + 2 < bubbleRadius) {
                this.directionY = 1;
            } else if (fishSprite.getY() > y_limit_max || fishSprite.getY() < y_limit_min) {
                this.directionY = 0; // Arrêter le mouvement si le poisson sort des limites
            } else {
                this.directionY = -1;
            }
        } else {
            this.directionY = 0;
        }
        // déplacement du poisson sur l'axe Y
        fishSprite.translateY(speedy * this.directionY);

        // affichage de la victoire si le poisson atteint la limite Y
        if (fishSprite.getY() >= 190) {
            System.out.println("Vous avez gagné");
            this.State = 2;
        }
    }

    // permet d'afficher les sprites du poisson et de la bulle
    @Override
    public void draw(PecheActiveScreen screen) {
        fishSprite.draw(screen.jeu.batch);
        bubbleSprite.draw(screen.jeu.batch);
    }

    // permet de récupérer l'état du mini-jeu depuis la méthode appelante
    @Override
    public int getState() {
        return State;
    }
}
