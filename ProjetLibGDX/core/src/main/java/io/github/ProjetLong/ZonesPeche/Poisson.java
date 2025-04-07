package main.java.io.github.ProjetLong.ZonesPeche;
import com.badlogic.gdx.graphics.Texture;


public class Poisson {
    
    //Attributs en final car ne doivent pas changer
    private final String nom;
    private final int id;
    private final int rarete; // 0: Normal ; 1: Shiny
    
    private final Texture[] texturesPossibles;
    private final int[] tailleSprite;

    public Poisson(String inNom, int inId, int inRarete, Texture[] inTexturesPossibles, int[] inTailleSprite) {
        this.nom = inNom;
        this.id = inId;
        this.rarete = inRarete;

        if (inTexturesPossibles.length == 2) {
            this.texturesPossibles = inTexturesPossibles;
        } else {
            this.texturesPossibles = new Texture[] {null, null};
        }
        if (inTailleSprite.length == 2) {
            this.tailleSprite = inTailleSprite;
        } else {
            this.tailleSprite = new int[] {52, 52};
        }
    }
    public Poisson(String inNom, int inId, int inRarete, Texture[] inTexturesPossibles) {
        this.nom = inNom;
        this.id = inId;
        this.rarete = inRarete;

        if (inTexturesPossibles.length == 2) {
            this.texturesPossibles = inTexturesPossibles;
        } else {
            this.texturesPossibles = new Texture[] {null, null};
        }
        this.tailleSprite = new int[] {52, 52};
    }

    public String getNom() {
        return this.nom;
    }
    public int getId() {
        return this.id;
    }
    public int getRarete() {
        return this.rarete;
    }
    public int[] getTailleSprite() {
        return this.tailleSprite;
    }

    public Texture getCurrentTexture() {
        return this.texturesPossibles[this.rarete];
    }
}
