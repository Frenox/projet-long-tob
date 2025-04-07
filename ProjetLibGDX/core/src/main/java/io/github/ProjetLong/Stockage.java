package io.github.ProjetLong;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.Texture;

public class Stockage implements ModuleBateau {
    private int TailleDisponible;
    private int Niveau;
    private List<Poisson> Contenu;
    private Texture stockageTexture;

    public Stockage(int Niveau) {
        Contenu = new ArrayList<Poisson>();
        if (Niveau == 1) {
            stockageTexture = new Texture("stockage_lvl1.png");
        }
    }

    public Texture getTexture() {
        return stockageTexture;
    }

    @Override
    public int getNiveau() {
        return Niveau;
    }

    public List<Poisson> getContenu() {
        return Contenu;
    }

    public int getTailleDisponible() {
        return TailleDisponible;
    }

    public void addPoisson(Poisson poisson) {
        Contenu.add(poisson);
    }

    public void removePoisson(Poisson poisson) {
        Contenu.remove(poisson);
    }

}
