package io.github.ProjetLong.ZonesPeche;

import java.util.Map;

public class SousZoneOceanHP extends SousZone {

    public SousZoneOceanHP(boolean estDebloquee) {
        super(estDebloquee);
        setBackground("OceanHP_Background");
    }
    public SousZoneOceanHP(boolean estDebloquee, Map<Poisson, Float> inPoissonsDispo) {
        super(estDebloquee, inPoissonsDispo);
        setBackground("OceanHP_Background");
    }
}
