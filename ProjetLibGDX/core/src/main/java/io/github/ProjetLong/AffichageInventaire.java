package io.github.ProjetLong;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import io.github.ProjetLong.ZonesPeche.Poisson;

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
    private Texture fishInv;
    final private List<Vector2> slotsCoordonnees;
    private int page;
    double time;

    public AffichageInventaire(Bateau bateau) {
        this.bateau = bateau;
        fondInventaire = new Texture("bg_inventory.png");
        tabMod = new Texture("boat_tab_inventory.png");
        tabStock = new Texture("fish_tab_inventory.png");
        whichTab = false;
        time = 0;
        page = 0;
        fishInv = new Texture("fish_tab_fish.png");
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
            } else if (436 < mouseCoor.x && mouseCoor.x < 451 && 32 < mouseCoor.y && mouseCoor.y < 44) {
                if ((page < (bateau.getContenu().size() / 9))) {
                    page++;
                }
            } else if (399 < mouseCoor.x && mouseCoor.x < 414 && 32 < mouseCoor.y && mouseCoor.y < 44) {
                if (page > 0) {
                    page--;
                }
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
            // Draw inventaire
            List<Poisson> temp = bateau.getContenu();
            int len = temp.size();
            for (int i = (page * 9); i != (9 * (page + 1)); i++) {
                if (i < len) {
                    screen.jeu.batch.draw(fishInv, 362, 199 - ((i % 9) * 19));
                    screen.jeu.HebertBold.draw(screen.jeu.batch, temp.get(i).getNom(), 368f, 210.5f - ((i % 9) * 19));
                }
            }
            // print le nb de page
            screen.jeu.HebertBold.draw(screen.jeu.batch, Integer.toString(page + 1), 419.5f, 43f);
            screen.jeu.HebertBold.draw(screen.jeu.batch, "/", 423.6f, 42f);
            screen.jeu.HebertBold.draw(screen.jeu.batch, Integer.toString((len / 9) + 1), 426.75f, 40f);

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
