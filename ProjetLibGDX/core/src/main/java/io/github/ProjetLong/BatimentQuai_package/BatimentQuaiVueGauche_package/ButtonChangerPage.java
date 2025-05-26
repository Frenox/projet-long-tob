package io.github.ProjetLong.BatimentQuai_package.BatimentQuaiVueGauche_package;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiModele;
/*Bouton pour changer de page
 */
public class ButtonChangerPage extends Button {

        public ButtonChangerPage(ButtonStyle buttonStyle, BatimentQuaiModele modele, int pas) {
            super(buttonStyle);

            this.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    modele.avancerPage(pas);
                }
            });
        }
    }
