package io.github.ProjetLong;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class Minijeu3 implements Minijeu {

    private Array<Sprite> poissons;
    private Array<Float> directions; //  direction de chaque poisson 
    private Sprite hamecon;
    private Texture poissonTexture;
    private Texture hameconTexture;

    private boolean hameconLance = false;
    private float hameconY;

    private int state = 1;

    //  Coordonnées du cadre du minjeu
    private final float CADRE_X_MIN = 22.5f;
    private final float CADRE_X_MAX = 187.5f;
    private final float CADRE_Y_MIN = 81.5f;
    private final float CADRE_Y_MAX = 211f;

    public Minijeu3() {
        poissonTexture = new Texture("poisson.png");
        hameconTexture = new Texture("hamecon.png");  // image du hamecon

        poissons = new Array<>();
        directions = new Array<>();

        //  Ajouter 3 poissons avec direction initiale aléatoire
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            Sprite fish = new Sprite(poissonTexture);
            float x = CADRE_X_MIN + rand.nextFloat() * (CADRE_X_MAX - CADRE_X_MIN - fish.getWidth());
            float y = CADRE_Y_MIN + i * 40f;
            fish.setPosition(x, y);
            poissons.add(fish);

            float dir = rand.nextBoolean() ? 1f : -1f;
            directions.add(dir);
        }

        hamecon = new Sprite(hameconTexture);
        hamecon.setSize(10, 40);
        resetHamecon();
    }

    private void resetHamecon() {
        float centerX = (CADRE_X_MIN + CADRE_X_MAX) / 2f;
        hamecon.setPosition(centerX - hamecon.getWidth() / 2f, CADRE_Y_MAX);
        hameconY = hamecon.getY();
        hameconLance = false;
    }

    @Override
    public void input(PecheActiveScreen screen) {
        if (!hameconLance && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            hameconLance = true;
        }
    }

    @Override
    public void logic(PecheActiveScreen screen) {
        float speedPoisson = 1.5f;
        float speedHamecon = 2.5f;

        for (int i = 0; i < poissons.size; i++) {
            Sprite poisson = poissons.get(i);
            float direction = directions.get(i);

            poisson.translateX(speedPoisson * direction);

            //  Rebondir sur les bords du cadre
            if (poisson.getX() < CADRE_X_MIN) {
                poisson.setX(CADRE_X_MIN);
                directions.set(i, 1f); // vers la droite
            } else if (poisson.getX() + poisson.getWidth() > CADRE_X_MAX) {
                poisson.setX(CADRE_X_MAX - poisson.getWidth());
                directions.set(i, -1f); // vers la gauche
            }
        }

        if (hameconLance) {
            hameconY -= speedHamecon;
            hamecon.setY(hameconY);

            for (Sprite poisson : poissons) {
                if (hamecon.getBoundingRectangle().overlaps(poisson.getBoundingRectangle())) {
                    System.out.println(" Poisson attrapé !");
                    state = 2;
                    hameconLance = false;
                    return;
                }
            }

            if (hamecon.getY() < CADRE_Y_MIN) {
                System.out.println(" Raté !");
                state = 2;
                hameconLance = false;
            }
        }
    }

    @Override
    public void draw(PecheActiveScreen screen) {
        for (Sprite poisson : poissons) {
            poisson.draw(screen.jeu.batch);
        }

        hamecon.draw(screen.jeu.batch);
    }

    @Override
    public int getState() {
        return state;
    }
}
