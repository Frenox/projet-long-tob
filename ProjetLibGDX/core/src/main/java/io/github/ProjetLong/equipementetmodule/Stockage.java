package io.github.ProjetLong.equipementetmodule;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.Texture;

import io.github.ProjetLong.ZonesPeche.Poisson;

public class Stockage implements ModuleBateau {

    // Variables de stats du module
    private int TailleDisponible;
    private int Niveau;
    private List<Poisson> Contenu;

    // Texture utilisée
    private Texture stockageTexture;

    public Stockage(int inNiveau) {

        // Setup les visuels
        Contenu = new ArrayList<Poisson>();
        Niveau = inNiveau;
        if (Niveau == 1 || Niveau == 2 || Niveau == 3) {
            stockageTexture = new Texture("stockage_lvl1.png");
        }

        // Set la taille du module
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

    // Ajoute un poisson au stockage
    public void addPoisson(Poisson poisson) {
        Contenu.add(poisson);
        TailleDisponible -= 1;
    }

    // Enlève un poisson du stockage
    public void removePoisson(Poisson poisson) {
        Contenu.remove(poisson);
        TailleDisponible += 1;
    }

    public void addPlace() {
        TailleDisponible += 1;
    }

}
