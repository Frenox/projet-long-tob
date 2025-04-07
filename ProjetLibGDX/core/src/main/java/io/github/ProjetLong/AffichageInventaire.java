package io.github.ProjetLong;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class AffichageInventaire implements Minijeu {
    private Bateau bateau;
    private Texture fondInventaire;
    private Texture tabStock;
    private Texture tabMod;
    private boolean whichTab;
    final private List<Vector2> slotsCoordonnees;

    double time;

    public AffichageInventaire(Bateau bateau) {
        this.bateau = bateau;
        fondInventaire = new Texture("bg_inventory.png");
        tabMod = new Texture("boat_tab_inventory.png");
        tabStock = new Texture("fish_tab_inventory.png");
        whichTab = false;
        time = 0;
        // ajout de coordonnées de slots pour les textures
        slotsCoordonnees = new ArrayList<Vector2>();
        slotsCoordonnees.add(new Vector2(369, 158));
        slotsCoordonnees.add(new Vector2(430, 158));
        slotsCoordonnees.add(new Vector2(369, 98));
        slotsCoordonnees.add(new Vector2(430, 98));
        slotsCoordonnees.add(new Vector2(369, 38));
        slotsCoordonnees.add(new Vector2(430, 38));
    }

    @Override
    public void input(PecheActiveScreen screen) {

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector3 mouseCoor = screen.jeu.viewport.getCamera()
                    .unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (425 < mouseCoor.x && mouseCoor.x < 494 && 246 < mouseCoor.y && mouseCoor.y < 265) {
                whichTab = true;
            } else if (356 < mouseCoor.x && mouseCoor.x < 425 && 246 < mouseCoor.y && mouseCoor.y < 265) {
                whichTab = false;
            }
        }

    }

    @Override
    public void logic(PecheActiveScreen screen) {
        bateau.addSpriteY((float) (Math.sin(time) / 50));
        time += 0.02;
    }

    @Override
    public void draw(PecheActiveScreen screen) {
        screen.jeu.batch.draw(fondInventaire, 356, 25);
        // stock
        if (whichTab) {
            screen.jeu.batch.draw(tabStock, 356, 25);
            // modules
        } else {
            screen.jeu.batch.draw(tabMod, 356, 25);
            // draw les modules du bateau
            int pos = 0;
            for (ModuleBateau mod : bateau.getModules()) {
                Vector2 coord = slotsCoordonnees.get(pos);
                screen.jeu.batch.draw(mod.getTexture(), coord.x, coord.y);
                pos++;
            }
        }

        // draw Bateau
        bateau.getSprite().draw(screen.jeu.batch);
    }

}
