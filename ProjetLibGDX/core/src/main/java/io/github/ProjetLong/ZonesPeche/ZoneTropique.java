package main.java.io.github.ProjetLong.ZonesPeche;
import java.util.Map;

public class ZoneTropique extends Zone {

    public ZoneTropique() {
       ajouterSousZone(new SousZoneTropiqueLagon(false));
       ajouterSousZone(new SousZoneTropiqueBP(false));
       ajouterSousZone(new SousZoneTropiqueAbysses(false));
    }
}
