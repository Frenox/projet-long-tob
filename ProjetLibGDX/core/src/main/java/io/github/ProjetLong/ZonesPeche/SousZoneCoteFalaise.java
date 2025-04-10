package io.github.ProjetLong.ZonesPeche;

import java.util.Map;

public class SousZoneCoteFalaise extends SousZone {

    public SousZoneCoteFalaise(boolean estDebloquee) {
        super(estDebloquee);
        setBackground("CoteFalaise_Background");
    }
    public SousZoneCoteFalaise(boolean estDebloquee,  Map<Poisson, Float> inPoissonsDispo) {
        super(estDebloquee, inPoissonsDispo);
        setBackground("CoteFalaise_Background");
    }
}
