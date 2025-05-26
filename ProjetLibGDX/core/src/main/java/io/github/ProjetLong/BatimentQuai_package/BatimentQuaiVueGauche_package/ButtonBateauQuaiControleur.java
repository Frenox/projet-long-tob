package io.github.ProjetLong.BatimentQuai_package.BatimentQuaiVueGauche_package;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Null;

import io.github.ProjetLong.Bateau;
import io.github.ProjetLong.BatimentQuai;
import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiVue;
import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiModele;

/* Bouton pour sélectionner quel bateau afficher
 * 
 */

public class ButtonBateauQuaiControleur extends Button {
        /* Numéro du bateau affiché modulo CAPACITE_MAX_MENU. */
        private int i;
        /* Bouton pour supprimer un bateau */
        private TextButton SupprimerButton;
        /* Modèle du quai gérant l'interface */
        private BatimentQuaiModele batimentQuaiModele;

        public ButtonBateauQuaiControleur(BatimentQuai batimentQuai, BatimentQuaiModele batimentQuaiModele, ButtonStyle buttonStyle, int i) {
            super(buttonStyle);

            //Initialisation des variables
            this.i = i;
            this.batimentQuaiModele = batimentQuaiModele;

            int width1 = 20;
            int height1 = 10;

            //Mise en place de l'arrière-plan du bouton supprimer
            Pixmap pixmap = new Pixmap(width1, height1, Format.RGBA8888);
            pixmap.setColor(30 / 255, 11 / 255, 2 / 255, 1f);
            pixmap.fillRectangle(0, 0, width1, height1);

            Texture pixmaptex1 = new Texture(pixmap);
            Skin skin1 = new Skin();
            skin1.add("Rectangle", pixmaptex1);

            TextButtonStyle style = new TextButtonStyle(skin1.getDrawable("Rectangle"), skin1.getDrawable("Rectangle"),
                    skin1.getDrawable("Rectangle"), BatimentQuaiVue.skin.getFont("HebertSansBold"));

            //Création du bouton Supprimer
            this.SupprimerButton = new TextButton("Supprimer", style);
            this.SupprimerButton.setPosition(this.getX() + 20, this.getY() + 12);
            this.SupprimerButton.setVisible(false);
            this.SupprimerButton.setZIndex(1);
            batimentQuai.addActor(this.SupprimerButton);

            this.afficher();

            //Lorsqu'un bateau est supprimé, on cache un bouton
            batimentQuaiModele.addPropertyChangeListener("Nombre de bateaux différent", new PropertyChangeListener() {

                @Override
                public void propertyChange(PropertyChangeEvent arg0) {
                    afficher();
                }

            });

            //Lorsque le bouton supprimer est cliqué, le bateau correspondant est supprimé
            this.SupprimerButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    batimentQuaiModele.retirer_element(i);
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);
                    SupprimerButton.setVisible(true);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, @Null Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                    SupprimerButton.setVisible(false);
                }
            });

            //Ajout d'un ClickListener pour savoir s'il y a un bateau à afficher
            //et si le bouton supprimer doit être affiché
            this.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    batimentQuaiModele.setBateauCourant(i);
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);
                    SupprimerButton.setVisible(true);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, @Null Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                    SupprimerButton.setVisible(false);
                }
            });


        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            this.SupprimerButton.setPosition(this.getX() + 75, this.getY() + 2);
            
            //On dessine le nom du bateau sur le bouton
            if (this.batimentQuaiModele.element_affichable(i)) {
                Bateau bateau = this.batimentQuaiModele.bateau_i(i);
                BatimentQuaiVue.skin.getFont("HebertSansBold").draw(batch, bateau.getName(), this.getX() + 4, this.getY() + 12);
            }
        }

        //Afficher le bouton s'il y a un bateau à afficher
        public void afficher() {
            if (batimentQuaiModele.element_affichable(i)) {
                setVisible(true);
            } else {
                setVisible(false);
                SupprimerButton.setVisible(false);
            }
        }
    }
