package io.github.ProjetLong.ZonesPeche;

import java.util.Map;

public class SousZoneCotePort extends SousZone {

    public SousZoneCotePort(boolean estDebloquee) {
        super(estDebloquee);
        setBackground("CotePort_Background");
        addPoissonDispo(new Poisson(1, 0), 28.5f);
        addPoissonDispo(new Poisson(1, 1), 1.5f);
        addPoissonDispo(new Poisson(2, 0), 57f);
        addPoissonDispo(new Poisson(2, 1), 3f);
        addPoissonDispo(new Poisson(3, 0), 14.25f);
        addPoissonDispo(new Poisson(3, 1), 0.75f);
    }

    public SousZoneCotePort(boolean estDebloquee, Map<Poisson, Float> inPoissonsDispo) {
        super(estDebloquee, inPoissonsDispo);
        setBackground("CotePort_Background");
    }

    public SousZoneCotePort() {
        super(true);
        addPoissonDispo(new Poisson(1, 0), 28.5f);
        addPoissonDispo(new Poisson(1, 1), 1.5f);
        addPoissonDispo(new Poisson(2, 0), 57f);
        addPoissonDispo(new Poisson(2, 1), 3f);
        addPoissonDispo(new Poisson(3, 0), 14.25f);
        addPoissonDispo(new Poisson(3, 1), 0.75f);
    }
}
