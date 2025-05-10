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

    @Override
    public Texture getTexture() {
        return new Texture("cregut.png");
    }

    public Voile(String nom) {
        this.nom = nom;
        this.niveau = 1;
    }

    public Voile(String nom, int niveau) {
        if (niveau < 1 || niveau > 4) {
            throw new IllegalArgumentException("Le niveau doit être entre 1 et 4.");
        }
        this.nom = nom;
        this.niveau = niveau;
    }

    public void ameliorer() {
        if (niveau < 4) {
            niveau++;
            System.out.println("Voile améliorée au niveau " + niveau + " (" + getZoneActuelle() + ")");
        } else {
            System.out.println("La voile est déjà au niveau maximum.");
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
}
