package io.github.ProjetLong.DataManager;

import io.github.ProjetLong.ZonesPeche.SousZone;
import io.github.ProjetLong.equipementetmodule.CanneAPeche;

public class SerializerCanneAPeche extends SerializerBaseClass<CanneAPeche> {

    private final SerializerSousZone sousZoneSerializer;

    public SerializerCanneAPeche() {
        sousZoneSerializer = new SerializerSousZone();
    }

    /**
     * Serialise un objet de type CanneAPeche sous la forme :
     * Zone#-1-#Niveau
     * 
     * @param element          (CanneAPeche) : Objet a serialiser
     * @param compositionLevel (int) : Niveau de composition de l'objet
     * @return _______ (String) : Chaine de caractere serialisee
     */
    @Override
    public String serializeElement(CanneAPeche element, int compositionLevel) {
        if (element == null) {
            return "";
        }

        String separateur = getSeparateur(compositionLevel);
        String zoneString = sousZoneSerializer.serializeElement(element.getZone().getClass(), compositionLevel + 1);
        return zoneString + separateur + element.getNiveau();
    }

    /**
     * Deserialise un objet de type CanneAPeche etant sous la forme :
     * Zone#-1-#Niveau
     * 
     * @param element          (String) : Chaine de caractere serialisee
     * @param compositionLevel (int) : Niveau de composition de l'objet
     * @return _______ (CanneAPeche) : Objet deserialise
     */
    @Override
    public CanneAPeche deserializeElement(String element, int compositionLevel) {
        try {
            String[] canneData = element.split(getSeparateur(compositionLevel));
            Class<? extends SousZone> zoneClass = sousZoneSerializer.deserializeElement(canneData[0], compositionLevel); // Recupere
                                                                                                                         // la
                                                                                                                         // classe
                                                                                                                         // de
                                                                                                                         // la
                                                                                                                         // sous
                                                                                                                         // zone
            SousZone zone = zoneClass.getDeclaredConstructor(boolean.class).newInstance(true); // Instancie la sous zone
                                                                                               // depuis la classe
                                                                                               // variable
            CanneAPeche newCanne = new CanneAPeche(Integer.parseInt(canneData[1]), zone);

            return newCanne;
        } catch (Exception e) {
            return null;
        }
    }

}
