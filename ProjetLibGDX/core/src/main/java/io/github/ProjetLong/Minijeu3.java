package io.github.ProjetLong;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Minijeu3 implements Minijeu {

    // Le stage permet de mettre des élément dedans et de les affichers après
    private Stage stage;

    // Texture du poisson et de la bulle
    private Texture fish;
    private Texture bubble;

    // Sprite du poisson
    private Sprite fishSprite;

    private int directionY;

    private int State; // 0 si inactif, 1 si actif, 2 si finit

    // Data des bulles
    private Array<Sprite> bubbles;
    private Array<Float> bubblesTime;

    private Random random;
    private int bubbleClique;

    private float bubbleTimer;

    private float bubbleTimerMax;

    // Variables de déplacement du poisson
    private float speed_montee;
    private int sens = 1;
    private final float x_limit_min = 50;
    private final float x_limit_max = 150;
    private final float y_limit_min = 100;
    private final float y_limit_max = 190;

    // Son des bulles qui explose
    private static final Sound popSfx = Gdx.audio.newSound(Gdx.files.internal("audio/BubblePop.mp3"));

    public Minijeu3() {

        //setup du stage
        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

        //Setup Textures
        this.fish = new Texture("poisson.png");
        this.bubble = new Texture("CercleMinijeu3.png");

        this.fishSprite = new Sprite(fish);
        this.random = new Random();
        fishSprite.setPosition(x_limit_min, y_limit_min);

        //Setup des variables d'actions
        this.directionY = 1;

        this.State = 1;

        this.bubbles = new Array<>();
        this.bubblesTime = new Array<>();

        this.bubbleClique = -1;
        this.bubbleTimer = 0;
        this.bubbleTimerMax = (5 + random.nextInt(10)) / 10;
        this.speed_montee = 0.0f;
    }

    @Override
    public void input(PecheActiveScreen screen) {

        bubbleClique = -1;

        //détecte le clique de la souris sur les bulles
        if (Gdx.input.justTouched()) {
            Vector3 click = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            screen.jeu.viewport.getCamera().unproject(click);

            int i = 0;

            while (i < bubbles.size && bubbleClique == -1) {
                Sprite bubbleSprite = bubbles.get(i);
                if (bubbleSprite.getBoundingRectangle().contains(click.x, click.y)) {
                    bubbleClique = i;
                }
                i++;
            }
        }

    }

    //Créé une nouvelle bulle
    private void createBubble() {
        Sprite bubbleSprite = new Sprite(bubble);
        bubbleSprite.setPosition(random.nextInt(100) + 50, random.nextInt(100) + 70);
        bubbles.add(bubbleSprite);
        bubblesTime.add(0f);
    }

    @Override
    public void logic(PecheActiveScreen screen) {
        float delta = Gdx.graphics.getDeltaTime();

        float speed_reel = delta * speed_montee;

        // Déplacement du poisson sur l'axe Y
        if (fishSprite.getY() + speed_reel * this.directionY >= y_limit_min) {
            fishSprite.translateY(speed_reel * this.directionY);
            speed_montee -= 0.62f;
        } else {
            fishSprite.setY(y_limit_min);
            speed_montee = 0.0f;
        }

        // Création de bulles
        bubbleTimer += delta;
        if (bubbleTimer >= bubbleTimerMax) {
            createBubble();
            bubbleTimer = 0;
            bubbleTimerMax = (4f + random.nextInt(5)) / 10;
        }

        // Suppression de la bulle cliquée
        if (bubbleClique != -1) {
            long id = popSfx.play();
            popSfx.setPitch(id, 0.75f + (float) Math.random() / 4);
            bubbles.removeIndex(bubbleClique);
            bubblesTime.removeIndex(bubbleClique);
            speed_montee += 30f;
            bubbleClique = -1;
        }
        float speed = 0.3f;

        //Déplacement horizontale du poisson
        fishSprite.translateX(speed * sens);

        if (fishSprite.getX() < x_limit_min || fishSprite.getX() > x_limit_max) {
            sens *= -1;
            fishSprite.flip(true, false);

        }
        // Suppression des bulles qui sont là depuis trop longtemps
        for (int i = bubblesTime.size - 1; i >= 0; i--) {
            float time = bubblesTime.get(i);
            if (time > .7f) {
                bubblesTime.removeIndex(i);
                bubbles.removeIndex(i);
            } else {
                bubblesTime.set(i, time + delta);
            }
        }

        // Affichage de la victoire si le poisson atteint la limite Y
        if (fishSprite.getY() >= y_limit_max) {
            System.out.println("Vous avez gagné");
            this.State = 2;
        }
    }

    @Override
    public void draw(PecheActiveScreen screen) {
        //affiche le poisson et les bulles
        fishSprite.draw(screen.jeu.batch);
        for (Sprite bubbleSprite : bubbles) {
            bubbleSprite.draw(screen.jeu.batch);
        }
    }

    @Override
    public int getState() {
        return State;
    }
}
