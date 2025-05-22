package io.github.ProjetLong;

import com.badlogic.gdx.graphics.Texture;

import io.github.ProjetLong.ZonesPeche.Poisson;
import io.github.ProjetLong.ZonesPeche.SousZone;

public class CanneAPeche implements ModuleBateau {
    private int Niveau;
    private SousZone zone;
    private Texture canneAPechTexture;

    public CanneAPeche(int Niveau, SousZone zone) {
        this.zone = zone;
        if (Niveau == 1) {
            canneAPechTexture = new Texture("fishing_rod_1_lvl1.png");
        } else {
            canneAPechTexture = new Texture("cregut.png");
        }
    }

    public Texture getTexture() {
        return canneAPechTexture;
    }

    @Override
    public int getNiveau() {
        return this.Niveau;
    }

    public Poisson getRandPoisson() {

        return null;
    }

    public SousZone getZone() {
        return zone;
    }
}
