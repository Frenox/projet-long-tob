package io.github.ProjetLong.ZonesPeche;

import java.util.Map;

public class SousZoneTropiqueLagon extends SousZone {

    public SousZoneTropiqueLagon(boolean estDebloquee) {
        super(estDebloquee);
        setBackground("TropiqueLagon_Background");
    }
    public SousZoneTropiqueLagon(boolean estDebloquee, Map<Poisson, Float> inPoissonsDispo) {
        super(estDebloquee, inPoissonsDispo);
        setBackground("TropiqueLagon_Background");
    }
}
