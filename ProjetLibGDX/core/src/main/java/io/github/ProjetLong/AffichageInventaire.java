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
    private Texture selecCanneTexture;
    final private List<Vector2> slotsCoordonnees;
    private int page;
    double time;
    private int caseCanne;

    public AffichageInventaire(Bateau bateau) {
        this.bateau = bateau;
        fondInventaire = new Texture("bg_inventory.png");
        tabMod = new Texture("boat_tab_inventory.png");
        tabStock = new Texture("fish_tab_inventory.png");
        selecCanneTexture = new Texture("contour_slot.png");
        whichTab = false;
        time = 0;
        page = 0;
        caseCanne = 0;
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
            } else if (436 < mouseCoor.x && mouseCoor.x < 451 && 32 < mouseCoor.y && mouseCoor.y < 44
                    && whichTab == true) {
                if ((page < (bateau.getContenu().size() / 9))) {
                    page++;
                }
            } else if (399 < mouseCoor.x && mouseCoor.x < 414 && 32 < mouseCoor.y && mouseCoor.y < 44
                    && whichTab == true) {
                if (page > 0) {
                    page--;
                } // test si canne à pêche cliqué
            } else if (370 < mouseCoor.x && mouseCoor.x < 419 && 158 < mouseCoor.y && mouseCoor.y < 209) {
                if (bateau.getModules().size() >= 1 && bateau.getModules().get(0) instanceof CanneAPeche) {
                    bateau.setEquipedCanne((CanneAPeche) bateau.getModules().get(0));
                    caseCanne = 1;
                }
            } else if (370 < mouseCoor.x && mouseCoor.x < 419 && 98 < mouseCoor.y && mouseCoor.y < 149) {
                if (bateau.getModules().size() >= 3 && bateau.getModules().get(2) instanceof CanneAPeche) {
                    bateau.setEquipedCanne((CanneAPeche) bateau.getModules().get(2));
                    caseCanne = 2;
                }
            } else if (370 < mouseCoor.x && mouseCoor.x < 419 && 38 < mouseCoor.y && mouseCoor.y < 89) {
                if (bateau.getModules().size() >= 5 && bateau.getModules().get(4) instanceof CanneAPeche) {
                    bateau.setEquipedCanne((CanneAPeche) bateau.getModules().get(4));
                    caseCanne = 3;
                }
            } else if (430 < mouseCoor.x && mouseCoor.x < 481 && 158 < mouseCoor.y && mouseCoor.y < 209) {
                if (bateau.getModules().size() >= 2 && bateau.getModules().get(1) instanceof CanneAPeche) {
                    bateau.setEquipedCanne((CanneAPeche) bateau.getModules().get(1));
                    caseCanne = 11;
                }
            } else if (430 < mouseCoor.x && mouseCoor.x < 481 && 98 < mouseCoor.y && mouseCoor.y < 149) {
                if (bateau.getModules().size() >= 4 && bateau.getModules().get(3) instanceof CanneAPeche) {
                    bateau.setEquipedCanne((CanneAPeche) bateau.getModules().get(3));
                    caseCanne = 12;
                }
            } else if (430 < mouseCoor.x && mouseCoor.x < 481 && 38 < mouseCoor.y && mouseCoor.y < 89) {
                if (bateau.getModules().size() >= 6 && bateau.getModules().get(5) instanceof CanneAPeche) {
                    bateau.setEquipedCanne((CanneAPeche) bateau.getModules().get(5));
                    caseCanne = 13;
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
            // screen.jeu.HebertBold.getRegion().getTexture().setFilter(TextureFilter.Linear,
            // TextureFilter.Linear);
            screen.jeu.HebertBold.draw(screen.jeu.batch, Integer.toString(page + 1), 419.5f, 43f);
            screen.jeu.HebertBold.draw(screen.jeu.batch, "/", 423.6f, 42f);
            screen.jeu.HebertBold.draw(screen.jeu.batch, Integer.toString((len / 9) + 1), 426f, 40f);

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
            if (caseCanne != 0) {
                int colonne = 0;
                if (caseCanne > 10) {
                    colonne = 1;
                }
                screen.jeu.batch.draw(selecCanneTexture, 369 + 61 * colonne,
                        158 - ((caseCanne - 10 * colonne) - 1) * 60);
            }
        }

        // draw Bateau
        bateau.getSprite().draw(screen.jeu.batch);
    }

}
