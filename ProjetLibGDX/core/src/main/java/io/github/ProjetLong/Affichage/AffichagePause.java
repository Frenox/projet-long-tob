package io.github.ProjetLong.Affichage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

import io.github.ProjetLong.screen.PecheActiveScreen;
import io.github.ProjetLong.screen.SousFenetre;
import io.github.ProjetLong.screen.VilleScreen;
import io.github.ProjetLong.screen.mainMenuScreen;

public class AffichagePause implements SousFenetre {
    private int state;
    private Texture BG = new Texture("Menu_bg.png");
    private Texture reprendre;
    private Texture option;
    private Texture quitter;
    private Vector3 mouseCoor;
    private boolean hover;

    private static final Sound buttonSfx = Gdx.audio.newSound(Gdx.files.internal("audio/ButtonClick.mp3"));

    public AffichagePause() {
        state = 1;
        reprendre = new Texture("Menu_reprendre.png");
        option = new Texture("Menu_options.png");
        quitter = new Texture("Menu_quitter.png");
    }

    @Override
    public void input(PecheActiveScreen screen) {
        mouseCoor = screen.jeu.viewport.getCamera()
                .unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && state != 3) {
            state += 1;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && state != 1) {
            state -= 1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            screen.menuShow = false;
            screen.jeu.soundManager.resumeAudio();
        }
        if (state == 3 && (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
                || (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && hover))) {
            buttonSfx.play();
            screen.jeu.data.saveGame(screen.jeu.data.getActData());
            screen.jeu.soundManager.resumeAudio();
            screen.jeu.setScreen(new mainMenuScreen(screen.jeu));
        } else if (state == 1 && (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
                || (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && hover))) {
            buttonSfx.play();
            screen.menuShow = false;
        }

        // Test hover souris
        if (10 < mouseCoor.x && mouseCoor.x < 64 && 200 < mouseCoor.y && mouseCoor.y < 212) {
            state = 3;
            hover = true;
        } else if (10 < mouseCoor.x && mouseCoor.x < 69 && 221 < mouseCoor.y && mouseCoor.y < 233) {
            state = 2;
            hover = true;
        } else if (10 < mouseCoor.x && mouseCoor.x < 96 && 242 < mouseCoor.y && mouseCoor.y < 254) {
            state = 1;
            hover = true;
        } else {
            hover = false;
        }

    }

    @Override
    public void logic(PecheActiveScreen screen) {
    }

    @Override
    public void draw(PecheActiveScreen screen) {
        screen.jeu.batch.draw(BG, 0, 0);
        if (state == 1) {
            screen.jeu.batch.draw(reprendre, 0, 0);
        } else if (state == 2) {
            screen.jeu.batch.draw(option, 0, 0);
        } else {
            screen.jeu.batch.draw(quitter, 0, 0);
        }
    }

    public void draw(VilleScreen screen) {
        screen.jeu.batch.draw(BG, 0, 0);
        if (state == 1) {
            screen.jeu.batch.draw(reprendre, 0, 0);
        } else if (state == 2) {
            screen.jeu.batch.draw(option, 0, 0);
        } else {
            screen.jeu.batch.draw(quitter, 0, 0);
        }
    }

    public void input(VilleScreen screen) {
        mouseCoor = screen.jeu.viewport.getCamera()
                .unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && state != 3) {
            state += 1;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && state != 1) {
            state -= 1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            screen.menuShow = false;
            screen.jeu.soundManager.resumeAudio();
        }
        if (state == 3 && (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
                || (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && hover))) {
            buttonSfx.play();
            screen.jeu.data.saveGame(screen.jeu.data.getActData());
            screen.jeu.soundManager.resumeAudio();
            screen.jeu.setScreen(new mainMenuScreen(screen.jeu));
        } else if (state == 1 && (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
                || (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && hover))) {
            buttonSfx.play();
            screen.menuShow = false;
        }

        // Test hover souris
        if (10 < mouseCoor.x && mouseCoor.x < 64 && 200 < mouseCoor.y && mouseCoor.y < 212) {
            state = 3;
            hover = true;
        } else if (10 < mouseCoor.x && mouseCoor.x < 69 && 221 < mouseCoor.y && mouseCoor.y < 233) {
            state = 2;
            hover = true;
        } else if (10 < mouseCoor.x && mouseCoor.x < 96 && 242 < mouseCoor.y && mouseCoor.y < 254) {
            state = 1;
            hover = true;
        } else {
            hover = false;
        }

    }
}
