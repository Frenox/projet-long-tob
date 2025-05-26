package io.github.ProjetLong.Bateaux;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Vaisseau extends Bateau {
    private Sprite boatSprite;

    public Vaisseau() {
        // crée un bateau par défaut
        super(6);

        // Ajoute les modules par défaut de la barque
        setName("Vaisseau");
        setModeleName("Vaisseau");

        // Gestion du sprite du bateau
        boatSprite = new Sprite(new Texture("voilier.png"));
        boatSprite.setPosition(227, 118);

        // Met le logo
        setLogo(new Texture("boat_logo5.png"));
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
