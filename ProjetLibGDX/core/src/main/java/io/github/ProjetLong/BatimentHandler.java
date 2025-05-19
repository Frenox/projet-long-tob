package io.github.ProjetLong;

import java.util.LinkedHashMap;
import java.util.Map;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class BatimentHandler { //Nécessité d'utiliser un InputAdapter car l'état de la molette n'est pas gardé en mémoire par LibGDX

    private int offset;
    private int size;
    private Map<Batiment, Boolean> BatimentList; //Associe a chaque bat un show ou pas booleen

    public BatimentHandler() {
        this.offset = 0;
        this.size = 0;
        this.BatimentList = new LinkedHashMap<Batiment,Boolean>();
    }

    public void input(VilleScreen screen){

    }

    public void logic(VilleScreen screen){

    }

    public void draw(VilleScreen screen){

    }

    public void offsetIncrementer() {
        this.offset = this.offset + 1;
    }

    public void offsetDecrementer() {
        this.offset = this.offset - 1;
    }

    public void addBatiment(Batiment bat) {
        
    }
}
    
