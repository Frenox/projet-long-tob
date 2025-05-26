package io.github.ProjetLong.BatimentQuai_package.BatimentQuaiVueGauche_package;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiModele;
import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiVue;

public class LabelPage extends Label{
    public LabelPage(BatimentQuaiModele modele) {
        super((modele.getPage() + 1) + " / " + (modele.getMaxPage() + 1), BatimentQuaiVue.skin, "HebertSansBold", Color.WHITE);
        
        modele.addPropertyChangeListener("Nombre de bateaux différent", new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent arg0) {
                
                setText((modele.getPage() + 1) + " / " + (modele.getMaxPage() + 1));
            }

        });
    }
}
