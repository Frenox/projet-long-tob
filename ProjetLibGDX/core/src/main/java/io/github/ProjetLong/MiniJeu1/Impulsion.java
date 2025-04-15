package io.github.ProjetLong.MiniJeu1;

public class Impulsion {
    /**
     * Modélise une impulsion
     */
    private float duration;
    private float t;
    private float timeStep;
    private float max;

    public Impulsion(float duration, float timeStep, float max) {
        this.duration = duration;
        this.t = 0;
        this.timeStep = timeStep;
        this.max = max;
    }

    public float getImpulsionValue() {
        return max * (float) Math.sin(t / duration * Math.PI);
    }

    public void evolve() {
        t += timeStep;
    }

    public boolean isFinished() {
        return t > duration;
    }

}
