package io.github.ProjetLong.ZonesPeche;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;

//FONCTIONNEMENT
//creation à partir de son id et de si il est shiny ou non
//
//possibilité de générer une taille random dans une certaine range pour chaque poisson
//dans ce cas rajouter un boolean à la fin du new
//
//A une texture qui lui est associée et un nom ==> getFishText() et getNom()
//
// ID
// 1 = bar
// 2 = maquereau
// 3 = rascasse

public class Poisson {

    // Attributs en final car ne doivent pas changer
    private final String nom;
    private final int id;
    private final int rarete; // 0: Normal ; 1: Shiny
    private final float taille;
    private final Texture fishText;
    private final Texture hoverText;

    public Poisson(int inId, int inRarete, boolean aUneTaille) {
        this.nom = getIDNom(inId, inRarete);
        this.id = inId;
        this.rarete = inRarete;
        fishText = getiDTexture(inId, inRarete);
        taille = getIDTaille(inId, inRarete);
        hoverText = getiDTextureHover(inId, inRarete);

    }

    public Poisson(int inId, int inRarete) {
        this.nom = getIDNom(inId, inRarete);
        this.id = inId;
        this.rarete = inRarete;
        fishText = getiDTexture(inId, inRarete);
        taille = 0;
        hoverText = getiDTextureHover(inId, inRarete);

    }

    public String getNom() {
        return this.nom;
    }

    public Texture getHoverText() {
        return hoverText;
    }

    public int getId() {
        return this.id;
    }

    public int getRarete() {
        return this.rarete;
    }

    public Texture getFishText() {
        return this.fishText;
    }

    public float getTaille() {
        return this.taille;
    }

    private Texture getiDTexture(int id, int rarete) {
        // ID
        // 1 = bar
        // 2 = maquereau
        // 3 = rascasse

        if (id == 1 && rarete == 0) {
            return new Texture("poissons/fish_bar.png");

        } else if (id == 1 && rarete == 1) {
            return new Texture("poissons/fish_bar_shiny.png");

        } else if (id == 2 && rarete == 0) {
            return new Texture("poissons/fish_maquereau.png");

        } else if (id == 2 && rarete == 1) {
            return new Texture("poissons/fish_maquereau_shiny.png");

        } else if (id == 3 && rarete == 0) {
            return new Texture("poissons/fish_rascasse.png");

        } else if (id == 3 && rarete == 1) {
            return new Texture("poissons/fish_rascasse_shiny.png");

        } else {
            return new Texture("cregut.png");
        }
    }

    private String getIDNom(int id, int rarete) {
        if (id == 1 && rarete == 0) {
            return "Bar";

        } else if (id == 1 && rarete == 1) {
            return "Bar corrompu";

        } else if (id == 2 && rarete == 0) {
            return "Maquereau";

        } else if (id == 2 && rarete == 1) {
            return "Maquereau corrompu";

        } else if (id == 3 && rarete == 0) {
            return "Rascasse";

        } else if (id == 3 && rarete == 1) {
            return "Rascasse corrompue";

        } else {
            return "cregut";
        }
    }

    public float getIDTaille(int id, int rarete) {
        Random r = new Random();

        if (id == 1 && rarete == 0) {
            float min = 20;
            float max = 80;
            return min + r.nextFloat() * (max - min);

        } else if (id == 1 && rarete == 1) {
            float min = 40;
            float max = 110;
            return min + r.nextFloat() * (max - min);

        } else if (id == 2 && rarete == 0) {
            float min = 5;
            float max = 35;
            return min + r.nextFloat() * (max - min);

        } else if (id == 2 && rarete == 1) {
            float min = 15;
            float max = 50;
            return min + r.nextFloat() * (max - min);

        } else if (id == 3 && rarete == 0) {
            float min = 30;
            float max = 70;
            return min + r.nextFloat() * (max - min);

        } else if (id == 3 && rarete == 1) {
            float min = 30;
            float max = 80;
            return min + r.nextFloat() * (max - min);

        } else {
            return 0;
        }
    }

    public Texture getiDTextureHover(int id, int rarete) {
        // ID
        // 1 = bar
        // 2 = maquereau
        // 3 = rascasse

        if (id == 1 && rarete == 0) {
            return new Texture("poissons/hover_bar.png");

        } else if (id == 1 && rarete == 1) {
            return new Texture("poissons/hover_bar_shiny.png");

        } else if (id == 2 && rarete == 0) {
            return new Texture("poissons/hover_maquereau.png");

        } else if (id == 2 && rarete == 1) {
            return new Texture("poissons/hover_maquereau_shiny.png");

        } else if (id == 3 && rarete == 0) {
            return new Texture("poissons/hover_rascasse.png");

        } else if (id == 3 && rarete == 1) {
            return new Texture("poissons/hover_rascasse_shiny.png");

        } else {
            return new Texture("cregut.png");
        }
    }
}
