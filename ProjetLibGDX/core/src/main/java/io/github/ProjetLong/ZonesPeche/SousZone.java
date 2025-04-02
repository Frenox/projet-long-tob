package main.java.io.github.ProjetLong.ZonesPeche;
import java.util.Map;
import java.util.HashMap;

public abstract class SousZone {
    
    protected boolean estDebloquee;
    protected Map<Poisson, Float> poissonsDispo = new HashMap<>();


    public Poisson getRandPoisson() {
        return null;
    }
    public boolean getEstDebloquee() {
        return this.estDebloquee;
    }
    
    /**
     * Debloque la sous zone
     */
    public void debloquer() {
        this.estDebloquee = true;
    }
}
