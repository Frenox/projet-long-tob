package io.github.ProjetLong.BatimentQuai_package;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiVueGauche_package.ButtonsBateaux;
import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiVueGauche_package.ChangerPage;
import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiVueGauche_package.InformationsPrincipales;
import io.github.ProjetLong.batiments.BatimentQuai;

public class BatimentQuaiVueGauche extends Table {
    public BatimentQuaiVueGauche(BatimentQuai quai, BatimentQuaiModele modele) {

        Table informations_principales = new InformationsPrincipales(modele);
        Table buttonsBateaux = new ButtonsBateaux(quai, modele);
        Table changer_page = new ChangerPage(modele);

        this.add(informations_principales).left().pad(5).row();
        this.add(buttonsBateaux).row();
        this.add(changer_page);
    }
}
