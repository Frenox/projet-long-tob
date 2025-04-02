package main.java.io.github.ProjetLong.ZonesPeche;
import java.util.Map;

public class ZoneArctique extends Zone {

    public ZoneArctique() {
       ajouterSousZone(new SousZoneArctiqueBP());
       ajouterSousZone(new SousZoneArctiqueSousGlace());
       ajouterSousZone(new SousZoneArctiqueLac());
    }
}
