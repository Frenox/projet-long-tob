package io.github.ProjetLong.ZonesPeche;

public class ZoneOcean extends Zone {

    public ZoneOcean() {
        ajouterSousZone(new SousZoneOceanBP(false));
        ajouterSousZone(new SousZoneOceanHP(false));
        ajouterSousZone(new SousZoneOceanAbysses(false));
    }
}
