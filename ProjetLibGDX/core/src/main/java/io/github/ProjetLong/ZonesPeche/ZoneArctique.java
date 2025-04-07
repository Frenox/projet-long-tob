package main.java.io.github.ProjetLong.ZonesPeche;
import java.util.Map;

public class ZoneArctique extends Zone {

    public ZoneArctique() {
       ajouterSousZone(new SousZoneArctiqueBP(false));
       ajouterSousZone(new SousZoneArctiqueSousGlace(false));
       ajouterSousZone(new SousZoneArctiqueLac(false));
    }
}
