package io.github.ProjetLong.BatimentQuai_package;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.ProjetLong.BatimentQuai;

/*
 * Regroupe l'ensemble des éléments affichés sur l'interface du quai
 */
public class BatimentQuaiVue extends Table {
    public static final Texture fishInv = new Texture("fish_tab_fish.png");
    public static final Skin skin = new Skin(Gdx.files.internal("skin.json"));
    
    public BatimentQuaiVue(BatimentQuai quai, BatimentQuaiModele modele) {
        
        quai.addActor(this);
        this.add(new BatimentQuaiVueGauche(quai, modele));
        this.add(new BatimentQuaiVueMilieu(modele));
        this.add(new BatimentQuaiVueDroite(modele));
    }
}
