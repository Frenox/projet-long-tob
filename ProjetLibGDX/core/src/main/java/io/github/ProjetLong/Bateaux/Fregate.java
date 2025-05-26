package io.github.ProjetLong.Bateaux;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Fregate extends Bateau {
    private Sprite boatSprite;

    public Fregate() {
        // crée un bateau par défaut
        super(5);

        // Met les noms par défaut du module
        setName("Fregate");
        setModeleName("Fregate");

        // Gestion du sprite du bateau
        boatSprite = new Sprite(new Texture("voilier.png"));
        boatSprite.setPosition(227, 118);

        // Met le logo
        setLogo(new Texture("boat_logo4.png"));
    }

    // Deplacement en x
    @Override
    public void addSpriteX(float x) {
        boatSprite.translateX(x);
    }

    // Deplacement en y
    @Override
    public void addSpriteY(float y) {
        boatSprite.translateY(y);
    }

    @Override
    public Sprite getSprite() {

        return boatSprite;
    }

    @Override
    public void setSprite(float x, float y) {

        boatSprite.setPosition(x, y);
    }

}
