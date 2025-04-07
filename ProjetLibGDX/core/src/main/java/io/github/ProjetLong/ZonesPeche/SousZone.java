package main.java.io.github.ProjetLong.ZonesPeche;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;

public abstract class SousZone {
    
    protected boolean estDebloquee;
    protected Map<Poisson, Float> poissonsDispo = new HashMap<>();

    public SousZone(boolean estDebloquee) {
        this.estDebloquee = estDebloquee;
    }

    /**Renvoie un poisson aleatoire dans la liste de poissons disponibles
     * en ponderant par les probabilites de chaque poisson
     */
    public Poisson getRandPoisson() {
        float probaTotale = 0.0f;
        for (float probaPoisson : poissonsDispo.values()) {
            probaTotale += probaPoisson;
        }

        float randomVal = new Random().nextFloat() * probaTotale;
        float probaCumul = 0.0f;

        for (Map.Entry<Poisson, Float> entry : poissonsDispo.entrySet()) {
            probaCumul += entry.getValue();
            if (randomVal <= probaCumul) {
                return entry.getKey();
            }
        }
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
