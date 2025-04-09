package io.github.ProjetLong.ZonesPeche;

import java.util.ArrayList;
import java.util.List;

public abstract class Zone {
    /** Sous zones rattachees a cette zone */
    protected List<SousZone> sousZones = new ArrayList<>();

    protected void ajouterSousZone(SousZone inZone) {
        sousZones.add(inZone);
    }

    public List<SousZone> getSousZones() {
        return this.sousZones;
    }

    /**
     * Renvoie si la zone est debloquee (au moins une sous zone de debloquee)
     * 
     * @return ____ (Boolean) : La zone est-elle debloquee
     */
    public Boolean estDebloquee() {
        boolean sousZoneDebloquee = false;
        for (SousZone sousZone : this.sousZones) {
            if (sousZone.getEstDebloquee()) {
                sousZoneDebloquee = true;
            }
        }
        return sousZoneDebloquee;
    }

    /**
     * Debloque une Sous Zone de la zone actuelle
     * (ne fait rien si la Sous Zone n'existe pas)
     * 
     * @param inZone (SousZone) : Sous Zone a debloquer
     */
    public void debloquerSousZone(Integer zoneId) {
        if (this.sousZones.size() > zoneId) {
            this.sousZones.get(zoneId).debloquer();
        }
    }
}
