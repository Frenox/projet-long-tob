package io.github.ProjetLong.MiniJeu1;

public class Impulsion {
    /**
     * Modélise une impulsion
     */
    private float duration;
    private float t;
    private float timeStep;
    private float valeur;

    public Impulsion(float duration, float timeStep, float valeur) {
        this.duration = duration;
        this.t = 0;
        this.timeStep = timeStep;
        this.valeur = valeur;
        System.out.println(valeur);
    }

    public float getImpulsionValue() {

        return valeur * (float) Math.sin(Math.PI * (t / duration));
    }

    public void evolve() {
        t += timeStep;
    }

    public boolean isFinished() {
        return t > duration;
    }

}
