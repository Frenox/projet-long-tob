package io.github.ProjetLong.BatimentQuai_package.BatimentQuaiVueGauche_package;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import io.github.ProjetLong.Bateau;
import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiModele;
import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiVue;

/*
 * Partie de l'interface qui regroupe les informations principales du bateau
 * comme, le nom du bateau, son modele et sa taille de stockage
 */
public class InformationsPrincipales extends Table {
    Label nom_bateau;
    Label modele_bateau;
    Label taille_stockage;

    public InformationsPrincipales(BatimentQuaiModele modele) {
        nom_bateau = new Label("Bateau :", BatimentQuaiVue.skin, "HebertSansBold", Color.WHITE);
        modele_bateau = new Label("Modele :", BatimentQuaiVue.skin, "HebertSansBold", Color.WHITE);
        taille_stockage = new Label("Taille Stockage :", BatimentQuaiVue.skin, "HebertSansBold", Color.WHITE);

        this.add(nom_bateau).pad(2).row();
        this.add(modele_bateau).pad(2).row();
        this.add(taille_stockage).pad(2).row();

        modele.addPropertyChangeListener("Nouveau bateau affiché", new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent arg0) {
                Bateau bateau = (Bateau) arg0.getNewValue();

                nom_bateau.setText("Bateau :" + bateau.getName());
                modele_bateau.setText("Modele : " + bateau.getModeleName());
                taille_stockage.setText("Taille Stockage : " + bateau.getTailleStockage());
            }
            
        });
    }
}
