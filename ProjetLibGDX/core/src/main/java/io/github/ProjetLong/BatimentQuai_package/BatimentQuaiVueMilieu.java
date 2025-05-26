package io.github.ProjetLong.BatimentQuai_package;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import io.github.ProjetLong.Bateaux.Bateau;

/*
 * Regroupe les éléments affichés au milieu de l'interface quai
 */
public class BatimentQuaiVueMilieu extends Table {
    public BatimentQuaiVueMilieu(BatimentQuaiModele modele){
        super();
        
        Image Equipement_Texture = new Image();

        //La canne à pêche équipée est affichée
        this.add(new Label("Equipement : ", BatimentQuaiVue.skin, "HebertSansBold", Color.WHITE)).top().left().pad(2).expand().row();
        this.add(Equipement_Texture).center().pad(2).expand();

        //Lorsque l'on veut afficher un nouveau bateau, on met à jour l'affichage de la canne à pêche équipée
        modele.addPropertyChangeListener("Nouveau bateau affiché", new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent arg0) {
                Bateau bateau = (Bateau) arg0.getNewValue();

                Equipement_Texture.setDrawable(new TextureRegionDrawable(bateau.getEquipedCanne().getTexture()));
            }
            
        });
    }
}
