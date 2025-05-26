package io.github.ProjetLong.DataManager;

import io.github.ProjetLong.ZonesPeche.Poisson;


public class SerializerPoisson extends SerializerBaseClass<Poisson> {
    
    /**
     * Serialise un objet de type Poisson sous la forme :
     * ID#-1-#Rarete
     * 
     * @param element (Poisson) : Objet a serialiser
     * @param compositionLevel (int) : Niveau de composition de l'objet
     * @return _______ (String) : Chaine de caractere serialisee
     */
    @Override
    public String serializeElement(Poisson element, int compositionLevel) {
        if (element == null) {
            return "";
        }
        
        return element.getId() + getSeparateur(compositionLevel) + element.getRarete();
    }

    /**
     * Deserialise un objet de type Poisson etant sous la forme :
     * ID#-1-#Rarete
     * 
     * @param element (String) : Chaine de caractere serialisee
     * @param compositionLevel (int) : Niveau de composition de l'objet
     * @return _______ (Poisson) : Objet deserialise
     */
    @Override
    public Poisson deserializeElement(String element, int compositionLevel) {
        try {
            String[] poissonData = element.split(getSeparateur(compositionLevel));
            return new Poisson(Integer.parseInt(poissonData[0]), Integer.parseInt(poissonData[1]));
        } catch (Exception e) {
            return null;
        }
    }

}
