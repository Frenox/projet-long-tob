package io.github.ProjetLong;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.Texture;

import io.github.ProjetLong.ZonesPeche.Poisson;

public class Stockage implements ModuleBateau {
    private int TailleDisponible;
    private int Niveau;
    private List<Poisson> Contenu;
    private Texture stockageTexture;

    public Stockage(int Niveau) {
        Contenu = new ArrayList<Poisson>();
        if (Niveau == 1 || Niveau == 2) {
            stockageTexture = new Texture("stockage_lvl1.png");
        }
        TailleDisponible = (int) Math.pow(2, (2 + Niveau));
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
        TailleDisponible -= 1;
    }

    public void removePoisson(Poisson poisson) {
        Contenu.remove(poisson);
        TailleDisponible += 1;
    }

}
