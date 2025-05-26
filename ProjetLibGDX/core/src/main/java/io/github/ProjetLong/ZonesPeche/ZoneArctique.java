package io.github.ProjetLong.ZonesPeche;

public class ZoneArctique extends Zone {

    public ZoneArctique() {
        ajouterSousZone(new SousZoneArctiqueBP(false));
        ajouterSousZone(new SousZoneArctiqueSousGlace(false));
        ajouterSousZone(new SousZoneArctiqueLac(false));
    }
}
