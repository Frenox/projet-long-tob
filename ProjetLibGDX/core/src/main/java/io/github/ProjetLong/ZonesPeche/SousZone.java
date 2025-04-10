package io.github.ProjetLong.ZonesPeche;

import java.util.Map;
import java.util.Random;
import java.util.HashMap;

public abstract class SousZone {

    protected boolean estDebloquee;
    private Map<Poisson, Float> poissonsDispo = new HashMap<>();

    public SousZone(boolean estDebloquee) {
        this.estDebloquee = estDebloquee;
    }

    /**
     * Renvoie un nouveau poisson aleatoire dans la liste de poissons disponibles
     * en ponderant par les probabilites de chaque poisson
     * PROBAS ET POISSONS A SETUP DANS CONSTRUCTEURS DES SOUS ZONES
     * Le calcul de la taille du poisson (autre facteur random en plus de rarete)
     * est faite dans la classe poisson
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
                return new Poisson(entry.getKey().getId(), entry.getKey().getRarete(), true);
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

    public void addPoissonDispo(Poisson poisson, float proba) {
        poissonsDispo.put(poisson, proba);
    }
}
