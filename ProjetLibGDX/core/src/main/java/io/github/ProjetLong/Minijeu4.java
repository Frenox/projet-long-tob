package io.github.ProjetLong;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class Minijeu4 implements Minijeu {

    private Array<Sprite> poissons;
    private Array<Float> directions; // direction de chaque poisson
    private Sprite hamecon;
    private Texture poissonTexture;
    private Texture hameconTexture;

    private boolean hameconLance = false;
    private float hameconY;

    private int state = 1;
    private float speedPoisson;
    int dir = 1;
    // Coordonnées du cadre du minjeu
    private final float CADRE_X_MIN = 22.5f;
    private final float CADRE_X_MAX = 187.5f;
    private final float CADRE_Y_MIN = 90f;
    private final float CADRE_Y_MAX = 211f;

    public Minijeu4() {
        poissonTexture = new Texture("poisson.png");
        hameconTexture = new Texture("hamecon.png"); // image du hamecon
        poissons = new Array<>();
        directions = new Array<>();
        speedPoisson = 0.65f + new Random().nextFloat() * 0.25f;
        // Ajouter 3 poissons avec direction initiale aléatoire
        Random rand = new Random();

        Sprite fish = new Sprite(poissonTexture);
        float x = CADRE_X_MIN + rand.nextFloat() * (CADRE_X_MAX - CADRE_X_MIN - fish.getWidth());
        float y = CADRE_Y_MIN + 10f + new Random().nextFloat() * 35f;
        fish.setPosition(x, y);
        poissons.add(fish);

        directions.add(1f);

        hamecon = new Sprite(hameconTexture);

        resetHamecon();
    }

    private void resetHamecon() {
        float centerX = (CADRE_X_MIN + CADRE_X_MAX) / 2f;
        hamecon.setPosition(centerX - hamecon.getWidth() / 2f, CADRE_Y_MAX);
        hameconY = hamecon.getY();
        hameconLance = false;
        speedPoisson = 0.50f + new Random().nextFloat() * 0.50f;
    }

    @Override
    public void input(PecheActiveScreen screen) {
        if (!hameconLance && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            hameconLance = true;
        }
    }

    @Override
    public void logic(PecheActiveScreen screen) {

        float speedHamecon = 0.85f;

        for (int i = 0; i < poissons.size; i++) {
            Sprite poisson = poissons.get(i);
            float direction = directions.get(i);

            poisson.translateX(speedPoisson * direction);

            // Rebondir sur les bords du cadre
            if (poisson.getX() < CADRE_X_MIN) {
                poisson.setX(CADRE_X_MIN);
                poisson.flip(true, false);
                dir = 1;
                directions.set(i, 1f); // vers la droite
            } else if (poisson.getX() + poisson.getWidth() > CADRE_X_MAX) {
                poisson.setX(CADRE_X_MAX - poisson.getWidth());
                poisson.flip(true, false);
                directions.set(i, -1f); // vers la gauche
                dir = 0;
            }
        }

        if (hameconLance) {
            hameconY -= speedHamecon;
            hamecon.setY(hameconY);

            for (Sprite poisson : poissons) {

                if (hamecon.getY() + 3 > poisson.getY() && hamecon.getY() + 3 < poisson.getY() + 12
                        && hamecon.getX() + 3 > poisson.getX() + 16 * dir
                        && hamecon.getX() + 3 < poisson.getX() + 15 + 16 * dir) {
                    System.out.println(" Poisson attrapé !");
                    state = 2;
                    hameconLance = false;
                    return;
                }
            }

            if (hamecon.getY() < CADRE_Y_MIN) {
                System.out.println(" Raté !");
                resetHamecon();
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
