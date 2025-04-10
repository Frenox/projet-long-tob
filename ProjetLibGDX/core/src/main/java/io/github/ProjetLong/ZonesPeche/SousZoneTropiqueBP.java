package io.github.ProjetLong.ZonesPeche;

import java.util.Map;

public class SousZoneTropiqueBP extends SousZone {

    public SousZoneTropiqueBP(boolean estDebloquee) {
        super(estDebloquee);
        setBackground("TropiqueBP_Background");
    }
    public SousZoneTropiqueBP(boolean estDebloquee, Map<Poisson, Float> inPoissonsDispo) {
        super(estDebloquee, inPoissonsDispo);
        setBackground("TropiqueBP_Background");
    }
}
