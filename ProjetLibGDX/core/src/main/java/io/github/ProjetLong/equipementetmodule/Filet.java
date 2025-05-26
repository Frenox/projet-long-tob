package io.github.ProjetLong.equipementetmodule;

import com.badlogic.gdx.graphics.Texture;

import io.github.ProjetLong.ZonesPeche.SousZone;

public class Filet implements ModuleBateau, Equipement {
    private int Niveau;
    private SousZone zone;
    private Texture filetTexture;
    private String nom;
    private final String categorie = "Filet"; // fixe

    private boolean aLaVente;
    private final static int prixInitial = 35; // Plus cher qu'une canne car plus efficace
    private final static int prixParNiveau = 8;

    private int capaciteCapture; // Capacité de capture de poissons

    public Filet(int Niveau, SousZone zone) {
        this.zone = zone;
        this.Niveau = Niveau;
        this.nom = "Filet";
        this.aLaVente = false;

        // Capacité de capture augmente avec le niveau
        this.capaciteCapture = 2 + Niveau; // Niveau 1 = 3 poissons max, etc.

        // Texture selon le niveau
        switch (Niveau) {
            case 1:
                filetTexture = new Texture("fishing_rod_1_lvl1.png");
                break;
            case 2:
                filetTexture = new Texture("fishing_rod_1_lvl1.png");
                break;
            case 3:
                filetTexture = new Texture("fishing_rod_1_lvl1.png");
                break;
            case 4:
                filetTexture = new Texture("fishing_rod_1_lvl1.png");
                break;
            default:
                filetTexture = new Texture("fishing_rod_1_lvl1.png");
        }
    }

    public Filet(int Niveau) {
        this.Niveau = Niveau;
        this.nom = "Filet";
        this.aLaVente = true; // pour la vente
        this.capaciteCapture = 2 + Niveau;

        // Texture selon le niveau
        switch (Niveau) {
            case 1:
                filetTexture = new Texture("fishing_rod_1_lvl1.png");
                break;
            case 2:
                filetTexture = new Texture("fishing_rod_1_lvl1.png");
                break;
            case 3:
                filetTexture = new Texture("fishing_rod_1_lvl1.png");
                break;
            case 4:
                filetTexture = new Texture("fishing_rod_1_lvl1.png");
                break;
            default:
                filetTexture = new Texture("fishing_rod_1_lvl1.png");
        }
    }

    // Constructeur pour la vente
    public Filet() {
        this(1);
    }

    @Override
    public Texture getTexture() {
        return filetTexture;
    }

    @Override
    public int getNiveau() {
        return this.Niveau;
    }

    public SousZone getZone() {
        return zone;
    }

    public void setZone(SousZone zone) {
        this.zone = zone;
    }

    public int getCapaciteCapture() {
        return capaciteCapture;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String getCategorie() {
        return categorie;
    }

    @Override
    public void ameliorer() {
        if (Niveau < 4) {
            Niveau++;
            this.capaciteCapture = 2 + Niveau;
        }
    }

    @Override
    public int getPrix() {
        return (aLaVente) ? prixInitial + (Niveau - 1) * prixParNiveau : -1;
    }

    // Enleve de la possibilité d'acheter
    @Override
    public void enleverDeLaVente() {
        aLaVente = false;
    }

    @Override
    public Equipement dupliquer() {
        Equipement e = new Filet(this.Niveau, this.zone);
        e.setNom(this.nom);
        e.enleverDeLaVente(); // Duplique sans la vente
        return e;
    }
}