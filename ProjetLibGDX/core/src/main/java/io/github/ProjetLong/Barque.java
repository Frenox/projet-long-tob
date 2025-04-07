package io.github.ProjetLong;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Barque extends Bateau {
    private Sprite boatSprite;

    public Barque() {
        super(2);
        addStockage(new Stockage(1));
        addCannes(new CanneAPeche(1));
        boatSprite = new Sprite(new Texture("boat1.png"));
        boatSprite.setPosition(227, 118);

    }

    @Override
    public void addSpriteX(float x) {
        boatSprite.translateX(x);
    }

    @Override
    public void addSpriteY(float y) {
        boatSprite.translateY(y);
    }

    @Override
    public Sprite getSprite() {

        return boatSprite;
    }
}
