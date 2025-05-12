package io.github.ProjetLong;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class Minijeu3 implements Minijeu {

    private Array<Sprite> poissons;
    private Array<Float> directions;
    private Sprite hamecon;
    private Texture poissonTexture;
    private Texture hameconTexture;
    private BitmapFont font;

    private boolean hameconLance = false;
    private float hameconY;

    private int state = 1; // 0 = inactif, 1 = actif, 2 = terminé
    private final int NB_POISSONS_INIT = 5;

    private final float CADRE_X_MIN = 22.5f;
    private final float CADRE_X_MAX = 187.5f;
    private final float CADRE_Y_MIN = 81.5f;
    private final float CADRE_Y_MAX = 211f;

    public Minijeu3() {
        poissonTexture = new Texture("poisson.png");
        hameconTexture = new Texture("hamecon.png");
        font = new BitmapFont();

        poissons = new Array<>();
        directions = new Array<>();

        Random rand = new Random();
        for (int i = 0; i < NB_POISSONS_INIT; i++) {
            Sprite fish = new Sprite(poissonTexture);
            float x = CADRE_X_MIN + rand.nextFloat() * (CADRE_X_MAX - CADRE_X_MIN - fish.getWidth());
            float y = CADRE_Y_MIN + i * 25f;
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
        if (state != 1) return;

        float speedPoisson = 1.5f;
        float speedHamecon = 2.5f;

        for (int i = 0; i < poissons.size; i++) {
            Sprite poisson = poissons.get(i);
            float direction = directions.get(i);

            poisson.translateX(speedPoisson * direction);

            if (poisson.getX() < CADRE_X_MIN) {
                poisson.setX(CADRE_X_MIN);
                directions.set(i, 1f);
            } else if (poisson.getX() + poisson.getWidth() > CADRE_X_MAX) {
                poisson.setX(CADRE_X_MAX - poisson.getWidth());
                directions.set(i, -1f);
            }
        }

        if (hameconLance) {
            hameconY -= speedHamecon;
            hamecon.setY(hameconY);

            for (int i = 0; i < poissons.size; i++) {
                Sprite poisson = poissons.get(i);
                if (hamecon.getBoundingRectangle().overlaps(poisson.getBoundingRectangle())) {
                    poissons.removeIndex(i);
                    directions.removeIndex(i);
                    System.out.println("Poisson attrapé !");
                    if (poissons.size == 0) {
                        state = 2;
                        System.out.println("Victoire !");
                    } else {
                        resetHamecon();
                    }
                    return;
                }
            }

            if (hamecon.getY() < CADRE_Y_MIN) {
                System.out.println("Raté !");
                state = 2;
            }
        }
    }

    @Override
    public void draw(PecheActiveScreen screen) {
        for (Sprite poisson : poissons) {
            poisson.draw(screen.jeu.batch);
        }

        hamecon.draw(screen.jeu.batch);

        font.draw(screen.jeu.batch, "Poissons restants : " + poissons.size, 30, 280);

        if (state == 2) {
            String messageFin = (poissons.size == 0) ? "Victoire !" : "Défaite !";
            font.draw(screen.jeu.batch, messageFin, 30, 220);
        }
    }

    @Override
    public int getState() {
        return state;
    }
}
