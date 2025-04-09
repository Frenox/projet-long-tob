package io.github.ProjetLong.ZonesPeche;

import java.util.Map;

public class SousZoneOceanAbysses extends SousZone {

    public SousZoneOceanAbysses(boolean estDebloquee) {
        super(estDebloquee);
        setBackground("OceanAbysses_Background");
    }
    public SousZoneOceanAbysses(boolean estDebloquee, Map<Poisson, Float> inPoissonsDispo) {
        super(estDebloquee, inPoissonsDispo);
        setBackground("OceanAbysses_Background");
    }
}
