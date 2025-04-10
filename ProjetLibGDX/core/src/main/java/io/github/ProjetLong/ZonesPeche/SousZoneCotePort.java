package io.github.ProjetLong.ZonesPeche;

public class SousZoneCotePort extends SousZone {

    public SousZoneCotePort(boolean estDebloquee) {
        super(estDebloquee);
    }

    public SousZoneCotePort() {
        super(true);
        addPoissonDispo(new Poisson(1, 0), 28.5f);
        addPoissonDispo(new Poisson(1, 1), 1.5f);
        addPoissonDispo(new Poisson(2, 0), 38);
        addPoissonDispo(new Poisson(2, 1), 2);
        addPoissonDispo(new Poisson(3, 0), 28.5f);
        addPoissonDispo(new Poisson(3, 1), 1.5f);
    }
}
