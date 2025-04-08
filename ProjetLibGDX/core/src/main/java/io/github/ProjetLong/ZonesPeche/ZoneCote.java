package io.github.ProjetLong.ZonesPeche;

import java.util.Map;

public class ZoneCote extends Zone {

    public ZoneCote() {
        ajouterSousZone(new SousZoneCotePort(true));
        ajouterSousZone(new SousZoneCoteBP(false));
        ajouterSousZone(new SousZoneCoteFalaise(false));
    }
}
