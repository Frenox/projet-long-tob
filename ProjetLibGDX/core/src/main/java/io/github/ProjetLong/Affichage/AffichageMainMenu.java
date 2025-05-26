package io.github.ProjetLong.Affichage;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

import io.github.ProjetLong.screen.VilleScreen;
import io.github.ProjetLong.screen.mainMenuScreen;

public class AffichageMainMenu {
    // Dans quel sous menu on se situe
    // Le principal ou le gestionnaire de save
    // et quel bouton est survolé
    private int state;
    private boolean hover;
    private int idMenu; // quel menu afficher

    // Textures du menu
    private Texture BG = new Texture("menu_princ_bg.png");
    private Texture reprendre;
    private Texture option;
    private Texture quitter;
    private Texture rienm;
    private Texture slot1;
    private Texture slot2;
    private Texture slot3;
    private Texture slotr;
    private Texture slotret;
    private Texture vide;
    private Vector3 mouseCoor;

    // Son de bouton
    private static final Sound buttonSfx = Gdx.audio.newSound(Gdx.files.internal("audio/ButtonClick.mp3"));

    public AffichageMainMenu() {

        // Setup les paramètres
        idMenu = 0;
        state = 0;

        // Textures du menu
        vide = new Texture("vide.png");
        reprendre = new Texture("menu_princ_bt3.png");
        option = new Texture("menu_princ_bt2.png");
        quitter = new Texture("menu_princ_bt1.png");
        rienm = new Texture("menu_princ_btr.png");
        slot1 = new Texture("slot1.png");
        slot2 = new Texture("slot2.png");
        slot3 = new Texture("slot3.png");
        slotr = new Texture("slotn.png");
        slotret = new Texture("slotret.png");
    }

    // Renvoie si la sauvegarde existe
    private boolean slotExiste(int slotNb) {
        String slotName = "slot" + (String.valueOf(slotNb)) + ".json";
        File slotFile = new File(slotName);
        return slotFile.exists() && !slotFile.isDirectory();
    }

    // Supprime la sauvegarde
    private void slotDelete(int slotNb) {
        String slotName = "slot" + (String.valueOf(slotNb)) + ".json";
        File slotFile = new File(slotName);
        slotFile.delete();
    }

    public void input(mainMenuScreen screen) {

        // Récupère les coordonnées de la souris
        mouseCoor = screen.jeu.viewport.getCamera()
                .unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        // Si on est dans le premier menu il y a 3 boutons
        if (idMenu == 0) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && state != 3) {
                state += 1;
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && state != 1) {
                state -= 1;
                if (state == 0)
                    state = 1;
            }
            if (state == 3 && (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
                    || (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && hover))) {

                screen.jeu.exit();
            } else if (state == 1 && (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
                    || (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && hover))) {
                idMenu = 1;
                state = 0;
                buttonSfx.play();
            }
            // Test hover souris
            if (226 < mouseCoor.x && mouseCoor.x < 286 && 200 - 103 < mouseCoor.y && mouseCoor.y < 212 - 103) {
                state = 3;
                hover = true;
            } else if (226 < mouseCoor.x && mouseCoor.x < 286 && 221 - 103 < mouseCoor.y && mouseCoor.y < 233 - 103) {
                state = 2;
                hover = true;
            } else if (226 < mouseCoor.x && mouseCoor.x < 286 && 242 - 103 < mouseCoor.y && mouseCoor.y < 254 - 103) {
                state = 1;
                hover = true;
            } else {
                hover = false;
            }

            // Si on est dans le premier menu il y a 4 boutons

        } else if (idMenu == 1) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && state != 4) {
                state += 1;
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && state != 1) {
                state -= 1;
                if (state == 0)
                    state = 1;
            }

            // Bouton retour
            if (state == 4 && (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
                    || (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && hover))) {
                idMenu = 0;
                state = 0;
                buttonSfx.play();

                // Gestion des sauvegardes
            } else if (state == 1 && (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
                    || (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && hover))) {
                // slot 1 lancement
                buttonSfx.play();
                boolean reussi = screen.jeu.data.loadGame("slot1");
                if (!reussi) {
                    screen.jeu.data.saveGame("slot1");
                    screen.jeu.data.loadGame("slot1");
                }
                screen.jeu.setScreen(new VilleScreen(screen.jeu));

            } else if (state == 2 && (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
                    || (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && hover))) {
                // slot 2 lancement
                buttonSfx.play();
                boolean reussi = screen.jeu.data.loadGame("slot2");
                if (!reussi) {
                    screen.jeu.data.saveGame("slot2");
                    screen.jeu.data.loadGame("slot2");
                }
                screen.jeu.setScreen(new VilleScreen(screen.jeu));

            } else if (state == 3 && (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
                    || (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && hover))) {
                // slot 3 lancement
                buttonSfx.play();
                boolean reussi = screen.jeu.data.loadGame("slot3");
                if (!reussi) {
                    screen.jeu.data.saveGame("slot3");
                    screen.jeu.data.loadGame("slot3");
                }
                screen.jeu.setScreen(new VilleScreen(screen.jeu));

            }

            // Test hover souris sur les boutons
            if (191 < mouseCoor.x && mouseCoor.x < 320 && 89 < mouseCoor.y && mouseCoor.y < 114) {
                state = 3;
                hover = true;
            } else if (191 < mouseCoor.x && mouseCoor.x < 320 && 119 < mouseCoor.y && mouseCoor.y < 144) {
                state = 2;
                hover = true;
            } else if (191 < mouseCoor.x && mouseCoor.x < 320 && 149 < mouseCoor.y && mouseCoor.y < 174) {
                state = 1;
                hover = true;
            } else if (228 < mouseCoor.x && mouseCoor.x < 285 && 76 < mouseCoor.y && mouseCoor.y < 84) {
                state = 4;
                hover = true;
            } else {
                hover = false;
            }
        }

    }

    // Pas de logic
    public void logic(mainMenuScreen screen) {
    }

    public void draw(mainMenuScreen screen) {

        // Dessin du background
        screen.jeu.batch.draw(BG, 0, 0);

        // Dessin du menu principal
        if (idMenu == 0) {
            if (state == 1) {
                screen.jeu.batch.draw(reprendre, 0, 45);
            } else if (state == 2) {
                screen.jeu.batch.draw(option, 0, 45);
            } else if (state == 3) {
                screen.jeu.batch.draw(quitter, 0, 45);
            } else {
                screen.jeu.batch.draw(rienm, 0, 0);
            }

            // Dessin du menu des sauvegardes
        } else if (idMenu == 1) {
            if (state == 1) {
                screen.jeu.batch.draw(slot1, 0, -6);
            } else if (state == 2) {
                screen.jeu.batch.draw(slot2, 0, -6);
            } else if (state == 3) {
                screen.jeu.batch.draw(slot3, 0, -6);
            } else if (state == 4) {
                screen.jeu.batch.draw(slotret, 0, -6);
            } else {
                screen.jeu.batch.draw(slotr, 0, -6);
            }

            // Dessin de l'indication "Vide"
            // Et de suppression des slot
            if (!slotExiste(1)) {
                screen.jeu.batch.draw(vide, 191, 149);
            } else {
                if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
                    slotDelete(1);
                }
            }
            if (!slotExiste(2)) {
                screen.jeu.batch.draw(vide, 191, 119);
            } else {
                if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
                    slotDelete(2);
                }
            }
            if (!slotExiste(3)) {
                screen.jeu.batch.draw(vide, 191, 89);
            } else {
                if (Gdx.input.isKeyJustPressed(Input.Keys.SEMICOLON)) {
                    slotDelete(3);
                }
            }
        }
    }

}
