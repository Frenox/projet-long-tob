package io.github.ProjetLong;

import org.w3c.dom.Text;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;

    private FitViewport viewport;
    private SpriteBatch spriteBatch;
    private Texture poisson;

    private Sprite fishSprite;
    private ProgressBar jauge;

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));

        viewport = new FitViewport(8, 8);
        spriteBatch = new SpriteBatch();

        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

        poisson = new Texture("poisson.png");

        jauge = new ProgressBar(0, 1, 0.1f, true, skin);
        jauge.setSize(10f, 100f);
        jauge.setPosition(300, 300);
        stage.addActor(jauge);

        fishSprite = new Sprite(poisson);
        fishSprite.setSize(0.5f, 0.5f);
        fishSprite.setPosition(2, 5);

    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        viewport.update(width, height, true);
    }

    int sens = 1;
    double t = 0;

    public void input() {
        if (Gdx.input.isKeyJustPressed(Keys.P)) {
            float reussite = (float) (0.7 + Math.sin(t) / 4);

            if (reussite < 0.5) {
                System.out.println("Dommage : Poisson non capturé !");
            } else {
                System.out.println("Poisson capturé avec : " + reussite * 100 + " % de réussite");
            }
        }
    }

    public void logic() {
        float x_limit_min = 2;
        float x_limit_max = 3;
        float speed = 0.01f;

        fishSprite.translateX(speed * sens);

        if (fishSprite.getX() < x_limit_min || fishSprite.getX() > x_limit_max) {
            sens *= -1;
            fishSprite.flip(true, false);
            ;
        }

        fishSprite.translateY((float) Math.sin(t) / 20);

        jauge.setValue((float) (0.7 + Math.sin(t) / 4));
        t += 0.2;
    }

    public void draw() {
        ScreenUtils.clear(Color.WHITE);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        stage.act();
        stage.draw();
        spriteBatch.begin();

        fishSprite.draw(spriteBatch);

        spriteBatch.end();

    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }
}
