package io.github.ProjetLong;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import io.github.ProjetLong.ZonesPeche.SousZoneCotePort;

public class Barque extends Bateau {
    private Sprite boatSprite;

    public Barque() {
        super(2);
        addCannes(new CanneAPeche(1, new SousZoneCotePort()));
        addStockage(new Stockage(1));
        setName("Barque");
        setModeleName("Barque");
        boatSprite = new Sprite(new Texture("boat1.png"));
        boatSprite.setPosition(227, 118);
        setEquipedCanne(getCannes().get(0));
        for (int i = 0; i != 5; i++) {
            addPoisson(getEquipedCanne().getZone().getRandPoisson());
        }
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
