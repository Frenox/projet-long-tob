package io.github.ProjetLong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Minijeu1 implements Minijeu {

    private Skin skin;
    private Stage stage;

    private Texture poisson;

    private Sprite fishSprite;
    private ProgressBar jauge;

    public Minijeu1() {
        skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));

        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

        poisson = new Texture("poisson.png");

        jauge = new ProgressBar(0, 1, 0.1f, true, skin);
        jauge.setSize(10f, 100f);
        jauge.setPosition(300, 300);
        stage.addActor(jauge);

        fishSprite = new Sprite(poisson);

        fishSprite.setPosition(50, 100);

    }

    int sens = 1;
    double t = 0;

    @Override
    public void input(PecheActiveScreen screen) {
        if (Gdx.input.isKeyJustPressed(Keys.P)) {
            float reussite = (float) (0.7 + Math.sin(t) / 4);

            if (reussite < 0.5) {
                System.out.println("Dommage : Poisson non capturé !");
            } else {
                System.out.println("Poisson capturé avec : " + reussite * 100 + " % de réussite");
            }
        }
    }

    @Override
    public void logic(PecheActiveScreen screen) {
        float x_limit_min = 50;
        float x_limit_max = 150;
        float speed = 0.2f;

        fishSprite.translateX(speed * sens);

        if (fishSprite.getX() < x_limit_min || fishSprite.getX() > x_limit_max) {
            sens *= -1;
            fishSprite.flip(true, false);

        }

        fishSprite.translateY((float) Math.sin(t) / 10);

        jauge.setValue((float) (0.7 + Math.sin(t) / 4));
        t += 0.05;
    }

    @Override
    public void draw(PecheActiveScreen screen) {
        fishSprite.draw(screen.jeu.batch);

    }
}
