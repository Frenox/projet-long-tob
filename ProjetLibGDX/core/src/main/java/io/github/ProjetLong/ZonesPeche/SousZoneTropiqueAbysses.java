package io.github.ProjetLong.ZonesPeche;

import java.util.Map;

public class SousZoneTropiqueAbysses extends SousZone {

    public SousZoneTropiqueAbysses(boolean estDebloquee) {
        super(estDebloquee);
        setBackground("TropiqueAbysses_Background");
    }
    public SousZoneTropiqueAbysses(boolean estDebloquee, Map<Poisson, Float> inPoissonsDispo) {
        super(estDebloquee, inPoissonsDispo);
        setBackground("TropiqueAbysses_Background");
    }
}
