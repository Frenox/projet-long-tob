package io.github.ProjetLong;

// Classe qui représente un ordre de construction dans la file d'attente
public class ConstructionOrder {
    private final EquipementType type;
    private final double totalTime; // temps total (après réduction) alloué à la construction
    private double remainingTime; // temps restant en secondes

    public ConstructionOrder(EquipementType type, int effectiveTime) {
        this.type = type;
        this.totalTime = effectiveTime;
        this.remainingTime = effectiveTime;
    }

    public EquipementType getType() {
        return type;
    }

    public double getRemainingTime() {
        return remainingTime;
    }

    // Décrémente le temps restant
    public void decrementTime() {
        remainingTime -= (1f / 60f);
        if (remainingTime < 0) {
            remainingTime = 0;
        }
    }

    // Vérifie si la construction est terminée
    public boolean isCompleted() {
        return remainingTime <= 0;
    }

    @Override
    public String toString() {
        return type.getName()
                + " - Temps restant : " + (int) remainingTime + " s";
    }
}
