package io.github.ProjetLong;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import io.github.ProjetLong.ZonesPeche.SousZoneCotePort;

public class Voilier extends Bateau {
    private Sprite boatSprite;

    public Voilier() {
        // crée un bateau par défaut
        super(3);

        // Ajoute les modules par défaut de la barque
        addVoile(new Voile("", 4));
        addStockage(new Stockage(2));
        addCannes(new CanneAPeche(1, new SousZoneCotePort()));
        // Met les noms par défaut du module
        setName("Voilier");
        setModeleName("Voilier");

        // Gestion du sprite du bateau
        boatSprite = new Sprite(new Texture("voilier.png"));
        boatSprite.setPosition(195, 118);
        setEquipedCanne(getCannes().get(0));
        // Met le logo
        setLogo(new Texture("boat_logo2.png"));
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
