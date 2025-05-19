package io.github.ProjetLong.ZonesPeche;

import java.util.Map;

public class SousZoneCoteBP extends SousZone {

    public SousZoneCoteBP(boolean estDebloquee) {
        super(estDebloquee);
        setBackground("CoteBP_Background");
    }
    public SousZoneCoteBP(boolean estDebloquee,  Map<Poisson, Float> inPoissonsDispo) {
        super(estDebloquee, inPoissonsDispo);
        setBackground("CoteBP_Background");
    }
}
