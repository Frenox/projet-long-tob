package io.github.ProjetLong.ZonesPeche;

import java.util.Map;

public class SousZoneArctiqueSousGlace extends SousZone {

    public SousZoneArctiqueSousGlace(boolean estDebloquee) {
        super(estDebloquee);
        setBackground("ArctiqueSousGlace_Background");
    }
    public SousZoneArctiqueSousGlace(boolean estDebloquee,  Map<Poisson, Float> inPoissonsDispo) {
        super(estDebloquee, inPoissonsDispo);
        setBackground("ArctiqueSousGlace_Background");
    }
}
