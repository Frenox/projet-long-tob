package io.github.ProjetLong.ZonesPeche;

import java.util.Map;

public class SousZoneCotePort extends SousZone {

    public SousZoneCotePort(boolean estDebloquee) {
        super(estDebloquee);
        setBackground("CotePort_Background");
    }
    public SousZoneCotePort(boolean estDebloquee,  Map<Poisson, Float> inPoissonsDispo) {
        super(estDebloquee, inPoissonsDispo);
        setBackground("CotePort_Background");
    }
}
