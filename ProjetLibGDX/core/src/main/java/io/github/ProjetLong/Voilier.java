package io.github.ProjetLong;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import io.github.ProjetLong.ZonesPeche.SousZoneCotePort;

public class Voilier extends Bateau {
    private Sprite boatSprite;

    public Voilier() {
        super(3);
        setName("Voilier");
        setModeleName("Voilier");
        addVoile(new Voile("", 4));
        boatSprite = new Sprite(new Texture("voilier.png"));
        boatSprite.setPosition(195, 118);
        setLogo(new Texture("boat_logo2.png"));
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
