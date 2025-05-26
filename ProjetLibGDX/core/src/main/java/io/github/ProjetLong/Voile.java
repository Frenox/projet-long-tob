package io.github.ProjetLong;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;

public class Voile implements ModuleBateau, Equipement {
    private int niveau;
    private String nom;
    private final String categorie = "voile"; // fixe
    private final static String[] ZONES = {
            "Côte", // niveau 1
            "Océan", // niveau 2
            "Tropique", // niveau 3
            "Arctique" // niveau 4
    };

    // Achetable ou non
    private boolean aLaVente = false; // pas à la vente
    private final static int prixInitial = 100;
    private final static int prixParNiveau = 50;

    @Override
    public Texture getTexture() {
        return new Texture("voile_lvl_1.png");
    }

    // Créé la voile par défaut
    public Voile(String nom) {
        this.nom = nom;
        this.niveau = 1;
    }

    // Créé la voile avec un niveau donné
    public Voile(String nom, int niveau) {
        if (niveau < 1 || niveau > 4) {
            throw new IllegalArgumentException("Le niveau doit être entre 1 et 4.");
        }
        this.nom = nom;
        this.niveau = niveau;
    }

    // Améliore la voile jusqu'au niveau 4 maximum
    public void ameliorer() {
        if (niveau < 4) {
            niveau++;
        }
    }

    public String getZoneActuelle() {
        return ZONES[niveau - 1];
    }

    public List<String> getZonesAccessibles() {
        List<String> accessibles = new ArrayList<>();
        for (int i = 0; i < niveau; i++) {
            accessibles.add(ZONES[i]);
        }
        return accessibles;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        if (niveau < 1 || niveau > 4) {
            throw new IllegalArgumentException("Le niveau doit être entre 1 et 4.");
        }
        this.niveau = niveau;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public int getPrix() {
        return (aLaVente) ? prixInitial + (niveau - 1) * prixParNiveau : -1; // -1 si pas à la vente
    }

    // Enlève la voile des items disponible à la vente
    public void enleverDeLaVente() {
        this.aLaVente = false;
    }

    @Override
    public Equipement dupliquer() {
        Equipement e = new Voile(this.nom, this.niveau);
        e.enleverDeLaVente(); // Duplique sans la vente
        return e;
    }
}
