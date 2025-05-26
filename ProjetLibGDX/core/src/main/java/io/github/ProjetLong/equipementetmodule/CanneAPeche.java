package io.github.ProjetLong.equipementetmodule;

import com.badlogic.gdx.graphics.Texture;

import io.github.ProjetLong.ZonesPeche.SousZone;

public class CanneAPeche implements ModuleBateau, Equipement {

    private int Niveau;

    // Quel modèle est la canne à pêche
    private SousZone zone;

    // Texture
    private Texture canneAPechTexture;

    // Nom et catégorie
    private String nom;
    private final String categorie = "Canne a peche"; // fixe

    private boolean aLaVente; // pas à la vente

    // Prix des cannes à pêche
    private final static int prixInitial = 20;
    private final static int prixParNiveau = 5;

    public CanneAPeche(int Niveau, SousZone zone) {

        // set la zone
        this.zone = zone;

        // Asset selon le niveau
        if (Niveau == 1) {
            canneAPechTexture = new Texture("fishing_rod_1_lvl1.png");
        } else {
            canneAPechTexture = new Texture("cregut.png");
        }

        // set le niveau le nom
        this.Niveau = Niveau;
        this.nom = "Canne";
        this.aLaVente = false;
    }

    // Autre constructeur
    public CanneAPeche(int Niveau) {
        this.Niveau = Niveau;
        canneAPechTexture = new Texture("fishing_rod_1_lvl1.png");
        this.nom = "Canne";
        this.aLaVente = true; // pour la vente
    }

    // Autre constructeur
    public CanneAPeche() {
        this(1);
    }

    @Override
    public Texture getTexture() {
        return canneAPechTexture;
    }

    @Override
    public int getNiveau() {
        return this.Niveau;
    }

    public SousZone getZone() {
        return zone;
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

    // augmente le niveau
    @Override
    public void ameliorer() {
        if (Niveau < 4) {
            Niveau++;
        }
    }

    @Override
    public int getPrix() {
        return (aLaVente) ? prixInitial + (Niveau - 1) * prixParNiveau : -1;
    }

    // Il faut lire le nom de la fonction
    @Override
    public void enleverDeLaVente() {
        aLaVente = false;
    }

    // Pareil
    @Override
    public Equipement dupliquer() {
        Equipement e = new CanneAPeche(this.Niveau, this.zone);
        e.setNom(this.nom);
        e.enleverDeLaVente(); // Duplique sans la vente
        return e;
    }
}
