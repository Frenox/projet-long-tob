package io.github.ProjetLong.equipementetmodule;

// Enumération qui définit les différents types d'équipement et leurs caractéristiques de base
public enum EquipementType {
    BOAT("Barque", 20, 500), // 120 secondes, 500 unités de coût
    SAIL("Voile", 60, 200), // 60 secondes, 200 unités de coût
    STORAGE("Stockage", 180, 800); // 180 secondes, 800 unités de coût

    private final String name;
    private final double baseTime; // temps de construction de base (en secondes)
    private final int baseCost; // coût de construction de base

    EquipementType(String name, float baseTime, int baseCost) {
        this.name = name;
        this.baseTime = baseTime;
        this.baseCost = baseCost;
    }

    public String getName() {
        return name;
    }

    public double getBaseTime() {
        return baseTime;
    }

    public int getBaseCost() {
        return baseCost;
    }
}
