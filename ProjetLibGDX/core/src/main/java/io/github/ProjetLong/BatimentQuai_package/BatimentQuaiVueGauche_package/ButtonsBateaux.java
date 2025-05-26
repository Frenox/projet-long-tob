package io.github.ProjetLong.BatimentQuai_package.BatimentQuaiVueGauche_package;

import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import io.github.ProjetLong.BatimentQuai;
import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiVue;
import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiModele;

/*
 * Partie de l'interface regroupant l'ensemble
 */
public class ButtonsBateaux extends Table{
    public ButtonsBateaux(BatimentQuai quai, BatimentQuaiModele modele) {
        super();

        ButtonStyle buttonStylePoisson = new ButtonStyle(BatimentQuaiVue.skin.getDrawable("fish_tab_fish"),
                BatimentQuaiVue.skin.getDrawable("fish_tab_fish"), BatimentQuaiVue.skin.getDrawable("fish_tab_fish"));


        for (int i = 0; i < modele.CAPACITE_MAX_MENU; i++) {
                this.add(new ButtonBateauQuaiControleur(quai, modele, buttonStylePoisson, i)).pad(2);
                this.row();
        }
    }
}
