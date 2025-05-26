package io.github.ProjetLong.Bateaux;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import io.github.ProjetLong.ZonesPeche.SousZoneCotePort;
import io.github.ProjetLong.equipementetmodule.CanneAPeche;
import io.github.ProjetLong.equipementetmodule.Stockage;

public class Barque extends Bateau {
    private Sprite boatSprite;

    public Barque() {
        // crée un bateau par défaut
        super(2);

        // Ajoute les modules par défaut de la barque
        addCannes(new CanneAPeche(1, new SousZoneCotePort()));
        addStockage(new Stockage(1));

        // Met les noms par défaut du module
        setName("Barque");
        setModeleName("Barque");

        // Gestion du sprite du bateau
        boatSprite = new Sprite(new Texture("boat1.png"));
        boatSprite.setPosition(227, 118);
        setEquipedCanne(getCannes().get(0));

        // Met le logo
        setLogo(new Texture("boat_logo1.png"));
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
