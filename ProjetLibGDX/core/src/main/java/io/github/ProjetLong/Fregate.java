package io.github.ProjetLong;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import io.github.ProjetLong.ZonesPeche.SousZoneCotePort;

public class Fregate extends Bateau {
    private Sprite boatSprite;

    public Fregate() {
        super(5);
        setName("Fregate");
        setModeleName("Fregate");
        boatSprite = new Sprite(new Texture("voilier.png"));
        boatSprite.setPosition(227, 118);
        setLogo(new Texture("boat_logo4.png"));
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
