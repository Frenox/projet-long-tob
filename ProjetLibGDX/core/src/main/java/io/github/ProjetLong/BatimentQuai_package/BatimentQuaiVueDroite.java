package io.github.ProjetLong.BatimentQuai_package;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import io.github.ProjetLong.Bateau;
import io.github.ProjetLong.ModuleBateau;
import io.github.ProjetLong.ZonesPeche.Poisson;

/*
 * Regroupe les éléments affichés à droite de l'interface quai
 */
public class BatimentQuaiVueDroite extends Table {
    public BatimentQuaiVueDroite(BatimentQuaiModele modele) {
        super();

        //Poissons et éléments dans l'espace de stockage affichés 
        Table poissons_table = new Table();
        Table stockage_table = new Table();

        ScrollPane scrollpane_poissons = new ScrollPane(poissons_table);
        ScrollPane scrollpane_stockage = new ScrollPane(stockage_table);

        this.setSkin(BatimentQuaiVue.skin);
        this.add("Equipements disponibles :", "HebertSansBold", Color.WHITE).row();
        this.add(scrollpane_stockage).center().bottom().expand().height(BatimentQuaiVue.fishInv.getWidth() * 1.618f * 0.3f)
                .pad(5).padBottom(5).row();
        this.add("Poissons", "HebertSansBold", Color.WHITE).padBottom(0).row();
        this.add(scrollpane_poissons).center().bottom().expand().height(BatimentQuaiVue.fishInv.getWidth() * 1.618f * 0.3f).pad(5)
                .padTop(5);

        //Lorsque l'on veut afficher un nouveau bateau, la table des poissons et du stockage se mettent à jour
        modele.addPropertyChangeListener("Nouveau bateau affiché", new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent arg0) {
                Bateau bateau = (Bateau) arg0.getNewValue();

                poissons_table.clear();
                stockage_table.clear();

                for (Poisson poisson : bateau.getContenu()) {
                    poissons_table.add(new Image(poisson.getFishText())).row();
                }
                for (ModuleBateau module : bateau.getModules()) {
                    stockage_table.add(new Image(module.getTexture())).row();
                }
            }
            
        });
    }
}
