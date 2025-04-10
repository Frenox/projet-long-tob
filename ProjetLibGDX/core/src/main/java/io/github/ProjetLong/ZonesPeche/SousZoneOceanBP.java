package io.github.ProjetLong.ZonesPeche;

import java.util.Map;

public class SousZoneOceanBP extends SousZone {

    public SousZoneOceanBP(boolean estDebloquee) {
        super(estDebloquee);
        setBackground("OceanBP_Background");
    }
    public SousZoneOceanBP(boolean estDebloquee, Map<Poisson, Float> inPoissonsDispo) {
        super(estDebloquee, inPoissonsDispo);
        setBackground("OceanBP_Background");
    }
}
