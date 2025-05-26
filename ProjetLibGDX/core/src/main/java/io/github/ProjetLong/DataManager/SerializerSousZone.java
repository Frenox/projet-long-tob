package io.github.ProjetLong.DataManager;

import java.util.HashMap;
import java.util.Map;

import io.github.ProjetLong.ZonesPeche.SousZone;
import io.github.ProjetLong.ZonesPeche.SousZoneArctiqueBP;
import io.github.ProjetLong.ZonesPeche.SousZoneArctiqueLac;
import io.github.ProjetLong.ZonesPeche.SousZoneArctiqueSousGlace;
import io.github.ProjetLong.ZonesPeche.SousZoneCoteBP;
import io.github.ProjetLong.ZonesPeche.SousZoneCoteFalaise;
import io.github.ProjetLong.ZonesPeche.SousZoneCotePort;
import io.github.ProjetLong.ZonesPeche.SousZoneOceanAbysses;
import io.github.ProjetLong.ZonesPeche.SousZoneOceanBP;
import io.github.ProjetLong.ZonesPeche.SousZoneOceanHP;
import io.github.ProjetLong.ZonesPeche.SousZoneTropiqueAbysses;
import io.github.ProjetLong.ZonesPeche.SousZoneTropiqueBP;
import io.github.ProjetLong.ZonesPeche.SousZoneTropiqueLagon;


public class SerializerSousZone extends SerializerBaseClass<Class<? extends SousZone>> { 

    private static final Map<String, Class<? extends SousZone>> sousZoneCodes;

    static {
        sousZoneCodes = new HashMap<>();
        
        sousZoneCodes.put(SousZoneCotePort.class.getName(), SousZoneCotePort.class);
        sousZoneCodes.put(SousZoneCoteFalaise.class.getName(), SousZoneCoteFalaise.class);
        sousZoneCodes.put(SousZoneCoteBP.class.getName(), SousZoneCoteBP.class);

        sousZoneCodes.put(SousZoneOceanBP.class.getName(), SousZoneOceanBP.class);
        sousZoneCodes.put(SousZoneOceanHP.class.getName(), SousZoneOceanHP.class);
        sousZoneCodes.put(SousZoneOceanAbysses.class.getName(), SousZoneOceanAbysses.class);

        sousZoneCodes.put(SousZoneArctiqueBP.class.getName(), SousZoneArctiqueBP.class);
        sousZoneCodes.put(SousZoneArctiqueSousGlace.class.getName(), SousZoneArctiqueSousGlace.class);
        sousZoneCodes.put(SousZoneArctiqueLac.class.getName(), SousZoneArctiqueLac.class);

        sousZoneCodes.put(SousZoneTropiqueLagon.class.getName(), SousZoneTropiqueLagon.class);
        sousZoneCodes.put(SousZoneTropiqueBP.class.getName(), SousZoneTropiqueBP.class);
        sousZoneCodes.put(SousZoneTropiqueAbysses.class.getName(), SousZoneTropiqueAbysses.class);     
    }
    
    /**
     * Serialise un objet de type SousZone sous la forme :
     * [NOM DE LA CLASSE]
     * 
     * @param element (SousZone) : Objet a serialiser
     * @param compositionLevel (int) : Niveau de composition de l'objet
     * @return _______ (String) : Chaine de caractere serialisee
     */
    @Override
    public String serializeElement(Class<? extends SousZone> element, int compositionLevel) {
        if (element == null) {
            return "";
        }
        return element.getName();
        
    }

    /**
     * Deserialise un objet de type SousZone etant sous la forme :
     * [NOM DE LA CLASSE]
     * 
     * @param element (String) : Chaine de caractere serialisee
     * @param compositionLevel (int) : Niveau de composition de l'objet
     * @return _______ (SousZone) : Objet deserialise
     */
    @Override
    public Class<? extends SousZone> deserializeElement(String element, int compositionLevel) {
        try {
            return sousZoneCodes.get(element);
            
        } catch (Exception e) {
            return null;
        }
    }

}
