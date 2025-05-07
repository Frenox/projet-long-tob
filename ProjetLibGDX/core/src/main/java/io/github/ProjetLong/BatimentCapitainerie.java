package io.github.ProjetLong;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.ProjetLong.ZonesPeche.Poisson;

public class BatimentCapitainerie implements Batiment {
    private boolean isOpened;

    private int page;

    private Vector3 mouse;

    private Texture batCapitainerieTexture;
    private Sprite batCapitainerieSprite;

    private Texture interfaceBatCapitainerieTexture;
    private Sprite interfaceOverlaybatCapitainerieSprite;

    public BatimentCapitainerie() {
        this.isOpened = false;

        this.mouse = new Vector3(0, 0, 0);

        this.batCapitainerieTexture = new Texture("bat1.png");
        this.interfaceBatCapitainerieTexture = new Texture("overlayMarche.png");

        this.batCapitainerieSprite = new Sprite(batCapitainerieTexture);
        this.interfaceOverlaybatCapitainerieSprite = new Sprite(interfaceBatCapitainerieTexture);

        this.batCapitainerieSprite.setPosition(0, 90);
        this.interfaceOverlaybatCapitainerieSprite.setPosition(130, 50);

        this.page = 0;

    }

    @Override
    public void input(VilleScreen screen) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            if (this.isOpened == true) {
                this.isOpened = false;
            } else {
                this.isOpened = true;
            }
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            this.mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            screen.jeu.viewport.getCamera().unproject(mouse);
        } else {
            this.mouse = new Vector3(0, 0, 0);
        }
    }

    @Override
    public void logic(VilleScreen screen) {

        // à décommenter quand ca sera implémenté
        // this.coinAmount = screen.jeu.DataManagerJeu.getArgent();

        if (this.isOpened) {
            // changer de page
            if (mouse.x >= 216 && mouse.x <= 265 && mouse.y >= 56 && mouse.y <= 67 && page < 0) {
                page++;
            }
            if (mouse.x >= 139 && mouse.x <= 190 && mouse.y >= 56 && mouse.y <= 67 && page > 0) {
                page--;
            }

        }
    }

    @Override
    public void draw(VilleScreen screen, int position) {
        // alwasy draw the batiment itself
        this.batCapitainerieSprite.draw(screen.jeu.batch);
        this.batCapitainerieSprite.setPosition(64 * position, 90);

    }

    @Override
    public void affichageInterface(VilleScreen screen) {
        if (isOpened) {
            // Affichage de l'overlay
            this.interfaceOverlaybatCapitainerieSprite.draw(screen.jeu.batch);
        }
    }

}
