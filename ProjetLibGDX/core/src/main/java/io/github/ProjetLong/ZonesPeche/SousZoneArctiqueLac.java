package io.github.ProjetLong.ZonesPeche;

import java.util.Map;

public class SousZoneArctiqueLac extends SousZone {

    public SousZoneArctiqueLac(boolean estDebloquee) {
        super(estDebloquee);
        setBackground("ArctiqueLac_Background");

    }
    public SousZoneArctiqueLac(boolean estDebloquee,  Map<Poisson, Float> inPoissonsDispo) {
        super(estDebloquee, inPoissonsDispo);
        setBackground("ArctiqueLac_Background");

    }
}
