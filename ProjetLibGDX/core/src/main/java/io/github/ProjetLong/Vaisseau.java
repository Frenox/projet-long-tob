package io.github.ProjetLong;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import io.github.ProjetLong.ZonesPeche.SousZoneCotePort;

public class Vaisseau extends Bateau {
    private Sprite boatSprite;

    public Vaisseau() {
        super(6);
        setName("Vaisseau");
        setModeleName("Vaisseau");
        boatSprite = new Sprite(new Texture("voilier.png"));
        boatSprite.setPosition(227, 118);
        setEquipedCanne(getCannes().get(0));
        setLogo(new Texture("boat_logo5"));
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

    @Override
    public void setSprite(float x, float y) {

        boatSprite.setPosition(x, y);
    }

}
