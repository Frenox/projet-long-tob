package main.java.io.github.ProjetLong.ZonesPeche;
import java.util.Map;

public class ZoneCote extends Zone {

    public ZoneCote() {
       ajouterSousZone(new SousZoneCotePort());
       ajouterSousZone(new SousZoneCoteBP());
       ajouterSousZone(new SousZoneCoteFalaise());
    }
}
