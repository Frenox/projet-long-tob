package io.github.ProjetLong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Minijeu2 implements Minijeu{

    private Skin skin;
    private Stage stage;

    private Texture fish;
    private Texture bubble;

    private Sprite fishSprite;
    private Sprite bubbleSprite;

    private int directionX;
    private int directionY;

    private int State; //0 si inactif, 1 si actif, 2 si finit

    public Minijeu2() {
        skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));
        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

        this.fish = new Texture("poisson.png");
        this.bubble = new Texture("CercleMinijeu3.png");

        this.fishSprite = new Sprite(fish);
        this.bubbleSprite = new Sprite(bubble);

        fishSprite.setPosition(100, 100);
        bubbleSprite.setPosition(Gdx.input.getX(), Gdx.input.getY());

        this.directionX = 1;
        this.directionY = 0;

        this.State = 0;

    }

    @Override
    public void input(PecheActiveScreen screen) {
        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        screen.jeu.viewport.getCamera().unproject(mouse);
    
        bubbleSprite.setPosition(
            mouse.x - bubbleSprite.getWidth() / 2f,
            mouse.y - bubbleSprite.getHeight() / 2f
        );

        if (Gdx.input.isKeyJustPressed(Keys.P)) {
            this.State = 1;
        }
    }

    @Override
    public void logic(PecheActiveScreen screen) {
        float x_limit_min = 50;
        float x_limit_max = 150;
        float y_limit_min = 100;
        float y_limit_max = 190;
        float speed = 0.2f;
    
        // Déplacement du poisson sur l'axe X
        fishSprite.translateX(speed * this.directionX);
        if (fishSprite.getX() < x_limit_min || fishSprite.getX() > x_limit_max) {
            this.directionX *= -1;
            fishSprite.flip(true, false);
        }
    
        //calculer les centres du poisson et de la bulle
        float fishCenterX = fishSprite.getX() + fishSprite.getWidth() / 2f;
        float fishCenterY = fishSprite.getY() + fishSprite.getHeight() / 2f;
        float bubbleCenterX = bubbleSprite.getX() + bubbleSprite.getWidth() / 2f;
        float bubbleCenterY = bubbleSprite.getY() + bubbleSprite.getHeight() / 2f;
    
        //distance entre les centres du poisson et de la bulle
        float dx = fishCenterX - bubbleCenterX;
        float dy = fishCenterY - bubbleCenterY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
    
        //rayon de la bulle
        float bubbleRadius = bubbleSprite.getWidth() / 2f;
        

        //déplace le poisson uniquement si le jeu a commencé
        if (this.State == 1) {
            if (distance < bubbleRadius) {
                this.directionY = 1;
            } else if (fishSprite.getY() > y_limit_max || fishSprite.getY() < y_limit_min){
                this.directionY = 0; // Arrêter le mouvement si le poisson sort des limites
            } else {
                this.directionY = -1;
            }
        } else {
            this.directionY = 0;
        }
        // Déplacement du poisson sur l'axe Y
        fishSprite.translateY(speed * this.directionY);

    
        // Affichage de la victoire si le poisson atteint la limite Y
        if (fishSprite.getY() >= 190) {
            System.out.println("Vous avez gagné");
            this.State = 2;
        }
    }
    @Override
    public void draw(PecheActiveScreen screen) {
        fishSprite.draw(screen.jeu.batch);
        bubbleSprite.draw(screen.jeu.batch);
    }
    
}
