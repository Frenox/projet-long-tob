package main.java.io.github.ProjetLong.ZonesPeche;
import java.util.Map;

public class ZoneTropique extends Zone {

    public ZoneTropique() {
       ajouterSousZone(new SousZoneTropiqueLagon());
       ajouterSousZone(new SousZoneTropiqueBP());
       ajouterSousZone(new SousZoneTropiqueAbysses());
    }
}
