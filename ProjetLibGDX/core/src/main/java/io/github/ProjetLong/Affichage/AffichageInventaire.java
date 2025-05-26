package io.github.ProjetLong.Affichage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import io.github.ProjetLong.Bateaux.Bateau;
import io.github.ProjetLong.ZonesPeche.Poisson;
import io.github.ProjetLong.equipementetmodule.CanneAPeche;
import io.github.ProjetLong.equipementetmodule.ModuleBateau;
import io.github.ProjetLong.screen.PecheActiveScreen;
import io.github.ProjetLong.screen.SousFenetre;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class AffichageInventaire implements SousFenetre {

    // bateau actuel
    private Bateau bateau;

    // Texture nécessaires
    private Texture fondInventaire;
    private Texture tabStock;
    private Texture tabMod;
    private Texture fishInv;
    private Texture hoverGradienTexture;
    private Texture selecCanneTexture;
    private Texture hoverTexture;

    // Affiche le stockage ou les modules
    private boolean whichTab;

    // Liste des coordonnées des cases
    final private List<Vector2> slotsCoordonnees;

    private int page;
    private int caseCanne;
    private int timer;

    // Detecte la souris sur l'écran
    private Vector3 mouseCoor;
    private Vector2 hoverCoord;
    private int whichHover;

    public AffichageInventaire(Bateau bateau) {
        this.bateau = bateau;

        // Crée les texture
        fondInventaire = new Texture("bg_inventory.png");
        tabMod = new Texture("boat_tab_inventory.png");
        tabStock = new Texture("fish_tab_inventory.png");
        selecCanneTexture = new Texture("contour_slot.png");
        fishInv = new Texture("fish_tab_fish.png");
        hoverTexture = new Texture("mouse_hover_actif.png");
        hoverGradienTexture = new Texture("hover_alpha.png");

        // Initialise les variables
        page = 0;
        caseCanne = 0;
        whichTab = false;

        // creer les variable de position souris
        hoverCoord = new Vector2();
        whichHover = 0;

        // ajout de coordonnées de slots pour les textures
        slotsCoordonnees = new ArrayList<Vector2>();
        slotsCoordonnees.add(new Vector2(369, 158));
        slotsCoordonnees.add(new Vector2(430, 158));
        slotsCoordonnees.add(new Vector2(369, 98));
        slotsCoordonnees.add(new Vector2(430, 98));
        slotsCoordonnees.add(new Vector2(369, 38));
        slotsCoordonnees.add(new Vector2(430, 38));

        // Dans quelle case se situe la canne à pêche équipée
        int canneequip = bateau.getModules().indexOf(bateau.getEquipedCanne());
        switch (canneequip) {
            case 0:
                caseCanne = 1;
                break;
            case 1:
                caseCanne = 11;
                break;
            case 2:
                caseCanne = 2;
                break;
            case 3:
                caseCanne = 12;
                break;
            case 4:
                caseCanne = 3;
                break;
            case 5:
                caseCanne = 13;
                break;
            default:
                break;
        }

    }

    @Override
    public void input(PecheActiveScreen screen) {

        // récupère les coordonnés de la souris tout le temps et quand clic pour
        // Utiliser après dans le survol et les boutons
        mouseCoor = screen.jeu.viewport.getCamera()
                .unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            // test si il faut changer entre stockage et bateau
            if (425 < mouseCoor.x && mouseCoor.x < 494 && 246 < mouseCoor.y && mouseCoor.y < 265) {
                whichTab = true;
            } else if (356 < mouseCoor.x && mouseCoor.x < 425 && 246 < mouseCoor.y && mouseCoor.y < 265) {
                whichTab = false;

                // Test si les boutons flèches sont cliqués
            } else if (436 < mouseCoor.x && mouseCoor.x < 451 && 32 < mouseCoor.y && mouseCoor.y < 44
                    && whichTab == true) {
                if ((page < ((bateau.getContenu().size() - 1) / 9))) {
                    page++;
                }
            } else if (399 < mouseCoor.x && mouseCoor.x < 414 && 32 < mouseCoor.y && mouseCoor.y < 44
                    && whichTab == true) {
                if (page > 0) {
                    page--;
                }
            }

            // Equipe la canne à peche quand clique dessus
            verifierEtEquiperModule(370, 419, 158, 209, 0, 1);
            verifierEtEquiperModule(370, 419, 98, 149, 2, 2);
            verifierEtEquiperModule(370, 419, 38, 89, 4, 3);
            verifierEtEquiperModule(430, 481, 158, 209, 1, 11);
            verifierEtEquiperModule(430, 481, 98, 149, 3, 12);
            verifierEtEquiperModule(430, 481, 38, 89, 5, 13);

        }
        // CHECK DE POSITION POUR LE HOVER DES MODULES
        if (370 < mouseCoor.x && mouseCoor.x < 419 && 158 < mouseCoor.y && mouseCoor.y < 209
                && bateau.getModules().size() >= 1 && whichTab == false) {
            if (timer > 0) {
                timer--;
                hoverCoord.set(mouseCoor.x, mouseCoor.y);
            }
        } else if (370 < mouseCoor.x && mouseCoor.x < 419 && 98 < mouseCoor.y && mouseCoor.y < 149
                && bateau.getModules().size() >= 3 && whichTab == false) {
            if (timer > 0) {
                timer--;
                hoverCoord.set(mouseCoor.x, mouseCoor.y);
            }
        } else if (370 < mouseCoor.x && mouseCoor.x < 419 && 38 < mouseCoor.y && mouseCoor.y < 89
                && bateau.getModules().size() >= 5 && whichTab == false) {
            if (timer > 0) {
                timer--;
                hoverCoord.set(mouseCoor.x, mouseCoor.y);
            }
        } else if (430 < mouseCoor.x && mouseCoor.x < 481 && 158 < mouseCoor.y && mouseCoor.y < 209
                && bateau.getModules().size() >= 2 && whichTab == false) {
            if (timer > 0) {
                timer--;
                hoverCoord.set(mouseCoor.x, mouseCoor.y);
            }
        } else if (430 < mouseCoor.x && mouseCoor.x < 481 && 98 < mouseCoor.y && mouseCoor.y < 149
                && bateau.getModules().size() >= 4 && whichTab == false) {
            if (timer > 0) {
                timer--;
                hoverCoord.set(mouseCoor.x, mouseCoor.y);
            }
        } else if (430 < mouseCoor.x && mouseCoor.x < 481 && 38 < mouseCoor.y && mouseCoor.y < 89
                && bateau.getModules().size() >= 6 && whichTab == false) {
            if (timer > 0) {
                timer--;
                hoverCoord.set(mouseCoor.x, mouseCoor.y);
            }

            // CHECK DE POSITION POUR LE HOVER DES POISSONS
        } else if (362 < mouseCoor.x && mouseCoor.x < 488 && 199 < mouseCoor.y && mouseCoor.y < 216
                && bateau.getContenu().size() >= 1 + 9 * page && whichTab == true) {
            if (timer > 0) {
                timer--;
                hoverCoord.set(mouseCoor.x, mouseCoor.y);
                whichHover = 1;
            }
        } else if (362 < mouseCoor.x && mouseCoor.x < 488 && 199 - 19 * 1 < mouseCoor.y && mouseCoor.y < 216 - 19 * 1
                && bateau.getContenu().size() >= 2 + 9 * page && whichTab == true) {
            if (timer > 0) {
                timer--;
                hoverCoord.set(mouseCoor.x, mouseCoor.y);
                whichHover = 2;
            }
        } else if (362 < mouseCoor.x && mouseCoor.x < 488 && 199 - 19 * 2 < mouseCoor.y && mouseCoor.y < 216 - 19 * 2
                && bateau.getContenu().size() >= 3 + 9 * page && whichTab == true) {
            if (timer > 0) {
                timer--;
                hoverCoord.set(mouseCoor.x, mouseCoor.y);
                whichHover = 3;
            }
        } else if (362 < mouseCoor.x && mouseCoor.x < 488 && 199 - 19 * 3 < mouseCoor.y && mouseCoor.y < 216 - 19 * 3
                && bateau.getContenu().size() >= 4 + 9 * page && whichTab == true) {
            if (timer > 0) {
                timer--;
                hoverCoord.set(mouseCoor.x, mouseCoor.y);
                whichHover = 4;
            }
        } else if (362 < mouseCoor.x && mouseCoor.x < 488 && 199 - 19 * 4 < mouseCoor.y && mouseCoor.y < 216 - 19 * 4
                && bateau.getContenu().size() >= 5 + 9 * page && whichTab == true) {
            if (timer > 0) {
                timer--;
                hoverCoord.set(mouseCoor.x, mouseCoor.y);
                whichHover = 5;
            }
        } else if (362 < mouseCoor.x && mouseCoor.x < 488 && 199 - 19 * 5 < mouseCoor.y && mouseCoor.y < 216 - 19 * 5
                && bateau.getContenu().size() >= 6 + 9 * page && whichTab == true) {
            if (timer > 0) {
                timer--;
                hoverCoord.set(mouseCoor.x, mouseCoor.y);
                whichHover = 6;
            }
        } else if (362 < mouseCoor.x && mouseCoor.x < 488 && 199 - 19 * 6 < mouseCoor.y && mouseCoor.y < 216 - 19 * 6
                && bateau.getContenu().size() >= 7 + 9 * page && whichTab == true) {
            if (timer > 0) {
                timer--;
                hoverCoord.set(mouseCoor.x, mouseCoor.y);
                whichHover = 7;
            }
        } else if (362 < mouseCoor.x && mouseCoor.x < 488 && 199 - 19 * 7 < mouseCoor.y && mouseCoor.y < 216 - 19 * 7
                && bateau.getContenu().size() >= 8 + 9 * page && whichTab == true) {
            if (timer > 0) {
                timer--;
                hoverCoord.set(mouseCoor.x, mouseCoor.y);
                whichHover = 8;
            }
        } else if (362 < mouseCoor.x && mouseCoor.x < 488 && 199 - 19 * 8 < mouseCoor.y && mouseCoor.y < 216 - 19 * 8
                && bateau.getContenu().size() >= 9 + 9 * page && whichTab == true) {
            if (timer > 0) {
                timer--;
                hoverCoord.set(mouseCoor.x, mouseCoor.y);
                whichHover = 9;
            }
        } else {
            // Reset le timer si rien n'est survolé
            timer = 45;
            whichHover = 0;
        }

    }

    // Permet de factoriser le code de check de position
    private void verifierEtEquiperModule(int minX, int maxX, int minY, int maxY, int index, int caseValue) {
        if (mouseCoor.x >= minX && mouseCoor.x < maxX && mouseCoor.y >= minY && mouseCoor.y < maxY) {
            if (bateau.getModules().size() > index && bateau.getModules().get(index) instanceof CanneAPeche) {
                bateau.setEquipedCanne((CanneAPeche) bateau.getModules().get(index));
                caseCanne = caseValue;
            }
        }
    }

    // Pas de logic
    @Override
    public void logic(PecheActiveScreen screen) {
    }

    @Override
    public void draw(PecheActiveScreen screen) {
        screen.jeu.batch.draw(fondInventaire, 356, 25);

        // Affiche le stockage
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

            // Affiche le numéro de page et la place disponible
            screen.jeu.HebertBold.draw(screen.jeu.batch, Integer.toString(page + 1), 419.5f, 43f);
            screen.jeu.HebertBold.draw(screen.jeu.batch, "/", 423.6f, 42f);
            screen.jeu.HebertBold.draw(screen.jeu.batch, Integer.toString(((len - 1) / 9) + 1), 426f, 40f);
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Utilise : " + Integer.toString(bateau.getContenu().size())
                    + "/" + Integer.toString(bateau.getTailleStockage()), 362f, 236f);

            // Affiche les modules
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

            // Met l'en-tête du bateau
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Nom : \n" + bateau.getName(), 362f, 242f);
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Modele : \n" + bateau.getModeleName(), 425f, 242f);

        }

        // draw hover tab
        if (timer < 1) {
            screen.jeu.batch.draw(hoverTexture, hoverCoord.x - 106, hoverCoord.y);

            // affiche les caractériqtiques du poissons
            if (whichTab) {
                screen.jeu.batch.draw(bateau.getContenu().get(whichHover + 9 * page - 1).getHoverText(),
                        hoverCoord.x - 105, hoverCoord.y + 1);
                screen.jeu.batch.draw(hoverGradienTexture, hoverCoord.x - 106, hoverCoord.y);
                screen.jeu.HebertBold.draw(screen.jeu.batch,
                        bateau.getContenu().get(whichHover + 9 * page - 1).getNom(), hoverCoord.x - 101.5f,
                        hoverCoord.y + 60);
                screen.jeu.HebertBold.draw(screen.jeu.batch, "Taille : " + Float.toString(
                        (float) ((int) (bateau.getContenu().get(whichHover + 9 * page - 1).getTaille() * 10)) / 10)
                        + "cm", hoverCoord.x - 101.5f,
                        hoverCoord.y + 45);
            }
        }

    }

}
