package main.java.io.github.ProjetLong.ZonesPeche;
import java.util.Map;

public class ZoneOcean extends Zone {

    public ZoneOcean() {
       ajouterSousZone(new SousZoneOceanBP(false));
       ajouterSousZone(new SousZoneOceanHP(false));
       ajouterSousZone(new SousZoneOceanAbysses(false));
    }
}
