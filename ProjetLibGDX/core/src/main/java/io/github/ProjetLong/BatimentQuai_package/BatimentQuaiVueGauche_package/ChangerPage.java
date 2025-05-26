package io.github.ProjetLong.BatimentQuai_package.BatimentQuaiVueGauche_package;

import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiModele;
import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiVue;

/*
 * Ensemble des boutons et informations pour changer de page
 */
public class ChangerPage extends Table {
    public ChangerPage(BatimentQuaiModele modele) {
        super();
        
        Drawable arrow_left = BatimentQuaiVue.skin.getDrawable("left_arrow");
        Drawable arrow_right = BatimentQuaiVue.skin.getDrawable("right_arrow");
        ButtonStyle styleChangerPageGauche = new ButtonStyle(arrow_left,
                arrow_left, arrow_left);
        ButtonStyle styleChangerPageDroite = new ButtonStyle(arrow_right, arrow_right, arrow_right);

        this.add(new ButtonChangerPage(styleChangerPageGauche, modele, -1)).pad(3);
        this.add(new LabelPage(modele)).pad(3);
        this.add(new ButtonChangerPage(styleChangerPageDroite, modele, 1)).pad(3);
    }
}
