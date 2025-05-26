package io.github.ProjetLong.ZonesPeche;

public class ZoneTropique extends Zone {

    public ZoneTropique() {
        ajouterSousZone(new SousZoneTropiqueLagon(false));
        ajouterSousZone(new SousZoneTropiqueBP(false));
        ajouterSousZone(new SousZoneTropiqueAbysses(false));
    }
}
