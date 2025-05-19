package io.github.ProjetLong.DataManager;

import io.github.ProjetLong.CanneAPeche;
import io.github.ProjetLong.ZonesPeche.SousZone;


public class SerializerCanneAPeche extends SerializerBaseClass<CanneAPeche> {
    
    
    private final SerializerSousZone sousZoneSerializer;

    public SerializerCanneAPeche() {
        sousZoneSerializer = new SerializerSousZone();
    }

    @Override
    public String serializeElement(CanneAPeche element, int compositionLevel) {
        if (element == null) {
            return "";
        }
        
        String separateur = getSeparateur(compositionLevel);
        String zoneString = sousZoneSerializer.serializeElement(element.getZone().getClass(), compositionLevel+1);
        return zoneString + separateur + element.getNiveau();
    }
    @Override
    public CanneAPeche deserializeElement(String element, int compositionLevel) {
        try {
            String[] canneData = element.split(getSeparateur(compositionLevel));
            Class<? extends SousZone> zoneClass = sousZoneSerializer.deserializeElement(canneData[0], compositionLevel); // Recupere la classe de la sous zone
            SousZone zone = zoneClass.getDeclaredConstructor(boolean.class).newInstance(true); // Instancie la sous zone depuis la classe variable
            CanneAPeche newCanne = new CanneAPeche(Integer.parseInt(canneData[1]), zone);
    
            return newCanne;
        } catch (Exception e) {
            return null;
        }
    }

}
