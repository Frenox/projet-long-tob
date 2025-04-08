package io.github.ProjetLong;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import io.github.ProjetLong.ZonesPeche.Poisson;

public class Barque extends Bateau {
    private Sprite boatSprite;

    public Barque() {
        super(2);
        addStockage(new Stockage(2));
        addCannes(new CanneAPeche(1));
        boatSprite = new Sprite(new Texture("boat1.png"));
        boatSprite.setPosition(227, 118);
        addPoisson(new Poisson(1, 0));
        addPoisson(new Poisson(2, 1));
        addPoisson(new Poisson(3, 0));
        addPoisson(new Poisson(2, 1));
        addPoisson(new Poisson(3, 0));
        addPoisson(new Poisson(2, 1));
        addPoisson(new Poisson(2, 0));
        addPoisson(new Poisson(2, 1));
        addPoisson(new Poisson(2, 1));
        addPoisson(new Poisson(0, 0));
        addPoisson(new Poisson(3, 1));
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
