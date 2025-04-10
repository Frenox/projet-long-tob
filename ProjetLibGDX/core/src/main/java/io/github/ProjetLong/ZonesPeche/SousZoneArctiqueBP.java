package io.github.ProjetLong.ZonesPeche;

import java.util.Map;

public class SousZoneArctiqueBP extends SousZone {

    public SousZoneArctiqueBP(boolean estDebloquee) {
        super(estDebloquee);
        setBackground("ArctiqueBP_Background");
    }
    public SousZoneArctiqueBP(boolean estDebloquee,  Map<Poisson, Float> inPoissonsDispo) {
        super(estDebloquee, inPoissonsDispo);
        setBackground("ArctiqueBP_Background");
    }
}
